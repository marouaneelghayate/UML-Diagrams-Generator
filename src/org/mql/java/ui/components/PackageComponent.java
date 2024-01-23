package org.mql.java.ui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.Box;
import javax.swing.JPanel;
import org.mql.java.models.Package;

public class PackageComponent extends JPanel{
	private static final long serialVersionUID = 1L;
	private Package pkg;
	private int cols, rows, hgap = 50, vgap = 50;

	public PackageComponent(Package p) {
		this.pkg = p;
		if(p.getPackages().size() == 0) {
			add(Box.createRigidArea(new Dimension(250,180)));
		}
		else {
			//cols - rows <= 1 for square grid
			cols = (int)Math.floor(Math.sqrt(p.getPackages().size()));
			rows = (int)Math.ceil(Math.sqrt(p.getPackages().size()));
			
			setLayout(new GridLayout(rows, cols, hgap, vgap));
			for(Package pkg : p.getPackages()) {
				add(new PackageComponent(pkg));
			}
		}
		setOpaque(false);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(new Font("Arial", Font.BOLD, 16));
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(pkg.getName(), g2d);
		g2d.setStroke(new BasicStroke(2f));
		g2d.setColor(new Color(45, 88, 134)); //grey - blue
		
		//top rectangle
		g2d.fillRoundRect(0, 0, (int)rect.getWidth() + 10, (int)rect.getHeight() + 10, 5, 5);
		
		//bottom rectangle
		g2d.drawRoundRect(1, (int)rect.getHeight() + 10, getWidth() - 3, getHeight() - (int)rect.getHeight() - 10 - 2, 4, 4);
		
		//hide gap
		g2d.drawLine(1, (int)rect.getHeight(), 1, (int)rect.getHeight() + 20);
		
		//draw name in center
		g2d.setColor(Color.white);
		g2d.drawString(pkg.getName(),5, (int)rect.getHeight());
	}
	
	
	
	
}
