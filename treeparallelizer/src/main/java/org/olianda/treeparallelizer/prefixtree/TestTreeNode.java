package org.olianda.treeparallelizer.prefixtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.mb.tedd.graph.GraphNode;

public class TestTreeNode implements MutableTreeNode {
	
	private GraphNode<String> testCase;
	private List<TestTreeNode> children;
	private MutableTreeNode parent;
	private String result;
	private String failCause;
	private String stackTrace;
	private double testTime;
	private int priority;
	
	public TestTreeNode(GraphNode<String> testCase) {
		this.testCase = testCase;
		children = new LinkedList<>();
		result ="NOTEXEC";
	}
	
	public TestTreeNode(GraphNode<String> testCase, double testTime) {
		this.testCase = testCase;
		this.testTime = testTime;
		children = new ArrayList<>();
		result ="NOTEXEC";
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return children.size() == 0;
	}

	@Override
	public Enumeration children() {
		// TODO Auto-generated method stub
		return Collections.enumeration(children);
	}
	
	public List<TestTreeNode> getChildren() {
		return children;
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
		children.add(index, (TestTreeNode) child);
		child.setParent(this);

	}

	@Override
	public void remove(int index) {
		children.remove(index);

	}

	@Override
	public void remove(MutableTreeNode node) {
		children.remove(node);

	}

	@Override
	public void setUserObject(Object object) {
		testCase = (GraphNode<String>) object;
	}
	
	public GraphNode<String> getTestCase() {
		return testCase;
	}

	@Override
	public void removeFromParent() {
		parent.remove(this);

	}

	@Override
	public void setParent(MutableTreeNode newParent) {
		parent = newParent;
	}
	
	@Override
	public boolean equals(Object o) {
		 if(this == o)
			 return true;
		 if(!(o instanceof TestTreeNode))
			 return false;
		 TestTreeNode gtn = (TestTreeNode) o;
		 return gtn.testCase.equals(this.testCase) && gtn.parent.equals(this.parent) && gtn.children.equals(this.children);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.testCase, this.parent);
		
	}
	
	@Override
	public String toString() {
		return testCase.getTestCase();
	}
	
	public TestTreeNode getChildWithTestCase(GraphNode<String> target) {
		
		for(MutableTreeNode genChild : children) {
			TestTreeNode child = (TestTreeNode) genChild;
			if(child.getTestCase().equals(target)) 
				return child;
		}
		return null;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public double getTestTime() {
		return testTime;
	}

	public void setTestTime(double testTime) {
		this.testTime = testTime;
	}

	public double getDockerCloneTime() {
		return 0.0;
	}

	public void setDockerCloneTime(double dockerCloneTime) {
		
	}

	public double getDockerKillTime() {
		return 0.0;
	}

	public void setDockerKillTime(double dockerKillTime) {
		
	}
	
	public int getLevel() {
		if(parent == null) {
			return 0;
		} else {
			return ((TestTreeNode) parent).getLevel()+1;
		}
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	

}
