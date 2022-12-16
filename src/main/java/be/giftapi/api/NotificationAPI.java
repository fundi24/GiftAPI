package be.giftapi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	    
	    Customer customer = Customer.getCustomer(idCustomer);
		
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

}
