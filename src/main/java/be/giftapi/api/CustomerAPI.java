package be.giftapi.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.giftapi.javabeans.Customer;

@Path("/customer")
public class CustomerAPI {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertCustomer(String data) {
		
		JSONObject json = new JSONObject(data);
		String firstName = json.getString("firstName");
		String lastName = json.getString("firstName");
		String dob = json.getString("dateOfBirth");
		String username = json.getString("username");
		String password = json.getString("password");
		
		if(firstName == null || lastName == null || dob.equals(null) || username == null 
				|| password == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
		
		
		Customer customer = new Customer(0,firstName, lastName, dateOfBirth, username, password);
	
		boolean success = customer.insert();
		if(!success) {
			
			return Response
					.status(Status.SERVICE_UNAVAILABLE)
					.build();
		}
		else {
			return Response
					.status(Status.CREATED)
					.header("Location", "/GiftAPI/api/customer/" + customer.getIdCustomer())
					.build();
		}
		
	}
	
}
