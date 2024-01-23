package org.mql.java.scanner;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.mql.java.models.Project;
import org.mql.java.util.SourceClassLoader;

import org.mql.java.models.Entity;
import org.mql.java.models.EntityLink;
import org.mql.java.models.Package;
import org.mql.java.models.PackageLink;


public class ProjectScanner {
	private String root;
	private Project project ;

	public ProjectScanner(String root) {
		this.root = root.replace("\\", "/") + "/bin";

	}


	public Project scan() {
		File folder = new File(root);
		if(!folder.exists()) 
			return null;
		
		project = new Project(root.replace("/bin", ""));
		
//		Arrays.stream(folder.listFiles())
//			.filter(file -> file.isDirectory())
//			.map(this::scanPackage)
//			.forEach(project::addPackage);
//		
//		Arrays.stream(folder.listFiles())
//			.filter(file -> !file.isDirectory())
//			.map(this::getEntity)
//			.forEach(project::addInternalEntity);
		
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				project.addPackage(scanPackage(file));				
			}
			else {
				project.addInternalEntity(getEntity(file));
 			}
		}
		
		for(Entity e : project.getInternalEntities()) {
			extractLinks(e);
		}
		
		return project;
		
	}
	private Package scanPackage(File folder) {
		Package pkg = new Package(folder.getName());
		
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				pkg.addPackage(scanPackage(file));				
			}
			else {			
				project.addInternalEntity(getEntity(file));
 			}
			
		}
		return pkg;
	}
	
	
	private Entity getEntity(File file) {
		String className = file.getPath().substring(root.length() + 1).replace("\\",".").replace(".class", "");
		Class<?> cls = SourceClassLoader.loadClass(root,className);
		Entity wrapper = new Entity(cls, true);
		return wrapper;
	}
	
	
	private void extractLinks(Entity e) {
		Class<?> cls = e.getCls();
		if(cls == null) 
			return ;
		
		Class<?> parent = cls.getSuperclass();
		if(parent != null) {
			project.addExtrnalEntity(new Entity(parent));
			project.addLink(new EntityLink(EntityLink.EXTENSION, cls.getName(), parent.getName()));
		}
		
		Class<?> superInterfaces[] = cls.getInterfaces();
		for (Class<?> i : superInterfaces) {
			project.addLink(new	EntityLink(EntityLink.IMPLEMENTATION, cls.getName(),i.getName()));
			project.addExtrnalEntity(new Entity(i));
		}
		
		for(Field f : cls.getDeclaredFields()) {
			if(!f.getType().isPrimitive()) {
				if(f.getType().isMemberClass()) {
					project.addLink(new EntityLink(EntityLink.COMPOSITION, cls.getName(), f.getType().getName()));
					project.addLink(new PackageLink(PackageLink.USE, cls.getPackageName(), f.getType().getPackageName()));
				}
				else {
					Class<?> type = f.getType();
					if(f.getType().isArray()) {
						type = f.getType().getComponentType();
					}
					project.addExtrnalEntity(new Entity(type));
					project.addLink(new EntityLink(EntityLink.AGGREGATION, cls.getName(), type.getName()));
					project.addLink(new PackageLink(PackageLink.ACCESS, cls.getPackageName(), type.getPackageName()));
				}
			}
		}
	}

}
