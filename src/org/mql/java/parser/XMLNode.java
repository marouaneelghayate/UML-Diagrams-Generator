package org.mql.java.parser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLNode {
	private Node node;
	private XMLNode children[] = new XMLNode[0]; 
	private String source;
	private Document document;
	private static XMLNode instance;
	
	
	public static XMLNode newDefaultInstance() {
		instance = new XMLNode();
		return instance;
	}
	
	private XMLNode() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document =  builder.newDocument();
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}

	}
	
	public XMLNode(String source) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document =  builder.parse(source);
			
			Node node = document.getFirstChild();
			while(node.getNodeType() != Node.ELEMENT_NODE) {
				node = node.getNextSibling();
				
			}
			setNode(node);
			
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
	}
	
	public XMLNode(Node node) {
		setNode(node);
	}
	
	public void setNode(Node node) {
		this.node = node;
		extractChildren();
	}
	
	private void extractChildren() {
		NodeList list = node.getChildNodes();
		LinkedList<XMLNode> nodes = new LinkedList<XMLNode>();
		for (int i = 0; i < list.getLength(); i++) {
			if(list.item(i).getNodeType() == Node.ELEMENT_NODE)
				//System.out.println(list.item(i).getNodeName());
				nodes.add(new XMLNode(list.item(i)));
		}
		children = new XMLNode[nodes.size()];
		nodes.toArray(children);
	}
	
	public void setDocumentElement(String name) {
		node = document.createElement(name);
		document.appendChild(node);
	}
	
	
	public XMLNode createNode(String name) {
		Node item = document.createElement(name);
		return new XMLNode(item);
	}
	
	public void setSource(String src) {
		source = src;
	}
	
	
	public Node getNode() {
		return node;
	}
	
	public XMLNode[] getChildren() {
		return children;
	}
	
	public String getName() {
		return node.getNodeName();
	}

	public XMLNode getChild(String name) {
		for (XMLNode child : children) {
			if(child.isNamed(name))
				return child;
		}
		return null;
	}
	
	public boolean isNamed(String name) {
		return node.getNodeName().equals(name);
	}
	
	
	public String getValue() {
		Node child = node.getFirstChild();
		if(child != null && child.getNodeType() == Node.TEXT_NODE)
			return child.getNodeValue();
		return "";
	}
	
	
	public void setValue(String value) {
		node.setTextContent(value);
	}
	
	public String getAttribute(String name) {
		return ((Element)node).getAttribute(name);
	}
	
	public int getIntAttribute(String name) {
		String s = getAttribute(name);
		int value = -1; 
		try {
			value = Integer.parseInt(s);
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		return value;
	}
	
	public void setAttribute(String name, Object value) {
		((Element)node).setAttribute(name, "" + value);
	}
	
	public void appendChild(XMLNode child) {
		if(child == null)
			return;
		node.appendChild(child.getNode());
		extractChildren();
	}
	

	public XMLNode[] getNodes(String nodeName) {
		List<XMLNode> nodes = new LinkedList<XMLNode>();
		for (int i = 0; i < children.length; i++) {
			if(children[i].isNamed(nodeName))
				nodes.add(children[i]);
		}
		return nodes.toArray(new XMLNode[nodes.size()]);
	}
	
	
	public void removeChild(XMLNode child) {
		node.removeChild(child.node);
	}
	
	//trouver un element a partir de son (name) et  la valeur (value) de son attribut (attribute)
	public XMLNode querySelector(String name, String attribute, String value) {
		NodeList list = document.getElementsByTagName(name);
		for(int i = 0; i < list.getLength(); i++ ) {
			if(value.equals(((Element)list.item(i)).getAttribute(attribute))) {
				return new XMLNode(list.item(i));
			}
		}
		return null;
	}

	public void save() {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult result = new StreamResult(new File(source));
			transformer.transform(domSource, result);
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
	}
}
