package org.olianda.treeparallelizer.execution.testcases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.olianda.treeparallelizer.docker.BrowserContainer;
import org.olianda.treeparallelizer.docker.DockerContainer;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestCaseCommander {
	protected TestTreeNode testNode;
	protected DockerContainer container;
	protected String appName;
	protected TestProcessManager processManager;
	protected boolean isNewContainer;
	protected String host;
	protected BrowserContainer browser;
	
	public TestCaseCommander(TestTreeNode testNode, String appName, DockerContainer container, TestProcessManager processManager, BrowserContainer browser, boolean isNewContainer, String host) {
		this.testNode = testNode;
		this.appName = appName;
		this.container = container;
		this.processManager = processManager;
		this.isNewContainer = isNewContainer;
		this.host = host;
		this.browser = browser;
	}
	

	
	public void run() {
		int exitCode = -1;
		try {
			//exitCode = executeTestCaseRemotely(testNode.getTestCase().getTestCase(), container.getAppPort());
			exitCode = executeInRemoteProcess(testNode.getTestCase().getTestCase(), container.getAppPort());

		} catch(Exception e) {
			System.err.println("Exception caught while running test case "+testNode.getTestCase().getTestCase());
			e.printStackTrace();
			testNode.setResult("EXCEPTION");
			return;
		}
		if(exitCode == 0) {
			testNode.setResult("PASSED");
		}
		else if(exitCode == 1) {
			testNode.setResult("FAILED");
		}
		else if(exitCode == 2) {
			testNode.setResult("SKIPPED");
		}
	    else if(exitCode == 3) {
			testNode.setResult("NOT LOADED");
		} else {
			System.err.println("Unexpected exit code: "+exitCode);
			testNode.setResult("UNKNOWN");
		}
	}
	
	private int executeInRemoteProcess(String testCase, int port) throws IOException, InterruptedException {
		Process p = processManager.getProcess();
		OutputStream stdin = p.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		String msgToProc = testCase+":"+port+":"+browser.getPort()+":"+host;
		if(isNewContainer) {
			msgToProc+=":wait\n";
		}
		else {
			msgToProc+=":no\n";
		}
		writer.write(msgToProc);
		writer.flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String err;
	    String stackTrace;
	    String tmpLine;
		String line = "";
	    while ((tmpLine = in.readLine()) != null) {
	        line += tmpLine+"\n";
	    }
	    in.close();
	    stackTrace = StringUtils.substringBetween(line, "<ParallelStackTrace>", "</ParallelStackTrace>");
	    if(stackTrace != null) {
	    	testNode.setStackTrace(stackTrace);
	    }
        // Wait for everything to finish ... ?
        return p.waitFor();
	}
	
	public static URL[] createClassPath(String[] classes) {
		URL[] urls = new URL[classes.length];
		try {
			for(int i =0; i<urls.length; i++) {
				urls[i] = new URL(classes[i]);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urls;
	}


}
