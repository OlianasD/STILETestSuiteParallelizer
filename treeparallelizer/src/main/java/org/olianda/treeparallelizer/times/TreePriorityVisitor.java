package org.olianda.treeparallelizer.times;

import java.util.List;

import org.olianda.treeparallelizer.prefixtree.PrefixTree;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class TreePriorityVisitor {
	
	
	public void visitTree(PrefixTree tree) {
		TestTreeNode root = (TestTreeNode) tree.getTree().getRoot();
		List<TestTreeNode> rootChildren = root.getChildren();
		for(TestTreeNode child : rootChildren) {
			new NodePriorityVisitor(child).visit();
		}
	}
	
}
