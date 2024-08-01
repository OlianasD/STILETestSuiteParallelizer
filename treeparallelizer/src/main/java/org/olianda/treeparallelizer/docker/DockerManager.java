package org.olianda.treeparallelizer.docker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerManager {
	
	
	protected DockerClient client;
	protected volatile int extAppPort;
	protected volatile int extDbPort;
	protected int intAppPort;
	protected int intDbPort;
	protected volatile List<DockerContainer> createdContainers;
	protected volatile List<String> createdImages;
	protected String workDir;
	protected String entrypoint;
	private String host;
	private DateTimeFormatter dtf;
	
	

	
	public DockerManager(String host, String workDir, String entrypoint, int extAppPort, int extDbPort, int intAppPort, int intDbPort) {
		client = DockerClientBuilder.getInstance("tcp://"+host+":2375").build();
		createdContainers = new ArrayList<>();
		createdImages = new ArrayList<>();
		this.host = host;
		this.workDir = workDir;
		this.entrypoint = entrypoint;
		this.extAppPort = extAppPort;
		this.extDbPort = extDbPort;
		this.intAppPort = intAppPort;
		this.intDbPort = intDbPort;
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
	}
	
	public synchronized LinkedList<DockerContainer> createContainers(int nContainers, String image) {
		LinkedList<DockerContainer> containers = new LinkedList<>();
		for(int i=0; i<nContainers; i++) {
			containers.add(runContainerFromImage(image));
		}
		return containers;
	}
	
	public synchronized DockerContainer runContainerFromImage(String image) {
		List<ExposedPort> ports = new ArrayList<>();
		List<PortBinding> binds = new ArrayList<>();
		ports.add(ExposedPort.parse(String.valueOf(extAppPort)));
		ports.add(ExposedPort.parse(String.valueOf(extDbPort)));
		binds.add(PortBinding.parse(extAppPort+":"+intAppPort));
		binds.add(PortBinding.parse(extDbPort+":"+intDbPort));
		
		DockerContainer container = null;
		boolean available = false;
		for(int i=0; i<6; i++) {
			CreateContainerResponse res = createAndStartContainer(image, ports, binds);
			container = new DockerContainer(res.getId(), extAppPort, extDbPort);
			available = checkContainerAndRetry(res, container);
			if(available) break;
		}
		if(available) return container;
		else throw new RuntimeException("Unable to create container with port "+extAppPort);
	}

	private boolean checkContainerAndRetry(CreateContainerResponse res, DockerContainer container) {
		if(client.inspectContainerCmd(res.getId()).exec().getState().getRunning()) {
			System.out.println(dtf.format(LocalDateTime.now())+": Container "+res.getId()+" started for Docker API");
			boolean started = false;
			for(int i=0; i<6; i++) {
				started = checkContainerAvailability();
				if(started) {
					System.out.println(dtf.format(LocalDateTime.now())+": Container "+res.getId()+"is up and running with app port = "+extAppPort+", db port = "+extDbPort);
					createdContainers.add(container);
					extAppPort++;
					extDbPort++;
					return true;
				}
			}
		} 
		System.out.println(dtf.format(LocalDateTime.now())+dtf.format(LocalDateTime.now())+": Failed to start container with app port = "+extAppPort+", retrying");
		killContainer(container);
		client.removeContainerCmd(container.getId()).exec();
		return false;
		
	}

	private CreateContainerResponse createAndStartContainer(String image, List<ExposedPort> ports,
			List<PortBinding> binds) {
		CreateContainerResponse res = client.createContainerCmd(image)
			.withCmd("bash")
			.withEntrypoint(entrypoint)
			.withExposedPorts(ports)
			.withPortBindings(binds)
			.withWorkingDir(workDir)
			.exec();
		System.out.println(dtf.format(LocalDateTime.now())+": Starting container "+res.getId()+" with app port = "+extAppPort+", db port = "+extDbPort);
		client.startContainerCmd(res.getId()).exec();
		return res;
	}
	
	public synchronized boolean checkContainerAvailability() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Socket socket = new Socket()) {
	        socket.connect(new InetSocketAddress(host, extAppPort), 5000);
	        return true;
	    } catch (IOException e) {
	        return false; // Either timeout or unreachable or failed DNS lookup.
	    }
	}
	
	public synchronized DockerContainer cloneAndStartContainer(DockerContainer container) {
		String snapshotId = client.commitCmd(container.getId()).exec();
		createdImages.add(snapshotId);
		return runContainerFromImage(snapshotId);
	}
	
	public synchronized void killContainer(DockerContainer container) {
		System.out.println(dtf.format(LocalDateTime.now())+": Stopping container with app port "+container.getAppPort());
		try {
			client.stopContainerCmd(container.getId()).exec();
			//client.killContainerCmd(container.getId()).exec();
		} catch(Exception e) {
			System.err.println(dtf.format(LocalDateTime.now())+"Exception caught when killing container with app port"+container.getAppPort());
		}

	}
	
	public synchronized void removeAllCreatedContainers() {
		System.out.println("Removing all created containers...");
		for(DockerContainer container : createdContainers) {
			if(client.inspectContainerCmd(container.getId()).exec().getState().getRunning()) {
				try {
					client.killContainerCmd(container.getId()).exec();

				} catch(NotModifiedException e) {
					System.err.println("NotModifiedException caught when killing container "+container.getId());
				}
			}
			client.removeContainerCmd(container.getId()).exec();
		}
	}
	
	public synchronized void removeAllCreatedImages() {
		System.out.println("Removing all created images...");
		for(String img : createdImages) {
			try {
				client.removeImageCmd(img).exec();
			} catch(Exception e) {
				System.err.println("Exception caught when removing image "+img);
			}
		}
	}
	
	public int getCreatedContainersCount() {
		return createdContainers.size();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
}
