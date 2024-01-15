package org.mql.java.ui.dialogs;

import javax.swing.JDialog;

import org.mql.java.ui.panels.DiagramsPanel;

public class DiagramsDialog extends JDialog implements Runnable{
	private static final long serialVersionUID = 1L;
	private Thread runner;

	public DiagramsDialog() {
		runner = new Thread(this);
		runner.start();
	}

	@Override
	public void run() {
		setTitle("Diagrammes UML");
		setContentPane(new DiagramsPanel());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

}
