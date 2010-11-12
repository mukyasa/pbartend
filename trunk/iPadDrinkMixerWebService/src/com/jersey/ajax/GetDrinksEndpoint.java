package com.jersey.ajax;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.jersey.dao.SQL;
import com.jersey.model.DrinkDetails;
import com.jersey.model.Ingredient;
import com.jersey.resource.DOService;

@Path("/drinks")
public class GetDrinksEndpoint extends SQL {
	
	@POST
	@Path("create")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postCreateDrink(
			@FormParam("dt") String drinkTitle,
			@FormParam("g") int glass,
			@FormParam("in") String instructions,
			@FormParam("cat") int category,
			@FormParam("uid") String uid,
			@FormParam("ings") String ingredients,
			@FormParam("img") String img){

		DOService dos = new DOService();

		dos.createDrink(drinkTitle,glass,instructions,category,ingredients,uid,img);
		
	}
	
	@POST
	@Path("update")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void updateDrink(
			@FormParam("dt") String drinkTitle,
			@FormParam("g") int glass,
			@FormParam("in") String instructions,
			@FormParam("cat") int category,
			@FormParam("ings") String ingredients,
			@FormParam("did") int drink_id_input,
			@FormParam("img") String img){

		DOService dos = new DOService();

		dos.updateDrink(drinkTitle,glass,instructions,category,ingredients, drink_id_input,img);
	}
	

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getAllDrinks(
			@QueryParam("startIndex") String startIndex) {

		DOService dos = new DOService();

		return dos.getAllDrinks(startIndex);
	}
	

	

	@GET
	@Path("rate")
	@Produces(MediaType.APPLICATION_JSON)
	public String setRating(@QueryParam("rating") int rating,
			@QueryParam("id") int drink_id,
			@QueryParam("ip") String ip,
			@QueryParam("uid") String uid,
			@QueryParam("version") String version,
			@QueryParam("name") String name){

		DOService dos = new DOService();
		return dos.insertRating(drink_id, rating,ip,uid,version,name)+"";
	}
	
	@GET
	@Path("shared")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getAllSharedDrinks(
			@QueryParam("startIndex") String startIndex) {

		DOService dos = new DOService();

		return dos.getAllSharedDrinks(startIndex);
	}

	@GET
	@Path("details{drinkId}")
	@Produces(MediaType.APPLICATION_JSON)
	public DrinkDetails getDrinkDetails(@PathParam("drinkId") String drinkId,
			@QueryParam("detailTypeShared") boolean detailTypeShared) {

		int int_drinkId = Integer.valueOf(drinkId).intValue();
		DOService dos = new DOService();

		return dos.getDrinkDetails(int_drinkId, detailTypeShared);

	}

	@GET
	@Path("cats{catid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getDrinksByCategory(
			@PathParam("catid") String id,
			@QueryParam("startIndex") String startIndex) {

		int int_id = Integer.valueOf(id).intValue();
		DOService dos = new DOService();

		return dos.getDrinksByCategory(int_id, startIndex);
	}

	@GET
	@Path("ingsId{ingid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getDrinksByIngId(@PathParam("ingid") String id,
			@QueryParam("startIndex") String startIndex,
			@QueryParam("typeName") String typeName) {

		int int_id = Integer.valueOf(id).intValue();
		DOService dos = new DOService();

		return dos.getDrinksByIngId(int_id, typeName, startIndex);
	}

	@GET
	@Path("ings{ingId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> getAllIngredients(@PathParam("ingId") String id,
			@QueryParam("startIndex") String startIndex,
			@QueryParam("isLimited") boolean isLimited) {

		int int_id = Integer.valueOf(id).intValue();
		DOService dos = new DOService();

		return dos.getAllIngredients(int_id, startIndex,isLimited);

	}
	
	@GET
	@Path("ingsfilter{ingId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> filterIngredientsList(
			@PathParam("ingId") String id,
			@QueryParam("searchParam") String searchParam,
			@QueryParam("startIndex") String startIndex) {

		int int_id = Integer.valueOf(id).intValue();
		DOService dos = new DOService();

		return dos.filterIngredientsList(int_id, startIndex,searchParam);

	}

	@GET
	@Path("favs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getAllFavoriteDrinks(
			@QueryParam("startIndex") String startIndex,
			@QueryParam("ids") String ids) {

		DOService dos = new DOService();

		return dos.getAllFavoriteDrinks(startIndex, ids);
	}

	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> filterDrinksList(
			@QueryParam("startIndex") String startIndex,
			@QueryParam("searchParam") String searchParam,
			@QueryParam("catid") int catid) {
		DOService dos = new DOService();

		return dos.filterDrinksList(startIndex, searchParam,catid);
	}
	
	
	/************
	 * ADMIN *
	/************/
	@GET
	@Path("adminShared")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DrinkDetails> getAllSharedDrinksAdmin() {
		DOService dos = new DOService();

		return dos.getAllSharedDrinksAdmin();
	}

}

