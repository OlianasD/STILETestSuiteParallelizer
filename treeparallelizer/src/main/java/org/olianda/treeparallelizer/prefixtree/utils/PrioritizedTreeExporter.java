package org.olianda.treeparallelizer.prefixtree.utils;

import javax.swing.tree.DefaultTreeModel;

import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class PrioritizedTreeExporter extends TreeExporter {
	
	public PrioritizedTreeExporter(DefaultTreeModel tree) {
		super(tree);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String colorNode(String nodes, TestTreeNode child) {
		nodes += " fillcolor=\""+colors[child.getPriority()]+"\" style=\"filled\" fontcolor=\"black\" ];\n";
		return nodes;
	}
	
}
