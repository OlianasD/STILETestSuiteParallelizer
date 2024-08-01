package org.olianda.treeparallelizer.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.mb.tedd.graph.GraphEdge;
import org.mb.tedd.graph.GraphNode;
import org.mb.tedd.graph.GraphUtils;

import org.mb.tedd.utils.Properties;

public class WarrantedScheduleExtractor {
	
	
	public static List<Set<GraphNode<String>>> generateWarrantedPathsForZeroInDegreeNodes(Graph<GraphNode<String>, GraphEdge> graph) {
		List<Set<GraphNode<String>>> paths = new LinkedList<>();
		Set<GraphNode<String>> indeps = WarrantedScheduleExtractor.getZeroInDegreeNodes(graph);
		System.out.println(indeps);
		for(GraphNode<String> node : indeps) {
			paths.add(WarrantedScheduleExtractor.getWarranteds(graph, node));
		}
		return paths;
	}
	
	public static List<Set<GraphNode<String>>> generateWarrantedPathsForAllNodes(Graph<GraphNode<String>, GraphEdge> graph) {
		List<Set<GraphNode<String>>> paths = new LinkedList<>();
		Set<GraphNode<String>> nodes = getOriginalOrder();
		for(GraphNode<String> node : nodes) {
			paths.add(WarrantedScheduleExtractor.getWarranteds(graph, node));
		}
		return paths;
	}

	public static Set<GraphNode<String>> getZeroInDegreeNodes(Graph<GraphNode<String>, GraphEdge> graph) {
		Set<GraphNode<String>> indep = new LinkedHashSet<>();
		for(GraphNode<String> node : graph.vertexSet()) {
			if(graph.inDegreeOf(node) == 0) {
				indep.add(node);
			}
		}
		return indep;
	}
	
	public static Set<GraphNode<String>> getWarrantedForSpecificNode(Graph<GraphNode<String>, GraphEdge> graph, String node) {
		for(GraphNode<String> currNode : graph.vertexSet()) {
			if(currNode.getTestCase().equals(node)) {
				return getWarranteds(graph, currNode);
			}
		}
		return null;
	}

	public static Set<GraphNode<String>> getWarranteds(Graph<GraphNode<String>, GraphEdge> graph, GraphNode<String> node) {
		System.out.println("Generating warranted schedule for "+node+": ");
		BreadthFirstIterator<GraphNode<String>, GraphEdge> it = new BreadthFirstIterator<>(graph, node);
		List<GraphNode<String>> preconditions = new ArrayList<>();
		while (it.hasNext()) {
			preconditions.add(it.next());
		}
			
		Set<GraphNode<String>> ori = getOriginalOrder();
		Set<GraphNode<String>> res = new LinkedHashSet<>();
		
		for(GraphNode<String> n : ori) {
			if(preconditions.contains(n)) {
				res.add(n);
			}
		}
		System.out.println("\t"+res);
		return res;
	}

	public static Set<GraphNode<String>> getOriginalOrder() {
		return GraphUtils.mapTestsOrderToNodesOrder(Arrays.asList(Properties.tests_order));
	}
	
}
