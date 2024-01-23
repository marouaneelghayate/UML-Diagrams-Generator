package org.mql.java.parsers;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mql.java.ui.windows.ErrorDialog;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXFormatter extends DefaultHandler{

	private String formatted = "";
	private String indent = "";
	
	public SAXFormatter(String path) {
		SAXParserFactory factory = SAXParserFactory.newDefaultInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(path, this);
		} catch (Exception e) {
			new ErrorDialog(e.getMessage());
		} 
	}
	
	public String getFormattedString() {
		return formatted;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String str = indent + "<" + qName;
		
		for(int i = 0; i < attributes.getLength(); i++) {
			str += " " + attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"";
		}
		str += ">\n";
		formatted+= str;
		indent += "\t";
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		indent = indent.substring(1);
		String str = indent + "</" + qName + ">\n";
		formatted += str;

	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str =indent +  new String(ch, start, length) + "\n";
		formatted += str;
	}



}
