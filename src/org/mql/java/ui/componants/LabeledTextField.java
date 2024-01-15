package org.mql.java.ui.componants;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabeledTextField extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JTextField textField;
	private JPanel container;
	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel l1) {
		this.label = l1;
	}

	public JTextField getTextField() {
		return textField;
		
	}

	public LabeledTextField(String label,int size) {
		container = new JPanel();
		container.setLayout(new FlowLayout(FlowLayout.LEFT));
		label = label.replace(":", "") + " : ";
		
		this.label = new JLabel(label);
		textField = new JTextField(size);
		
		container.add(this.label);
		container.add(textField);
		add(container);
	}
	
	public LabeledTextField(String label, int size, int labelSize) {
		this(label,size);
		JLabel l1 = (JLabel) getComponent(0);
		l1.setPreferredSize(new Dimension(labelSize,l1.getPreferredSize().height));
	}
	
	public JPanel getContainerPanel() {
		return container;
	}

}
