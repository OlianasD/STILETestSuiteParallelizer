package org.olianda.treeparallelizer.times;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.olianda.treeparallelizer.prefixtree.PrefixTree;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class TreeTimeVisitor {
	
	private List<TimedWarrantedSchedule> leafTimes;
	
	public TreeTimeVisitor() {
		leafTimes = new LinkedList<>();
	}
	
	public List<TimedWarrantedSchedule> visitTree(PrefixTree tree) {
		TestTreeNode root = (TestTreeNode) tree.getTree().getRoot();
		List<TestTreeNode> rootChildren = root.getChildren();
		for(TestTreeNode child : rootChildren) {
			new NodeTimeVisitor(leafTimes, new TimedWarrantedSchedule(new LinkedList<TestTreeNode>(), 0.0)).visitNode(child);
		}
		return leafTimes;
	}
	
}
