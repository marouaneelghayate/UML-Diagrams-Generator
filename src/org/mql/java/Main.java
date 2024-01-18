package org.mql.java;


import java.awt.Toolkit;

import javax.swing.JFrame;

import org.mql.java.models.Project;
import org.mql.java.persistance.ProjectLoader;
import org.mql.java.persistance.ProjectWriter;
import org.mql.java.scanner.ProjectScanner;
import org.mql.java.ui.dialogs.DiagramsDialog;
import org.mql.java.ui.dialogs.ErrorDialog;
import org.mql.java.ui.panels.FormPanel;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;


	public Main() {
		try {			
			init();
		}
		catch(Exception e) {
			new ErrorDialog(e.getMessage());
		}
	}
	private void init() {
		setResizable(false);
		setContentPane(new FormPanel());
		setSize(400,300);
		setIconImage(Toolkit.getDefaultToolkit().createImage("resources/icons/logo.png"));
		pack();
		setTitle("UML Diagrams Generator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new Main();
	}

}
