package com.jersey.ajax;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.jersey.dao.DbConnectionTest;
import com.jersey.model.Glass;

@Path("/allGlasses")
public class GetGlassesEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Glass> allGlasses(
    		@QueryParam("drinkName") String drinkName,
            @QueryParam("id") String id
            ) {
        
    	Statement stmt=null;
		ResultSet rs=null;
		Connection conn=null;
		List<Glass> result = new ArrayList<Glass>();
		Glass glass = null;
		
    	try{
    		conn = DbConnectionTest.getConnection();
    		
			String sql = "SELECT * FROM tblGlasses order by name";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				glass = new Glass();
				glass.setId(rs.getInt("_id"));
				glass.setName(rs.getString("name"));
				
				result.add(glass); 
			}
    		
    	}catch(Exception e)
    	{
    		
    	}
    	finally{
    		try {
				conn.close();
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
        return result;
    }
}
