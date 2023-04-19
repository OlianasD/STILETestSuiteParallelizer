package org.olianda.treeparallelizer.prefixtree.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.mb.tedd.graph.GraphNode;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class TreeExporter {
	
	protected DefaultTreeModel tree;
	protected static String[] colors = new String[] { 
			"#ff0000", "#00ff00", "#0000ff", "#ffff00", "#ff00ff", "#00ffff", "#000000", "#800000", "#008000", "#000080", "#808000", "#800080", "#008080", "#808080", "#c00000", "#00c000", "#0000c0", "#c0c000", "#c000c0", "#00c0c0", "#c0c0c0", "#400000", "#004000", "#000040", "#404000", "#400040", "#004040", "#404040", "#200000", "#002000", "#000020", "#202000", "#200020", "#002020", "#202020", "#600000", "#006000", "#000060", "#606000", "#600060", "#006060", "#606060", "#a00000", "#00a000", "#0000a0", "#a0a000", "#a000a0", "#00a0a0", "#a0a0a0", "#e00000", "#00e000", "#0000e0", "#e0e000", "#e000e0", "#00e0e0", "#e0e0e0"
	    };
	
	public TreeExporter(DefaultTreeModel tree) {
		this.tree = tree;
	}
	
	public void export(String exportedTreeName) {
		String edges = "";
		TestTreeNode root = (TestTreeNode) tree.getRoot();
		String nodes = root.getTestCase().getTestCase()+"_"+Math.abs(root.hashCode())+" [ label=\"\" color = \"black\" ];\n";
		
		LinkedList<TestTreeNode> toVisit = new LinkedList<TestTreeNode>(root.getChildren());
		int i = 0;
		while(!toVisit.isEmpty()) {
			TestTreeNode child = toVisit.remove();
			GraphNode<String> childTestCase = child.getTestCase();
			TestTreeNode parent = (TestTreeNode) child.getParent();
			GraphNode<String> parentTestCase = parent.getTestCase();
			nodes += childTestCase.getTestCase()+"_"+Math.abs(child.hashCode())+" [ label=\""+childTestCase.getTestCase()+"\"";
			nodes = colorNode(nodes, child);
			
			edges += "  "+parentTestCase.getTestCase() +"_" +Math.abs(parent.hashCode())
					+" -> "+
					childTestCase.getTestCase()+"_"+Math.abs(child.hashCode())
					+" [ label=\"\" color=\"blue\" ];\n";
			toVisit.addAll(child.getChildren());
		}
		edges += "}\n";
		String res = "strict digraph G {\n"+nodes+edges;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(exportedTreeName));
			writer.write(res);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected String colorNode(String nodes, TestTreeNode child) {
		if(child.getResult().equals("FAILED") ) {
			nodes += " fillcolor=\"red\" style=\"filled\" fontcolor=\"black\" ];\n";
		} else if(child.getResult().equals("PASSED")) {
			nodes += " fillcolor=\"green\" style=\"filled\" fontcolor=\"black\" ];\n";
		} else if(child.getResult().equals("SKIPPED")) {
			nodes += " fillcolor=\"orange\" style=\"filled\" fontcolor=\"black\" ];\n";
		} else if(child.getResult().equals("NOT LOADED")) {
			nodes += " fillcolor=\"yellow\" style=\"filled\" fontcolor=\"black\" ];\n";
		} else if(child.getResult().equals("UNKNOWN")) {
			nodes += " fillcolor=\"purple\" style=\"filled\" fontcolor=\"black\" ];\n";
		} else {
			 nodes += " ];\n";
		}
		return nodes;
	}
}
