package org.mql.java.ui.panels;





import java.awt.GridLayout;

import javax.swing.JPanel;

import org.mql.java.models.Container;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.ui.components.PackageComponent;

public class PackagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Project project;

	public PackagePanel(Project p) {
		this.project = p;
		
		//setLayout(new GridLayout(rows, cols, 50, 50));
		add(new PackageComponent(new Package("testing")));
		//addComponants(project);
	}
	
	private void addComponants(Container c) {
		for(Package p: c.getPackages()) {
			add(new PackageComponent(p));
		}
	}

	

}
