package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class Project {
	private String projectPath;
	List<Package> packages;

	public Project(String projectPath) {
		this.projectPath = projectPath;
		packages = new Vector<Package>();
	}

	@Override
	public String toString() {
		return "Project [projectPath=" + projectPath + ", packages=" + packages + "\n]";
	}
	 
	
	public void print() {
		System.out.println("Project path=" + projectPath);
		for (Package package1 : packages) {
			package1.print("");
		}
	}

	public void addPackage(Package pkg) {
		packages.add(pkg);
		
	}
	
	public List<Package> getPackages() {
		return packages;
	}

}
