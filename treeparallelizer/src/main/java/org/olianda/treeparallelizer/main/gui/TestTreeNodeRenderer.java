package org.olianda.treeparallelizer.main.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.olianda.treeparallelizer.prefixtree.TestTreeNode;

public class TestTreeNodeRenderer extends DefaultTreeCellRenderer {
	
	
	 @Override
	  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
	                                                boolean leaf, int row, boolean hasFocus) {
		 super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		 TestTreeNode node = (TestTreeNode) value;
		 String result = node.getResult();
		 if(result.equals("PASSED")) {
			 setTextNonSelectionColor(Color.GREEN);
			 setTextSelectionColor(Color.GREEN);
		 } else if(result.equals("FAILED")) {
			 setTextNonSelectionColor(Color.RED);
			 setTextSelectionColor(Color.RED);
		 } else if(result.equals("SKIPPED")) {
			 setTextNonSelectionColor(Color.YELLOW);
			 setTextSelectionColor(Color.YELLOW);
		 } else if(result.equals("NOT LOADED")) {
			 setTextNonSelectionColor(Color.ORANGE);
			 setTextSelectionColor(Color.ORANGE);
		 } else if(result.equals("NOTEXEC")) {
			 setTextNonSelectionColor(Color.BLACK);
			 setTextSelectionColor(Color.BLACK);
		 } else {
			 setTextNonSelectionColor(Color.MAGENTA);
			 setTextSelectionColor(Color.MAGENTA);
		 }
		 return this;
		 
	 }

}
