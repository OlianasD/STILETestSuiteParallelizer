package org.olianda.treeparallelizer.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jgrapht.Graph;
import org.json.JSONObject;
import org.mb.tedd.graph.GraphEdge;
import org.mb.tedd.graph.GraphNode;
import org.mb.tedd.graph.dot.importgraph.GraphImporter;
import org.mb.tedd.utils.ExecutionTime;
import org.mb.tedd.utils.Properties;
import org.olianda.treeparallelizer.docker.BrowserContainer;
import org.olianda.treeparallelizer.docker.BrowserContainerManager;
import org.olianda.treeparallelizer.docker.DockerContainer;
import org.olianda.treeparallelizer.docker.DockerManager;
import org.olianda.treeparallelizer.execution.WarrantedScheduleExtractor;
import org.olianda.treeparallelizer.execution.testcases.TestProcessManager;
import org.olianda.treeparallelizer.prefixtree.utils.ImportExportUtils;

import com.google.common.io.Files;

public class PlainParallelizer {
	
	public static HashMap<String, String> appConfig;
	public static String cfgFilesPath = "../config_files";
	public static String resPath = "src/main/resources";
	private static BrowserContainerManager browsers;
	private static DockerManager dockerManager;
	private static TestProcessManager processManager;
	private static List<TestProcessListener> listeners;
	
	
	public static void loadAppCfg(String app) {
		JSONObject cfg = ImportExportUtils.loadJSONObject(Main.resPath+"/app_config/docker/"+app+".json");
		appConfig = new HashMap(cfg.toMap());
		
	}
	
	public static Graph<GraphNode<String>, GraphEdge> loadGraph(String path) {
		Graph<GraphNode<String>, GraphEdge> graph;
		GraphImporter graphImporter = new GraphImporter();
		graph = graphImporter.importGraph(path);
		return graph;
	}
	
	public static void copyTestSuiteCfg(String app) throws IOException {
		File src = new File(cfgFilesPath+"/"+app+".properties");
		File dest = new File(resPath+"/app.properties");
		Files.copy(src, dest);
	}
	
	
	public static void main(String[] args) throws IOException {
		String app = "addressbook";
		try {
			copyTestSuiteCfg(app);
		} catch (IOException e) {
			System.err.println("Cannot copy app configuration file");
			e.printStackTrace();
			System.exit(1);
		}
		Properties.getInstance().createPropertiesFile();
		loadAppCfg(app);
		Graph<GraphNode<String>, GraphEdge> graph = loadGraph(appConfig.get("graphPath"));
		browsers = new BrowserContainerManager("127.0.0.1", "selenium/standalone-chrome");
		dockerManager = new DockerManager(appConfig.get("dockerHost"), appConfig.get("dockerHome"), appConfig.get("dockerEntryPoint"), 3100, 3400, 80, 3306);
		processManager = new TestProcessManager(true, app, "org.olianda.treeparallelizer.execution.testcases.RemotePrestartedScheduleExecutor");
		listeners = new LinkedList<>();
		List<Set<GraphNode<String>>> warranteds = WarrantedScheduleExtractor.generateWarrantedPathsForZeroInDegreeNodes(graph);
		
		//List<Set<GraphNode<String>>> warranteds = allWarranteds.subList(0, 2);
		System.out.println(warranteds.size());
		browsers.startBrowsers(warranteds.size());
		LinkedList<DockerContainer> containers = dockerManager.createContainers(warranteds.size(), appConfig.get("dockerImage"));
		processManager.startProcesses(warranteds.size(), app);
		long start = System.currentTimeMillis();
		for(Set<GraphNode<String>> wtd : warranteds) {
			System.out.println("Starting warranted schedule...");
			BrowserContainer browser = browsers.getBrowser();
			DockerContainer container = containers.remove();
			Process p = processManager.getProcess();
			runSchedule(wtd, p, browser, container);
			TestProcessListener currTpl = new TestProcessListener(p);
			listeners.add(currTpl);
			currTpl.start();
		}
		List<String> results = new LinkedList<>();
		for(TestProcessListener tpl : listeners) {
			System.out.println("Waiting for all shcedules to finish...");
			try {
				tpl.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			results.add(tpl.result);
		}
		long end = System.currentTimeMillis();
    	System.out.println("Time spent: "+new ExecutionTime()
    			.computeExecutionTime(Arrays.asList((end - start))));
		browsers.removeAllCreatedContainers();
		dockerManager.removeAllCreatedContainers();
		
		int i=1;
		boolean allright = true;
		for(String res : results) {
			String passed =  StringUtils.substringBetween(res, "<passed>", "</passed>");
			String failed = StringUtils.substringBetween(res, "<failed>", "</failed>");
			String skipped = StringUtils.substringBetween(res, "<skipped>", "</skipped>");
			System.out.println("Schedule "+i+":");
			System.out.println("Passsed: "+passed);
			System.out.println("Failed: "+failed);
			System.out.println("Skipped: "+skipped+"\n");
			i++;
			if(!failed.equals("") || !skipped.equals("")) {
				System.out.println("WARNING: failures in schedule "+i);
				allright = false;
			}
		}
		if(!allright) {
			System.out.println("Execution failed");
		}
		//String msgToProc = "[AddressBookAddAddressBookTest,AddressBookSearchAddressBookNameTest]:3000:4445:192.168.1.109:no\n";
		
	}

	private static void runSchedule(Set<GraphNode<String>> schedule, Process p, BrowserContainer browser, DockerContainer appContainer) throws IOException {
		String scheduleStr = schedule.toString().replaceAll("[0-9]", "");
		scheduleStr = scheduleStr.replaceAll("_", "");
		scheduleStr = scheduleStr.replaceAll(" ", "");
		String msgToProc = scheduleStr+":"+appContainer.getAppPort()+":"+browser.getPort()+":"+appConfig.get("dockerHost")+":no\n";
		System.out.println(msgToProc);
		OutputStream stdin = p.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		writer.write(msgToProc);
		writer.flush();
	}
}
