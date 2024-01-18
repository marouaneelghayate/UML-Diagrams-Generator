package org.mql.java.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ErrorPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ErrorPanel(String message) {
		setLayout(new BorderLayout());
		JPanel head = new JPanel();
		head.setOpaque(false);
		head.setBorder(new EmptyBorder(0,0,10,0));
		head.add(new JLabel(message + "!"));
		add(head, BorderLayout.NORTH);
		setBackground(Color.white);
		add(new ImagePanel("resources/icons/not_found.png"));
		
		setBorder(new EmptyBorder(20,20,20,20));
	
	}
}
