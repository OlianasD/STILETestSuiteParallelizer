package org.olianda.treeparallelizer.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.jgrapht.Graph;
import org.json.JSONObject;
import org.mb.tedd.algorithm.execution.TestResult;
import org.mb.tedd.graph.GraphEdge;
import org.mb.tedd.graph.dot.importgraph.GraphImporter;
import org.mb.tedd.graph.GraphNode;
import org.olianda.treeparallelizer.docker.DockerManager;
import org.olianda.treeparallelizer.execution.TreeExecutor;
import org.olianda.treeparallelizer.results.PrefixTreeResultCollector;

import com.google.common.io.Files;

import org.mb.tedd.utils.ExecutionTime;
import org.mb.tedd.utils.Properties;
import org.olianda.treeparallelizer.execution.WarrantedScheduleExtractor;
import org.olianda.treeparallelizer.execution.testcases.TestProcessManager;
import org.olianda.treeparallelizer.graphviz.GraphvizManager;
import org.olianda.treeparallelizer.prefixtree.PrefixTree;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;
import org.olianda.treeparallelizer.prefixtree.utils.ImportExportUtils;

public class Main {
	public static String cfgFilesPath = "/home/anonymous/workspace/FSE19-submission-material/config_files";
	public static String resPath = "src/main/resources";
	public static String timesPath = "/home/anonymous/workspace/FSE19-submission-material/treeparallelizer/src/main/resources/testNGtimes/meantimes/json/";
	public static String treePath = "src/main/resources/trees/";
	public static HashMap<String, String> appConfig;
	public static void copyTestSuiteCfg(String app) throws IOException {
		File src = new File(cfgFilesPath+"/"+app+".properties");
		File dest = new File(resPath+"/app.properties");
		Files.copy(src, dest);
	}
	
	public static void loadAppCfg(String app) {
		JSONObject cfg = ImportExportUtils.loadJSONObject(resPath+"/app_config/docker/"+app+".json");
		appConfig = new HashMap(cfg.toMap());
		
	}
	
	
	public static void main(String[] args) {
		String app = args[0];
		String mode = args[1];
		try {
			copyTestSuiteCfg(app);
		} catch (IOException e) {
			System.err.println("Cannot copy app configuration file");
			e.printStackTrace();
			System.exit(1);
		}
		Properties.getInstance().createPropertiesFile();
		loadAppCfg(app);
		//generate warranted paths, build radix tree and run parallelization
    	if(mode.equals("--extractAndRun")) {
    		PrefixTree rt = buildRadixTreeFromGraph(app, appConfig.get("graphPath"));
    		runTreeParallelization(rt, appConfig, app);
    		String ts = getCurrentTimestamp();
    		String dotPath = "results/dot/"+app+"_"+ts+".dot";
			ImportExportUtils.exportTree(rt, dotPath);
    		JSONObject jsonTree = rt.toJSON();
    		String jsonPath = "results/json/"+app+"_"+ts+".json";
			ImportExportUtils.stringToFile(jsonPath, jsonTree.toString());
			GraphvizManager gv = new GraphvizManager();
			BufferedImage graphPng = gv.dotToPng(dotPath);
			gv.saveImageToFile(graphPng, "results/png/"+app+"_"+ts+".png", "png");
    	}
    	//generate warranted paths, build radix tree and export it in DOT
    	else if(mode.equals("-re")) {
    		PrefixTree rt = buildRadixTreeFromGraph(app, appConfig.get("graphPath"));
    		ImportExportUtils.exportTree(rt, treePath+"/dot/"+app+"_"+getCurrentTimestamp()+".dot");
    	}
    	//generate and export warranted paths
    	else if(mode.equals("-we")) {
    		List<Set<GraphNode<String>>> warranteds = loadGraphAndGetWarranteds();
    		ImportExportUtils.exportWarranteds(warranteds, app);
    	}
    	//load a tree from JSON, run parallelization and export tree with results and actual times
    	else if(mode.equals("--runFromJSON")) {
    		JSONObject jsonTree = ImportExportUtils.loadJSONObject("file://"+treePath+"json/"+app+".json");
    		PrefixTree rt = new PrefixTree(jsonTree);
    		runTreeParallelization(rt, appConfig, app);
    		jsonTree = rt.toJSON();
    		ImportExportUtils.stringToFile(treePath+"timed/"+app+".json", jsonTree.toString());
    		System.exit(0);
    	}
    	else if(mode.equals("--countEdges")) {
    		Graph<GraphNode<String>, GraphEdge> graph = loadGraph(appConfig.get("graphPath"));
    		PrefixTree rt = buildRadixTreeFromGraph(app, appConfig.get("graphPath"));
    		System.out.println("Nodes in graph:"+graph.vertexSet().size());
    		System.out.println("Edges in graph:"+graph.edgeSet().size());
    		System.out.println("Nodes in the radix tree: "+(rt.getNodeCount()-1));
    	}
    	else {
    		System.out.println(System.getProperty("user.dir"));
    		System.err.println("Unrecognized mode "+mode);
    	}
    	
	}

	public static List<Set<GraphNode<String>>> loadGraphAndGetWarranteds() {
		Graph<GraphNode<String>, GraphEdge> graph = loadGraph(appConfig.get("graphPath"));
		List<Set<GraphNode<String>>> warranteds = WarrantedScheduleExtractor.generateWarrantedPathsForZeroInDegreeNodes(graph);
		return warranteds;
	}

	public static PrefixTree buildRadixTreeFromGraph(String app, String graphPath) {
		Graph<GraphNode<String>, GraphEdge> graph = loadGraph(graphPath);
		List<Set<GraphNode<String>>> warranteds = WarrantedScheduleExtractor.generateWarrantedPathsForZeroInDegreeNodes(graph);
		PrefixTree rt = buildRadixTree(warranteds);
		return rt;
	}

	public static Graph<GraphNode<String>, GraphEdge> loadGraph(String path) {
		Graph<GraphNode<String>, GraphEdge> graph;
		GraphImporter graphImporter = new GraphImporter();
		graph = graphImporter.importGraph(path);
		return graph;
	}

	public static void runTreeParallelization(PrefixTree rt, HashMap<String, String> appCfg, String appName) {
    	DockerManager docker = new DockerManager(appCfg.get("dockerHome"), appCfg.get("dockerEntryPoint"), 3100, 3400, 80, 3306);
    	TestProcessManager processManager = new TestProcessManager();
    	System.out.println("Pre-starting "+(rt.getNodeCount()-1)+" test processes...");
    	long preStartStart = System.currentTimeMillis();
    	processManager.startProcesses(rt.getNodeCount()-1, appName);
    	long preStartEnd = System.currentTimeMillis();
    	TreeExecutor executionVisit = new TreeExecutor(docker, appCfg.get("dockerImage"), appName, processManager);
    	long start = System.currentTimeMillis();
    	try {
    		executionVisit.executeTree(rt, true);
    	} catch(Exception e) {
    		System.err.println("!! EXCEPTION IN MAIN !!");
    		e.printStackTrace();
    	} finally {
    		long end = System.currentTimeMillis();
        	System.out.println("Time spent: "+new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((end - start))));
        	System.out.println("Created containers: "+docker.getCreatedContainersCount());
        	System.out.println("Time for pre-starting test processes: "+new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((preStartEnd - preStartStart))));
        	PrefixTreeResultCollector rc = new PrefixTreeResultCollector(rt);
        	rc.printResults();
        	docker.removeAllCreatedContainers();
        	docker.removeAllCreatedImages();
        	//rc.saveToCsv(appName+".csv");
    	}
	}
	

	private static PrefixTree buildRadixTree(List<Set<GraphNode<String>>> warranteds) {
		PrefixTree rt = new PrefixTree();
    	for(Set<GraphNode<String>> path : warranteds) {
    		rt.insert(path);
    	}
		return rt;
	}
	
	public static String getCurrentTimestamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
}
