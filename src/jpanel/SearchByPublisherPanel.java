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
import dto.Book;
import dto.Branch;
import dto.Publisher;

public class SearchByPublisherPanel extends JPanel{

	private static final long serialVersionUID = -465263078307482383L;

	HenryDAO henryDAO;
	
	JComboBox<Publisher> publishers;
	JComboBox<Book> books;
	JTextArea price;
	JList<Branch> branchData;
	
	public SearchByPublisherPanel(Connection conn){
		henryDAO = new HenryDAO(conn);
		
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(400, 300));
		this.setBackground(Color.WHITE);
		//Add authors
		Vector<Publisher> publishersList = henryDAO.getPublishers();
		publishers = new JComboBox<Publisher>(publishersList);
		publishers.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<Publisher> publishers = (JComboBox<Publisher>) e.getSource();
					Publisher publisher = (Publisher) publishers.getSelectedItem();
					refillBooks(publisher.getCode());
				}
			});
		JTextArea publishersText = new JTextArea("Publisher");
		publishersText.setFont(new Font("Arial", 1, 13));
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
		branchData = new JList();
		JTextArea branchDataText = new JTextArea("Quantity");
		branchDataText.setFont(new Font("Arial", 1, 13));
		//Add all items
		Insets insets = new Insets(5, 6, 5, 6);
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(publishersText, c);
		c = new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(publishers, c);
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
		//Initialize the remaining data
		refillBooks(publishersList.get(0).getCode());
		Book book = (Book) books.getSelectedItem();
		refillPrice(book.getCode());
		refillBranchData(book.getCode());
	}
	
	/*
	 * Resets the books JComboBox to show books for the selected author.
	 */
	public void refillBooks(String publisherCode){
		Vector<Book> bookItems = henryDAO.getBooksForPublisher(publisherCode);
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
		price.setText("$" + cost);
	}
	
	/*
	 * Resets branchData to show the attributes of the selected book.
	 */
	public void refillBranchData(String bookCode){
		Vector<Branch> data = henryDAO.getBranchDataForBook(bookCode);
		branchData.setListData(data);
	}
}
