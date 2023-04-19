package org.olianda.treeparallelizer.execution.testcases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.olianda.treeparallelizer.prefixtree.utils.ImportExportUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class RemotePrestartedTestNGExecutor {
	
	public static URL[] createClassPath(List<String> classes) {
		URL[] urls = new URL[classes.size()];
		try {
			for(int i =0; i<urls.length; i++) {
				urls[i] = new URL(classes.get(i));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urls;
	}
	
	

	public static void main(String[] args) {
		//load classpath for app under test
		String app = args[0];
		JSONObject jsonCp = ImportExportUtils.loadJSONObject("src/main/resources/app_config/testsuite_cp/"+app+".json");
		HashMap<String, List<String>> classPathMap = new HashMap(jsonCp.toMap());
		URL[] cp = createClassPath(classPathMap.get(app));
		TestNG testng = new TestNG();
		TestListenerAdapter tla = new TestListenerAdapter();
		testng.setUseDefaultListeners(false);
		//wait for test case name and port
		Scanner scan = new Scanner(System.in);
		String inputStr = scan.next();
		scan.close();
		String[] input = inputStr.split("\\:");
		String testCase = input[0];
		String port = input[1];
		String browserPort = input[2];
		String host = input[3];
		String wait = input[4];
		MyClassLoader classLoader = new MyClassLoader(cp);
		Class<?> cls = null;
		try {
			cls = classLoader.findClass("tests."+testCase);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			System.err.println("Class not found for test "+testCase);
			System.exit(3);
		}
		
		prepareTestCase(testCase, port, browserPort, host, testng, tla, classLoader, cls);
		if(wait.equals("wait")) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		testng.run();
		List<ITestResult> passed = tla.getPassedTests();
		List<ITestResult> failed = tla.getFailedTests();
		List<ITestResult> skipped = tla.getSkippedTests();
		if(!failed.isEmpty()) {
			System.out.println("<ParallelStackTrace>");
			failed.get(0).getThrowable().printStackTrace(System.out);
			System.out.println("</ParallelStackTrace>");
			System.exit(1);
		}
		else if(!passed.isEmpty()) {
			System.out.println("Passed");
			System.exit(0);
		}
		else if(!skipped.isEmpty()) {
			System.out.println("<ParallelStackTrace>");
			skipped.get(0).getThrowable().printStackTrace(System.out);
			System.out.println("</ParallelStackTrace>");
			System.exit(2);
		}
		/*if(!passed.isEmpty()) {
			System.out.println("Passed");
			System.exit(0);
		}
		else if(!failed.isEmpty()) {
			System.out.println("<ParallelStackTrace>");
			failed.get(0).getThrowable().printStackTrace(System.out);
			System.out.println("</ParallelStackTrace>");
			System.exit(1);
		}
		else if(!skipped.isEmpty()) {
			System.out.println("<ParallelStackTrace>");
			skipped.get(0).getThrowable().printStackTrace(System.out);
			System.out.println("</ParallelStackTrace>");
			System.exit(2);
		}*/
	}
	
	
	private static void prepareTestCase(String testCase, String port, String browserPort, String host, TestNG testng, TestListenerAdapter tla,
			MyClassLoader classLoader, Class<?> cls) {
		XmlSuite suite = new XmlSuite();
		XmlTest test = new XmlTest();
		XmlClass clz = new XmlClass();
		testng.addClassLoader(classLoader);
		Map<String, String> params = new HashMap<>();
		params.put("port", String.valueOf(port));
		params.put("browserPort", browserPort);
		params.put("host", host);
		clz.setClass(cls);
		clz.setParameters(params);
		List<XmlClass> clzList = new ArrayList<>();
		clzList.add(clz);
		test.setClasses(clzList);
		test.setName(testCase);
		test.setParameters(params);
		suite.setName(testCase+"_SUITE_"+port);
		test.setSuite(suite);
		suite.addTest(test);
		suite.setParameters(params);
		testng.setCommandLineSuite(suite);
		testng.addListener(tla);
	}
	
}
