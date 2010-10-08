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

import com.jersey.dao.DbConnectionTest;
import com.jersey.dao.SQL;
import com.jersey.model.DrinkDetails;

@Path("/drinks")
public class GetDrinksEndpoint {

    @GET
    @Produces("application/json")
    public List<DrinkDetails> getDrinks() {
        
    	Statement stmt=null;
		ResultSet rs=null;
		Connection conn=null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();
		DrinkDetails drink = null;
		
    	try{
    		conn = DbConnectionTest.getConnection();
    		
			String sql = SQL.sqlGetAllDrinksAndGlass + " LIMIT 200";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				drink = new DrinkDetails();
				drink.setId(rs.getInt("_id"));
				
				//only show the first 15 chars
				String sub_name= rs.getString("name");
				if(sub_name.length()>=15)
					sub_name = sub_name.substring(0,15) +"...";
				
				drink.setDrinkName(sub_name);
				drink.setFavorites(rs.getInt("favorite"));
				String drink_css =  rs.getString(SQL.COL_GLASS_NAME).replace(" ", "-");
				drink.setGlass(drink_css);
				
				result.add(drink); 
			}
    		
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
    	
        return result;
    }
}
