package org.mql.java.models;

import java.util.List;
import java.util.Vector;


public class Project implements Container{
	private String projectPath;
	private List<Package> packages;
	private List<Association> associations;

	public Project(String projectPath) {
		this.projectPath = projectPath;
		packages = new Vector<Package>();
		associations  = new Vector<Association>();
	}

	@Override
	public String toString() {
		return "Project [projectPath=" + projectPath + ", packages=" + packages + "\n]";
	}
	
	public String getProjectPath() {
		return projectPath;
	}
	 
	
	public void print() {
		System.out.println("Project path=" + projectPath);
		for (Package package1 : packages) {
			package1.print("");
		}
	}

	public void addPackage(Package pkg) {
		if(pkg != null)
			packages.add(pkg);
		
	}
	
	public void addAssociation(Association a) {
		associations.add(a);
	}
	
	public List<Package> getPackages() {
		return packages;
	}
	
	
	public List<Association> getAssociations() {
		return associations;
	}


}
