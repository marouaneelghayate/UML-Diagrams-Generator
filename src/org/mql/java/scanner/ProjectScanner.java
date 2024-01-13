package org.mql.java.scanner;

import java.io.File;
import java.lang.reflect.Field;

import org.mql.java.models.Project;
import org.mql.java.util.SourceClassLoader;
import org.mql.java.models.Association;
import org.mql.java.models.ClassWrapper;
import org.mql.java.models.Package;


public class ProjectScanner {
	private String root;
	private Project project ;

	public ProjectScanner(String root) {
		super();
		project = new Project(root);
		this.root = root + "\\bin";
	}


	public Project scan() {
		File folder = new File(root);
		if(!folder.exists())
			return null;
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
				Class<?> cls = SourceClassLoader.loadClass(root, className);
				extractAssociations(cls);
				ClassWrapper wrapper = new ClassWrapper(cls);
				wrapper.discover();
				if(cls.getSimpleName().equals("XMLNode")) {
					System.out.println("" + wrapper);
				}
				pkg.addClass(wrapper);
				
 			}
			
		}
		return pkg;
	}
	
	private void extractAssociations(Class<?> cls) {
		Class<?> parent = cls.getSuperclass();
		if(parent != null) {
			project.addAssociation(new Association("extends", cls.getName(), parent.getName()));
		}
		Class<?> superInterfaces[] = cls.getInterfaces();
		for (Class<?> i : superInterfaces) {
			project.addAssociation(new Association("implements", cls.getName(),i.getName()));
		}
		
		for(Field f : cls.getDeclaredFields()) {
			if(!f.getType().isPrimitive()) {
				if(f.getType().isMemberClass()) {
					project.addAssociation(new Association("composition", f.getType().getName(), cls.getName()));
				}
				else {
					project.addAssociation(new Association("aggregation", f.getType().getName(), cls.getName()));
				}
			}
		}
	}

}
