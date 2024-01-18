package org.mql.java.ui.panels;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.mql.java.models.Project;

public class DiagramsPanel extends JTabbedPane{
	private static final long serialVersionUID = 1L;

	public DiagramsPanel(Project p) {
		addTab("Diagramme de de Classes", new JScrollPane(new ClassPanel(p)));
		addTab("Diagramme de Package", new JScrollPane(new PackagePanel(p)));
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1200, 700);
	}

}
