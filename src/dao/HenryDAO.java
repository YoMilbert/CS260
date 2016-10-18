package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import dto.Author;

public class HenryDAO {
	static final String DB_URL = "jdbc:oracle:thin:@dario.cs.uwec.edu:1521:csdev";
	
	static final String USER = "HUPFAUSM2796";
	static final String PASS = "PDDDXEUM";
	
	String book_price;
	
	String book_title = "Beloved";
	
	String book_category = "FIC";
	String book_publisher = "Plume";
	
	String book_location;
	String location_name;
	String book_quant;
	
	String authorName;
	static Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		HenryDAO test = new HenryDAO(conn);
		test.createConnection();
		//System.out.println(test.getAuthorNames());
		//System.out.println(test.getBooksForAuthor());
		//System.out.println(test.getPriceForBook());
		//System.out.println(test.getCategories());
		//System.out.println(test.getPublishers());
		//System.out.println(test.getBooksForCategory());
		//System.out.println(test.getBooksForPublisher());
		System.out.println(test.getBranchDataForBook());
	}
	
	public HenryDAO(Connection conn){
		this.conn = conn;
	}
	
	public static Connection createConnection()
	{
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
			stmt = conn.createStatement();
			String sql = "SELECT AUTHOR_NUM, AUTHOR_FIRST, AUTHOR_LAST FROM HENRY_AUTHOR";
			rs = stmt.executeQuery(sql);
		
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
	
	public Hashtable<String, String> getBooksForAuthor(int authorNum){
		Hashtable<String, String> books = new Hashtable<String, String>();
		
		try{
			stmt = conn.createStatement();
			String sql = "SELECT BOOK_CODE, TITLE FROM HENRY_BOOK WHERE BOOK_CODE IN (SELECT " +
						 "BOOK_CODE FROM HENRY_WROTE WHERE AUTHOR_NUM LIKE (SELECT" +
						 " AUTHOR_NUM FROM HENRY_AUTHOR WHERE AUTHOR_NUM = '" + authorNum + "'))";
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				String book_code = rs.getString("BOOK_CODE");
				String book_title = rs.getString("TITLE");
				books.put(book_code, book_title);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return books;
	}
	
	public String getPriceForBook(){
	
		try{
			stmt = conn.createStatement();
			String sql = "SELECT PRICE FROM HENRY_BOOK WHERE TITLE = '"+book_title+"'";
			rs = stmt.executeQuery(sql);
	
			while(rs.next()){
				String book_price = rs.getString("PRICE");
			
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
	
		return book_price;
	}
	
	public Vector<String> getCategories(){
		Vector<String> categories = new Vector<String>();
		
		try{
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT TYPE FROM HENRY_BOOK";
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				book_category = rs.getString("TYPE");
				categories.addElement(book_category);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return categories;
	}
	
	public Vector<String> getPublishers(){
		Vector<String> publishers = new Vector<String>();
		
		try{
			stmt = conn.createStatement();
			String sql = "SELECT PUBLISHER_NAME FROM HENRY_PUBLISHER";
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				book_publisher = rs.getString("PUBLISHER_NAME");
				publishers.addElement(book_publisher);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return publishers;
	}
	
	public Vector<String> getBooksForCategory(){
		Vector<String> categoryBooks = new Vector<String>();
		
		try{
			stmt = conn.createStatement();
			String sql = "SELECT TITLE FROM HENRY_BOOK WHERE TYPE LIKE '"+book_category+"'";
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				String book_title = rs.getString("TITLE");
				categoryBooks.addElement(book_title);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return categoryBooks;
	}
	
	public Vector<String> getBooksForPublisher(){
		Vector<String> publisherBooks = new Vector<String>();
		
		try{
			stmt = conn.createStatement();
			String sql = "SELECT TITLE FROM HENRY_BOOK WHERE PUBLISHER_CODE IN (SELECT PUBLISHER_CODE FROM HENRY_PUBLISHER WHERE PUBLISHER_NAME = '"+book_publisher+"')";
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				String book_title = rs.getString("TITLE");
				publisherBooks.addElement(book_title);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return publisherBooks;
	}
	
	public Hashtable<String, Integer> getBranchDataForBook(){
		Hashtable<String, Integer> branch = new Hashtable<String, Integer>();
		
		try{
			stmt = conn.createStatement();
			String sql = "SELECT BRANCH_NAME, ON_HAND FROM HENRY_INVENTORY INNER JOIN HENRY_BRANCH ON HENRY_INVENTORY.BRANCH_NUM = HENRY_BRANCH.BRANCH_NUM WHERE HENRY_INVENTORY.BOOK_CODE = (SELECT BOOK_CODE FROM HENRY_BOOK WHERE TITLE = '"+book_title+"')";
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				//book_location = rs.getString("BRANCH_LOCATION");
				String name = rs.getString("BRANCH_NAME");
				int quant = rs.getInt("ON_HAND");
				branch.put(name, quant);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return branch;
	}
	
}

