package org.olianda.treeparallelizer.docker;


public class DockerContainer {
	protected String id;
	protected int appPort;
	protected int dbPort;
	
	public DockerContainer(String id, int app_port, int db_port) {
		this.id = id;
		this.appPort = app_port;
		this.dbPort = db_port;
	}

	public String getId() {
		return id;
	}

	public int getAppPort() {
		return appPort;
	}

	public int getDbPort() {
		return dbPort;
	}
	
	
}
