package jpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SearchByCategoryPanel extends JPanel{

	JComboBox categories;
	
	public SearchByCategoryPanel(){
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500, 500));
	}
}
