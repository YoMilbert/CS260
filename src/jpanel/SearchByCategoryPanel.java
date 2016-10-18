package jpanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dao.HenryDAO;

public class SearchByCategoryPanel extends JPanel{

HenryDAO henryDAO;
	
	JComboBox categories;
	
	
	public SearchByCategoryPanel(Connection conn){
		henryDAO = new HenryDAO(conn);
		
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(500, 500));
		//Add authors
		Vector<String> categoriesList = henryDAO.getCategories();
		categories = new JComboBox<String>(categoriesList);
		categories.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<String> categories = (JComboBox<String>) e.getSource();
					String category = (String) categories.getSelectedItem();
					//refillBooks(author);
				}
			});
		
		this.add(categories);
	}
	
	/*
	 * Resets the books JComboBox to show books for the selected author.
	 */
	/*
	public void refillBooks(String authorName){
		Vector<String> bookNames = henryDAO.getBooksForAuthor();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(bookNames);
		books.setModel(model);
	}
	*/
}
