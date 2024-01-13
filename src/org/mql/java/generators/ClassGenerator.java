package org.mql.java.generators;


import org.mql.java.models.Project;
import org.mql.java.parser.XMLNode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mql.java.models.Association;
import org.mql.java.models.ClassWrapper;
import org.mql.java.models.Package;

public class ClassGenerator implements Generator{
	private static XMLNode root;
	private Project project;
	private String target;

	
	public ClassGenerator(Project project, String target) {
		super();
		this.project = project;
		this.target = target;
	}

	public void generate() {
		XMLNode.setSource(target);
		root = XMLNode.getNewDeaultInstance();
		root.createRootElement("project");
		root.setAttribute("path", project.getProjectPath());
		for (Package pkg : project.getPackages()) {
			discoverClasses(pkg);
		}
		for(Association a : project.getAssociations()) {
			root.appendChild(getAssociationNode(a));
		}
		root.save();
	}
	
	private void discoverClasses(Package p) {
		for (Package pkg : p.getSubPackages()) {
			discoverClasses(pkg);
		}
		for (ClassWrapper wrapper : p.getClasses()) {
			appendClassNode(wrapper);
		}
	}
	
	
	private void appendClassNode(ClassWrapper wrapper) {
		if(wrapper == null) {
			return ;
		}
		XMLNode exists = root.getElementById(wrapper.getFullName());
		if(exists != null) {
			return ;
		}
		appendClassNode(wrapper.getSuperClass());
		XMLNode node = root.createNode(wrapper.getType());
		node.setAttribute("id", wrapper.getFullName());
		
		
		XMLNode name = root.createNode("name");
		name.setValue(wrapper.getName());
		node.appendChild(name);
//		XMLNode superClass = root.createNode("super");
//		superClass.setValue(wrapper.getSuperClass().getName());
//		node.appendChild(superClass);
				
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
		
		for(ClassWrapper cw : wrapper.getAggregates()) {

			appendClassNode(cw);
		}
		
		for(ClassWrapper cw : wrapper.getComponants()) {
			appendClassNode(cw);
		}
		for(ClassWrapper cw : wrapper.getInterfaces()) {
			appendClassNode(cw);
		}
		root.appendChild(node);
		
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
