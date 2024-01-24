package org.mql.java.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.mql.java.models.Project;
import org.mql.java.persistance.ProjectLoader;
import org.mql.java.persistance.ProjectWriter;
import org.mql.java.scanner.ProjectScanner;
import org.mql.java.ui.components.LabeledTextField;
import org.mql.java.ui.windows.DiagramsFrame;
import org.mql.java.ui.windows.ErrorDialog;

public class FormPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton generate,choose;
	private JLabel label;
	private LabeledTextField labeledTextField;
	
	public FormPanel() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(5, 10, 5, 10));
		
		labeledTextField = new LabeledTextField("Chemin du projet", 30);
		add(labeledTextField);
		
		generate = new JButton("chercher");
		generate.addActionListener(this);
		generate.setAlignmentX(CENTER_ALIGNMENT);
		generate.setFocusable(false);
		add(generate);
		
				
		JLabel ou = new JLabel("ou");
		ou.setAlignmentX(CENTER_ALIGNMENT);
		ou.setBorder(new EmptyBorder(10,0,10,0));
		add(ou);
		
		
		choose = new JButton("choisir un dossier");
		choose.setAlignmentX(CENTER_ALIGNMENT);
		choose.addActionListener(this);
		choose.setFocusable(false);
		add(choose);
		
		label = new JLabel("Aucun projet avec ce nom !");
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setBorder(new EmptyBorder(10,0,10,0));
		label.setVisible(false);
		add(label);
	}
	
	public void actionPerformed(ActionEvent e) {
		String path = labeledTextField.getTextField().getText();
		if(e.getSource().equals(choose)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setApproveButtonText("Selectionner");
			fileChooser.setDialogTitle("Selectionner un dossier");
			int val = fileChooser.showOpenDialog(FormPanel.this);
			if(val == JFileChooser.APPROVE_OPTION) {
				path = fileChooser.getSelectedFile().getAbsolutePath();
				labeledTextField.getTextField().setText(path);
			}
		}
		
		if("".equals(path)) {
			label.setVisible(true);
			JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
			frame.pack();
			return;
		}
		
		label.setVisible(false);
		ProjectScanner scanner = new ProjectScanner(path);
		Project project = scanner.scan();
		
		if(project == null) {
			new ErrorDialog("Projet introuvable");
			return;
		}
		ProjectWriter writer = new ProjectWriter();
		writer.write(project, "resources/xml/diagram.xml");
		ProjectLoader loader = new ProjectLoader();
		project = loader.load("resources/xml/diagram.xml");
		
		new DiagramsFrame(project);
		
		if(project == null) {
			System.out.println("projet introuvable ");
			return ; 
		}
		
		
	}
	
}
