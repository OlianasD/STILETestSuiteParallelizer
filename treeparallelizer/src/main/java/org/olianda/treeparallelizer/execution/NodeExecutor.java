package org.olianda.treeparallelizer.execution;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mb.tedd.utils.ExecutionTime;
import org.olianda.treeparallelizer.docker.BrowserContainer;
import org.olianda.treeparallelizer.docker.BrowserContainerManager;
import org.olianda.treeparallelizer.docker.DockerContainer;
import org.olianda.treeparallelizer.docker.DockerManager;
import org.olianda.treeparallelizer.execution.testcases.TestCaseCommander;
import org.olianda.treeparallelizer.execution.testcases.TestProcessManager;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class NodeExecutor extends Thread {
	private TestTreeNode node;
	private DockerManager docker;
	private DockerContainer container;
	private String appName;
	private boolean isNewContainer;
	private TestProcessManager processManager;
	private BrowserContainerManager browsers;
	private DateTimeFormatter dtf;
	private BrowserContainer browser;
	
	public NodeExecutor(TestTreeNode node, DockerManager docker, DockerContainer container, String appName, boolean isNewContainer, TestProcessManager processManager, BrowserContainerManager browsers, BrowserContainer browser) {
		this.node = node;
		this.docker = docker;
		this.container = container;
		this.appName = appName;
		this.isNewContainer = isNewContainer;
		this.processManager = processManager;
		this.browsers = browsers;
		this.browser = browser;
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
	}
	
	@Override
	public void run() {
		System.out.println(dtf.format(LocalDateTime.now())+"Container with port"+container.getAppPort()+" assigned to test "+node.getTestCase().getTestCase()+" (priority "+node.getPriority()+")");
		//BrowserContainer browser = browsers.getBrowser();
		System.out.println(dtf.format(LocalDateTime.now())+"Browser with port"+browser.getPort()+" assigned to test "+node.getTestCase().getTestCase()+" (priority "+node.getPriority()+")");
		TestCaseCommander tce = new TestCaseCommander(node, appName, container, processManager, browser, isNewContainer, docker.getHost());
		long testStart = System.currentTimeMillis();
		tce.run();
		long testEnd = System.currentTimeMillis();
		System.out.println(dtf.format(LocalDateTime.now())+": test "+node.getTestCase().getTestCase()+" ended");
		//browsers.stopContainer(browser.getId());
		List<TestTreeNode> children = node.getChildren();
		//we reached a leaf: stop docker container and return
		if(children.size() == 0) {
			long dockerStopStart = System.currentTimeMillis();
			docker.killContainer(container);
			browsers.stopContainer(browser.getId());
			long dockerStopEnd = System.currentTimeMillis();
			node.setTestTime(new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((testEnd - testStart))).getTime());
			node.setDockerKillTime(new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((dockerStopEnd - dockerStopStart))).getTime());
			return;
		}
		//only one child, we proceed in the current thread
		else if(children.size() == 1) {
			node.setTestTime(new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((testEnd - testStart))).getTime());
			new NodeExecutor(children.get(0), docker, container, appName, false, processManager, browsers, browser).run();
		}
		//N children: start N-1 new threads, run the last child in the current thread then wait for the remaining threads
		else {
			List<NodeExecutor> childrenThreads = new ArrayList<>();
			long dockerCloneStart = System.currentTimeMillis();
			for(int i = 0; i<children.size()-1; i++) {
				DockerContainer currClone = docker.cloneAndStartContainer(container);
				NodeExecutor currChildThread = new NodeExecutor(children.get(i), docker, currClone, appName, true, processManager, browsers, browsers.getBrowser());
				childrenThreads.add(currChildThread);
				currChildThread.start();
			}
			long dockerCloneEnd = System.currentTimeMillis();
			new NodeExecutor(children.get(children.size()-1), docker, container, appName, false, processManager, browsers, browser).run();
			long dockerStopStart = System.currentTimeMillis();
			docker.killContainer(container);
			long dockerStopEnd = System.currentTimeMillis();
			node.setTestTime(new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((testEnd - testStart))).getTime());
			node.setDockerCloneTime(new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((dockerCloneEnd - dockerCloneStart))).getTime());
			node.setDockerKillTime(new ExecutionTime()
        			.computeExecutionTime(Arrays.asList((dockerStopEnd - dockerStopStart))).getTime());
			for(NodeExecutor thread : childrenThreads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					System.err.println("InterruptedException caught in node "+node);
				}
			}
			
		}
	}
	
}
