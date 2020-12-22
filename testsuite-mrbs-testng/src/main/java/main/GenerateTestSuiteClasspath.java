package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import tests.*;

public class GenerateTestSuiteClasspath {
	
	public static String cpFilePath = "/home/anonymous/workspace/FSE19-submission-material/treeparallelizer/src/main/resources/app_config/testsuite_cp/";
	
	
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
		String[] subpaths = cp.split("\\:");
		StringBuilder finalCp = new StringBuilder("{\"collabtive\":[");
		for(int i = 0; i<subpaths.length; i++) {
			finalCp.append("\"file://"+subpaths[i]);
			if(!subpaths[i].substring(subpaths[i].length()-4, subpaths[i].length()).equals(".jar")) {
				finalCp.append("/");
			}
			finalCp.append("\",");
		}
		finalCp.replace(finalCp.length()-1, finalCp.length(), "]");
		finalCp.append("}");
		stringToFile(cpFilePath+"collabtive.json", finalCp.toString());
	}
}
