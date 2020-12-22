package org.olianda.treeparallelizer.prefixtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mb.tedd.graph.GraphNode;

public class PrefixTree {
	
	protected DefaultTreeModel tree;
	
	public PrefixTree() {
		tree = new DefaultTreeModel(new TestTreeNode(new GraphNode<String>("root", -1)));
	}
	
	public PrefixTree(JSONObject jsonTree) {
		JSONObject rootTestObject = jsonTree.getJSONObject("testCaseObject");
		GraphNode<String> rootTestGraphNode = new GraphNode(rootTestObject.getString("testCase"), rootTestObject.getInt("numOrder"));
		TestTreeNode root = new TestTreeNode(rootTestGraphNode, jsonTree.getDouble("cloneTime"), jsonTree.getDouble("killTime"));
		List<TestTreeNode> children = childrenFromJSON(jsonTree.getJSONArray("children"));
		for(int j=0; j<children.size(); j++) {
			root.insert(children.get(j), j);
		}
		tree = new DefaultTreeModel(root);
	}
	
	private List<TestTreeNode> childrenFromJSON(JSONArray children) {
		List<TestTreeNode> res = new ArrayList<>();
		for(int i=0; i<children.length(); i++) {
			JSONObject currJsonNode = children.getJSONObject(i);
			JSONObject currTestObject = currJsonNode.getJSONObject("testCaseObject");
			GraphNode<String> currGraphNode = new GraphNode<>(currTestObject.getString("testCase"), currTestObject.getInt("numOrder"));
			TestTreeNode currNode = new TestTreeNode(currGraphNode, currJsonNode.getDouble("cloneTime"), currJsonNode.getDouble("killTime"));
			List<TestTreeNode> childrenLst = childrenFromJSON(currJsonNode.getJSONArray("children"));
			for(int j=0; j<childrenLst.size(); j++) {
				currNode.insert(childrenLst.get(j), j);
			}
			res.add(currNode);
		}
		return res;
	}
	
	public void insert(Set<GraphNode<String>> schedule) {
		TestTreeNode currTreeNode = (TestTreeNode) tree.getRoot();
		for(GraphNode<String> currSeqNode : schedule) {
			TestTreeNode target = currTreeNode.getChildWithTestCase(currSeqNode);
			if(target == null) {
				tree.insertNodeInto(new TestTreeNode(currSeqNode), currTreeNode, currTreeNode.getChildCount());
				target = (TestTreeNode) currTreeNode.getChildAt(currTreeNode.getChildCount()-1); //retrieve the last inserted node
			}
			currTreeNode = target;
		}
		
	}
	
	public DefaultTreeModel getTree() {
		return tree;
	}
	
	public int getNodeCount() {
		TestTreeNode root = (TestTreeNode) tree.getRoot();
		LinkedList<TestTreeNode> toVisit = new LinkedList<>(root.getChildren());
		int tot = 1;		
		while(!toVisit.isEmpty()) {
			TestTreeNode currNode = toVisit.remove();
			tot++;
			toVisit.addAll(currNode.getChildren());
		}
		return tot;
	}
	
	public JSONObject toJSON() {
		JSONObject jsonTree = new JSONObject();
		TestTreeNode root = (TestTreeNode) tree.getRoot();
		JSONObject testObject = new JSONObject();
		testObject.put("testCase", root.getTestCase().getTestCase());
		testObject.put("numOrder", root.getTestCase().getNumOrder());
		testObject.put("result", root.getResult());
		if(root.getStackTrace() != null) {
			testObject.put("stackTrace", root.getStackTrace());
		}
		//testObject.put("testTime", root.getTestCase().getTestTime());
		jsonTree.put("testCaseObject", testObject);
		jsonTree.put("cloneTime", root.getDockerCloneTime());
		jsonTree.put("killTime", root.getDockerKillTime());
		jsonTree.put("children", jsonifyChildren(root.getChildren()));
		return jsonTree;
	}
	
	private JSONArray jsonifyChildren(List<TestTreeNode> children) {
		if(children.isEmpty()) {
			return new JSONArray();
		}
		else {
			JSONArray res = new JSONArray();
			for(TestTreeNode child : children) {
				JSONObject jsonChild = new JSONObject();
				JSONObject testObject = new JSONObject();
				testObject.put("testCase", child.getTestCase().getTestCase());
				testObject.put("numOrder", child.getTestCase().getNumOrder());
				testObject.put("result", child.getResult());
				if(child.getStackTrace() != null) {
					testObject.put("stackTrace", child.getStackTrace());
				}
				//testObject.put("testTime", child.getTestCase().getTestTime());
				jsonChild.put("testCaseObject", testObject);
				jsonChild.put("cloneTime", child.getDockerCloneTime());
				jsonChild.put("killTime", child.getDockerKillTime());
				JSONArray jsonChildren = jsonifyChildren(child.getChildren());
				jsonChild.put("children", jsonChildren);
				res.put(jsonChild);
			}
			return res;
		}
		
	}
	
	public int getDepth() {
		TestTreeNode root = (TestTreeNode) tree.getRoot();
		LinkedList<TestTreeNode> toVisit = new LinkedList<>(root.getChildren());
		int currLevel = 0;		
		while(!toVisit.isEmpty()) {
			TestTreeNode currNode = toVisit.remove();
			if(currNode.getLevel() > currLevel) {
				currLevel = currNode.getLevel();
			}
			toVisit.addAll(currNode.getChildren());
		}
		return currLevel;
	}
	
	public List<TestTreeNode> getAncestors(TestTreeNode target) {
		LinkedList<TestTreeNode> ancestors = new LinkedList<>();
		TestTreeNode parent = (TestTreeNode) target.getParent();
		while(parent != null && !parent.getTestCase().getTestCase().equals("root")) {
			ancestors.push(parent);
			parent = (TestTreeNode) parent.getParent();
		}
		return ancestors;
	}
	
}
