package org.mql.java.persistance;

import java.io.File;
import java.util.Arrays;

import org.mql.java.models.EntityLink;
import org.mql.java.models.Entity;
import org.mql.java.models.Container;
import org.mql.java.models.Package;
import org.mql.java.models.PackageLink;
import org.mql.java.models.Project;
import org.mql.java.parsers.XMLNode;
import org.mql.java.util.SourceClassLoader;

public class ProjectLoader {
	private Project project;
	private XMLNode root;
	public ProjectLoader() {
		
	}
	
	public Project load(String path){
		File f = new File(path);
		if(!f.exists()) {
			return null;
		}
		root = new XMLNode(path);
		project = new Project(root.getAttribute("path"));
		loadPackages(root, project);
		loadEntities();
		loadLinks();
		return project;
	}
	
	private void loadPackages(XMLNode parent, Container c) {
		XMLNode packages[] = parent.getNodes("package");
		for (XMLNode pkgNode : packages) {
			Package p = new Package(pkgNode.getAttribute("id"));
			c.addPackage(p);
			loadPackages(pkgNode,p);
			
		}
		
	}
	
	private void loadEntities() {
		Arrays.stream(root.getNodes("class")).forEach(this::addEntityNode);
		Arrays.stream(root.getNodes("annotation")).forEach(this::addEntityNode);
		Arrays.stream(root.getNodes("interface")).forEach(this::addEntityNode);
	}
	
	private void addEntityNode(XMLNode node) {
		int scope = node.getIntAttribute("scope");
		if(scope == Entity.INTERNAL) {
			Class<?> cls = SourceClassLoader.loadClass(project.getProjectPath() + "/bin", node.getAttribute("id"));
			project.addIntrnalEntity(new Entity(cls,true));
			return ;
		}
		try {
			Class<? >cls = Class.forName(node.getAttribute("id"));
			project.addExtrnalEntity(new Entity(cls));
		} catch (Exception e) {
		}
		
	}
	

	private void loadLinks() {
		Arrays.stream(root.getNodes("entity-link"))
		.map(node -> 
			new EntityLink(
					node.getIntAttribute("type"), 
					node.getChild("start").getValue(), 
					node.getChild("end").getValue()
					)
		)
		.forEach(project::addLink);
		
		Arrays.stream(root.getNodes("package-link"))
		.map(node -> 
			new PackageLink(
				node.getIntAttribute("type"), 
				node.getChild("start").getValue(), 
				node.getChild("end").getValue()
			)
		)
		.forEach(project::addLink);;
		
	}
}
