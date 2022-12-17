package be.giftapi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
		int idCustomer = json.getInt("idCustomer");
		int idGift = json.getInt("idGift");
		
		if(amountPaid == 0.0 || idCustomer == 0 || idGift == 0) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
	    //Customer customer = Customer.getCustomer(idCustomer);
		Customer customer = new Customer();
		customer.setIdCustomer(idCustomer);
	    //Gift gift = Gift.getGift(idGift);
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
}
