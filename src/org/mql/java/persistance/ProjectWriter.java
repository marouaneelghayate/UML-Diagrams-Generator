package org.mql.java.persistance;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mql.java.models.Association;
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
		
	
		for(Association a : project.getAssociations()) {
			root.appendChild(getAssociationNode(a));
		}
		
		root.save();
	}
	
	private void appendPackage(Package p,XMLNode parent) {
		XMLNode pkgNode = root.createNode("package");
		pkgNode.setAttribute("id", p.getName());
		parent.appendChild(pkgNode);
		for (Package pkg : p.getSubPackages()) {
			appendPackage(pkg,pkgNode);
		}
		for (Entity wrapper : p.getClasses()) {
			appendClass(wrapper,pkgNode);
		}
		
	}
	
	
	private void appendClass(Entity wrapper,XMLNode parent) {
		if(wrapper == null) {
			return ;
		}
		if(wrapper.getSuperClass() == null && parent == null) { // les classes externe n'ont ni parent ni package			
			addExternalClass(wrapper);
			return ;
		}
		
		
		XMLNode node = root.createNode(wrapper.getType());
		node.setAttribute("id", wrapper.getFullName());
		node.setAttribute("scope", wrapper.getScope());
		
		parent.appendChild(node);
		
		XMLNode name = root.createNode("name");
		name.setValue(wrapper.getName());
		
		node.appendChild(name);

		for (Field f : wrapper.getFields()) {
			XMLNode field = root.createNode("field");
			field.setAttribute("type", f.getType().getName());
			field.setValue(f.getName());
			node.appendChild(field);
		}
		
		for (Method m : wrapper.getMethods()) {
			XMLNode method = root.createNode("method");
			method.setAttribute("type", m.getReturnType().getName());
			method.setValue(m.getName() + "()");
			node.appendChild(method);
		}
		
		appendClass(wrapper.getSuperClass(), null);
		
		for(Entity cw : wrapper.getAggregates()) {
			appendClass(cw, null);
		}
		
		for(Entity cw : wrapper.getComponants()) {
			appendClass(cw, null);
		}
		for(Entity cw : wrapper.getInterfaces()) {
			appendClass(cw,null);
		}
		
	}
		
	private void addExternalClass(Entity wrapper) {
		if(wrapper == null) {
			return ;
		}
		XMLNode exists = root.querySelector(wrapper.getType(), "id", wrapper.getFullName());
		if(exists != null) {
			return ;
		}
		XMLNode packageNode = root.querySelector("package", "id", wrapper.getPackageName());
		if(packageNode == null) {
			packageNode = root.createNode("package");
			packageNode.setAttribute("id", wrapper.getPackageName());
		}
		
		
		root.appendChild(packageNode);
		appendClass(wrapper, packageNode);
	}
	
	private XMLNode getAssociationNode(Association a) {
		XMLNode node = root.createNode("association");
		node.setAttribute("type", a.getType());
		
		
		XMLNode start = root.createNode("start");
		start.setValue(a.getStart());
		
		
		XMLNode end = root.createNode("end");
		end.setValue(a.getEnd());
		
		node.appendChild(start);
		node.appendChild(end);
		return node;
	}

}
