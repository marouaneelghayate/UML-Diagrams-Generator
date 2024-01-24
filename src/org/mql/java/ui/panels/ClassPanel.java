package org.mql.java.ui.panels;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mql.java.models.Entity;
import org.mql.java.models.EntityLink;
import org.mql.java.models.Link;
import org.mql.java.models.Project;
import org.mql.java.ui.components.ClassComponent;
import org.mql.java.ui.windows.ErrorDialog;

public class ClassPanel extends JPanel implements Diagram{
	private static final long serialVersionUID = 1L;
	//entity name to component index mapping for links
	private Map<String, Integer> mappings;
	
	//different color for each link
	private List<Color> colors;
	
	
	private Project project;
	private int cols, rows;
	private int hgap = 50, vgap = 50;
	private int colWidth, rowHeight;

	

	public ClassPanel(Project p) {
		this.project = p;
		mappings = new HashMap<String,Integer>();
		colors = new Vector<Color>();
		
		//for square-ish grid
		rows = (int)Math.floor((double) Math.sqrt(p.getInternalEntities().size()));
		cols = (int)Math.ceil((double) Math.sqrt(p.getInternalEntities().size()));
		
		setLayout(new GridLayout(rows, cols, hgap, vgap));
		setBorder(new EmptyBorder(vgap, hgap, vgap, hgap));
		setOpaque(true);
		setBackground(Color.white);
		

		
		addComponents();
	}
	
	private void addComponents() {
		//add entities
		for(Entity wrapper : project.getInternalEntities()) {
			JPanel panel = new JPanel();
			panel.add(new ClassComponent(wrapper));
			panel.setOpaque(false);
			add(panel);
			//store entity/component index
			mappings.put(wrapper.getFullName(), getComponents().length - 1);
			Color c = new Color((int)(Math.random()*Integer.MAX_VALUE));
			while(colors.contains(c)) {
				c = new Color((int)(Math.random()*Integer.MAX_VALUE));
			}
			colors.add(c);
		}
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		if(getComponents().length == 0)
			return;
		colWidth = getComponent(0).getWidth() + vgap;
		rowHeight =  getComponent(0).getHeight() + hgap;
		//then draw links
		for(Link l : project.getEntityLinks()) {
			addLink(l, g2d);
		}
		
	}
	
	private void addLink(Link l, Graphics2D g2d) {
		//return if link to/from external class
		if(mappings.get(l.getStart()) == null || mappings.get(l.getEnd()) == null) {
			return ;
		}
		
		int type = l.getType();
		
		//start and end indexes
		int startKey = mappings.get(l.getStart());
		int endKey = mappings.get(l.getEnd());
		
		//mix colors of start and end entity
		Color c = new Color(colors.get(startKey).getRGB() + colors.get(endKey).getRGB());
		g2d.setColor(c);
		
		//parent components
		JPanel startPanel = (JPanel)getComponent(startKey);
		JPanel endPanel = (JPanel)getComponent(endKey);
		
		//actual component
		JPanel startEntity = (JPanel) startPanel.getComponent(0);
		JPanel endEntity = (JPanel) endPanel.getComponent(0);
		
		//center points
		int x1 = startPanel.getLocation().x + startEntity.getLocation().x + startEntity.getSize().width / 2;
		int y1 = startPanel.getLocation().y + startEntity.getLocation().y + startEntity.getSize().height / 2;
		int x2 = endPanel.getLocation().x + endEntity.getLocation().x + endEntity.getSize().width / 2;
		int y2 = endPanel.getLocation().y + endEntity.getLocation().y + endEntity.getSize().height / 2;
		
		
		if(type == EntityLink.IMPLEMENTATION) {
			//dashed line
		    float[] dash = { 10.0f };
		    g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		}
		else {
			//simple line
			g2d.setStroke(new BasicStroke(1.5f));
		}
		
		drawLink(new Point(x1, y1), new Point(x2, y2), startPanel, endPanel, l, g2d);
		
		
	}
	
	private void drawLink(Point start,Point end, JPanel startPanel, JPanel endPanel, Link l,Graphics2D g2d) {
		//vertical and horizontal directions
		int dx = start.x - end.x;
		int dy = start.y - end.y;
		
		//offset each Link to reduce overlay 
		int randomOffset = g2d.getColor().getRGB()%25;
		
		//which direction is the steepest
		if(Math.abs(dx) >= Math.abs(dy)) {
			int x1 = randomOffset, x2 = randomOffset, pointerx;
			start.y += randomOffset;
			end.y -= randomOffset;
			//go right from start and left from end
			if(dx < 0) {
				x1 += startPanel.getLocation().x + startPanel.getWidth() + vgap/2;
				x2 += endPanel.getLocation().x - vgap/2;
				pointerx = endPanel.getLocation().x + endPanel.getComponent(0).getLocation().x;
			}
			//go left from start and right from end 
			else {
				x1 +=  startPanel.getLocation().x - vgap/2;
				x2 += endPanel.getLocation().x + endPanel.getWidth() + vgap/2;
				pointerx = endPanel.getLocation().x + endPanel.getComponent(0).getLocation().x + endPanel.getComponent(0).getWidth();
			}
			//draw links
			g2d.drawLine(start.x, start.y, x1, start.y);
			g2d.drawLine(end.x, end.y, x2, end.y);
			
			//update points
			start.x = x1;
			end.x = x2;
			
			Point p = new Point(pointerx, end.y);
			drawPointer(p, dx, dy, l, g2d);
			//if entities are horizontally adjacent
			if(start.x == end.x) {	
				g2d.drawLine(start.x, start.y, end.x, end.y);
				return ;
			}
			//otherwise connect link to grid
			else {
				//if entities in same row go to top
				if(Math.abs(dy) < getComponent(0).getHeight()) {
					dy = 0;
				}
				Point p1 = getNearestVerticalVertex(start, dy);
				p1.y += randomOffset;
				g2d.drawLine(start.x, start.y, p1.x, p1.y);
				start = p1;
				
				Point p2 = getNearestVerticalVertex(end, -dy);
				p2.y += randomOffset;
				g2d.drawLine(end.x, end.y, p2.x, p2.y);
				end = p2;
			}
			
		}
		else {
			int y1 = randomOffset, y2 = randomOffset, pointery;
			start.x += randomOffset;
			end.x -= randomOffset;
			//go down from start and up from end
			if(dy < 0) {
				y1 +=  startPanel.getLocation().y + startPanel.getHeight() + hgap/2;
				y2 += endPanel.getLocation().y - hgap/2;
				pointery = endPanel.getLocation().y + endPanel.getComponent(0).getLocation().y;

			}
			//go up from start and down from end
			else {
				y1 +=  startPanel.getLocation().y - hgap/2;
				y2 += endPanel.getLocation().y + endPanel.getHeight() + hgap/2;
				pointery = endPanel.getLocation().y + endPanel.getComponent(0).getHeight();
			}
			g2d.drawLine(start.x, start.y, start.x,y1);
			g2d.drawLine(end.x, end.y, end.x, y2 );
			start.y = y1;
			end.y = y2;
			
			Point p = new Point(end.x, pointery);
			drawPointer(p, dx, dy, l, g2d);
			//if entities are vertically adjacent
			if(start.y == end.y) {								
				g2d.drawLine(start.x, start.y, end.x, end.y);
				return ;
			}
			else {
				if(Math.abs(dx) < colWidth) {
					dx = 0;
				}
				Point p1 = getNearestHorizontalVertex(start, dx);
				p1.x += randomOffset;
				g2d.drawLine(start.x, start.y, p1.x, p1.y);
				start = p1;
				
				Point p2 = getNearestHorizontalVertex(end, -dx);
				p2.x += randomOffset;
				g2d.drawLine(end.x, end.y, p2.x, p2.y);
				end = p2;
			}
			
		}
		
		drawPath(start, end, g2d);
	
	}
	

	private void drawPointer(Point p, int dx, int dy, Link l, Graphics2D g2d) {
		int xUnit = 1;
		int yUnit = 1;
		//link from entity to itself goes from left to right 
		if(dx == 0 && dy==0) {
			dx = 1;
		}
		//find out the direction of the pointer 
		if(dx != 0) {
			xUnit = dx/Math.abs(dx);
		}
		if(dy != 0) {
			yUnit = dy/Math.abs(dy);
		}
		//Pointer should be drawn horizontally
		if(Math.abs(dx) > Math.abs(dy)) {		
			if(l.getType() == EntityLink.IMPLEMENTATION) {
				//draw lines in specified directions
				g2d.drawLine(p.x,  p.y, p.x + xUnit*10,p.y + yUnit*10);
				g2d.drawLine(p.x,  p.y, p.x + xUnit*10,p.y - yUnit*10);
			}
			else if(l.getType() == EntityLink.EXTENSION) {
				int x[] = {p.x, p.x + xUnit*10, p.x + xUnit*10};
				int y[] = {p.y, p.y + yUnit*10, p.y - yUnit*10};
				Color c = g2d.getColor();
				g2d.drawPolygon(x, y, 3); //draw triangle outline
				g2d.setColor(new Color(255, 255, 230)); //Yellow triangle for extension
				g2d.fillPolygon(x, y, 3);
				g2d.setColor(c);
			}
			else if(l.getType() == EntityLink.COMPOSITION) {
				int x[] = {p.x, p.x + xUnit*10, p.x + xUnit*20, p.x + xUnit*10};
				int y[] = {p.y, p.y + yUnit*10, p.y, p.y - yUnit*10};
				g2d.fillPolygon(x, y, 4);
			}
			else if(l.getType() == EntityLink.AGGREGATION) {
				int x[] = {p.x, p.x + xUnit*10, p.x + xUnit*20, p.x + xUnit*10};
				int y[] = {p.y, p.y + yUnit*10, p.y, p.y - yUnit*10};
				g2d.drawPolygon(x, y, 4);
				Color c = g2d.getColor();
				g2d.setColor(Color.white);
				g2d.fillPolygon(x, y, 4);
				g2d.setColor(c);
			}
		}
		//Pointer should be drawn vertically
		else {
			if(l.getType() == EntityLink.IMPLEMENTATION) {
				g2d.drawLine(p.x,  p.y, p.x + xUnit*10,p.y + yUnit*10);
				g2d.drawLine(p.x,  p.y, p.x - xUnit*10,p.y + yUnit*10);

			}
			else if(l.getType() == EntityLink.AGGREGATION) {
				int x[] = {p.x, p.x + xUnit*10, p.x , p.x - xUnit*10};
				int y[] = {p.y, p.y + yUnit*10, p.y + yUnit*20, p.y + yUnit*10};
				g2d.drawPolygon(x, y, 4);
				Color c = g2d.getColor();
				g2d.setColor(Color.white);
				g2d.fillPolygon(x, y, 4);
				g2d.setColor(c);
			}
			else if(l.getType() == EntityLink.COMPOSITION){
				int x[] = {p.x, p.x + xUnit*10, p.x , p.x - xUnit*10};
				int y[] = {p.y, p.y + yUnit*10, p.y + yUnit*20, p.y + yUnit*10};
				g2d.fillPolygon(x, y, 4);
			}
			else {
				int x[] = {p.x, p.x + xUnit*10, p.x - xUnit*10};
				int y[] = {p.y, p.y + yUnit*10, p.y + yUnit*10};
				Color c = g2d.getColor();
				g2d.drawPolygon(x,y,3);
				g2d.setColor(new Color(255,255,230));
				g2d.fillPolygon(x, y, 3);
				g2d.setColor(c);
			}
		}
	}
	
	
	private Point getNearestVerticalVertex(Point start, int dy) {
		int y = start.y;
		//go down
		if(dy < 0) {			
			y = (int)Math.ceil((double)start.y/ rowHeight) * rowHeight + vgap/2;

		}
		//go up
		else{
			y = (int)Math.floor((double)start.y/ rowHeight) * rowHeight + vgap/2;
		}
		
		return new Point(start.x,y);
	}
	
	private Point getNearestHorizontalVertex(Point start, int dx) {
		int x = start.x;
		//go right
		if(dx < 0) {			
			x = (int)Math.ceil((double)start.x / colWidth) * colWidth + vgap/2;
		}
		//go left
		else {
			x = (int)Math.floor(start.x/ colWidth) * colWidth + vgap/2;
		}
		
		return new Point(x,start.y);
	}
	//find path through grid of intersection points to avoid other entities
	private void drawPath(Point start, Point end, Graphics2D g2d) {
		int dx = start.x - end.x ;
		int dy = start.y - end.y;
		while(!start.equals(end)) {
			//go vertically or horizontally 
			if(Math.abs(dx) > Math.abs(dy)) {
				int x = colWidth;
				if(dx >= 0) {
					x = -x;
				}
				g2d.drawLine(start.x, start.y, start.x + x, start.y);
				start.x += x;
				
			}
			else {
				int y = rowHeight ;
				if(dy >= 0) {					
					y = -y;
				}
				g2d.drawLine(start.x, start.y, start.x, start.y + y);
				start.y += y;
			}
			dx = start.x - end.x ;
			dy = start.y - end.y;
			
		}
	}
	
	public void export(String path) {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		printAll(g);
		g.dispose();
		try { 
			String split[] =  project.getProjectPath().split("/");
			String filePath = path + "/" + split[split.length - 1] + getClass().getSimpleName().replace("Panel", "") + ".png";
		    ImageIO.write(image, "png", new File(filePath)); 
		    JOptionPane.showMessageDialog(null, "Exporté avec succès!");
		} catch (Exception e) {
			new ErrorDialog("échec! opération annulée");
		}
	}

}
