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

public class SearchByCategoryPanel extends JPanel{

	private static final long serialVersionUID = 3251075343457163481L;

	HenryDAO henryDAO;
	
	JComboBox<String> categories;
	JComboBox<Book> books;
	JTextArea price;
	JList<Branch> branchData;
	
	public SearchByCategoryPanel(Connection conn){
		henryDAO = new HenryDAO(conn);
		
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(400, 300));
		this.setBackground(Color.WHITE);
		//Add categories
		Vector<String> categoriesList = henryDAO.getCategories();
		categories = new JComboBox<String>(categoriesList);
		categories.setPrototypeDisplayValue(new String(" XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"));
		categories.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JComboBox<String> categories = (JComboBox<String>) e.getSource();
					String category = (String) categories.getSelectedItem();
					refillBooks(category);
				}
			});
		JTextArea categoriesText = new JTextArea("Category");
		categoriesText.setFont(new Font("Arial", 1, 13));
		//Setup remaining items
		books = new JComboBox<Book>();
		books.setPrototypeDisplayValue(new Book("", " XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"));
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
		branchData.setPrototypeCellValue(new Branch(1, " XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"));
		JTextArea branchDataText = new JTextArea("Quantity");
		branchDataText.setFont(new Font("Arial", 1, 13));
		//Add all items
		Insets insets = new Insets(5, 6, 5, 6);
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(categoriesText, c);
		c = new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		this.add(categories, c);
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
		refillBooks(categoriesList.get(0));
		Book book = (Book) books.getSelectedItem();
		refillPrice(book.getCode());
		refillBranchData(book.getCode());
	}
	
	/*
	 * Resets the books JComboBox to show books for the selected author.
	 */
	public void refillBooks(String category){
		Vector<Book> bookItems = henryDAO.getBooksForCategory(category);
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
