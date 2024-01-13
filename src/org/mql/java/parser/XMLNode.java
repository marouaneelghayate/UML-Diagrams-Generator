package org.mql.java.parser;

import java.io.File;
import java.util.LinkedList;

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


//une façade
public class XMLNode {
	private Node node;
	private XMLNode children[]; //elements
	private static Document document;
	private static XMLNode instance;
	private static String source;
	
	
	public static XMLNode getNewDeaultInstance() {
		instance = new XMLNode();
		return instance;
	}
	
	private XMLNode() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document =  builder.newDocument();
			save();
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}

	}
	
	public void createRootElement(String name) {
		node = document.createElement(name);
		document.appendChild(node);
	}
	
	private XMLNode(String source) {
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
	
	public static XMLNode getInstance() {
		if(instance == null) {
			instance =  new XMLNode(source);
		}
		return instance;
	}
	
	public static void setSource(String src) {
		source = src;
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
	
	public Node getNode() {
		return node;
	}
	
	public XMLNode[] getChildren() {
		return children;
	}
	
	public String getName() { //delegate method
		return node.getNodeName();
	}
	
	public boolean isNamed(String name) {
		return node.getNodeName().equals(name);
	}
	
	public XMLNode getChild(String name) {
		extractChildren();
		for (XMLNode child : children) {
			if(child.isNamed(name))
				return child;
		}
		return null;
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
	public void setAttribute(String name, String value) {
		((Element)node).setAttribute(name, value);
		if("ID".equals(name) || "id".equals(value)) {
			((Element)node).setIdAttribute(name, true);
		}
	}
	
	
	public String getAttribute(String name) {
		Node att = node.getAttributes().getNamedItem(name);
		if(att == null) return "";
		return att.getNodeValue();
	}
	
	public int getIntAttribute(String name) {
		String s = getAttribute(name);
		int value = 0; //valeur par defaut
		try {
			value = Integer.parseInt(s);
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		return value;
	}
	
	public void appendChild(XMLNode child) {
		node.appendChild(child.getNode());
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
	
	public XMLNode createNode(String name) {
		Node item = document.createElement(name);
		
		return new XMLNode(item);
	}
	
	public XMLNode[] getNodes(String nodeName) {
		NodeList list = document.getElementsByTagName(nodeName);
		LinkedList<XMLNode> nodes = new LinkedList<XMLNode>();
		for (int i = 0; i < list.getLength(); i++) {
			if(list.item(i).getNodeType() == Node.ELEMENT_NODE)
				nodes.add(new XMLNode(list.item(i)));
		}
		return nodes.toArray(new XMLNode[nodes.size()]);
	}
	
	
	public void removeChild(XMLNode child) {
		node.removeChild(child.node);
	}
	
	
	public XMLNode getElementById(String id) {
		Node documentElement = document.getDocumentElement();
		XMLNode doc = new XMLNode(documentElement);
		if(((Element)documentElement).hasAttribute("id") & ((Element)documentElement).getAttribute("id").equals(id)) {
			return new XMLNode(documentElement);
		}
		for(XMLNode child : doc.getChildren()) {
			if(child.getAttribute("id").equals(id)) {
				return child;
			}
		}
		return null;
	}

}
