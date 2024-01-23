package org.mql.java.ui.panels;


import javax.swing.JTextArea;

import org.mql.java.parsers.SAXFormatter;

public class XMLArea extends JTextArea{
	private static final long serialVersionUID = 1L;

	public XMLArea() {
		setEditable(false);
		setTabSize(2);
		writeFormattedXML();
	}

	private void writeFormattedXML() {
		SAXFormatter loader = new SAXFormatter("resources/xml/diagram.xml");
		String formatted = loader.getFormattedString();
		setText(formatted);
	}
}
