package com.jersey.ajax;

import java.util.List;

import javax.ws.rs.GET;
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
			@QueryParam("ip") String ip) {

		DOService dos = new DOService();
		return dos.insertRating(drink_id, rating,ip)+"";
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
	public List<Ingredient> getAllLiquors(@PathParam("ingId") String id,
			@QueryParam("startIndex") String startIndex) {

		int int_id = Integer.valueOf(id).intValue();
		DOService dos = new DOService();

		return dos.getAllLiquors(int_id, startIndex);

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
			@QueryParam("searchParam") String searchParam) {
		DOService dos = new DOService();

		return dos.filterDrinksList(startIndex, searchParam);
	}

}
