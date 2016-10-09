package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

public class HenryDAO {
	static final String DB_URL = "jdbc:oracle:thin:@dario.cs.uwec.edu:1521:csdev";
	
	static final String USER = "HUPFAUSM2796";
	static final String PASS = "PDDDXEUM";
	
	
	
	String authorName;
	static Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		HenryDAO test = new HenryDAO(conn);
		test.createConnection();
		System.out.println(test.GetAuthorNames());		
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
	
	public Vector<String> GetAuthorNames(){
		Vector<String> names = new Vector<String>();
		
		try{
			stmt = conn.createStatement();
			String sql = "SELECT AUTHOR_FIRST, AUTHOR_LAST FROM HENRY_AUTHOR";
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				String name_first = rs.getString("AUTHOR_FIRST");
				String name_last = rs.getString("AUTHOR_LAST");
				names.addElement(name_first + " " + name_last);
			}
		} 
			catch(SQLException sqle){
			sqle.printStackTrace();
		}
		/*
		java.util.Iterator<String> it = names.iterator();
	
		while (it.hasNext())
		{
		System.out.println(it.next());
		}
		 */
		return names;
		
	}
	
	
}

