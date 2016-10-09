package dao;

import java.sql.*;

public class HenryDAO {
	static final String DB_URL = "jdbc:oracle:thin:@dario.cs.uwec.edu:1521:csdev";
	
	static final String USER = "HUPFAUSM2796";
	static final String PASS = "PDDDXEUM";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			stmt = conn.createStatement();
			String sql = "SELECT * FROM mlb_team";
			rs = stmt.executeQuery(sql);
		} catch(SQLException sqle){
			sqle.printStackTrace();
		}
	}
}
