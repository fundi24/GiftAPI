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

import be.giftapi.javabeans.Customer;
import be.giftapi.javabeans.Notification;

@Path("/notification")
public class NotificationAPI {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertNotification(String data) {
		JSONObject json = new JSONObject(data);
	    String message = json.getString("message");
	    boolean read = json.getBoolean("read");
	    int idCustomer = json.getInt("idCustomer");
	    
	    if(message == null || idCustomer == 0) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
	    }
	    
	    //Customer customer = Customer.getCustomer(idCustomer);
	    Customer customer = new Customer();
		customer.setIdCustomer(idCustomer);
		
		Notification notification = new Notification(0,message,read,customer);
		boolean success = notification.insert();
		if(!success) {
			return Response
					.status(Status.SERVICE_UNAVAILABLE)
					.build();
		}
		else {
			return Response
					.status(Status.CREATED)
					.header("Location", "/GiftAPI/api/notification/" + notification.getIdNotification())
					.build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("customer/{id}")
	public Response getNotificationFromCustomer(@PathParam("id") int idCustomer) {
		ArrayList<Notification> notification = Notification.getNotificationFromCustomer(idCustomer);
		
		if(notification == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		
		return Response.status(Status.OK).entity(notification).build();

	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateNotification(@PathParam("id") int id, String data) {
		//JSONObject json = new JSONObject(data);
	    Notification notification = new Notification();
	    notification.setIdNotification(id);
		boolean success = notification.update();
		if(!success) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		else {
			return Response.status(Status.NO_CONTENT).build();
		}
	}

}
