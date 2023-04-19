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
	private boolean prestart;
	private String app;
	private String process;
	
	public TestProcessManager(boolean prestart, String app, String process) {
		processes = new LinkedList<>();
		this.prestart = prestart;
		this.app = app;
		this.process = process;
	}
	
	public void startProcesses(int nproc, String app) {
		for(int i=0; i<nproc; i++) {
			try {
				processes.add(startProcess(app));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Process startProcess(String app) throws IOException {
		List<String> cmdArgs = new ArrayList<>();
		JSONObject jsonCp = ImportExportUtils.loadJSONObject("src/main/resources/app_config/testprocess_cp/"+app+".json");
		HashMap<String, String> classPathMap = new HashMap(jsonCp.toMap());
		//String cp = ImportExportUtils.readStringFromFile(cpFilePath);
		cmdArgs.add("java");
		cmdArgs.add("-Dfile.encoding=UTF-8");
		cmdArgs.add("-classpath");
		cmdArgs.add(classPathMap.get(app));
		//cmdArgs.add(cp);
		cmdArgs.add(process);
		cmdArgs.add(app);
		ProcessBuilder processBuilder = new ProcessBuilder(cmdArgs);
		return processBuilder.start();
	}
	
	public synchronized Process getProcess() {
		if(prestart) {
			return processes.remove();
		} else {
			try {
				return startProcess(app);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
	
	
}
