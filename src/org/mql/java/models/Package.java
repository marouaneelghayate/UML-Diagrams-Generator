package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class Package implements Container{
	
	private String name;
	private List<Package> subPackages;
	public Package(String name) {
		this.name = name;
		subPackages = new Vector<Package>();
	}
	
	

	public void addPackage(Package pkg) {
		if(pkg != null)
			subPackages.add(pkg);
	}
	
	public List<Package> getPackages() {
		return subPackages;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	public void print(String indent) {
		System.out.println(indent + "Package : " + name);
		for (Package pkg : subPackages) {
			pkg.print(indent + "\t");
		}
		
		
	}


}
