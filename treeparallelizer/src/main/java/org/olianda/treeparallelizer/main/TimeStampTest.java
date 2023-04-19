package org.olianda.treeparallelizer.main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStampTest {
	
	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		System.out.println(dtf.format(LocalDateTime.now()));
	}
}
