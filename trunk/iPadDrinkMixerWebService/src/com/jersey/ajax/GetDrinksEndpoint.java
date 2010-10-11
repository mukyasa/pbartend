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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.jersey.dao.DbConnectionTest;
import com.jersey.dao.SQL;
import com.jersey.model.DrinkDetails;
import com.jersey.model.Ingredient;

@Path("/drinks")
public class GetDrinksEndpoint {

	private final int TYPE_LIQUOR = 1;// "Liquor";
	private final int TYPE_MIXERS = 2;// "Mixers";
	private final int TYPE_GARNISH = 3;// "Garnish";
	
	private final int CAT_COCKTAIL = 1;
	private final int CAT_HOT_DRINK = 2;
	private final int CAT_JELLO_SHOT = 3;
	private final int CAT_MARTINIS = 4;
	private final int CAT_NON_ALC = 5;
	private final int CAT_PUNCH = 6;
	private final int CAT_SHOOTER = 7;
	
	@GET
	@Path("fav{drinkId}")
	@Produces(MediaType.TEXT_HTML)
	public String setFavorite(
			@PathParam("drinkId") String drinkId,
			@QueryParam("isOn") boolean isOn) {

		int int_drinkId = Integer.valueOf(drinkId).intValue();
		Statement stmt = null;
		Connection conn = null;
		String success="success";
		int setFav=1;
		
		try {
			conn = DbConnectionTest.getConnection();

			if(isOn)setFav=0;
			
			String sql = "UPDATE tblDrinks SET "+SQL.COL_FAVORITE+"="+setFav+" WHERE "+SQL.COL_ROW_ID+"="+int_drinkId;

			stmt = conn.createStatement();
			int wasSucccessful = stmt.executeUpdate(sql);
			
			if(wasSucccessful==0)
				success="fail";


		} catch (Exception e) {
			e.printStackTrace();
			success="fail";
		} finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return success;

	}
	
	
	@GET
	@Path("cats{catid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getDrinksByCategory(
			@PathParam("catid") String id,
			@QueryParam("startIndex") String startIndex) {

		int int_id = Integer.valueOf(id).intValue();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();
		DrinkDetails drink = null;

		try {
			conn = DbConnectionTest.getConnection();

			String sql = SQL.sqlGetDrinksByDrinkCatId + " LIMIT " + startIndex+",200";

			stmt = conn.prepareStatement(sql);
			switch (int_id) {
			case CAT_COCKTAIL:
				stmt.setInt(1, CAT_COCKTAIL);
				break;
			case CAT_HOT_DRINK:
				stmt.setInt(1, CAT_HOT_DRINK);
				break;
			case CAT_JELLO_SHOT:
				stmt.setInt(1, CAT_JELLO_SHOT);
				break;
			case CAT_MARTINIS:
				stmt.setInt(1, CAT_MARTINIS);
				break;
			case CAT_NON_ALC:
				stmt.setInt(1, CAT_NON_ALC);
				break;
			case CAT_PUNCH:
				stmt.setInt(1, CAT_PUNCH);
				break;
			case CAT_SHOOTER:
				stmt.setInt(1, CAT_SHOOTER);
				break;
			}

			rs = stmt.executeQuery();

			result = loopDrinks(rs,drink,result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

	@GET
	@Path("ings{ingId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> getAllLiquors(
			@PathParam("ingId") String id,
			@QueryParam("startIndex") String startIndex) {

		int int_id = Integer.valueOf(id).intValue();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Ingredient> result = new ArrayList<Ingredient>();
		Ingredient liquor = null;

		try {
			conn = DbConnectionTest.getConnection();

			String sql = SQL.sqlGetAllIngredients+ " LIMIT " + startIndex+",200";

			stmt = conn.prepareStatement(sql);
			switch (int_id) {
			case TYPE_LIQUOR:
				stmt.setString(1, "Liquor");
				break;
			case TYPE_MIXERS:
				stmt.setString(1, "Mixers");
				break;
			case TYPE_GARNISH:
				stmt.setString(1, "Garnish");
				break;
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				liquor = new Ingredient();
				liquor.setId(rs.getInt(SQL.COL_ROW_ID));

				// only show the first 32 chars
				String sub_name = rs.getString(SQL.COL_CAT_NAME);
				if (sub_name.length() >= 32)
					sub_name = sub_name.substring(0, 32) + "...";

				liquor.setName(sub_name);

				result.add(liquor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getAllDrinks(
			@QueryParam("startIndex") String startIndex) {

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();
		DrinkDetails drink = null;

		try {
			conn = DbConnectionTest.getConnection();

			String sql = SQL.sqlGetAllDrinksAndGlass + " LIMIT " + startIndex+",200";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			result = loopDrinks(rs,drink,result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	
	/**
	 * Builds a list of Drinks
	 * @param rs
	 * @param drink
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private List<DrinkDetails> loopDrinks(ResultSet rs,DrinkDetails drink,List<DrinkDetails> result) throws Exception{
		
		while (rs.next()) {
			drink = new DrinkDetails();
			drink.setId(rs.getInt(SQL.COL_ROW_ID));

			// only show the first 26 chars
			String sub_name = rs.getString(SQL.COL_NAME);
			if (sub_name.length() >= 26)
				sub_name = sub_name.substring(0, 26) + "...";

			drink.setDrinkName(sub_name);
			drink.setFavorites(rs.getInt(SQL.COL_FAVORITE));
			String drink_css = rs.getString(SQL.COL_GLASS_NAME).replace(
					" ", "-");
			drink.setGlass(drink_css);

			result.add(drink);
		}
		
		return result;
		
	}
}
