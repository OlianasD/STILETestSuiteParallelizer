package org.olianda.treeparallelizer.docker;

import java.util.ArrayList;
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
	
	

	
	public DockerManager(String workDir, String entrypoint, int extAppPort, int extDbPort, int intAppPort, int intDbPort) {
		client = DockerClientBuilder.getInstance().build();
		createdContainers = new ArrayList<>();
		createdImages = new ArrayList<>();
		this.workDir = workDir;
		this.entrypoint = entrypoint;
		this.extAppPort = extAppPort;
		this.extDbPort = extDbPort;
		this.intAppPort = intAppPort;
		this.intDbPort = intDbPort;
	}
	
	public synchronized DockerContainer runContainerFromImage(String image) {
		List<ExposedPort> ports = new ArrayList<>();
		List<PortBinding> binds = new ArrayList<>();
		ports.add(ExposedPort.parse(String.valueOf(extAppPort)));
		ports.add(ExposedPort.parse(String.valueOf(extDbPort)));
		binds.add(PortBinding.parse(extAppPort+":"+intAppPort));
		binds.add(PortBinding.parse(extDbPort+":"+intDbPort));
		
		CreateContainerResponse res = client.createContainerCmd(image)
			.withCmd("bash")
			.withEntrypoint(entrypoint)
			.withExposedPorts(ports)
			.withPortBindings(binds)
			.withWorkingDir(workDir)
			.exec();
		DockerContainer container = new DockerContainer(res.getId(), extAppPort, extDbPort);
		createdContainers.add(container);
		System.out.println("Starting container "+res.getId()+" with app port = "+extAppPort+", db port = "+extDbPort);
		client.startContainerCmd(res.getId()).exec();
		if(client.inspectContainerCmd(res.getId()).exec().getState().getRunning()) {
			System.out.println("Container "+res.getId()+"is up and running with app port = "+extAppPort+", db port = "+extDbPort);
			extAppPort++;
			extDbPort++;
			return container;
		} else {
			return null;
		}
	}
	
	public synchronized DockerContainer cloneAndStartContainer(DockerContainer container) {
		String snapshotId = client.commitCmd(container.getId()).exec();
		createdImages.add(snapshotId);
		return runContainerFromImage(snapshotId);
	}
	
	public synchronized void killContainer(DockerContainer container) {
		try {
			client.killContainerCmd(container.getId()).exec();
		} catch(Exception e) {
			System.err.println("Exception caught when killing container "+container.getId());
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
	
}
