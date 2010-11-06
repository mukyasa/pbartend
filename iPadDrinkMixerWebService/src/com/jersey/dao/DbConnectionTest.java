package com.jersey.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



 public class DbConnectionTest {
 
	 public static Connection getConnection() throws Exception{
		 String driverName = "com.mysql.jdbc.Driver";
		Class.forName(driverName);
		
		String serverName = "mypocketmobile.com:3306";
		String myDb = "mypocket_mixer";//drinkmixer; 
		String url="jdbc:mysql://"+serverName + "/" + myDb;
		
		String user = "mypocket_root";//root
		String pwd = "door78";
		
		return DriverManager.getConnection(url,user,pwd);
		 
	 }
	 
	 
	 public static void main(String[] args)
	 {
		 
		Statement stmt=null;
		ResultSet rs=null;
		Connection conn=null;
			
		try {
			
			conn = DbConnectionTest.getConnection();
			
			System.out.println("conn=" + conn);
			String sql = "SELECT * FROM tblGlasses";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				System.out.println(rs.getString("name"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	 }

}
 