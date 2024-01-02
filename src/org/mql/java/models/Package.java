package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class Package {
	
	private String name;
	private List<Class<?>> classes;
	private List<Package> subPackages;
	public Package(String name) {
		this.name = name;
		subPackages = new Vector<Package>();
		classes = new Vector<Class<?>>();
	}
	
	
	public void addClass(Class<?> cls) {
		classes.add(cls);
	}
	
	public void addPackage(Package pkg) {
		subPackages.add(pkg);
	}
	
	public List<Package> getSubPackages() {
		return subPackages;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Package [name=" + name + ", classes=" + classes + ", subPackages=" + subPackages + "\n]\n";
	}

	public void print(String indent) {
		System.out.println(indent + "Package : " + name);
		for (Package pkg : subPackages) {
			pkg.print(indent + "\t");
		}
		for (Class<?> cls : classes) {
			System.out.println(indent + "\tClass : " + cls);
		}
		
	}


}
