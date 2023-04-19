package org.olianda.treeparallelizer.main.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.json.JSONObject;
import org.mb.tedd.graph.GraphNode;
import org.mb.tedd.utils.Properties;
import org.olianda.treeparallelizer.execution.WarrantedScheduleExtractor;
import org.olianda.treeparallelizer.graphviz.GraphvizManager;
import org.olianda.treeparallelizer.main.Main;
import org.olianda.treeparallelizer.prefixtree.PrefixTree;
import org.olianda.treeparallelizer.prefixtree.TestTreeNode;
import org.olianda.treeparallelizer.prefixtree.utils.ImportExportUtils;

public class StileWindow extends JFrame {
	
	Container content;
	JComboBox appsDropDown;
	JButton genWtds;
	JButton genTree;
	JButton runTree;
	JButton runSeq;
	JButton showTreeBtn;
	JTextArea consoleArea;
	Container bottomLineContent;
	Container centerBoxContent;
	JScrollPane treePane;
	JScrollPane consolePane;
	JTextPane testInfoArea;
	JScrollPane testInfoPane;
	PrefixTree tree;
	JTree visualTree;
	JLabel treeArea;
	String[] apps;
	BufferedImage treeImg;
	
	
	public StileWindow() {
		//initializing window
		super("STILE: Test suite parallelizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//initializing drop down menu of applications
		apps = getConfiguredApps();
		appsDropDown = new JComboBox(apps);
		appsDropDown.setSelectedIndex(0);
		
		//initializing buttons
		genWtds = new JButton("Generate warranteds");
		genTree = new JButton("Generate prefix tree");
		runTree = new JButton("Run test suite");
		runSeq = new JButton("Run sequentially");
		showTreeBtn = new JButton("Show prefix tree");
		showTreeBtn.setEnabled(false);
		genWtds.addActionListener(e -> genWtdListener());
		genTree.addActionListener(e -> genTreeListener());
		runTree.addActionListener(e -> runTestSuiteListener());
		runSeq.addActionListener(e -> runTestSuiteSequentiallyListener());
		showTreeBtn.addActionListener(e -> showTreeListener());
		
		bottomLineContent = new Container();
		bottomLineContent.setLayout(new FlowLayout());
		bottomLineContent.add(genWtds);
		bottomLineContent.add(genTree);
		bottomLineContent.add(runTree);
		bottomLineContent.add(runSeq);
		bottomLineContent.add(showTreeBtn);
		
		
		consoleArea = new JTextArea();
		consoleArea.setEditable(false);
		consolePane = new JScrollPane(consoleArea);
		consolePane.setPreferredSize(new Dimension(275, 800));
		PrintStream printStream = new PrintStream(new StandardOutputRedirector(consoleArea));
		System.setOut(printStream);
		System.setErr(printStream);
		
		treePane = new JScrollPane(new JTree(new DefaultTreeModel(new TestTreeNode(new GraphNode<String>("", -1)))));
		
		testInfoArea = new JTextPane();
		testInfoArea.setEditable(false);
		testInfoArea.setContentType("text/html");
		testInfoArea.setText("Choose a test case...");
		testInfoPane = new JScrollPane(testInfoArea);
		testInfoPane.setPreferredSize(new Dimension(275, 800));
		//initalize layout
		centerBoxContent = new Container();
		centerBoxContent.setLayout(new BoxLayout(centerBoxContent, BoxLayout.LINE_AXIS));
		centerBoxContent.add(consolePane);
		centerBoxContent.add(testInfoPane);
		content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(appsDropDown, BorderLayout.PAGE_START);
		content.add(bottomLineContent, BorderLayout.PAGE_END);
		content.add(centerBoxContent, BorderLayout.CENTER);
		content.add(treePane, BorderLayout.LINE_START);
		setVisible(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
	}
	
	
	public String[] getConfiguredApps() {
		String[] cfgFiles = new File(Main.cfgFilesPath).list();
		for(int i=0; i<cfgFiles.length; i++) {
			cfgFiles[i] = cfgFiles[i].replace(".properties", "");
		}
		Arrays.sort(cfgFiles);
		return cfgFiles;
	}
	
	class GenerateWarrantedsWorker extends SwingWorker<String, Integer> {
		private String app;
		
		public GenerateWarrantedsWorker(String app) {
			this.app = app;
		}
		
		@Override
		protected String doInBackground() throws Exception {
			initializeApp(app);
			return wtdToString(Main.loadGraphAndGetWarranteds());
		}
		
		@Override
		public void done() {
			try {
				String wtds = get();
				consoleArea.setText(wtds);
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(StileWindow.this, "Error while generating warranted schedules");
				e.printStackTrace();
			}
			
		}

	}
	
	public void reloadTree() {
		visualTree = new JTree(tree.getTree());
		visualTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//visualTree.setCellRenderer(new TestTreeNodeRenderer());
		visualTree.addTreeSelectionListener(e -> showTestInfo());
		content.remove(treePane);
		treePane = new JScrollPane(visualTree);
		content.add(treePane, BorderLayout.LINE_START);
		showTreeBtn.setEnabled(true);
		content.revalidate();
	}
	
	public void showTestInfo() {
		TestTreeNode currTest = (TestTreeNode) visualTree.getLastSelectedPathComponent();
		String text = "<b> Test case</b>: <br/>"+currTest.getTestCase().getTestCase() + "<br/><br/>"
				+ "<b> Result</b>: <br/>"+currTest.getResult()+"<br/><br/>"
				+ "<b> Predecessors</b>: <br/>"+tree.getAncestors(currTest)+"<br/><br/>";
		
		
		if(currTest.getStackTrace() != null) {
			text += "<b> Stack trace </b>: <br/>"+currTest.getStackTrace().replace("\n", "<br/>")+"<br/><br/>";
		}
		testInfoArea.setText(text);
	}
	
	class GeneratePrefixTreeWorker extends SwingWorker<BufferedImage, Integer> {
		public String app;
		
		public GeneratePrefixTreeWorker(String app) {
			this.app = app;
		}
		
		
		@Override
		protected BufferedImage doInBackground() throws Exception {
			initializeApp(app);
			PrefixTree rt = Main.buildRadixTreeFromGraph(app, Main.appConfig.get("graphPath"));
			String fnam = app+"_plain_"+Main.getCurrentTimestamp()+".dot";
			tree = rt;
    		ImportExportUtils.exportTree(rt, "results/dot/"+fnam);
    		treeImg = new GraphvizManager().dotToPng("results/dot/"+fnam);
    		return treeImg;
		}
		
		@Override
		protected void done() {
			try {
				reloadTree();
				new TreeWindow(get());
				
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(StileWindow.this, "Error while generating prefix tree");
				e.printStackTrace();
			}
		}
		
	}
	
	
	class RunTestSuiteWorker extends SwingWorker<BufferedImage, Integer> {
		
		
		public String app;
		
		public RunTestSuiteWorker(String app) {
			this.app = app;
		}
		
		@Override
		protected BufferedImage doInBackground() throws Exception {
			initializeApp(app);
			tree = Main.buildRadixTreeFromGraph(app, Main.appConfig.get("graphPath"));
			Main.runTreeParallelization(tree, Main.appConfig, app);
			String ts = Main.getCurrentTimestamp();
			String dot_fnam = app+"_"+ts+".dot";
			String json_fnam = app+"_"+ts+".json";
			String png_fnam = app+"_"+ts+".png";
			ImportExportUtils.exportTree(tree, "results/dot/"+dot_fnam);
    		JSONObject jsonTree = tree.toJSON();
    		ImportExportUtils.stringToFile("results/json/"+json_fnam, jsonTree.toString());
    		GraphvizManager gv = new GraphvizManager();
    		treeImg = gv.dotToPng("results/dot/"+dot_fnam);
    		gv.saveImageToFile(treeImg, "results/png/"+png_fnam, "png");
    		return treeImg;
		}
		

		@Override
		protected void done() {
			try {
				reloadTree();
				new TreeWindow(get());
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(StileWindow.this, "Error while running test suite");
				e.printStackTrace();
			}
		}
		
	}
	
	class RunTestSuiteSequentiallyWorker extends SwingWorker<BufferedImage, Integer> {
		
		
		public String app;
		
		public RunTestSuiteSequentiallyWorker(String app) {
			this.app = app;
		}
		
		@Override
		protected BufferedImage doInBackground() throws Exception {
			initializeApp(app);
			List<Set<GraphNode<String>>> wholeTestSuite = new LinkedList<>();
    		wholeTestSuite.add(WarrantedScheduleExtractor.getOriginalOrder());
    		tree = Main.buildRadixTree(wholeTestSuite);
    		Main.runTreeParallelization(tree, Main.appConfig, app);
    		String ts = Main.getCurrentTimestamp();
    		String dotPath = "results/dot/"+app+"_"+ts+".dot";
			ImportExportUtils.exportTree(tree, dotPath);
    		JSONObject jsonTree = tree.toJSON();
    		String jsonPath = "results/json/"+app+"_"+ts+".json";
			ImportExportUtils.stringToFile(jsonPath, jsonTree.toString());
			GraphvizManager gv = new GraphvizManager();
			treeImg = gv.dotToPng(dotPath);
			gv.saveImageToFile(treeImg, "results/png/"+app+"_"+ts+".png", "png");
    		return treeImg;
		}
		

		@Override
		protected void done() {
			try {
				reloadTree();
				new TreeWindow(get());
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(StileWindow.this, "Error while running test suite");
				e.printStackTrace();
			}
		}
		
	}
	
	public void genTreeListener() {
		//new TreeWindow("/home/anonymous/workspace/FSE19-submission-material/ready-to-run-parallelization/addressbook/baseline_complete/addresssbook-radixtree.png");
		new GeneratePrefixTreeWorker(appsDropDown.getSelectedItem().toString()).execute();
	}
	
	public void genWtdListener() {
		new GenerateWarrantedsWorker(appsDropDown.getSelectedItem().toString()).execute();
		
		//JOptionPane.showMessageDialog(StileWindow.this, "Generating warranted schedules for "+appsDropDown.getSelectedItem());
		//wtdPanel.setText("cause here we are again on the middle of the street \n you almost ran the red cause you were looking over me \n wind in my hair \n i was there \n i remeber it all too well, \n yeeeah \n and maybe we got lost in translation \n maybe I asked for too much \n but maybe this thing was a masterpiece til you tore it all up \n wind in my hair \n I was there I remember it all too well");
	}
	
	public void showTreeListener() {
		new TreeWindow(treeImg);
	}
	
	public void runTestSuiteListener() {
		new RunTestSuiteWorker(appsDropDown.getSelectedItem().toString()).execute();
	}
	
	public void runTestSuiteSequentiallyListener() {
		new RunTestSuiteSequentiallyWorker(appsDropDown.getSelectedItem().toString()).execute();
	}
	
	public String wtdToString(List<Set<GraphNode<String>>> warranteds) {
		String res =" ";
		for(Set<GraphNode<String>> path : warranteds) {
			res += "\n[";
			for(GraphNode<String> node : path) {
				res += "\""+node.getTestCase()+"\",";
			}
			res = res.substring(0, res.length()-1);
			res += "]\n";
		}
		return res;
	}


	public void initializeApp(String app) {
		try {
			Main.copyTestSuiteCfg(app);
		} catch (IOException e) {
			System.err.println("Cannot copy app configuration file");
			e.printStackTrace();
			System.exit(1);
		}
		Properties.newInstance().createPropertiesFile();
		Main.loadAppCfg(app);
	}
	

	
}
