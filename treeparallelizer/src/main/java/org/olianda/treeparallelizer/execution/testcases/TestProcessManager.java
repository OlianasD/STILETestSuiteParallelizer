package org.olianda.treeparallelizer.execution.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.olianda.treeparallelizer.prefixtree.utils.ImportExportUtils;

public class TestProcessManager {
	
	private volatile LinkedList<Process> processes;
	//protected String cpFilePath = "src/main/resources/classpath.txt";
	
	
	public TestProcessManager() {
		processes = new LinkedList<>();
	
	}
	
	public void startProcesses(int nproc, String app) {
		List<String> cmdArgs = new ArrayList<>();
		JSONObject jsonCp = ImportExportUtils.loadJSONObject("src/main/resources/app_config/testprocess_cp/"+app+".json");
		HashMap<String, String> classPathMap = new HashMap(jsonCp.toMap());
		//String cp = ImportExportUtils.readStringFromFile(cpFilePath);
		cmdArgs.add("/home/anonymous/workspace/java/jdk1.8.0_201/bin/java");
		cmdArgs.add("-Dfile.encoding=UTF-8");
		cmdArgs.add("-classpath");
		cmdArgs.add(classPathMap.get(app));
		//cmdArgs.add(cp);
		cmdArgs.add("org.olianda.treeparallelizer.execution.testcases.RemotePrestartedTestNGExecutor");
		cmdArgs.add(app);
		for(int i=0; i<nproc; i++) {
			ProcessBuilder processBuilder = new ProcessBuilder(cmdArgs);
			try {
				processes.add(processBuilder.start());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized Process getProcess() {
		return processes.remove();
	}
	
	
}
