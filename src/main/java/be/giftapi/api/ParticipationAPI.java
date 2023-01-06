package be.giftapi.api;

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
import be.giftapi.javabeans.Gift;
import be.giftapi.javabeans.Participation;

@Path("/participation")
public class ParticipationAPI {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertParticipation(String data) {
		JSONObject json = new JSONObject(data);
		double amountPaid = json.getDouble("amountPaid");
		JSONObject jsonCustomer = json.getJSONObject("customer");
		int idCustomer = jsonCustomer.getInt("idCustomer");
		JSONObject jsonGift = json.getJSONObject("gift");
		int idGift = jsonGift.getInt("idGift");
		
		if(amountPaid == 0.0 || idCustomer == 0 || idGift == 0) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
	    
		Customer customer = new Customer();
		customer.setIdCustomer(idCustomer);
	   
		Gift gift = new Gift();
		gift.setIdGift(idGift);
		
		
		Participation participation = new Participation(0,amountPaid, customer,gift);
		boolean success = participation.insert();
		if(!success) {
			return Response
					.status(Status.SERVICE_UNAVAILABLE)
					.build();
		}
		else {
			return Response
					.status(Status.CREATED)
					.header("Location", "/GiftAPI/api/participation/" + participation.getIdParticipation())
					.build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("gift/{id}")
	public Response getParticipationsFromGift(@PathParam("id") int idGift) {
		ArrayList<Participation> participations = Participation.getParticipationsFromGift(idGift);
		
		if(participations == null) {
			
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		
		return Response.status(Status.OK).entity(participations).build();
	}
}
