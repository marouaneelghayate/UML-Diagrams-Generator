package org.mql.java.ui.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import org.mql.java.models.Container;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.ui.components.PackageComponent;
import org.mql.java.ui.windows.ErrorDialog;

public class PackagePanel extends JPanel implements Diagram{
	private static final long serialVersionUID = 1L;
	private Project project;
	private int cols, rows, hgap = 50, vgap = 50;


	public PackagePanel(Project p) {
		this.project = p;
		
		//cols - rows <= 1 for square grid
		cols = (int)Math.ceil(Math.sqrt((double)project.getPackages().size()));
		rows = (int)Math.floor(Math.sqrt((double)project.getPackages().size()));
		
		setLayout(new GridLayout(rows, cols, hgap, vgap));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setBackground(Color.white);
		setOpaque(true);
		addComponents(project);
	}
	
	private void addComponents(Container c) {
		for(Package p: c.getPackages()) {
			add(new PackageComponent(p));
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
