package org.mql.java.ui.panels;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mql.java.models.Container;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.ui.components.PackageComponent;

public class PackagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Project project;
	private int cols, rows, hgap = 50, vgap = 50;

	public PackagePanel(Project p) {
		this.project = p;
		//cols - rows <= 1 for square grid
		cols = (int)Math.ceil(Math.sqrt(project.getPackages().size()));
		rows = (int)Math.floor(Math.sqrt(project.getPackages().size()));
		
		setLayout(new GridLayout(rows, cols, hgap, vgap));
		setBorder(new EmptyBorder(20, 20, 0, 0));
		
		addComponants(project);
	}
	
	private void addComponants(Container c) {
		for(Package p: c.getPackages()) {
			add(new PackageComponent(p));
		}
	}

	

}
