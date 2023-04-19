package org.olianda.treeparallelizer.docker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.api.model.VolumeBind;
import com.github.dockerjava.core.DockerClientBuilder;

public class BrowserContainerManager {
	
	protected DockerClient client;
	protected volatile int extPort;
	protected String host;
	protected String image;
	protected volatile LinkedList<BrowserContainer> createdContainers;
	protected volatile LinkedList<BrowserContainer> availableContainers;
	protected boolean prestart;
	
	public BrowserContainerManager(String host, String image) {
		client = DockerClientBuilder.getInstance().build();
		this.host = host;
		this.image = image;
		this.extPort = 4445;
		this.createdContainers = new LinkedList<>();
		this.availableContainers = new LinkedList<>();
	}
	
	public synchronized BrowserContainer startBrowser() {
		List<ExposedPort> ports = new ArrayList<>();
		List<PortBinding> binds = new ArrayList<>();
		List<VolumeBind> volume = new ArrayList<>();
		ports.add(ExposedPort.parse(String.valueOf(extPort)));
		binds.add(PortBinding.parse(extPort+":4444"));
		volume.add(new VolumeBind("/dev/shm", "/dev/shm"));
		CreateContainerResponse res = client.createContainerCmd(image)
				.withExposedPorts(ports)
				.withPortBindings(binds)
				.withBinds(new Bind("/dev/shm", new Volume("/dev/shm")))
				.exec();
		
		BrowserContainer container = new BrowserContainer(res.getId(), extPort);
		client.startContainerCmd(res.getId()).exec();
		if(client.inspectContainerCmd(res.getId()).exec().getState().getRunning()) {
			System.out.println("Browser container "+res.getId()+"is up and running with port = "+extPort);
			createdContainers.add(container);
			extPort++;
			return container;
		} else {
			return null;
		}
	}
	
	private boolean checkContainerAndRetry(CreateContainerResponse res, BrowserContainer container) {
		if(client.inspectContainerCmd(res.getId()).exec().getState().getRunning()) {
			System.out.println("Container "+res.getId()+" started");
			boolean started = false;
			for(int i=0; i<3; i++) {
				started = checkContainerAvailability();
				if(started) break;
			}
			if(started) {
				System.out.println("Container "+res.getId()+"is up and running with app port = "+extPort);
				createdContainers.add(container);
				extPort++;
				return true;
			}
			else {
				System.out.println("Failed to start container with app port = "+extPort+", retrying");
				stopContainer(container.getId());
				client.removeContainerCmd(container.getId()).exec();
				return false;
			}
		} else {
			System.out.println("Failed to start container with app port = "+extPort+", retrying");
			stopContainer(container.getId());
			client.removeContainerCmd(container.getId()).exec();
			return false;
		}
	}
	
	public synchronized boolean checkContainerAvailability() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Socket socket = new Socket()) {
	        socket.connect(new InetSocketAddress(host, extPort), 5000);
	        return true;
	    } catch (IOException e) {
	        return false; // Either timeout or unreachable or failed DNS lookup.
	    }
	}
	
	public synchronized void removeAllCreatedContainers() {
		System.out.println("Removing all created containers...");
		for(BrowserContainer container : createdContainers) {
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
	
	public synchronized void stopContainer(String id) {
		System.out.println("Stopping browser container "+id);
		if(client.inspectContainerCmd(id).exec().getState().getRunning()) {
			try {
				client.killContainerCmd(id).exec();
			} catch(NotModifiedException e) {
				System.err.println("NotModifiedException caught when killing browser container "+id);
			}
		}
	}
	
	public void startBrowsers(int nBrowsers) {
		prestart = true;
		for(int i=0; i<nBrowsers; i++) {
			availableContainers.add(startBrowser());
		}
	}
	
	public synchronized BrowserContainer getBrowser() {
		if(prestart) {
			return availableContainers.remove();
		} else {
			return startBrowser();
			//return new BrowserContainer("dummy", 4445);
		}
	}
	
	
}
