package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import dto.Author;
import dto.Book;
import dto.Branch;
import dto.Publisher;

public class HenryDAO {
	static final String DB_URL = "jdbc:oracle:thin:@dario.cs.uwec.edu:1521:csdev";
	static final String USER = "HUPFAUSM2796";
	static final String PASS = "PDDDXEUM";
	
	Connection conn;
	
	public HenryDAO(Connection conn){
		this.conn = conn;
	}
	
	public static Connection createConnection(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);	
			}
		catch(SQLException sqle){
				sqle.printStackTrace();
			}
		return conn;
	}
	
	public Vector<Author> getAuthors(){
		Vector<Author> names = new Vector<Author>();
		
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT AUTHOR_NUM, AUTHOR_FIRST, AUTHOR_LAST FROM HENRY_AUTHOR WHERE AUTHOR_NUM IN (SELECT AUTHOR_NUM FROM HENRY_WROTE)";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int num = rs.getInt("AUTHOR_NUM");
				String first = rs.getString("AUTHOR_FIRST");
				String last = rs.getString("AUTHOR_LAST");
				names.addElement(new Author(num, first, last));
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return names;
	}
	
	public Vector<Book> getBooksForAuthor(int authorNum){
		Vector<Book> books = new Vector<Book>();
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT BOOK_CODE, TITLE FROM HENRY_BOOK WHERE BOOK_CODE IN (SELECT " +
						 "BOOK_CODE FROM HENRY_WROTE WHERE AUTHOR_NUM LIKE (SELECT" +
						 " AUTHOR_NUM FROM HENRY_AUTHOR WHERE AUTHOR_NUM = '" + authorNum + "'))";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String bookCode = rs.getString("BOOK_CODE");
				String bookTitle = rs.getString("TITLE");
				books.addElement(new Book(bookCode, bookTitle));
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return books;
	}
	
	public Vector<String> getCategories(){
		Vector<String> categories = new Vector<String>();
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT DISTINCT TYPE FROM HENRY_BOOK";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String bookCategory = rs.getString("TYPE");
				categories.addElement(bookCategory);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return categories;
	}
	
	public Vector<Book> getBooksForCategory(String category){
		Vector<Book> categoryBooks = new Vector<Book>();
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT BOOK_CODE, TITLE FROM HENRY_BOOK WHERE TYPE LIKE '" + category + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String bookCode = rs.getString("BOOK_CODE");
				String bookTitle = rs.getString("TITLE");
				categoryBooks.addElement(new Book(bookCode, bookTitle));
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return categoryBooks;
	}
	
	public Vector<Publisher> getPublishers(){
		Vector<Publisher> publishers = new Vector<Publisher>();
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT PUBLISHER_CODE, PUBLISHER_NAME FROM HENRY_PUBLISHER " + 
						 "WHERE PUBLISHER_CODE IN (SELECT PUBLISHER_CODE FROM HENRY_BOOK)";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String publisherCode = rs.getString("PUBLISHER_CODE");
				String publisherName = rs.getString("PUBLISHER_NAME");
				publishers.addElement(new Publisher(publisherCode, publisherName));
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return publishers;
	}
	
	public Vector<Book> getBooksForPublisher(String publisherCode){
		Vector<Book> publisherBooks = new Vector<Book>();
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT BOOK_CODE, TITLE FROM HENRY_BOOK WHERE PUBLISHER_CODE = '" + publisherCode + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String bookCode = rs.getString("BOOK_CODE");
				String bookTitle = rs.getString("TITLE");
				publisherBooks.addElement(new Book(bookCode, bookTitle));
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return publisherBooks;
	}
	
	public double getPriceForBook(String bookCode){
		double bookPrice = -1;
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT PRICE FROM HENRY_BOOK WHERE BOOK_CODE = '" + bookCode + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				bookPrice = rs.getDouble("PRICE");
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return bookPrice;
	}
	
	public Vector<Branch> getBranchDataForBook(String bookCode){
		Vector<Branch> branch = new Vector<Branch>();
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT BRANCH_NAME, ON_HAND FROM HENRY_INVENTORY INNER " +
						 "JOIN HENRY_BRANCH ON HENRY_INVENTORY.BRANCH_NUM = HENRY_BRANCH.BRANCH_NUM" + 
						 " WHERE HENRY_INVENTORY.BOOK_CODE = '" + bookCode + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String name = rs.getString("BRANCH_NAME");
				int quant = rs.getInt("ON_HAND");
				branch.addElement(new Branch(quant, name));
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return branch;
	}
}

