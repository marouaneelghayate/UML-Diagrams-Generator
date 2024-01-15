package org.mql.java.ui.dialogs;




import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	public ErrorDialog(String message) {
		setTitle("Erreur");
		JPanel panel = new JPanel();
		JLabel label = new JLabel(message);
		
		panel.add(label);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		panel.setAlignmentY(CENTER_ALIGNMENT);
		//panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		setContentPane(panel);
		pack();
		setSize(350,250);
		setLocationRelativeTo(getParent());
		

		setVisible(true);
	}

}
