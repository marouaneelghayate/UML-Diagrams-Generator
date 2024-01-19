package org.mql.java.ui.panels;


import javax.swing.JTextArea;

import org.mql.java.parsers.SAXLoader;

public class XMLArea extends JTextArea{
	private static final long serialVersionUID = 1L;

	public XMLArea() {
		setEditable(false);
		setTabSize(2);
		writeFormattedXML();
	}

	private void writeFormattedXML() {
		SAXLoader loader = new SAXLoader("resources/xml/diagram.xml");
		String formatted = loader.getFormattedString();
		setText(formatted);
	}
}
