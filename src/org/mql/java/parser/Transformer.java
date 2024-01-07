package org.mql.java.parser;


import org.mql.java.models.Project;

public class Transformer {
	private static XMLNode root;

	public static void transform(Project project, String target) {
		XMLNode.setSource(target);
		root = XMLNode.getNewDeaultInstance();
		root.createRootElement("project");
		root.setAttribute("path", project.getProjectPath());
		for (org.mql.java.models.Package pkg : project.getPackages()) {
			root.appendChild(transformPackage(pkg));
		}
		root.save();
		
	}
	
	private static XMLNode transformPackage(org.mql.java.models.Package p) {
		if(p == null)
			return null;
		XMLNode pkgNode = root.createNode("package");
		pkgNode.setAttribute("id", p.getName());
		for (org.mql.java.models.Package pkg : p.getSubPackages()) {
			pkgNode.appendChild(transformPackage(pkg));
		}
		for (Class<?> cls : p.getClasses()) {
			pkgNode.appendChild(transformClass(cls));
		}
		
		return pkgNode;
	}
	
	private static XMLNode transformClass(Class<?> cls) {
		XMLNode node;
		if(cls.isAnnotation()) {
			node = root.createNode("annotation");

		}
		else if(cls.isInterface()) {
			node = root.createNode("interface");

		}
		else {			
			node = root.createNode("class");
		}
		node.setAttribute("id", cls.getName());
		Class<?> sup = cls.getSuperclass();
		if(sup != null) {
			XMLNode superNode = root.getElementById(sup.getName());
			if(superNode == null) {
				superNode = root.createNode("class");
				superNode.setAttribute("id", sup.getName());
			}
			superNode.appendChild(node);
			return superNode;
		}
		return node;
	}

}
