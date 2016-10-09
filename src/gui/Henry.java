package gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import jpanel.SearchByAuthorPanel;
import jpanel.SearchByCategoryPanel;
import jpanel.SearchByPublisherPanel;

public class Henry {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				runGUI();
			}
		});
	}
	
	private static void runGUI(){
		//Create frame
		JFrame frame = new JFrame("CS260 Assignment 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create tabbed Pane
		JTabbedPane tabbedPane = new JTabbedPane();
		//Add panels
		JComponent authorPanel = new SearchByAuthorPanel();
		JComponent categoryPanel = new SearchByPublisherPanel();
		JComponent publisherPanel = new SearchByCategoryPanel();
		tabbedPane.addTab("Search By Author", authorPanel);
		tabbedPane.addTab("Search By Category", categoryPanel);
		tabbedPane.addTab("Search By Publisher", publisherPanel);
		
		//Add tabbedPane to frame and display
		frame.add(tabbedPane);
		frame.pack();
		frame.setVisible(true);
	}
}
