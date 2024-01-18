package org.mql.java.ui.panels;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mql.java.models.Entity;
import org.mql.java.models.EntityLink;
import org.mql.java.models.Link;
import org.mql.java.models.Project;
import org.mql.java.ui.components.ClassComponent;

public class ClassPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> mappings;
	private int currentKey = 0;
	private Project project;
	private int cols = 4, rows;
	private int hgap = 50, vgap = 50;

	public ClassPanel(Project p) {
		this.project = p;
		mappings = new HashMap<String,Integer>();
		rows = (int)Math.ceil((double) Math.sqrt(p.getInternalEntities().size()));
		cols = (int)Math.floor((double) Math.sqrt(p.getInternalEntities().size()));
		addComponants();
	}
	
	private void addComponants() {
		setLayout(new GridLayout(rows, cols, hgap, vgap));
		
		for(Entity wrapper : project.getInternalEntities()) {
			JPanel panel = new JPanel();
			panel.add(new ClassComponent(wrapper));
			panel.setOpaque(false);
			panel.setBackground(Color.cyan);
			add(panel);
			mappings.put(wrapper.getFullName(), currentKey++);
		}
		
		
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for(Link l : project.getEntityLinks()) {
				addLink(l, g2d);
				
		}
	}
	
	private void addLink(Link l, Graphics2D g2d) {
		g2d.setColor(new Color((int)(Math.random() * Integer.MAX_VALUE)));
		int type = l.getType();
		if(mappings.get(l.getStart()) == null || mappings.get(l.getEnd()) == null) {
			return ;
		}
		int startKey = mappings.get(l.getStart());
		int endKey = mappings.get(l.getEnd());
		JPanel startPanel = (JPanel)getComponent(startKey);
		JPanel endPanel = (JPanel)getComponent(endKey);
		JPanel startEntity = (JPanel) startPanel.getComponent(0);
		JPanel endEntity = (JPanel) endPanel.getComponent(0);
		int x1 = startPanel.getLocation().x + startEntity.getLocation().x + startEntity.getSize().width / 2;
		int y1 = startPanel.getLocation().y + startEntity.getLocation().y + startEntity.getSize().height / 2;
		int x2 = endPanel.getLocation().x + endEntity.getLocation().x + endEntity.getSize().width / 2;
		int y2 = endPanel.getLocation().y + endEntity.getLocation().y + endEntity.getSize().height / 2;
		
		
		if(type == EntityLink.IMPLEMENTATION) {
			
		    float[] dash = { 10.0f };
		    g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		}
		else {
			g2d.setStroke(new BasicStroke(1.5f));
		}
		
		drawLink(
				new Point(x1, y1), 
				new Point(x2, y2), 
				startPanel, 
				endPanel, 
				g2d
				);
		
		
	}
	
	private void drawLink(Point start,Point end, JPanel startPanel, JPanel endPanel, Graphics2D g2d) {
		int dx = start.x - end.x ;
		int dy = start.y - end.y;
		if(Math.abs(dx) > Math.abs(dy)) {
			int x1, x2;
			if(dx < 0) {
				x1 = startPanel.getLocation().x + startPanel.getWidth() + vgap/2;
				x2 = endPanel.getLocation().x - vgap/2;
			}
			else {
				x1 =  startPanel.getLocation().x - vgap/2;
				x2 = endPanel.getLocation().x + endPanel.getWidth() + vgap/2;
			}
			g2d.drawLine(start.x, start.y, x1, start.y);
			g2d.drawLine(end.x, end.y, x2, end.y);
			start.x = x1;
			end.x = x2;
			
		}
		else if (dy != 0){
			int y1 = start.y, y2 = end.y;
			if(dy < 0) {
				y1 =  startPanel.getLocation().y + startPanel.getHeight() + hgap/2;
				y2 = endPanel.getLocation().y - hgap/2;
			}
			else {
				y1 =  startPanel.getLocation().y - hgap/2;
				y2 = endPanel.getLocation().y + endPanel.getHeight() + hgap/2;
			}
			g2d.drawLine(start.x, start.y, start.x,y1);
			g2d.drawLine(end.x, end.y, end.x, y2 );
			start.y = y1;
			end.y = y2;
		}
		
		g2d.drawLine(start.x, start.y, end.x, end.y);
	
	}
	
	
	

}
