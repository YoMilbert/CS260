package jpanel;

import java.awt.BorderLayout;
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
		
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(500, 500));
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
		books = new JComboBox<Book>();
		books.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<Book> books = (JComboBox<Book>) e.getSource();
					Book book = (Book) books.getSelectedItem();
					System.out.println(book.getCode());
					refillPrice(book.getCode());
					refillBranchData(book.getCode());
				}
			});
		this.add(publishers);
		this.add(books);
		price = new JTextArea();
		this.add(price);
		branchData = new JList();
		this.add(branchData);
	}
	
	/*
	 * Resets the books JComboBox to show books for the selected author.
	 */
	public void refillBooks(String publisherCode){
		Vector<Book> bookItems = henryDAO.getBooksForPublisher(publisherCode);
		DefaultComboBoxModel<Book> model = new DefaultComboBoxModel<Book>(bookItems);
		books.setModel(model);
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
