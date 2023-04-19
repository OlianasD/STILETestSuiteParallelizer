package org.olianda.treeparallelizer.times;

import java.util.List;

import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class NodePriorityVisitor {
	
	private TestTreeNode node;
	
	public NodePriorityVisitor(TestTreeNode node) {
		this.node = node;
	}
	
	public void visit() {
		System.out.println("Priority: "+node.getPriority());
		List<TestTreeNode> children = node.getChildren();
		for(TestTreeNode child : children) {
			new NodePriorityVisitor(child).visit();
		}
	}
}
