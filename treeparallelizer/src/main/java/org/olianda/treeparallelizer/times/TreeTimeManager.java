package org.olianda.treeparallelizer.times;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.jgrapht.Graph;
import org.json.JSONObject;
import org.mb.tedd.graph.GraphEdge;
import org.mb.tedd.graph.GraphNode;
import org.mb.tedd.utils.Properties;
import org.olianda.treeparallelizer.execution.WarrantedScheduleExtractor;
import org.olianda.treeparallelizer.main.Main;
import org.olianda.treeparallelizer.prefixtree.PrefixTree;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;
import org.olianda.treeparallelizer.prefixtree.utils.ImportExportUtils;

import com.google.common.io.Files;

public class TreeTimeManager {
	public static String treePath = "src/main/resources/trees/";
	public static String cfgFilesPath = "../config_files";
	public static String resPath = "src/main/resources";
	
	public static PrefixTree labelTree(PrefixTree tree, Map<String, Float> times) {
		TestTreeNode root = (TestTreeNode) tree.getTree().getRoot();
		LinkedList<TestTreeNode> toVisit = new LinkedList<>(root.getChildren());
		while(!toVisit.isEmpty()) {
			TestTreeNode currNode = toVisit.remove();
			currNode.setTestTime(times.get(currNode.getTestCase().getTestCase()));
			toVisit.addAll(currNode.getChildren());
		}
		return tree;
	}
	
	
	public static List<TimedWarrantedSchedule> getTimedWarrantedsFromGraph(String app, Map<String, Float> times) throws IOException {
		Main.copyTestSuiteCfg(app);
		Properties.getInstance().createPropertiesFile();
		Main.loadAppCfg(app);
		Graph<GraphNode<String>, GraphEdge> graph = Main.loadGraph(Main.appConfig.get("graphPath"));
		List<Set<GraphNode<String>>> baseWtds = WarrantedScheduleExtractor.generateWarrantedPathsForZeroInDegreeNodes(graph);
		List<TimedWarrantedSchedule> timedWtds = new LinkedList<>();
		for(Set<GraphNode<String>> currBase : baseWtds) {
			TimedWarrantedSchedule currTimed = new TimedWarrantedSchedule(new LinkedList<>(), 0.0);
			for(GraphNode<String> test : currBase) {
				currTimed.addNode(test, times.get(test.getTestCase()));
			}
			timedWtds.add(currTimed);
		}
		
		return timedWtds;
		
	}
	
	public static String generateParallelXml(List<TimedWarrantedSchedule> warranteds) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n"
				+ "<suite thread-count=\""+warranteds.size()+"\" name=\"FullParallel\" parallel=\"tests\">\n"
				+"<listeners>\n"
				+ "    <listener class-name=\"org.uncommons.reportng.HTMLReporter\"/>\n"
				+ "</listeners>\n";
		for(int i=0; i<warranteds.size(); i++) {
			xml += "<test name=\"Warranted"+i+"\">\n";
			xml += "<parameter name=\"port\"  value=\""+(3000+i)+"\"/>\n"
					+ "	<parameter name=\"browserPort\"  value=\"4444\"/>\n"
					+ "	<parameter name=\"host\"  value=\"192.168.1.6\"/>\n";
			xml += "<classes>\n";
			List<TestTreeNode> currWtd = warranteds.get(i).getSchedule();
			for(TestTreeNode test : currWtd) {
				xml += "\t<class name=\"tests."+test.getTestCase().getTestCase()+"\"/>\n";
			}
			xml += "</classes>\n</test>\n";
		}
		xml += "</suite>";
		return xml;
	}
	
	public static List<TimedWarrantedSchedule> generatePrioritizedWarranteds(String app, Map<String, Float> times) throws IOException {
		List<TimedWarrantedSchedule> timedWtdsFromGraph = getTimedWarrantedsFromGraph(app, times);
		timedWtdsFromGraph.sort(Comparator.comparing(TimedWarrantedSchedule::getTime).reversed());
		return timedWtdsFromGraph;
	}
	
	public static List<TimedWarrantedSchedule> generateTimedWarranteds(String app, Map<String, Float> times) throws IOException {
		List<TimedWarrantedSchedule> timedWtdsFromGraph = getTimedWarrantedsFromGraph(app, times);
		return timedWtdsFromGraph;
	}
	
	public void generateAndCompare(PrefixTree timedTree, String app, Map<String, Float> times) throws IOException {
		List<TimedWarrantedSchedule> timedWtdsFromJson = new TreeTimeVisitor().visitTree(timedTree);
		List<TimedWarrantedSchedule> timedWtdsFromGraph = getTimedWarrantedsFromGraph(app, times);
		timedWtdsFromJson.sort(Comparator.comparing(TimedWarrantedSchedule::getTime).reversed());
		timedWtdsFromGraph.sort(Comparator.comparing(TimedWarrantedSchedule::getTime).reversed());
		System.out.println("Timed warranteds from JSON:");
		for(TimedWarrantedSchedule schedule : timedWtdsFromJson) {
			System.out.println(schedule.toString());
		}
	}
	
	public static void sortChildren(PrefixTree tree) {
		TestTreeNode root = (TestTreeNode) tree.getTree().getRoot();
		root.getChildren().sort(Comparator.comparing(TestTreeNode::getPriority));
		LinkedList<TestTreeNode> toVisit = new LinkedList<>(root.getChildren());
		while(!toVisit.isEmpty()) {
			TestTreeNode currNode = toVisit.remove();
			currNode.getChildren().sort(Comparator.comparing(TestTreeNode::getPriority));
			toVisit.addAll(currNode.getChildren());
		}
		
	}
	
	public static void main(String[] args) throws FactoryConfigurationError, XMLStreamException, IOException {
		String app = "mantisbt";
		Map<String, Float> times = ImportExportUtils.getTestTimesFromXml(app, "sequentials_4core/");
		/*JSONObject jsonTree = ImportExportUtils.loadJSONObject(treePath+"json/"+app+".json");
		PrefixTree rt = new PrefixTree(jsonTree);
		PrefixTree timedTree = labelTree(rt, times);*/
		
		List<TimedWarrantedSchedule> timedWtdsFromGraph = generateTimedWarranteds(app, times);
		timedWtdsFromGraph.sort(Comparator.comparing(TimedWarrantedSchedule::getTime).reversed());
		int i=1;
		for(TimedWarrantedSchedule warranted : timedWtdsFromGraph) {
			warranted.setPriority(i);
			i++;
		}
		System.out.println("Timed warranteds from graph:");
		for(TimedWarrantedSchedule schedule : timedWtdsFromGraph) {
			System.out.println(schedule.toString());
		}
		PrefixTree priorityTree = new PrefixTree();
		for(TimedWarrantedSchedule warranted : timedWtdsFromGraph) {
			priorityTree.insert(warranted);
		}
		System.out.println("!! Visit order before children sorting: ");
		new TreePriorityVisitor().visitTree(priorityTree);
		sortChildren(priorityTree);
		System.out.println("\n\n!! Visit order after children sorting: ");
		new TreePriorityVisitor().visitTree(priorityTree);
		
		
		/*System.out.println("Are they equally sized? "+(timedWtdsFromJson.size() == timedWtdsFromGraph.size()));
		System.out.println("Are they equal? "+timedWtdsFromJson.equals(timedWtdsFromGraph));*/
		/*String xml = generateParallelXml(timedWtdsFromGraph);
		ImportExportUtils.stringToFile("parallel_"+app+".xml", xml);*/
		System.out.println("checkpoint");
	}
}
