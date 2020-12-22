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
}
