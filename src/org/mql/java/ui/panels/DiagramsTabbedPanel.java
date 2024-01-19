package org.mql.java.ui.panels;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.mql.java.models.Project;

public class DiagramsTabbedPanel extends JTabbedPane{
	private static final long serialVersionUID = 1L;

	public DiagramsTabbedPanel(Project p) {
		addTab("Diagramme de de Classes", new JScrollPane(new ClassPanel(p)));
		addTab("Diagramme de Package", new JScrollPane(new PackagePanel(p)));
		addTab("Document XML", new JScrollPane(new XMLArea()));
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1200, 700);
	}

}
