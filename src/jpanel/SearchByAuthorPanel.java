package jpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dao.HenryDAO;

public class SearchByAuthorPanel extends JPanel{
	
	HenryDAO henryDAO;
	
	JComboBox authors;
	JComboBox books;
	JTextArea price;
	JList branchData;
	
	public SearchByAuthorPanel(Connection conn){
		henryDAO = new HenryDAO(conn);
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500, 500));
		//Add authors
		Vector<String> authorNames = henryDAO.getAuthorNames();
		authors = new JComboBox<String>(authorNames);
		authors.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox authors = (JComboBox) e.getSource();
					String author = (String) authors.getSelectedItem();
					refillBooks(author);
				}
			});
		books = new JComboBox<String>(authorNames);
		books.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox books = (JComboBox) e.getSource();
					String book = (String) books.getSelectedItem();
					refillPrice(book);
					refillBranchData(book);
				}
			});
		this.add(authors);
		this.add(books);
	}
	
	/*
	 * Resets the books JComboBox to show books for the selected author.
	 */
	public void refillBooks(String authorName){
		Vector<String> bookNames = henryDAO.getBooksForAuthor();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(bookNames);
		books.setModel(model);
	}
	
	/*
	 * Resets the price JTextArea to show the cost of the selected book.
	 */
	public void refillPrice(String authorName){
		String cost = henryDAO.getPriceForBook();
		price.setText(cost);
	}
	
	/*
	 * Resets branchData to show the attributes of the selected book.
	 */
	public void refillBranchData(String authorName){
		Vector<String> data = henryDAO.getBranchDataForBook();
		branchData.setListData(data);
	}
}
