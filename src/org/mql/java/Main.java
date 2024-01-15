package org.mql.java;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.mql.java.models.Project;
import org.mql.java.persistance.ProjectLoader;
import org.mql.java.persistance.ProjectWriter;
import org.mql.java.scanner.ProjectScanner;
import org.mql.java.ui.panels.FormPanel;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;


	public Main() {
		//m1();
		init();
	}
	private void init() {
		
		setResizable(false);
		setContentPane(new FormPanel());
		//setContentPane(new DiagramsPanel());
		setSize(400,300);
		pack();
		setTitle("UML Diagrams Generator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	private void m1() {
		String path ="C:\\Users\\hp\\Documents\\MQL\\S1\\Java\\p03-Annotations and Reflection";
		ProjectScanner projectScanner = new ProjectScanner(path);
		Project project = projectScanner.scan();
		System.out.println("avant la persistance des donnees");
		project.print();
		ProjectWriter writer = new ProjectWriter();
		writer.write(project, "resources/xml/diagram.xml");
		ProjectLoader loader = new ProjectLoader();
		project = loader.load("resources/xml/diagram.xml");
		System.out.println("apres la persistance des donnees");
		project.print();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
