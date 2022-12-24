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

@Path("/customer")
public class CustomerAPI {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertCustomer(String data) {
		
		JSONObject json = new JSONObject(data);
		String firstName = json.getString("firstName");
		String lastName = json.getString("lastName");
		JSONObject jsonDob = json.getJSONObject("dateOfBirth");
		int year = jsonDob.getInt("year");
		int month = jsonDob.getInt("monthValue");
		int day = jsonDob.getInt("dayOfMonth");
		LocalDate dateOfBirth = LocalDate.of(year, month, day);
		String username = json.getString("username");
		String password = json.getString("password");
		
		if(firstName == null || lastName == null || username == null 
				|| password == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
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
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginCustomer(String data) {
		
		System.out.println(data);
		JSONObject json = new JSONObject(data);
		String username = json.getString("username");
		String password = json.getString("password");
		
		if(username == null || password == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		Customer customer = Customer.login(username, password);
		
		if(customer == null) {
			return Response
					.status(Status.UNAUTHORIZED)
					.build();
		}
		else {
			return Response
					.status(Status.OK)
					.header("Id", customer.getIdCustomer())
					.build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomers() {
		ArrayList<Customer> customers = Customer.getCustomers();
		if(customers == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.OK).entity(customers).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getCustomer(@PathParam("id") int id) {
		Customer customer = Customer.getCustomer(id);
		if(customer == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.OK).entity(customer).build();
	}
	
}
