package org.mql.java.ui.componants;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mql.java.models.Entity;

public class ClassComponant extends JPanel {
	private static final long serialVersionUID = 1L;
	private Entity wrapper;


	public ClassComponant(Entity wrapper) {
		this.wrapper = wrapper;
		
		
		setLayout(new BorderLayout());
		setBackground(new Color(255,255,230));
		if(wrapper.getScope() == Entity.EXTERNAL) {
			addExternalClass();
		}
		else {
			addInternalClass();
		}
	}
	
	private void addInternalClass() {
		JPanel namePanel = new  JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		namePanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black),new EmptyBorder(0, 5, 0, 5)));
		namePanel.setOpaque(false);
		
		String type = wrapper.getType();
		if(!"class".equals(type)) {
			JLabel typeLabel = new JLabel("<<" + type + ">>");
			typeLabel.setAlignmentX(CENTER_ALIGNMENT);
			namePanel.add(typeLabel);
			setBackground(new Color(230,243,255));
		}
		
		JLabel nameLabel = new JLabel(wrapper.getName());
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		namePanel.add(nameLabel);
		
		
		add(namePanel, BorderLayout.NORTH);
		
		JPanel classBody = new JPanel();
		classBody.setLayout(new BorderLayout());
		classBody.setOpaque(false);
		
		Field fields[] = wrapper.getFields();
		if(fields.length  != 0) {
			JPanel fieldsPanel = new JPanel();
			fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
			fieldsPanel.setOpaque(false);
			for(Field f: fields) {
				String labelText = getModifier(f.getModifiers()) + " " + f.getType().getSimpleName() + " : " + f.getName();
				JLabel label = new JLabel(labelText);
				label.setBorder(new EmptyBorder(2,0,2,0));
				fieldsPanel.add(label);
			}
			fieldsPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(0, 5, 0, 5)));
			classBody.add(fieldsPanel, BorderLayout.NORTH);
		}
		
		Method methods[] = wrapper.getMethods();
		if(methods.length != 0) {			
			JPanel methodsPanel = new JPanel();
			methodsPanel.setLayout(new BoxLayout(methodsPanel, BoxLayout.Y_AXIS));
			methodsPanel.setOpaque(false);
			for(Method m : methods) {
				String labelText = getModifier(m.getModifiers()) + " " + m.getReturnType().getSimpleName() + " : " + m.getName() + "()";
				JLabel label = new JLabel(labelText);
				label.setBorder(new EmptyBorder(2,0,2,0));
				methodsPanel.add(label);
			}
			methodsPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(0, 5, 0, 5)));
			classBody.add(methodsPanel, BorderLayout.CENTER);
		}
		
		add(classBody, BorderLayout.SOUTH);
		
	}
	
	private void addExternalClass() {
		
		JPanel namePanel = new  JPanel(new FlowLayout(FlowLayout.CENTER));
		namePanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black),new EmptyBorder(0, 5, 0, 5)));
		namePanel.setOpaque(false);

		JLabel nameLabel = new JLabel(wrapper.getFullName());
		namePanel.add(nameLabel);
		add(namePanel, BorderLayout.NORTH);
		
		
		JPanel filler = new JPanel();
		filler.setBorder(new LineBorder(Color.black));
		filler.setOpaque(false);
		filler.setPreferredSize(new Dimension(120,110));
		add(filler, BorderLayout.CENTER);
	}
	
	
	private String getModifier(int modifier) {
		if(Modifier.isPublic(modifier)) {
			return "+";
		}
		else if(Modifier.isPrivate(modifier)) {
			return "-";
		}
		else if(Modifier.isProtected(modifier)) {
			return "~";
		}
		return " ";
	}
	
	
	
	
	
	
	
	

}
