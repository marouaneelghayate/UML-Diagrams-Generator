package org.mql.java;


import java.awt.Toolkit;

import javax.swing.JFrame;

import org.mql.java.ui.panels.FormPanel;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;


	public Main() {
		init();
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
