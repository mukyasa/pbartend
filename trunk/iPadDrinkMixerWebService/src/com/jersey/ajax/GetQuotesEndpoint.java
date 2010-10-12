package com.jersey.ajax;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jersey.dao.DbConnectionTest;
import com.jersey.dao.SQL;

@Path("/quotes")
public class GetQuotesEndpoint extends SQL {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getAQuote() {
        
    	Statement stmt=null;
		ResultSet rs=null;
		Connection conn=null;
		String quote="";
		
		
    	try{
    		conn = DbConnectionTest.getConnection();
    		
			String sql = "SELECT quote FROM tblQuotes WHERE _id=0";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
				quote = rs.getString("quote");
    		
    	}catch(Exception e)
    	{
    		
    	}
    	finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
        return quote;
    }
}
