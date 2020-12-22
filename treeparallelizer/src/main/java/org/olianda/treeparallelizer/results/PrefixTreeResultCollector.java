package org.olianda.treeparallelizer.results;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.olianda.treeparallelizer.prefixtree.PrefixTree;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class PrefixTreeResultCollector {
	private PrefixTree tree;
	
	public PrefixTreeResultCollector(PrefixTree tree) {
		this.tree = tree;
	}
	
	public void printResults() {
		TestTreeNode root = (TestTreeNode) tree.getTree().getRoot();
		LinkedList<TestTreeNode> toVisit = new LinkedList<>(root.getChildren());
		int tot = 0;
		int passed = 0;
		int failed = 0;
		int skipped = 0;
		int notExec = 0;
		int exc = 0;
		int load = 0;
		int unk = 0;
		System.out.println("Docker clone time for root node: "+root.getDockerCloneTime());
		while(!toVisit.isEmpty()) {
			tot++;
			TestTreeNode currNode = toVisit.remove();
			if(currNode.getResult().equals("PASSED")) {
				System.out.println(currNode.getTestCase().getTestCase()+": PASS");
				passed++;
			}
			else if(currNode.getResult().equals("FAILED")) {
				System.out.println(currNode.getTestCase().getTestCase()+": FAIL");
				failed++;
			}
			else if(currNode.getResult().equals("SKIPPED")) {
				System.out.println(currNode.getTestCase().getTestCase()+": SKIP");
				skipped++;
			}
			else if(currNode.getResult().equals("NOTEXEC")) {
				System.out.println(currNode.getTestCase().getTestCase()+": NODE NOT VISITED");
				notExec++;
			} else if(currNode.getResult().equals("EXCEPTION")) {
				System.out.println(currNode.getTestCase().getTestCase()+": EXECUTOR EXCEPTION");
				exc++;
			}
			else if(currNode.getResult().equals("NOT LOADED")) {
				System.out.println(currNode.getTestCase().getTestCase()+": NOT LOADED");
				load++;
			}
			else if(currNode.getResult().equals("UNKNOWN")) {
				System.out.println(currNode.getTestCase().getTestCase()+": UNKNOWN FAILURE");
				unk++;
			}
			toVisit.addAll(currNode.getChildren());
		}
		System.out.println("Summary: "+tot+" total tests, "+passed+" passed ,"+failed+" failed, "+skipped+" skipped, "+notExec+" not visited, "
		+exc+" executor exceptions, "+load+" test class not loaded, "+unk+" unknown failures");
	}
	
	public void saveToCsv(String name) {
		TestTreeNode root = (TestTreeNode) tree.getTree().getRoot();
		LinkedList<TestTreeNode> toVisit = new LinkedList<>(root.getChildren());
		String res = "Test case,Test result,Exec time,Clone time,Stop time\n"+
						"root,NONE,0.0,"+root.getDockerCloneTime()+",0.0,0.0\n";
		
		while(!toVisit.isEmpty()) {
			
			TestTreeNode currNode = toVisit.remove();
			res += currNode.getTestCase().getTestCase()+","+currNode.getResult()+","+currNode.getTestTime()+","+currNode.getDockerCloneTime()+","+currNode.getDockerKillTime()+"\n";
			toVisit.addAll(currNode.getChildren());
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(name));
			writer.write(res);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
