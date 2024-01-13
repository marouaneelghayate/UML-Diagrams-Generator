package org.mql.java.generators;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mql.java.models.ClassWrapper;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.parser.XMLNode;

public class PackageGenerator implements Generator {
	private Project project;
	private String target;
	private XMLNode root;

	public PackageGenerator(Project project, String target) {
		super();
		this.project = project;
		this.target = target;
	}


	@Override
	public void generate() {
		XMLNode.setSource(target);
		root = XMLNode.getNewDeaultInstance();
		root.createRootElement("project");
		root.setAttribute("path", project.getProjectPath());
		for (Package pkg : project.getPackages()) {
			XMLNode pkgNode = root.createNode("package");
			pkgNode.setAttribute("id", pkg.getName());
			root.appendChild(pkgNode);
			transformPackage(pkg,pkgNode);
		}
		root.save();
	}

	private void transformPackage(Package p,XMLNode pkgNode) {
		if(p == null)
			return;
		
		for (Package pkg : p.getSubPackages()) {
			XMLNode child = root.createNode("package");
			child.setAttribute("id", pkg.getName());
			pkgNode.appendChild(child);
			transformPackage(pkg,child);
		}
		for (ClassWrapper wrapper : p.getClasses()) {
			transformClass(wrapper,pkgNode);
		}
		
	}
	
	private void transformClass(ClassWrapper wrapper,XMLNode pkg) {
		XMLNode node = root.createNode(wrapper.getType());
		node.setAttribute("id", wrapper.getFullName());
		
		
		
		
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
		
		pkg.appendChild(node);
	}


}
