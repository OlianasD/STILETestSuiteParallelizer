package org.olianda.treeparallelizer.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.jgrapht.Graph;
import org.json.JSONObject;
import org.mb.tedd.algorithm.execution.TestResult;
import org.mb.tedd.graph.GraphEdge;
import org.mb.tedd.graph.dot.importgraph.GraphImporter;
import org.mb.tedd.graph.GraphNode;
import org.olianda.treeparallelizer.docker.BrowserContainerManager;
import org.olianda.treeparallelizer.docker.DockerContainer;
import org.olianda.treeparallelizer.docker.DockerManager;
import org.olianda.treeparallelizer.execution.TreeExecutor;
import org.olianda.treeparallelizer.results.PrefixTreeResultCollector;
import org.olianda.treeparallelizer.times.TimedWarrantedSchedule;
import org.olianda.treeparallelizer.times.TreeTimeManager;

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
	public static String cfgFilesPath = "../config_files";
	public static String resPath = "src/main/resources";
	public static String timesPath = "src/main/resources/testNGtimes/meantimes/json/";
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
	
	
	public static void main(String[] args) throws FactoryConfigurationError, XMLStreamException, IOException {
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
    	else if(mode.equals("--runPrior")) {
    		Map<String, Float> times = ImportExportUtils.getTestTimesFromXml(app, "sequentials_4core/");
    		List<TimedWarrantedSchedule> timedWtdsFromGraph = TreeTimeManager.generateTimedWarranteds(app, times);
    		timedWtdsFromGraph.sort(Comparator.comparing(TimedWarrantedSchedule::getTime).reversed());
    		int i=1;
    		for(TimedWarrantedSchedule warranted : timedWtdsFromGraph) {
    			warranted.setPriority(i);
    			i++;
    		}
    		PrefixTree priorityTree = new PrefixTree();
    		for(TimedWarrantedSchedule warranted : timedWtdsFromGraph) {
    			priorityTree.insert(warranted);
    		}
    		TreeTimeManager.sortChildren(priorityTree);
    		runTreeParallelization(priorityTree, appConfig, app);
    		String ts = getCurrentTimestamp();
    		String dotPath = "results/dot/"+app+"_"+ts+".dot";
			ImportExportUtils.exportTree(priorityTree, dotPath);
    		JSONObject jsonTree = priorityTree.toJSON();
    		String jsonPath = "results/json/"+app+"_"+ts+".json";
    		GraphvizManager gv = new GraphvizManager();
			BufferedImage graphPng = gv.dotToPng(dotPath);
			gv.saveImageToFile(graphPng, "results/png/"+app+"_"+ts+".png", "png");
			ImportExportUtils.stringToFile(jsonPath, jsonTree.toString());
    	}
    	else if(mode.equals("--printPrior")) {
    		Map<String, Float> times = ImportExportUtils.getTestTimesFromXml(app, "sequentials_4core/");
    		List<TimedWarrantedSchedule> timedWtdsFromGraph = TreeTimeManager.generateTimedWarranteds(app, times);
    		timedWtdsFromGraph.sort(Comparator.comparing(TimedWarrantedSchedule::getTime).reversed());
    		int i=1;
    		for(TimedWarrantedSchedule warranted : timedWtdsFromGraph) {
    			warranted.setPriority(i);
    			i++;
    		}
    		PrefixTree priorityTree = new PrefixTree();
    		for(TimedWarrantedSchedule warranted : timedWtdsFromGraph) {
    			priorityTree.insert(warranted);
    		}
    		TreeTimeManager.sortChildren(priorityTree);
    		ImportExportUtils.exportPrioritizedTree(priorityTree, app+".dot");
    		GraphvizManager gv = new GraphvizManager();
			BufferedImage graphPng = gv.dotToPng(app+".dot");
			gv.saveImageToFile(graphPng, app+".png", "png");
    		
    	}
    	//generate warranted paths, build radix tree and export it in DOT
    	else if(mode.equals("-re")) {
    		System.out.println(appConfig.get("graphPath"));
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
    	else if(mode.equals("--genSingleWtd")) {
    		String target = args[2];
    		Graph<GraphNode<String>, GraphEdge> graph = loadGraph(appConfig.get("graphPath"));
    		Set<GraphNode<String>> wtd = WarrantedScheduleExtractor.getWarrantedForSpecificNode(graph, target);
    		StringBuilder wtdStr = new StringBuilder();
    		String lastTc = "";
    		for(GraphNode<String> node : wtd) {
    			wtdStr.append("tests."+node.getTestCase()+",");
    			lastTc = node.getTestCase();
    		}
    		wtdStr.deleteCharAt(wtdStr.length()-1);
    		ImportExportUtils.stringToFile(lastTc+".wtd", wtdStr.toString());
    		System.out.println(wtd);
    	}
    	else if(mode.equals("--genAllWtds")) {
    		Graph<GraphNode<String>, GraphEdge> graph = loadGraph(appConfig.get("graphPath"));
    		List<Set<GraphNode<String>>> warranteds = WarrantedScheduleExtractor.generateWarrantedPathsForZeroInDegreeNodes(graph);
    		for(Set<GraphNode<String>> wtd : warranteds) {
    			StringBuilder wtdStr = new StringBuilder();
        		String lastTc = "";
    			for(GraphNode<String> node : wtd) {
        			wtdStr.append("tests."+node.getTestCase()+",");
        			lastTc = node.getTestCase();
        		}
    			wtdStr.deleteCharAt(wtdStr.length()-1);
        		ImportExportUtils.stringToFile(lastTc+".wtd", wtdStr.toString());
    		}
    	}
    	else if (mode.equals("--genReachingWtds")) {
    		Graph<GraphNode<String>, GraphEdge> graph = loadGraph(appConfig.get("graphPath"));
    		List<Set<GraphNode<String>>> warranteds = WarrantedScheduleExtractor.generateWarrantedPathsForAllNodes(graph);
    		int total = 0;
    		for(Set<GraphNode<String>> wtd : warranteds) {
    			StringBuilder wtdStr = new StringBuilder();
        		String lastTc = "";
        		int i = 0;
        		if(wtd.size() > 1) {
        			total++;
	    			for(GraphNode<String> node : wtd) {
	    				if(i<wtd.size()-1)
	    					wtdStr.append("tests."+node.getTestCase()+",");
	        			i++;
	        			if(i==wtd.size()) {
	        				lastTc = node.getTestCase();
	        			}
	        		}
	    			wtdStr.deleteCharAt(wtdStr.length()-1);
	        		ImportExportUtils.stringToFile("reaching_wtds/"+lastTc+".wtd", wtdStr.toString());
        		}
    		}
    		System.out.println("Total: "+total);
    	}
    	else if(mode.equals("--runSequentially")) {
    		List<Set<GraphNode<String>>> wholeTestSuite = new LinkedList<>();
    		wholeTestSuite.add(WarrantedScheduleExtractor.getOriginalOrder());
    		PrefixTree rt = buildRadixTree(wholeTestSuite);
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
    	else if(mode.equals("--testRemoteContainer")) {
        	DockerManager docker = new DockerManager(appConfig.get("dockerHost"), appConfig.get("dockerHome"), appConfig.get("dockerEntryPoint"), 3100, 3400, 80, 3306);
        	System.err.println("Starting remote container");
        	DockerContainer container = docker.runContainerFromImage(appConfig.get("dockerImage"));
    	}
    	else if(mode.equals("--newBrowser")) {
        	BrowserContainerManager browserManager = new BrowserContainerManager("127.0.0.1", "selenium/standalone-chrome:4.1.1-20211217");
        	browserManager.startBrowser();
    	}
    	else if(mode.equals("--printGraph")) {
    		String fnam = args[2];
    		GraphvizManager gv = new GraphvizManager();
			BufferedImage graphPng = gv.dotToPng("results/dot/"+fnam);
			gv.saveImageToFile(graphPng, "results/png/"+fnam+".png", "png");
    	}
    	else if(mode.equals("--genParallelTestNgXml")) {
    		Map<String, Float> times = ImportExportUtils.getTestTimesFromXml(app, "sequentials_4core/");
    		List<TimedWarrantedSchedule> timedWtdsFromGraph = TreeTimeManager.generateTimedWarranteds(app, times);
    		timedWtdsFromGraph.sort(Comparator.comparing(TimedWarrantedSchedule::getTime).reversed());
    		int port = 3000;
    		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
    				+ "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n"
    				+ "<suite thread-count=\""+timedWtdsFromGraph.size()+"\" name=\"FullParallel\" parallel=\"tests\">\n";
    		for(int i=0; i<timedWtdsFromGraph.size(); i++) {
    			xml += "<test name=\"Warranted"+i+"\">\n";
    			xml += "<parameter name=\"port\"  value=\""+(3000+i)+"\"/>\n"
    					+ "	<parameter name=\"browserPort\"  value=\"4444\"/>\n"
    					+ "	<parameter name=\"host\"  value=\"192.168.1.6\"/>\n";
    			xml += "<classes>\n";
    			List<TestTreeNode> currWtd = timedWtdsFromGraph.get(i).getSchedule();
    			for(TestTreeNode test : currWtd) {
    				xml += "\t<class name=\"tests."+test.getTestCase().getTestCase()+"\"/>\n";
    			}
    			xml += "</classes>\n</test>\n";
    		}
    		xml += "</suite>";
    		ImportExportUtils.stringToFile("parallel_"+app+".xml", xml);
    	}
    	else {
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
		System.out.println(path);
		graph = graphImporter.importGraph(path);
		return graph;
	}

	public static void runTreeParallelization(PrefixTree rt, HashMap<String, String> appCfg, String appName) {
		DockerManager docker;
		if(appName.equals("expresscart")) 
			docker = new DockerManager(appCfg.get("dockerHost"), appCfg.get("dockerHome"), appCfg.get("dockerEntryPoint"), 3100, 3400, 1111, 3306);
		else
			docker = new DockerManager(appCfg.get("dockerHost"), appCfg.get("dockerHome"), appCfg.get("dockerEntryPoint"), 3100, 3400, 80, 3306);
    	BrowserContainerManager browserManager = new BrowserContainerManager("127.0.0.1", "selenium/standalone-chrome:latest");
    	boolean prestart = true;
    	TestProcessManager processManager = new TestProcessManager(prestart, appName, "org.olianda.treeparallelizer.execution.testcases.RemotePrestartedTestNGExecutor");
    	//System.out.println("Pre-starting "+(rt.getNodeCount()-1)+" test processes...");
    	long preStartStart = System.currentTimeMillis();
    	if(prestart) {
    		processManager.startProcesses(rt.getNodeCount()-1, appName);
    		browserManager.startBrowsers(rt.getLeavesCount());
    	}
    	long preStartEnd = System.currentTimeMillis();
    	TreeExecutor executionVisit = new TreeExecutor(docker, appCfg.get("dockerImage"), appName, processManager, browserManager);
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
        	browserManager.removeAllCreatedContainers();
        	//rc.saveToCsv(appName+".csv");
    	}
	}
	

	public static PrefixTree buildRadixTree(List<Set<GraphNode<String>>> warranteds) {
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
