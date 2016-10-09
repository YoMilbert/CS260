package jpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SearchByPublisherPanel extends JPanel{
	
	JComboBox publishers;
	
	public SearchByPublisherPanel(){
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500, 500));
	}

}
