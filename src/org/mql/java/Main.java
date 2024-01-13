package org.mql.java;

import org.mql.java.scanner.ProjectScanner;
import org.mql.java.generators.ClassGenerator;
import org.mql.java.generators.Generator;
import org.mql.java.generators.PackageGenerator;
import org.mql.java.models.Project;

public class Main {

	public Main() {
		init();
	}
	private void init() {
		String path ="C:\\Users\\hp\\Documents\\MQL\\S1\\Java\\ElGhayate Marouane - UML Diagrams Generator";
		ProjectScanner projectScanner = new ProjectScanner(path);
		Project project = projectScanner.scan();
		Generator generator = new PackageGenerator(project, "resources/xml/package.xml");
		generator.generate();
		generator = new ClassGenerator(project, "resources/xml/class.xml");
		generator.generate();
		
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
