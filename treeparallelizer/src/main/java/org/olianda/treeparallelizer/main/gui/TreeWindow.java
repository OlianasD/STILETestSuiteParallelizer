package org.olianda.treeparallelizer.main.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class TreeWindow extends JFrame {
	BufferedImage img;
	
	
	public TreeWindow(BufferedImage img) {
		this.img = img;
		buildWindow();
	}
	
	public TreeWindow(String path) {
		loadTreeImage(path);
		buildWindow();
	}

	public void buildWindow() {
		//setBounds(200, 0, img.getWidth(), img.getHeight());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		JLabel imgLb = new JLabel(new ImageIcon(img));
		JScrollPane imgPane = new JScrollPane(imgLb);
		content.add(imgPane, BorderLayout.CENTER);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	public void loadTreeImage(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(TreeWindow.this, "Error while loading image");
			e.printStackTrace();
		}
	}
}
