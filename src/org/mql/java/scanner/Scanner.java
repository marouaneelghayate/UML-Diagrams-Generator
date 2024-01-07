package org.mql.java.scanner;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


import org.mql.java.models.Project;
import org.mql.java.models.Package;


public class Scanner {
	private String root;

	public Scanner(String root) {
		super();
		this.root = root;
	}


	public Project scan() {
		File folder = new File(root + "\\bin");
		if(!folder.exists())
			return null;
		Project project = new Project(root);
		root = root + "\\bin";
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				project.addPackage(recursiveScan(root + "\\" + file.getName()));				
			}
			else {
				Package dft = recursiveScan(root);
				dft.setName("default");
				project.addPackage(dft);
 			}
			
		}
		return project;
		
	}
	private Package recursiveScan(String path) {
		File folder = new File(path);
		if(!folder.exists())
			return null;
		Package pkg = new Package(folder.getName());
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				pkg.addPackage(recursiveScan(file.getAbsolutePath()));				
			}
			else {
				
				String className = file.getPath().substring(root.length() + 1).replace("\\",".").replace(".class", "");
				pkg.addClass(loadClass(className));
				
 			}
			
		}
		return pkg;
	}


	private Class<?> loadClass(String className) {
		try {
			File f = new File(root);
			URL[] urls = {f.toURI().toURL()};
			URLClassLoader urlcl = new URLClassLoader(urls);
			Class<?> cls  = urlcl.loadClass(className);
			//urlcl.close();
			return cls;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	

}
