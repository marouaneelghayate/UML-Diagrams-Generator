package org.mql.java;


import java.awt.Toolkit;
import java.util.Scanner;

import javax.swing.JFrame;

import org.mql.java.models.Project;
import org.mql.java.persistance.ProjectLoader;
import org.mql.java.persistance.ProjectWriter;
import org.mql.java.scanner.ProjectScanner;
import org.mql.java.ui.panels.FormPanel;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;


	public Main() {
		testing();
//		init();
	}
	
	private void init() {
		setResizable(false);
		setContentPane(new FormPanel());
		setSize(400,300);
		setIconImage(Toolkit.getDefaultToolkit().createImage("resources/icons/logo.png"));
		pack();
		setTitle("UML Diagrams Generator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void testing() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Le chemin : ");
		String path = sc.nextLine();
		System.out.println("Decouverte du projet ");
		ProjectScanner projectScanner = new ProjectScanner(path);
		Project project = projectScanner.scan();
		if(project == null) {			
			sc.close();
			System.out.println("Project Introuvable!");
			return ;
		}
		
		System.out.println("Projet chargee en memoire");
		project.print();
		
		System.out.println("Ecriture du fichier dans le repertoire : resources/xml/diagram-test.xml");
		ProjectWriter writer = new ProjectWriter();
		writer.write(project, "resources/xml/diagram-test.xml");
		ProjectLoader loader = new ProjectLoader();
		project = loader.load("resources/xml/diagram-test.xml");
		
		System.out.println("apres chargement depuis le document XML");
		project.print();
		
		sc.close();
	}
	
	
	
	public static void main(String[] args) {
		new Main();
	}

}
