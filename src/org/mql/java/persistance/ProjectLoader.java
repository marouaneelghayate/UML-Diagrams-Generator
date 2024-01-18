package org.mql.java.persistance;

import java.io.File;

import org.mql.java.models.EntityLink;
import org.mql.java.models.Link;
import org.mql.java.models.Entity;
import org.mql.java.models.Container;
import org.mql.java.models.Package;
import org.mql.java.models.PackageLink;
import org.mql.java.models.Project;
import org.mql.java.parser.XMLNode;
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
		XMLNode classes[] = root.getNodes("class");
		for (XMLNode classNode : classes) {
			addEntityNode(classNode);
				
		}
		
		XMLNode annotations[] = root.getNodes("annotation");
		for (XMLNode classNode : annotations) {
			addEntityNode(classNode);
		}
		
		XMLNode interfaces[] = root.getNodes("interface");
		for (XMLNode classNode : interfaces) {
			addEntityNode(classNode);
		}
	}
	
	private void addEntityNode(XMLNode node) {
		int scope = node.getIntAttribute("scope");
		if(scope == Entity.INTERNAL) {
			Class<?> cls = SourceClassLoader.loadClass(project.getProjectPath() + "/bin", node.getAttribute("id"));
			project.addInetrnalEntity(new Entity(cls,true));
		}
		else {
			try {
				Class<? >cls = Class.forName(node.getAttribute("id"));
				project.addExetrnalEntity(new Entity(cls));
			} catch (Exception e) {
			}
		}
	}
	

	private void loadLinks() {
		XMLNode entityLinks[] = root.getNodes("entity-link");
		for (XMLNode node : entityLinks) {
			Link l = new EntityLink(
					node.getIntAttribute("type"), 
					node.getChild("start").getValue(), 
					node.getChild("end").getValue()
					);
			project.addEntityLink(l);
		}
		
		XMLNode packageLinks[] = root.getNodes("package-link");
		for (XMLNode node : packageLinks) {
			Link l = new PackageLink(
					node.getIntAttribute("type"), 
					node.getChild("start").getValue(), 
					node.getChild("end").getValue()
					);
			project.addEntityLink(l);
		}
	}
}
