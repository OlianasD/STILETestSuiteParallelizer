package org.olianda.treeparallelizer.execution.testcases;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import org.olianda.treeparallelizer.prefixtree.utils.ImportExportUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class RemotePrestartedScheduleExecutor {
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
	
	public static String[] getTestCases(String list) {
		list = list.replace("[", "");
		list = list.replace("]", "");
		return list.split(",");
		
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
		String[] testCases = getTestCases(input[0]);
		String port = input[1];
		String browserPort = input[2];
		String host = input[3];
		String wait = input[4];
		MyClassLoader classLoader = new MyClassLoader(cp);
		Map<String, Class<?>> testClasses = new HashMap<>();
		for(String test : testCases) {
			try {
				testClasses.put(test, classLoader.findClass("tests."+test));
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				System.err.println("Class not found for test "+test);
				System.exit(3);
			}
		}
		prepareTestCase(testClasses, port, browserPort, host, testng, tla, classLoader);
		if(wait.equals("wait")) {
			try {
				Thread.sleep(25000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		testng.run();
		List<ITestResult> passed = tla.getPassedTests();
		List<ITestResult> failed = tla.getFailedTests();
		List<ITestResult> skipped = tla.getSkippedTests();
		StringBuilder passedStr = new StringBuilder();
		StringBuilder failedStr = new StringBuilder();
		StringBuilder skippedStr = new StringBuilder();
		for(ITestResult t : passed) {
			passedStr.append(t.getTestClass().getName());
			passedStr.append(",");
		}
		for(ITestResult t : failed) {
			failedStr.append(t.getTestClass().getName());
			failedStr.append(",");
		}
		for(ITestResult t : skipped) {
			skippedStr.append(t.getTestClass().getName());
			skippedStr.append(",");
		}
		String result = "<passed>"+passedStr.toString()+"</passed>"+"<failed>"+failedStr.toString()+"</failed>"
				+"<skipped>"+skippedStr.toString()+"</skipped>\n";
		System.out.println(result);
		System.exit(0);
	}
	
	private static void prepareTestCase(Map<String, Class<?>> tests, String port, String browserPort, String host, TestNG testng, TestListenerAdapter tla,
			MyClassLoader classLoader) {
		XmlSuite suite = new XmlSuite();
		Map<String, String> params = new HashMap<>();
		params.put("port", String.valueOf(port));
		params.put("browserPort", browserPort);
		params.put("host", host);
		suite.setName("SUITE_"+port);
		suite.setParameters(params);
		for(String test : tests.keySet()) {
			XmlTest xmlTest = new XmlTest();
			XmlClass clz = new XmlClass();
			testng.addClassLoader(classLoader);
			clz.setClass(tests.get(test));
			clz.setParameters(params);
			List<XmlClass> clzList = new ArrayList<>();
			clzList.add(clz);
			xmlTest.setClasses(clzList);
			xmlTest.setSuite(suite);
			xmlTest.setName(test);
			xmlTest.setParameters(params);
			suite.addTest(xmlTest);
		}
		testng.setCommandLineSuite(suite);
		testng.addListener(tla);
	}
	
	
}
