package org.mql.java.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ErrorPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ErrorPanel(String message) {
		setLayout(new BorderLayout());
		setBackground(Color.white);
		setBorder(new EmptyBorder(20,20,20,20));
		
		JPanel head = new JPanel();
		head.setOpaque(false);
		head.setBorder(new EmptyBorder(0,0,10,0));
		head.add(new JLabel(message + "!"));
		add(head, BorderLayout.NORTH);
		
		add(new ImagePanel("resources/icons/not_found.png"));
		
	
	}
}
