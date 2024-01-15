package org.mql.java.ui.panels;



import java.awt.Dimension;

import javax.swing.JPanel;

import org.mql.java.models.Entity;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.ui.componants.ClassComponant;

public class ClassPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public ClassPanel(Project project) {
		for(Package p : project.getPackages()) {
			addComponants(p);
		}
	}
	
	private void addComponants(Package pkg) {
		for(Package p : pkg.getSubPackages()) {
			addComponants(p);
		}
		for(Entity wrapper : pkg.getClasses()) {
			add(new ClassComponant(wrapper));
			
		}
	}

	
	public Dimension getPreferredSize() {
		
		return new Dimension(1500, 1500);
	}
	
	

}
