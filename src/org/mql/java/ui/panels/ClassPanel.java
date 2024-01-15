package org.mql.java.ui.panels;

import javax.swing.JPanel;

import org.mql.java.models.ClassWrapper;
import org.mql.java.ui.componants.ClassComponant;

public class ClassPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public ClassPanel() {
		ClassWrapper wrapper = new ClassWrapper(String.class);
		wrapper.discover();
		add(new ClassComponant(wrapper));
	}

}
