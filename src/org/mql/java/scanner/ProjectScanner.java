package org.mql.java.scanner;

import java.io.File;
import java.lang.reflect.Field;

import org.mql.java.models.Project;
import org.mql.java.util.SourceClassLoader;

import org.mql.java.models.Entity;
import org.mql.java.models.EntityLink;
import org.mql.java.models.Package;


public class ProjectScanner {
	private String root;
	private Project project ;

	public ProjectScanner(String root) {
		this.root = root.replace("\\\\", "/") + "/bin";

	}


	public Project scan() {
		File folder = new File(root);
		if(!folder.exists()) {
			return null;
		}
		
		project = new Project(root.replace("/bin", ""));
		
		
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				project.addPackage(scanPackage(file.getAbsolutePath()));				
			}
			else {
				project.addInetrnalEntity(getEntity(file));
			
 			}
		}
		
		for(Entity e : project.getInternalEntities()) {
			extractAssociations(e.getCls());
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
				project.addInetrnalEntity(getEntity(file));
 			}
			
		}
		return pkg;
	}
	
	
	private Entity getEntity(File file) {
		String className = file.getPath().substring(root.length() + 1).replace("\\",".").replace(".class", "");
		Class<?> cls = SourceClassLoader.loadClass(root,className);
		//extractAssociations(cls);
		Entity wrapper = new Entity(cls, true);
		return wrapper;
	}
	
	
	private void extractAssociations(Class<?> cls) {
		Class<?> parent = cls.getSuperclass();
		if(parent != null) {
			project.addExetrnalEntity(new Entity(parent));
			project.addEntityLink(new EntityLink(EntityLink.EXTENSION, cls.getName(), parent.getName()));
		}
		Class<?> superInterfaces[] = cls.getInterfaces();
		for (Class<?> i : superInterfaces) {
			project.addEntityLink(new	EntityLink(EntityLink.IMPLEMENTATION, cls.getName(),i.getName()));
			project.addExetrnalEntity(new Entity(i));
		}
		
		for(Field f : cls.getDeclaredFields()) {
			if(!f.getType().isPrimitive()) {
				if(f.getType().isMemberClass()) {
					project.addEntityLink(new EntityLink(EntityLink.COMPOSITION, cls.getName(), f.getType().getName()));
				}
				else {
					Class<?> type = f.getType();
					if(f.getType().isArray()) {
						type = f.getType().getComponentType();
					}
					project.addExetrnalEntity(new Entity(type));
					project.addEntityLink(new EntityLink(EntityLink.AGGREGATION, cls.getName(), type.getName()));
					
				}
			}
		}
	}

}
