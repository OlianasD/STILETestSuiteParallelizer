package org.olianda.treeparallelizer.execution;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mb.tedd.utils.ExecutionTime;
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
	private boolean stopContainerWhenFinished;
	private TestProcessManager processManager;
	
	public NodeExecutor(TestTreeNode node, DockerManager docker, DockerContainer container, String appName, boolean stopContainerWhenFinished, TestProcessManager processManager) {
		this.node = node;
		this.docker = docker;
		this.container = container;
		this.appName = appName;
		this.stopContainerWhenFinished = stopContainerWhenFinished;
		this.processManager = processManager;
	}
	
	@Override
	public void run() {
		TestCaseCommander tce = new TestCaseCommander(node, appName, container, processManager);
		long testStart = System.currentTimeMillis();
		tce.run();
		long testEnd = System.currentTimeMillis();
		List<TestTreeNode> children = node.getChildren();
		//we reached a leaf: stop docker container and return
		if(children.size() == 0) {
			long dockerStopStart = System.currentTimeMillis();
			if(stopContainerWhenFinished) {
				docker.killContainer(container);
			}
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
			new NodeExecutor(children.get(0), docker, container, appName, stopContainerWhenFinished, processManager).run();
		}
		//N children: start N-1 new threads, run the last child in the current thread then wait for the remaining threads
		else {
			List<NodeExecutor> childrenThreads = new ArrayList<>();
			long dockerCloneStart = System.currentTimeMillis();
			for(int i = 0; i<children.size()-1; i++) {
				DockerContainer currClone = docker.cloneAndStartContainer(container);
				NodeExecutor currChildThread = new NodeExecutor(children.get(i), docker, currClone, appName, stopContainerWhenFinished, processManager);
				childrenThreads.add(currChildThread);
				currChildThread.start();
			}
			long dockerCloneEnd = System.currentTimeMillis();
			new NodeExecutor(children.get(children.size()-1), docker, container, appName, stopContainerWhenFinished, processManager).run();
			long dockerStopStart = System.currentTimeMillis();
			if(stopContainerWhenFinished) {
				docker.killContainer(container);
			}
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
