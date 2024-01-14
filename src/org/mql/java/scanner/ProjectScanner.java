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
		this.root = root + "\\bin";

	}


	public Project scan() {
		File folder = new File(root);
		if(!folder.exists()) {
			return null;
		}
		
		project = new Project(root.replace("\\bin", ""));
		
		Package dft = null;
		
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				project.addPackage(scanPackage(file.getAbsolutePath()));				
			}
			else {
				if(dft == null) {
					dft = new Package("(default package)");
				}
				dft.addClass(getClassWrapper(file));
 			}
		}
		
		if(dft != null) {
			project.addPackage(dft);
		}
		
		return project;
		
	}
	private Package scanPackage(String path) {
		File folder = new File(path);
		Package pkg = new Package(folder.getName());
		
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				pkg.addPackage(scanPackage(file.getAbsolutePath()));				
			}
			else {			
				pkg.addClass(getClassWrapper(file));
 			}
			
		}
		return pkg;
	}
	
	private ClassWrapper getClassWrapper(File file) {
		String className = file.getPath().substring(root.length() + 1).replace("\\",".").replace(".class", "");
		Class<?> cls = SourceClassLoader.loadClass(root,className);
		extractAssociations(cls);
		ClassWrapper wrapper = new ClassWrapper(cls);
		wrapper.discover();
		return wrapper;
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
					project.addAssociation(new Association("composition", cls.getName(), f.getType().getName()));
				}
				else {
					project.addAssociation(new Association("aggregation", cls.getName(), f.getType().getName()));
				}
			}
		}
	}

}
