package org.olianda.treeparallelizer.prefixtree.utils;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.mb.tedd.graph.GraphNode;
import org.olianda.treeparallelizer.prefixtree.PrefixTree;

import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.Attribute;


public class ImportExportUtils {
	
	public static String xmlPath = "/home/anonymous/testNGtimes/";
	public static String jsonPath = "/home/anonymous/workspace/FSE19-submission-material/treeparallelizer/src/main/resources/testNGtimes/test_times/";
	public static String csvPath = "/home/anonymous/workspace/FSE19-submission-material/treeparallelizer/src/main/resources/testNGtimes/test_times/csv/";
	public static String[] allApps = new String[] {"addressbook", "claroline", "collabtive", "mantisbt", "mrbs", "ppma"};
	
	
	public static void main(String[] args) throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		allSamplesToJson(11, allApps);
		unifyAllSamplesToCsv(11, allApps);
	}
	
	public static void unifyAllSamplesToCsv(int n_samples, String[] apps) throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		for(String app : apps) {
			List<Map<String, Float>> times = new ArrayList<>();
			for(int i=0; i<n_samples; i++) {
				times.add(getTestTimesFromXml(app+i, xmlPath+app+"/"));
			}
			String csv = unifyToCsv(times);
			stringToFile(csvPath+app+".csv", csv);
		}
		
	}
	
	public static String unifyToCsv(List<Map<String, Float>> times) {
		StringBuilder csv = new StringBuilder();
		for(String testCase : times.get(0).keySet()) {
			csv.append(testCase+",");
		}
		csv.replace(csv.length()-1, csv.length(), "\n");
		
		for(Map<String, Float> sample : times) {
			for(String testCase : sample.keySet()) {
				csv.append(sample.get(testCase)+",");
			}
			csv.replace(csv.length()-1, csv.length(), "\n");
		}
		return csv.toString();
	}
	
	

	public static void allSamplesToJson(int n_samples, String[] apps) throws FactoryConfigurationError, FileNotFoundException, XMLStreamException {
		
		for(String app : apps) {
			for(int i=0; i<n_samples; i++) {
				Map<String, Float> times = getTestTimesFromXml(app+i, xmlPath+app+"/");
				saveStringFloatJsonToFile(times, jsonPath+app+"/"+app+i+".json");
			}
				
		}
	}

	public static Map<String, Float> getTestTimesFromXml(String app, String path) throws FactoryConfigurationError, FileNotFoundException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream in;
		HashMap<String, Float> times = new HashMap<>();
		in = new FileInputStream(path+app+".xml");
		XMLEventReader eventReader = factory.createXMLEventReader(in);
		while(eventReader.hasNext()) {
			XMLEvent currEvent = eventReader.nextEvent();
			if(currEvent.isStartElement()) {
				StartElement se = currEvent.asStartElement();
				if(se.getName().getLocalPart().contentEquals("testcase")) {
					Iterator<Attribute> attrs = se.getAttributes();
					String testName = null;
					String testTime = null;
					while(attrs.hasNext()) {
						Attribute curr = attrs.next();
						if(curr.getName().getLocalPart().equals("classname")) {
							testName = curr.getValue();
						}
						if(curr.getName().getLocalPart().equals("time")) {
							testTime = curr.getValue();
						}
					}
					testName = testName.replace("tests.", "");
					times.put(testName, Float.parseFloat(testTime));
				}
			}
		}

		System.out.println("checkpoint");
		return times;
	}
	
	public static void saveStringFloatJsonToFile(Map<String, Float> times, String path) {
		JSONObject json = new JSONObject(times);
		String jsonStr = json.toString();
		stringToFile(path, jsonStr);
	}
	
	public static void saveStringStringJsonToFile(Map<String, String> data, String path) {
		JSONObject json = new JSONObject(data);
		String jsonStr = json.toString();
		stringToFile(path, jsonStr);
	}
	
	public static void saveStringStringArrayJsonToFile(Map<String, String[]> data, String path) {
		JSONObject json = new JSONObject(data);
		String jsonStr = json.toString();
		stringToFile(path, jsonStr);
	}

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

	public static JSONObject loadJSONObject(String path) {
		JSONTokener tokener = null;
		try {
			URI uri = new URI("file:///"+System.getProperty("user.dir")+"/"+path);
			tokener = new JSONTokener(uri.toURL().openStream());
		} catch(Exception e) {
			System.err.println("ERROR: cannot load JSON object from "+path);
			e.printStackTrace();
			System.exit(1);
		}
		JSONObject jsonTree = new JSONObject(tokener);
		return jsonTree;
	}

	public static void exportWarranteds(List<Set<GraphNode<String>>> warranteds, String app) {
		String res ="[ ";
		for(Set<GraphNode<String>> path : warranteds) {
			res += "[";
			for(GraphNode<String> node : path) {
				res += "\""+node.getTestCase()+"\",";
			}
			res = res.substring(0, res.length()-1);
			res += "],";
		}
		res = res.substring(0, res.length()-1);
		res += "]";
		stringToFile("/home/anonymous/workspace/FSE19-submission-material/treeparallelizer/src/main/resources/warranteds/"+app+".json", res);
	}

	public static void exportTree(PrefixTree rt, String path) {
		TreeExporter exporter = new TreeExporter(rt.getTree());
		exporter.export(path);
	}
	
	public static void exportPrioritizedTree(PrefixTree rt, String path) {
		PrioritizedTreeExporter exporter = new PrioritizedTreeExporter(rt.getTree());
		exporter.export(path);
	}
	
	public static String readStringFromFile(String path) {
		 byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 return new String(encoded);
	}
}
