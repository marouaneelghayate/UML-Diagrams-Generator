package org.mql.java.ui.panels;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

public class DiagramsPanel extends JTabbedPane{
	private static final long serialVersionUID = 1L;

	public DiagramsPanel() {
		addTab("Diagrammede de Classes", new ClassPanel());
		addTab("Diagramme de Package", new PackagePanel());
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1200, 700);
	}

}
