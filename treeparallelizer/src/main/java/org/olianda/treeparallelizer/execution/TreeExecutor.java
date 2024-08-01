package org.olianda.treeparallelizer.execution;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mb.tedd.utils.ExecutionTime;
import org.olianda.treeparallelizer.docker.BrowserContainerManager;
import org.olianda.treeparallelizer.docker.DockerContainer;
import org.olianda.treeparallelizer.docker.DockerManager;
import org.olianda.treeparallelizer.execution.testcases.TestProcessManager;
import org.olianda.treeparallelizer.prefixtree.PrefixTree;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class TreeExecutor {
	
	private DockerManager docker;
	private String imageId;
	private String appName;
	private TestProcessManager processManager;
	private BrowserContainerManager browsers;
	
	public TreeExecutor(DockerManager docker, String imageId, String appName, TestProcessManager processManager, BrowserContainerManager browsers) {
		this.docker = docker;
		this.imageId = imageId;
		this.appName = appName;
		this.processManager = processManager;
		this.browsers = browsers;
	}
	
	
	public void executeTree(PrefixTree tree, boolean stopContainerWhenFinished) {
		TestTreeNode root = (TestTreeNode) tree.getTree().getRoot();
		List<TestTreeNode> rootChildren = root.getChildren();
		List<NodeExecutor> childrenThreads = new ArrayList<>();
		long dockerCloneStart = System.currentTimeMillis();
		for(TestTreeNode node : rootChildren) {
			DockerContainer container = docker.runContainerFromImage(imageId);
			NodeExecutor currChildThread = new NodeExecutor(node, docker, container, appName, stopContainerWhenFinished, processManager, browsers, browsers.getBrowser());
			currChildThread.start();
			childrenThreads.add(currChildThread);
		}
		long dockerCloneEnd = System.currentTimeMillis();
		root.setDockerCloneTime(new ExecutionTime()
    			.computeExecutionTime(Arrays.asList((dockerCloneEnd - dockerCloneStart))).getTime());
		for(NodeExecutor thread : childrenThreads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println("InterruptedException caught in TreeExecutor");
			}
		}
		
		System.out.println("Finished");
		
		
	}
}
