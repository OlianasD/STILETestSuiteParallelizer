package org.olianda.treeparallelizer.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestProcessListener extends Thread {
	
	public String passed;
	public String failed;
	public String skipped;
	public String result;
	private Process process;
	
	public TestProcessListener(Process p) {
		this.process = p;
	}
	
	@Override
	public void run() {
		System.out.println("Listener thread started");
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String err;
	    String stackTrace;
	    String tmpLine;
		String line = "";
	    try {
			while ((tmpLine = in.readLine()) != null) {
			    line += tmpLine+"\n";
			}
			in.close();
		} catch (IOException e) {
			result = "IOException occurred";
			e.printStackTrace();
		}
	    result = line;
	}
	
}
