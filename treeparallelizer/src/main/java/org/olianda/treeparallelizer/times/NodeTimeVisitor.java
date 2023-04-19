package org.olianda.treeparallelizer.times;

import java.util.List;

import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class NodeTimeVisitor {
	
	private TimedWarrantedSchedule currWarranted;
	private List<TimedWarrantedSchedule> allWarranteds;
	
	public NodeTimeVisitor(List<TimedWarrantedSchedule> allWarranteds, TimedWarrantedSchedule currWarranted) {
		this.allWarranteds = allWarranteds;
		this.currWarranted = currWarranted;
	}
	
	public void visitNode(TestTreeNode node) {
		currWarranted.addNode(node);
		List<TestTreeNode> children = node.getChildren();
		if(children.size() > 0) {
			for(TestTreeNode child : children) {
				new NodeTimeVisitor(allWarranteds, currWarranted.clone()).visitNode(child);
			}
		}
		else {
			System.out.println("Leaf: "+node.getTestCase().getTestCase()+" path time: "+currWarranted.getTime());
			allWarranteds.add(currWarranted);
		}
	}
	
}
