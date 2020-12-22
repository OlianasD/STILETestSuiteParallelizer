package org.olianda.treeparallelizer.graphviz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class GraphvizManager {
	
	
	public BufferedImage dotToPng(String source) {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("dot");
		cmdArgs.add("-T");
		cmdArgs.add("png");
		cmdArgs.add(source);
		Process gvProc = null;
		try {
			gvProc = new ProcessBuilder(cmdArgs).start();
			BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(gvProc.getInputStream()));
			return img;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	public void saveImageToFile(BufferedImage image, String path, String format) {
		File output = new File(path);
		try {
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			System.err.println("Error while saving "+path);
			e.printStackTrace();
		}
	}
	
}
