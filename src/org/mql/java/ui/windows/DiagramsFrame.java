package org.mql.java.ui.windows;


import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.mql.java.models.Project;
import org.mql.java.ui.components.ExportMenu;
import org.mql.java.ui.panels.DiagramsTabbedPanel;

public class DiagramsFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public DiagramsFrame(Project p) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(new ExportMenu());
		setJMenuBar(menuBar);
		setTitle("Diagrammes UML");
		setContentPane(new DiagramsTabbedPanel(p));
		pack();
		setIconImage(Toolkit.getDefaultToolkit().createImage("resources/icons/logo.png"));
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}

	
}
