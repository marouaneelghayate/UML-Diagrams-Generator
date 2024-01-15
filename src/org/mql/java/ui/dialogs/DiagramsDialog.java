package org.mql.java.ui.dialogs;


import javax.swing.JDialog;

import org.mql.java.models.Project;
import org.mql.java.ui.panels.DiagramsPanel;

public class DiagramsDialog extends JDialog{
	private static final long serialVersionUID = 1L;

	public DiagramsDialog(Project p) {
		setTitle("Diagrammes UML");
		setContentPane(new DiagramsPanel(p));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
}
