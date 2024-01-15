package org.mql.java.persistance;

import java.io.File;

import org.mql.java.models.Association;
import org.mql.java.models.Entity;
import org.mql.java.models.Container;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.parser.XMLNode;
import org.mql.java.util.SourceClassLoader;

public class ProjectLoader {
	private Project project;
	private XMLNode root;
	public ProjectLoader() {
		
	}
	
	public Project load(String path) {
		File f = new File(path);
		if(!f.exists()) {
			return null;
		}
		root = new XMLNode(path);
		project = new Project(root.getAttribute("path"));
		loadPackages(root, project);
		loadAssociations(project);
		return project;
	}
	
	private void loadPackages(XMLNode parent, Container c) {
		XMLNode packages[] = parent.getNodes("package");
		for (XMLNode pkgNode : packages) {
			Package p = new Package(pkgNode.getAttribute("id"));
			c.addPackage(p);
			loadPackages(pkgNode,p);
			
		}
		XMLNode classes[] = parent.getNodes("class");
		for (XMLNode classNode : classes) {
				((Package)c).addClass(getClassWrapper(classNode));
		}
		
		XMLNode annotations[] = parent.getNodes("annotation");
		for (XMLNode classNode : annotations) {
			((Package)c).addClass(getClassWrapper(classNode));
		}
		
		XMLNode interfaces[] = parent.getNodes("interface");
		for (XMLNode classNode : interfaces) {
			((Package)c).addClass(getClassWrapper(classNode));
		}
	}
	
	private Entity getClassWrapper(XMLNode node) {
		int scope = node.getIntAttribute("scope");
		Class<?> cls;
		Entity wrapper = null;
		if(scope == Entity.INTERNAL) {
			cls = SourceClassLoader.loadClass(project.getProjectPath() + "/bin", node.getAttribute("id"));
			wrapper = new Entity(cls, true);
		}
		else {
			try {
				cls = Class.forName(node.getAttribute("id"));
				wrapper = new Entity(cls);
			} catch (Exception e) {
			}
		}
		return wrapper;
	}
	private void loadAssociations(Project project) {
		XMLNode associations[] = root.getNodes("association");
		for (XMLNode association : associations) {
			Association a = new Association(
					association.getAttribute("type"), 
					association.getChild("start").getValue(), 
					association.getChild("end").getValue()
					);
			project.addAssociation(a);
		}
	}
}
