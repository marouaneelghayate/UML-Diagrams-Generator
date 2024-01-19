package org.mql.java.ui.dialogs;

import javax.swing.JDialog;


import org.mql.java.ui.panels.ErrorPanel;

public class ErrorDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	public ErrorDialog(String message) {
		
		setTitle("Erreur");
		setContentPane(new ErrorPanel(message));
		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}
	
	

}
