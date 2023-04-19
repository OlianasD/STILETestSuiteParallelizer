package org.olianda.treeparallelizer.times;

import java.util.LinkedList;
import java.util.List;

import org.mb.tedd.graph.GraphNode;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class TimedWarrantedSchedule {
	
	private LinkedList<TestTreeNode> schedule;
	private double time;
	private int priority;
	
	public TimedWarrantedSchedule(LinkedList<TestTreeNode> schedule, double time) {
		this.schedule = schedule;
		this.time = time;
		this.priority = 0;
	}

	public List<TestTreeNode> getSchedule() {
		return schedule;
	}

	public void setSchedule(LinkedList<TestTreeNode> schedule) {
		this.schedule = schedule;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	public void addNode(TestTreeNode n) {
		schedule.add(n);
		this.time += n.getTestTime();
	}
	
	public TimedWarrantedSchedule clone() {
		return new TimedWarrantedSchedule((LinkedList<TestTreeNode>) schedule.clone(), time);
	}
	
	public String toString() {
		return priority+":"+schedule.toString()+":"+time;
	}
	
	public void addNode(GraphNode<String> graphNode, double testTime) {
		TestTreeNode tn = new TestTreeNode(graphNode);
		tn.setTestTime(testTime);
		addNode(tn);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof TimedWarrantedSchedule))
			return false;
		TimedWarrantedSchedule other = (TimedWarrantedSchedule) o;
		if(other.schedule.size() != this.schedule.size())
			return false;
		for(int i = 0; i<schedule.size(); i++) {
			if(!this.schedule.get(i).getTestCase().equals(other.schedule.get(i).getTestCase())) {
				System.err.println("Got difference on element "+i);
				return false;
			}
		}
		if(!(this.time == other.time)) {
			System.out.println("Got difference on time");
			return false;
		}
		return true;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
		for(TestTreeNode n : schedule) {
			n.setPriority(priority);
		}
	}
	
	public int size() {
		return schedule.size();
	}
	
}
