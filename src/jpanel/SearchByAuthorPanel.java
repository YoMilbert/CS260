package jpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import dto.Author;
import dto.Book;
import dto.Branch;

public class SearchByAuthorPanel extends JPanel{
	
	private static final long serialVersionUID = -8960689784128525788L;

	HenryDAO henryDAO;
	
	JComboBox<Author> authors;
	JComboBox<Book> books;
	JTextArea price;
	JList<Branch> branchData;
	
	public SearchByAuthorPanel(Connection conn){
		henryDAO = new HenryDAO(conn);
		
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(400, 300));
		this.setBackground(Color.WHITE);
		//Setup authors
		Vector<Author> authorsList = henryDAO.getAuthors();
		authors = new JComboBox<Author>(authorsList);
		authors.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<Author> authors = (JComboBox<Author>) e.getSource();
					Author author = (Author) authors.getSelectedItem();
					int authorNum = author.getNumber();
					refillBooks(authorNum);
				}
			});
		JTextArea authorText = new JTextArea("Author");
		authorText.setFont(new Font("Arial", 1, 13));
		//Setup remaining items
		books = new JComboBox<Book>();
		books.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<Book> books = (JComboBox<Book>) e.getSource();
					Book book = (Book) books.getSelectedItem();
					refillPrice(book.getCode());
					refillBranchData(book.getCode());
				}
			});
		JTextArea bookText = new JTextArea("Book");
		bookText.setFont(new Font("Arial", 1, 13));
		price = new JTextArea();
		price.setFont(new Font("Arial", 1, 12));
		JTextArea priceText = new JTextArea("Price");
		priceText.setFont(new Font("Arial", 1, 13));
		branchData = new JList<Branch>();
		JTextArea branchDataText = new JTextArea("Quantity");
		branchDataText.setFont(new Font("Arial", 1, 13));
		//Add all items
		Insets insets = new Insets(5, 6, 5, 6);
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(authorText, c);
		c = new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(authors, c);
		c = new GridBagConstraints(0, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(bookText, c);
		c = new GridBagConstraints(1, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(books, c);
		c = new GridBagConstraints(0, 2, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(priceText, c);
		c = new GridBagConstraints(1, 2, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(price, c);
		c = new GridBagConstraints(0, 3, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(branchDataText, c);
		c = new GridBagConstraints(1, 3, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(branchData, c);
		//Initialize the data
		refillBooks(authorsList.get(0).getNumber());
		Book book = (Book) books.getSelectedItem();
		refillPrice(book.getCode());
		refillBranchData(book.getCode());
	}
	
	/*
	 * Resets the books JComboBox to show books for the selected author.
	 */
	public void refillBooks(int authorNum){
		Vector<Book> bookItems = henryDAO.getBooksForAuthor(authorNum);
		DefaultComboBoxModel<Book> model = new DefaultComboBoxModel<Book>(bookItems);
		books.setModel(model);
		
		String bookCode = bookItems.get(0).getCode();
		refillPrice(bookCode);
		refillBranchData(bookCode);
	}
	
	/*
	 * Resets the price JTextArea to show the cost of the selected book.
	 */
	public void refillPrice(String bookCode){
		double cost = henryDAO.getPriceForBook(bookCode);
		String costFormatted = String.format("%.2f", cost);
		price.setText("$" + costFormatted);
	}
	
	/*
	 * Resets branchData to show the attributes of the selected book.
	 */
	public void refillBranchData(String bookCode){
		Vector<Branch> data = henryDAO.getBranchDataForBook(bookCode);
		branchData.setListData(data);
	}
}
