package org.mql.java.ui.componants;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;

import javax.swing.JPanel;

import org.mql.java.models.ClassWrapper;

public class ClassComponant extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClassWrapper wrapper;
	private int margin = 10;
	private int width = 150,height = 150;
	private final int TITLE_OFFSET = 20;

	public ClassComponant(ClassWrapper wrapper) {
		this.wrapper = wrapper;
		
	}
	
	public ClassComponant(ClassWrapper wrapper, int margin) {
		this(wrapper);
		this.margin = margin;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.red);
		FontMetrics metrics = g.getFontMetrics();
		if("external".equals(wrapper.getScope())){
			//g.drawString(wrapper.getFullName(), margin + 10 , margin + TITLE_OFFSET - (int)rect.getHeight()/2);
		}
		Rectangle2D rect = g.getFontMetrics().getStringBounds(wrapper.getName(), g);
		g.drawString(wrapper.getName(), margin + width/2 - (int)(rect.getWidth()/2) , margin + TITLE_OFFSET - (int)rect.getHeight()/2);
		int i = 0;
		for(Field f : wrapper.getFields()) {
			String fieldString = f.getType().getName() + " : " + f.getName();
			Rectangle2D r = g.getFontMetrics().getStringBounds(fieldString, g);
			if(r.getWidth() > width) {
				width = (int)r.getWidth() + 20;
			}
			g.drawString(f.getName(), margin + width/2 - (int)(r.getWidth()/2), margin + TITLE_OFFSET + (int)r.getHeight()/2 + i * 20);
			i++;
			
//			g.drawRect(margin, margin, width, wrapper.getFields().length * 20 + TITLE_OFFSET);
//			g.drawLine(margin, margin + TITLE_OFFSET, margin + width, margin + TITLE_OFFSET);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(2*margin + width, 2*margin + height);
	}

}
