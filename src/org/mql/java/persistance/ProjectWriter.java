package org.mql.java.persistance;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mql.java.models.EntityLink;
import org.mql.java.models.Link;
import org.mql.java.models.Entity;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.parser.XMLNode;

public class ProjectWriter{
	private XMLNode root;

	public ProjectWriter() {
	}


	public void write(Project project, String target) {
		root = XMLNode.newDefaultInstance();
		root.setSource(target);
		root.setDocumentElement("project");
		root.setAttribute("path", project.getProjectPath());
		for (Package pkg : project.getPackages()) {
			appendPackage(pkg,root);
		}
		
		for(Entity e : project.getInternalEntities()) {
			root.appendChild(getEntityNode(e));
		}
		for(Entity e : project.getExternalEntities()) {
			root.appendChild(getEntityNode(e));
		}
		for(Link l : project.getEntityLinks()) {
			root.appendChild(getLinkNode(l));
		}
		
		root.save();
	}
	
	private void appendPackage(Package p,XMLNode parent) {
		XMLNode pkgNode = root.createNode("package");
		pkgNode.setAttribute("id", p.getName());
		parent.appendChild(pkgNode);
		for (Package pkg : p.getPackages()) {
			appendPackage(pkg,pkgNode);
		}
		
		
	}
	
	
	private XMLNode getEntityNode(Entity e) {
		if(e == null) {
			return null;
		}
		XMLNode node = root.createNode(e.getType());
		node.setAttribute("id", e.getFullName());
		node.setAttribute("scope", e.getScope());
		
		if(e.getScope() == Entity.EXTERNAL) {
			return node;
		}
		
		
		XMLNode name = root.createNode("name");
		name.setValue(e.getName());
		
		node.appendChild(name);

		for (Field f : e.getFields()) {
			XMLNode field = root.createNode("field");
			field.setAttribute("type", f.getType().getName());
			field.setValue(f.getName());
			node.appendChild(field);
		}
		
		for (Method m : e.getMethods()) {
			XMLNode method = root.createNode("method");
			method.setAttribute("type", m.getReturnType().getName());
			method.setValue(m.getName() + "()");
			node.appendChild(method);
		}
		
		return node;
	}
	
	
	private XMLNode getLinkNode(Link l) {
		XMLNode node;
		if(l instanceof EntityLink) {			
			node = root.createNode("entity-link");
		}
		else {
			node =root.createNode("package-link");
		}
		node.setAttribute("type", l.getType());
		
		
		XMLNode start = root.createNode("start");
		start.setValue(l.getStart());
		
		
		XMLNode end = root.createNode("end");
		end.setValue(l.getEnd());
		
		node.appendChild(start);
		node.appendChild(end);
		return node;
	}

}
