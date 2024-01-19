package org.mql.java.ui.dialogs;


import java.awt.Toolkit;

import javax.swing.JDialog;

import org.mql.java.models.Project;
import org.mql.java.ui.panels.DiagramsTabbedPanel;

public class DiagramsDialog extends JDialog{
	private static final long serialVersionUID = 1L;

	public DiagramsDialog(Project p) {
		
		setTitle("Diagrammes UML");
		setContentPane(new DiagramsTabbedPanel(p));
		pack();
		setIconImage(Toolkit.getDefaultToolkit().createImage("resources/icons/logo.png"));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
}
