package org.mql.java.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.mql.java.ui.panels.Diagram;
import org.mql.java.ui.windows.ErrorDialog;

public class ExportMenu extends JMenu implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JMenuItem expClass, expPackage, expXML;

	public ExportMenu() {
		super("Exporter");
		expClass = new JMenuItem("Exporter diagramme de classes");
		expPackage = new JMenuItem("Exporter diagramme de packages");
		expXML = new JMenuItem("Exporter le document XML");
		
		expClass.addActionListener(this);
		expPackage.addActionListener(this);
		expXML.addActionListener(this);
		add(expClass);
		add(expPackage);
		add(expXML);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setApproveButtonText("Selectionner");
		fileChooser.setDialogTitle("Selectionner un fichier ou un dossier");
		int val = fileChooser.showOpenDialog(this);
		if(val == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getAbsolutePath();
		}
		if("".equals(path)) {
			new ErrorDialog("Aucun fichier ou dossier avec ce nom ");
			return;
		}
		File f = new File(path);
		if(!f.exists()) {
			new ErrorDialog("fichier ou dossier introuvable");
		}
		if(e.getSource().equals(expXML)) {
			if(f.isDirectory()) {
				f = new File(f.getAbsoluteFile() + "/diagram.xml");
			}
			new File("resources/xml/diagram.xml").renameTo(f);
			return;
		}
		Diagram d;
		if(e.getSource().equals(expClass)) {
			JTabbedPane tabbed = (JTabbedPane)frame.getContentPane();
			JScrollPane scroll = (JScrollPane)tabbed.getComponentAt(0);
			d =  (Diagram)(scroll.getViewport().getView());
		}
		else {
			JTabbedPane tabbed = (JTabbedPane)frame.getContentPane();
			JScrollPane scroll = (JScrollPane)tabbed.getComponentAt(1);
			d =  (Diagram)(scroll.getViewport().getView());
		}
		d.export(f.getAbsolutePath());
		
		
	}

}
