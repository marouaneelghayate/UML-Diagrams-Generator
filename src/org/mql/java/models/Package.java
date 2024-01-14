package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class Package implements Container{
	
	private String name;
	private List<ClassWrapper> classes;
	private List<Package> subPackages;
	public Package(String name) {
		this.name = name;
		subPackages = new Vector<Package>();
		classes = new Vector<ClassWrapper>();
	}
	
	
	public void addClass(ClassWrapper wrapper) {
		if(wrapper != null)
			classes.add(wrapper);
	}
	
	public void addPackage(Package pkg) {
		if(pkg != null)
			subPackages.add(pkg);
	}
	
	public List<Package> getSubPackages() {
		return subPackages;
	}
	
	public List<ClassWrapper> getClasses() {
		return classes;
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
		for (ClassWrapper cls : classes) {
			System.out.println(indent + "\t" + cls);
		}
		
	}


}
