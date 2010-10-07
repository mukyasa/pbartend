package com.jersey.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



 public class DbConnectionTest {
 
	 public static void main(String[] args)
	 {
		Statement stmt=null;
		ResultSet rs=null;
		Connection conn=null;
			
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			
			String serverName = "localhost";
			String myDb = "drinkmixer";
			String url="jdbc:mysql://"+serverName + "/" + myDb;
			
			String user = "root";
			String pwd = "door78";
			
			conn = DriverManager.getConnection(url,user,pwd);
			
			
			
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
 