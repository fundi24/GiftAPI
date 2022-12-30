package be.giftapi.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.giftapi.javabeans.Gift;
import be.giftapi.javabeans.ListGift;
import be.giftapi.javabeans.Notification;

@Path("/gift")
public class GiftAPI {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertGift(String data) {
		System.out.println(data);
		JSONObject json = new JSONObject(data);
		String name = json.getString("name");
		String description = json.getString("description");
		String picture = json.getString("picture");
		double price = json.getDouble("price");
		int priority = json.getInt("priority");
		boolean booked = json.getBoolean("booked");
		boolean multiplePayment = json.getBoolean("multiplePayment");
		String linkToWebsite = json.getString("linkToWebsite");
		JSONObject listGiftJson = json.getJSONObject("listGift");
		int idListGift = listGiftJson.getInt("idListGift");
		
		if (name == null || description == null || price == 0.0 || priority == 0 || idListGift == 0 ) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		
		
		ListGift listGift = new ListGift();
		listGift.setIdListGift(idListGift);
		
		Gift gift = new Gift(0, name, description, price, priority, picture ,booked, multiplePayment, linkToWebsite, listGift);
		
		
		boolean success = gift.insert();
		if(!success) {
			
			return Response
					.status(Status.SERVICE_UNAVAILABLE)
					.build();
		}
		else {
			return Response
					.status(Status.CREATED)
					.header("Location", "/GiftAPI/api/gift/" + gift.getIdGift())
					.build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listgift/{id}")
	public Response getListGiftsFromCustomer(@PathParam("id") int idListGift) {
		ArrayList<Gift> gifts = Gift.getGiftsFromListGift(idListGift);
		
		if(gifts == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		
		return Response.status(Status.OK).entity(gifts).build();
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateGift(@PathParam("id") int id, String data) {
		JSONObject json = new JSONObject(data);
		String name = json.getString("name");
		String description = json.getString("description");
		String picture = json.getString("picture");
		double price = json.getDouble("price");
		String linkToWebsite = json.getString("linkToWebsite");
		
		Gift gift = new Gift();
		gift.setIdGift(id);
		gift.setName(name);
		gift.setDescription(description);
		gift.setPicture(picture);
		gift.setPrice(price);
		gift.setLinkToWebsite(linkToWebsite);
		
		boolean success = gift.update();
	  
		if(!success) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		else {
			return Response.status(Status.NO_CONTENT).build();
		}
	}
	

}
