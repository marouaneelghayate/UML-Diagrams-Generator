package org.mql.java;

import org.mql.java.scanner.Scanner;
import org.mql.java.models.Project;
import org.mql.java.parser.Transformer;

public class Main {

	public Main() {
		init();
	}
	private void init() {
		Scanner scanner = new Scanner("C:\\Users\\hp\\Documents\\MQL\\S1\\Java\\p03-Annotations and Reflection");
		Project project = scanner.scan();
		project.print();
		Transformer.transform(project, "C:\\Users\\hp\\Documents\\MQL\\S1\\Java\\ElGhayate Marouane - UML Diagrams Generator\\resources\\xml\\project.xml");
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
