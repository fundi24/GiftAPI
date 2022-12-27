package be.giftapi.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.giftapi.javabeans.Customer;
import be.giftapi.javabeans.ListGift;


@Path("/listgift")
public class ListGiftAPI {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertListGift(String data) {
		JSONObject json = new JSONObject(data);
		String name = json.getString("name");
		String dl = json.getString("deadline");
		boolean status = json.getBoolean("status");
		String theme = json.getString("theme");
		int idOwner = json.getInt("idOwner");
		
		if(name == null || dl == null || theme == null || idOwner == 0) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate deadline = LocalDate.parse(dl, formatter);
		
		//Customer owner = Customer.getCustomer(idOwner);
		Customer owner = new Customer();
		owner.setIdCustomer(idOwner);
		
		ListGift listGift = new ListGift(0, name, deadline, status, theme, owner);
		boolean success = listGift.insert();
		if(!success) {
			
			return Response
					.status(Status.SERVICE_UNAVAILABLE)
					.build();
		}
		else {
			return Response
					.status(Status.CREATED)
					.header("Location", "/GiftAPI/api/listgift/" + listGift.getIdListGift())
					.build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("customer/{id}")
	public Response getListGiftsFromCustomer(@PathParam("id") int idCustomer) {
		ArrayList<ListGift> listGifts = ListGift.getListGiftFromCustomer(idCustomer);
		
		if(listGifts == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		
		return Response.status(Status.OK).entity(listGifts).build();
	}
	
}
