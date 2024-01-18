package org.mql.java.ui.components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.mql.java.models.Package;

public class PackageComponent extends JPanel{
	private static final long serialVersionUID = 1L;
	private Package pkg;
	private int cols, rows, hgap = 50, vgap = 50;

	public PackageComponent(Package p) {
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(250 ,180);
	}
	
	
}
