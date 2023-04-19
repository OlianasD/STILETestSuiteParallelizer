package org.olianda.treeparallelizer.execution.testcases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
import org.testng.TestNG;


public class GenerateTestProcessClasspath {
	
	public static String cpFilePath = "src/main/resources/classpath.txt";
	
	public static void stringToFile(String path, String payload) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(payload);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String cp = System.getProperty("java.class.path");
		/*
		 * workaround because using Guava 21 in test process makes the creation
		 * of the WebDriver fail due to a NoSuchMethodError in a
		 * com.google.inject class
		 */
		cp = cp.replace("/21.0/guava-21.0.jar", "/23.0/guava-23.0.jar");
		stringToFile(cpFilePath, cp);
	}
	
	
	
}
