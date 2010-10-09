package com.jersey.ajax;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jersey.dao.DbConnectionTest;
import com.jersey.dao.SQL;
import com.jersey.model.DrinkDetails;
import com.jersey.model.Ingredient;

@Path("/drinks")
public class GetDrinksEndpoint {
	
	private final int TYPE_LIQUOR = 1;//"Liquor";
	private final int TYPE_MIXERS = 2;//"Mixers";
	private final int TYPE_GARNISH = 3;//"Garnish";

	@GET
	@Path("{ing}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient>getAllLiquors(
			@PathParam("ing") String id){
		
		int int_id = Integer.valueOf(id).intValue();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Connection conn=null;
		List<Ingredient> result = new ArrayList<Ingredient>();
		Ingredient liquor = null;
		
    	try{
    		conn = DbConnectionTest.getConnection();
    		
			String sql = SQL.sqlGetAllIngredients;
			
			stmt = conn.prepareStatement(sql);
			switch(int_id)
			{
				case TYPE_LIQUOR: stmt.setString(1, "Liquor");break;
				case TYPE_MIXERS: stmt.setString(1, "Mixers");break;
				case TYPE_GARNISH: stmt.setString(1, "Garnish");break;
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				liquor = new Ingredient();
				liquor.setId(rs.getInt(SQL.COL_ROW_ID));
				
				//only show the first 30 chars
				String sub_name= rs.getString(SQL.COL_CAT_NAME);
				if(sub_name.length()>=35)
					sub_name = sub_name.substring(0,35) +"...";
				
				liquor.setName(sub_name);
				
				result.add(liquor); 
			}
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
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
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
				
				//only show the first 26 chars
				String sub_name= rs.getString("name");
				if(sub_name.length()>=26)
					sub_name = sub_name.substring(0,26) +"...";
				
				drink.setDrinkName(sub_name);
				drink.setFavorites(rs.getInt("favorite"));
				String drink_css =  rs.getString(SQL.COL_GLASS_NAME).replace(" ", "-");
				drink.setGlass(drink_css);
				
				result.add(drink); 
			}
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
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
