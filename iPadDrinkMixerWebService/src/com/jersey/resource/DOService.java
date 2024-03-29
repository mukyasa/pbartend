package com.jersey.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.jersey.dao.DbConnectionTest;
import com.jersey.dao.SQL;
import com.jersey.model.DrinkDetails;
import com.jersey.model.Ingredient;

public class DOService extends SQL {


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
	private final String LIMIT = "150";
	private final String IMG_ROOT= DbConnectionTest.SITE_URL + "/drinkimages/";
	
	public String updateDrink(String drinkTitle,int glass,String instructions,int category,String ingredients,int drink_id,String img){
		
		PreparedStatement pstmt = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlUpdateSharedDrink;

			//insert into tblShared
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, glass);
			pstmt.setString(2, drinkTitle);
			pstmt.setString(3, instructions);
			pstmt.setString(4, img);
			pstmt.setInt(5,category);
			pstmt.setInt(6,drink_id);
			
			
			pstmt.executeUpdate();
			
			//delete all old ings first
			sql="DELETE FROM "+TABLE_SHARED_DRINK_INGREDIENTS+" WHERE "+COL_DRINK_ID+"=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,drink_id);
			
			pstmt.executeUpdate();
			
			sql=sqlCreateSharedDrinkIngredients;
			//insert into tblSharedDrink_Ingredients
			//parse out params from ing string
			StringTokenizer toke = new StringTokenizer(ingredients,"|");//each ing sep by | then the ingid is after ~
			while(toke.hasMoreElements())
			{
				int ingId=0;
				String amount="";
				
				String tmpIng = toke.nextToken();
				String[] ings = tmpIng.split("~");
				amount = ings[0];
				ingId = Integer.valueOf(ings[1]).intValue();
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, drink_id);
				pstmt.setInt(2, ingId);
				pstmt.setString(3, amount);
				
				pstmt.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, pstmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "";
		
	}


	public String createDrink(String drinkTitle,int glass,String instructions,int category,String ingredients,String uid,String img){
		
		PreparedStatement pstmt = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs = null;
		int newId=0;

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlCreateSharedDrink;
			//insert into tblShared
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, glass);
			pstmt.setString(2, drinkTitle);
			pstmt.setString(3, instructions);
			pstmt.setInt(4,category);
			pstmt.setString(5, uid);
			pstmt.setString(6, img);
			
			pstmt.executeUpdate();
			
			sql = sqlGetMaxId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			//get the id of the drink you just adde
			while(rs.next())
			{
				newId = rs.getInt(COL_ROW_ID);
			}
			
			
			sql=sqlCreateSharedDrinkIngredients;
			//insert into tblSharedDrink_Ingredients
			//parse out params from ing string
			StringTokenizer toke = new StringTokenizer(ingredients,"|");//each ing sep by | then the ingid is after ~
			while(toke.hasMoreElements())
			{
				int ingId=0;
				String amount="";
				
				String tmpIng = toke.nextToken();
				String[] ings = tmpIng.split("~");
				amount = ings[0];
				ingId = Integer.valueOf(ings[1]).intValue();
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, newId);
				pstmt.setInt(2, ingId);
				pstmt.setString(3, amount);
				
				pstmt.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, pstmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "";
		
	}
	
	
	public float insertRating(int drink_id, int rating,String ip,String uid,String version,String name){
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		float totalRating=0;
		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlSetRating;

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, drink_id);
			stmt.setInt(2, rating);
			stmt.setString(3, ip);
			stmt.setString(4, uid);
			stmt.setString(5, version);
			stmt.setString(6, name);
			
			stmt.executeUpdate();

			totalRating = getRating(stmt, conn, rs, drink_id);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return totalRating;
		
	}
	
	public List<DrinkDetails> filterDrinksList(String startIndex,String searchParam,int catid) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();

		try {
			conn = DbConnectionTest.getConnection();
			String sql="";
			
			if(catid > -1)
				sql = sqlGetAllDringksFilterCategories + " LIMIT "+ startIndex + ","+LIMIT;
			else
				sql = sqlGetAllDrinksFilter + " LIMIT "+ startIndex + ","+LIMIT;
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, searchParam+"%");
			
			switch (catid) {
			case CAT_COCKTAIL:
				stmt.setInt(2, CAT_COCKTAIL);
				break;
			case CAT_HOT_DRINK:
				stmt.setInt(2, CAT_HOT_DRINK);
				break;
			case CAT_JELLO_SHOT:
				stmt.setInt(2, CAT_JELLO_SHOT);
				break;
			case CAT_MARTINIS:
				stmt.setInt(2, CAT_MARTINIS);
				break;
			case CAT_NON_ALC:
				stmt.setInt(2, CAT_NON_ALC);
				break;
			case CAT_PUNCH:
				stmt.setInt(2, CAT_PUNCH);
				break;
			case CAT_SHOOTER:
				stmt.setInt(2, CAT_SHOOTER);
				break;
			}
			
			
			
			rs = stmt.executeQuery();

			result = loopDrinks(rs, result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<DrinkDetails> getAllFavoriteDrinks(String startIndex,String ids) {

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();
		

		String[] idlist = ids.split(",");

		try {
			

			if(!"".equals(ids))
			{
			conn = DbConnectionTest.getConnection();
			
			String isSQL = "";
			// build sql
			for (int i = 0; i < idlist.length; i++) {
				if (i < (idlist.length - 1))
					isSQL += " d._id=" + idlist[i] + " OR ";
				else
					isSQL += " d._id=" + idlist[i];

			}

			String sql = sqlGetAllDrinksAndGlassById + isSQL + " LIMIT "
					+ startIndex + ","+LIMIT;

			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);

			result = loopDrinks(rs, result);
			
			//get the shared drinks too
			sql = sqlGetAllSharedDrinksAndGlassById + isSQL + " LIMIT "
			+ startIndex + ","+LIMIT;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			result = loopDrinks(rs, result, true);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<DrinkDetails> getAllSharedDrinks(String startIndex) {

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlGetAllSharedDrinksAndGlass + " LIMIT " + startIndex
					+ ","+LIMIT;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			result = loopDrinks(rs, result,true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<DrinkDetails> getAllDrinks(String startIndex) {

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlGetAllDrinksAndGlass + " LIMIT " + startIndex
					+ ","+LIMIT;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			result = loopDrinks(rs, result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<Ingredient> filterIngredientsList(int int_id,String startIndex,String searchParam) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Ingredient> result = new ArrayList<Ingredient>();
		Ingredient liquor = null;

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlGetAllIngredientsFilter ;

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
			
			stmt.setString(2, searchParam+"%");

			rs = stmt.executeQuery();

			while (rs.next()) {
				liquor = new Ingredient();
				liquor.setId(rs.getInt(COL_ROW_ID));

				// only show the first 32 chars
				String sub_name = rs.getString(COL_CAT_NAME);
				if (sub_name.length() >= 32)
					sub_name = sub_name.substring(0, 32) + "...";

				liquor.setName(sub_name);

				result.add(liquor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;

	}
	
	public List<Ingredient> getAllIngredients(int int_id,String startIndex,boolean isLimited) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Ingredient> result = new ArrayList<Ingredient>();
		Ingredient liquor = null;

		try {
			conn = DbConnectionTest.getConnection();

			String more="";
			if(isLimited)
				more = " LIMIT " + startIndex + ","+LIMIT;
			
			String sql = sqlGetAllIngredients + more;

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
				liquor.setId(rs.getInt(COL_ROW_ID));

				// only show the first 32 chars
				String sub_name = rs.getString(COL_CAT_NAME);
				if (sub_name.length() >= 32)
					sub_name = sub_name.substring(0, 32) + "...";

				liquor.setName(sub_name);

				result.add(liquor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;

	}
	
	
	public List<DrinkDetails> getDrinksByCategory(int int_id,String startIndex) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlGetDrinksByDrinkCatId + " LIMIT " + startIndex
					+ ","+LIMIT;

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

			result = loopDrinks(rs, result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<DrinkDetails> getDrinksByIngId(int int_id,String typeName,String startIndex) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlGetAllDrinksByIngredients + " LIMIT " + startIndex + ","+LIMIT;

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, int_id);
			stmt.setString(2, typeName);

			rs = stmt.executeQuery();

			result = loopDrinks(rs, result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public DrinkDetails getDrinkDetails(int int_drinkId,boolean detailTypeShared) {

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		DrinkDetails drink = null;
		StringBuffer ingredients = new StringBuffer();

		try {
			conn = DbConnectionTest.getConnection();

			float totalRating = getRating(stmt, conn, rs, int_drinkId);
			
			String sql = detailTypeShared? sqlGetSharedDrinkDetailById : sqlGetDrinkDetailById;

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, int_drinkId);

			rs = stmt.executeQuery();

			if (rs != null) {

				while (rs.next()) {
					drink = new DrinkDetails();
					drink.setId(rs.getInt(COL_ROW_ID));

					// only show the first 26 chars
					String sub_name = rs.getString(COL_NAME);
					if (sub_name.length() >= 26)
						sub_name = sub_name.substring(0, 26) + "...";

					drink.setDrinkName(sub_name);
					//drink.setFavorites(rs.getInt(COL_FAVORITE));
					String drink_css = rs.getString(COL_GLASS_NAME).replace(
							" ", "-");
					drink.setGlass(drink_css);

					ingredients.append("<li>-" + rs.getString(COL_AMOUNT) + " "
							+ rs.getString(COL_ING_NAME) + "<input  type=\"hidden\" value=\"" + rs.getString(COL_AMOUNT)+ "~"+ rs.getString(COL_SHARED_INGREDIENT_ID)+ "\" name=\"ing-item\"/><div class=\"delete-icon\"></div></li>");
					drink.addIng(rs.getString(COL_AMOUNT) + ", "
							+ rs.getString(COL_ING_NAME));
					drink.setId(rs.getInt(COL_ROW_ID));
					drink.setDrinkName(rs.getString(COL_NAME));
					drink.setGlass(rs.getString(COL_GLASS_NAME));
					drink.setDrinkType(rs.getString(COL_CAT_NAME));
					drink.setInstructions(rs.getString(COL_INSTUCTIONS));
					drink.setUid(rs.getString(COL_UID));
					drink.setRating(totalRating);
					drink.setEditAmount(rs.getString(COL_AMOUNT));

					drink.setGlassId(rs.getInt(COL_GLASS_ID));
					drink.setCatId(rs.getInt(COL_CAT_ID));
					
					drink.setImg(detailTypeShared? "data:image/jpeg;base64,"+rs.getString(COL_IMG): (rs.getString(COL_IMG_LOC) != null? IMG_ROOT+rs.getString(COL_IMG_LOC) : null)); //either blob or url
					

				}
				if(drink!=null)
					drink.setIngredients(ingredients.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return drink;
	}
	
	/**
	 * Gets the rating of the drink
	 * @param stmt
	 * @param conn
	 * @param rs
	 * @param int_drinkId
	 * @return
	 */
	private float getRating(PreparedStatement stmt,Connection conn,ResultSet rs,int int_drinkId) throws Exception{
		
		String sql = "SELECT rating FROM tblRating WHERE drink_id=?";
		stmt = conn.prepareStatement(sql);

		stmt.setInt(1, int_drinkId);

		rs = stmt.executeQuery();
		float totalRating=0;
		if (rs != null) {
			
			int tmpRating=0;
			float i=0;
			for(;rs.next();i++) {
				
				float rating = rs.getFloat(COL_RATING);
				tmpRating += rating;
				
			}
			totalRating=tmpRating/i;
		}
		
		return totalRating;
	}
	
	private void closeStuff(Connection conn, ResultSet rs,Statement stmt,PreparedStatement pstmt) throws SQLException{
		
		if(pstmt != null && !pstmt.isClosed())
			pstmt.close();
		if(conn!=null && !conn.isClosed())
			conn.close();
		if(rs != null && !rs.isClosed())
			rs.close();
		if(stmt !=null && !stmt.isClosed())
			stmt.close();
		
	}
	
	
	private List<DrinkDetails> loopDrinks(ResultSet rs,List<DrinkDetails> result) throws Exception{
		
		return loopDrinks(rs, result, false);
	}
	/**
	 * Builds a list of Drinks
	 * 
	 * @param rs
	 * @param drink
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private List<DrinkDetails> loopDrinks(ResultSet rs,List<DrinkDetails> result,boolean isSharedDrink) throws Exception {
		
		while (rs.next()) {
			DrinkDetails drink = new DrinkDetails();
			drink.setId(rs.getInt(COL_ROW_ID));

			// only show the first 26 chars
			String sub_name = rs.getString(COL_NAME);
			if (sub_name.length() >= 26)
				sub_name = sub_name.substring(0, 26) + "...";

			drink.setDrinkName(sub_name);
			//drink.setFavorites(rs.getInt(COL_FAVORITE));
			String drink_css = rs.getString(COL_GLASS_NAME).replace(" ","-");
			drink.setGlass(drink_css);
			drink.setCustom(isSharedDrink);

			result.add(drink);
		}

		return result;

	}
	
	/************
	 * ADMIN *
	/************/
	
	public List<DrinkDetails> getAllSharedDrinksAdmin() {

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlGetAllSharedAdmin;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			result = loopAdminDrinks(rs, result,false);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<DrinkDetails> getAllRatingsAdmin() {

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<DrinkDetails> result = new ArrayList<DrinkDetails>();

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlGetAllRatingsAdmin;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			result = loopAdminDrinks(rs, result,true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	private List<DrinkDetails> loopAdminDrinks(ResultSet rs,List<DrinkDetails> result,boolean getIp) throws Exception {
		
		while (rs.next()) {
			DrinkDetails drink = new DrinkDetails();
			drink.setId(rs.getInt(COL_ROW_ID));

			String sub_name = rs.getString(COL_NAME);

			drink.setDrinkName(sub_name);
			
			
			drink.setUid(rs.getString(COL_UID));
			
			if(getIp){
				drink.setVersion(rs.getString(COL_VERSION));
				drink.setIpAddress(rs.getString(COL_IP));	
				drink.setDrinkId(rs.getInt(COL_DRINK_ID));
				drink.setRating(rs.getInt(COL_RATING));
			}
			else
			{
				drink.setInstructions(rs.getString(COL_INSTUCTIONS));
				drink.setImg("data:image/jpeg;base64,"+rs.getString(COL_IMG));
			}

			result.add(drink);
		}

		return result;

	}
	
	public String deleteSharedDrinkAdmin(String drinkid){
		
		PreparedStatement pstmt = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnectionTest.getConnection();

			String sql = sqlDeleteSharedByIdAdmin;

			//insert into tblShared
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, drinkid);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeStuff(conn, rs, stmt, pstmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "";
		
	}
}
