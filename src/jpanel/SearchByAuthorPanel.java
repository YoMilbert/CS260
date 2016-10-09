package jpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import dao.HenryDAO;

public class SearchByAuthorPanel extends JPanel{
	
	private class AuthorSelector{
		
		JComboBox authors;
		
		private AuthorSelector(authors){
			
		}
		
		
	}
	
	HenryDAO henryDAO;
	
	JComboBox authors;
	JComboBox books;
	
	public SearchByAuthorPanel(Connection conn){
		henryDAO = new HenryDAO(conn);
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500, 500));
		//Add authors
		Vector<String> authorNames = henryDAO.getAuthorNames();
		authors = new JComboBox<String>(authorNames);
		
		
		this.add("authors", authors);
		this.refillBooks(authorNames.get(0));
	}
	
	/*
	 * Removes current books JComboBox and adds a new one based upon
	 * the selected author.
	 */
	public void refillBooks(String authorName){
		Vector<String> bookNames = henryDAO.getBookNames();
		books = new JComboBox<String>(bookNames);
		this.remove(books);
		this.add("books", books);
	}
}
