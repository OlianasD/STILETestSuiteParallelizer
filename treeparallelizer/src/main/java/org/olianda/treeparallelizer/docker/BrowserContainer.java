package org.olianda.treeparallelizer.docker;

public class BrowserContainer {
	protected String id;
	protected int port;
	protected int dbPort;
	
	public BrowserContainer(String id, int port) {
		this.id = id;
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public int getPort() {
		return port;
	}
}
