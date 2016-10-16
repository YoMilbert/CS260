package jpanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dao.HenryDAO;

public class SearchByAuthorPanel extends JPanel{
	
	private static final long serialVersionUID = -8960689784128525788L;

	HenryDAO henryDAO;
	
	JComboBox<String> authors;
	JComboBox<String> books;
	JTextArea price;
	JList<String> branchData;
	
	public SearchByAuthorPanel(Connection conn){
		henryDAO = new HenryDAO(conn);
		
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(500, 500));
		//Add authors
		Vector<String> authorNames = henryDAO.getAuthorNames();
		authors = new JComboBox<String>(authorNames);
		authors.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<String> authors = (JComboBox<String>) e.getSource();
					String author = (String) authors.getSelectedItem();
					refillBooks(author);
				}
			});
		books = new JComboBox<String>(authorNames);
		books.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<String> books = (JComboBox<String>) e.getSource();
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
