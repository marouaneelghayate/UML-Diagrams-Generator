package org.mql.java.models;

import java.util.List;
import java.util.Vector;



public class Project implements Container{
	private String projectPath;
	private List<Package> packages;
	private List<Entity> internalEntities;
	private List<Entity> externalEntities;
	private List<Link> entityLinks;
	private List<Link> packageLinks; 

	public Project(String projectPath) {
		this.projectPath = projectPath;
		packages = new Vector<Package>();
		packageLinks  = new Vector<Link>();
		internalEntities = new Vector<Entity>();
		externalEntities = new Vector<Entity>();
		entityLinks  = new Vector<Link>();
	}

	
	public String getProjectPath() {
		return projectPath;
	}
	 
	
	public void print() {
		System.out.println("Project path : " + projectPath);
		for (Package package1 : packages) {
			package1.print("");
		}
	}

	public void addPackage(Package pkg) {
		if(pkg != null)
			packages.add(pkg);
		
	}
	public void addInternalEntity(Entity e) {
		if(e != null) {			
			internalEntities.add(e);
		}
	}
	
	public void addExtrnalEntity(Entity e) {
		if(e != null && !internalEntities.contains(e) && !externalEntities.contains(e)) {			
			externalEntities.add(e);
		}
	}
	
	public void addLink(Link l) {
		if(l != null) {		
			if(l instanceof EntityLink) {				
				entityLinks.add(l);
			}
			else {
				packageLinks.add(l);
			}
		}
	}
	
	
	public List<Package> getPackages() {
		return packages;
	}
	
	public List<Entity> getInternalEntities() {
		return internalEntities;
	}
	
	public List<Entity> getExternalEntities() {
		return externalEntities;
	}
	
	public List<Link> getPackageLinks() {
		return packageLinks;
	}
	
	public List<Link> getEntityLinks() {
		return entityLinks;
	}
}
