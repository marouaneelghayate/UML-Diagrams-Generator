package org.mql.java;

import org.mql.java.models.Project;
import org.mql.java.persistance.ProjectLoader;
import org.mql.java.persistance.ProjectWriter;
import org.mql.java.scanner.ProjectScanner;

public class Main {

	public Main() {
		init();
	}
	private void init() {
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
