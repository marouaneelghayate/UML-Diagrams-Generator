package org.mql.java.ui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private ImageIcon icon;
	
	public ImagePanel(String path) {
		icon = new ImageIcon(path);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(icon.getImage(), getWidth()/2 - icon.getIconWidth()/2, getHeight()/2 - icon.getIconHeight()/2, null);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(icon.getIconWidth(), icon.getIconHeight());
	}

}
