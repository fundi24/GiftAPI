package be.giftapi.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import org.json.JSONArray;
import org.json.JSONObject;

import be.giftapi.javabeans.Customer;
import be.giftapi.javabeans.ListGift;
import be.giftapi.javabeans.Notification;

@Path("/listgift")
public class ListGiftAPI {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertListGift(String data) {
		JSONObject json = new JSONObject(data);
		String name = json.getString("name");
		JSONObject jsondl = json.getJSONObject("deadline");
		int year = jsondl.getInt("year");
		int month = jsondl.getInt("monthValue");
		int day = jsondl.getInt("dayOfMonth");
		LocalDate deadline = LocalDate.of(year, month, day);
		boolean status = json.getBoolean("status");
		String theme = json.getString("theme");
		JSONObject getIdOwner = json.getJSONObject("owner");
		int idOwner = getIdOwner.getInt("idCustomer");

		if (name == null || theme == null || idOwner == 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		Customer owner = new Customer();
		owner.setIdCustomer(idOwner);

		ListGift listGift = new ListGift(0, name, deadline, status, theme, owner);
		boolean success = listGift.insert();
		if (!success) {

			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		} else {
			return Response.status(Status.CREATED)
					.header("Location", "/GiftAPI/api/listgift/" + listGift.getIdListGift()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response findListGiftById(@PathParam("id") int idListGift) {
		ListGift listgift = ListGift.getListGiftById(idListGift);

		if (listgift == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}

		return Response.status(Status.OK).entity(listgift).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("customer/{id}")
	public Response getListGiftsFromCustomer(@PathParam("id") int idCustomer) {
		ArrayList<ListGift> listGifts = ListGift.getListGiftFromCustomer(idCustomer);

		if (listGifts == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}

		return Response.status(Status.OK).entity(listGifts).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("invitation/{id}")
	public Response getInvitationsFromListGift(@PathParam("id") int idListGift) {
		ArrayList<Customer> customers = ListGift.getInvitationsFromListGift(idListGift);

		if (customers == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}

		return Response.status(Status.OK).entity(customers).build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateNotification(@PathParam("id") int idListGift, String data) {
		JSONObject json = new JSONObject(data);
		boolean status = json.getBoolean("status");
		JSONObject jsonDl = json.getJSONObject("deadline");
		int year = jsonDl.getInt("year");
		int month = jsonDl.getInt("monthValue");
		int day = jsonDl.getInt("dayOfMonth");
		LocalDate deadline = LocalDate.of(year, month, day);
		ListGift listGift = new ListGift();
		listGift.setIdListGift(idListGift);
		listGift.setStatus(status);
		listGift.setDeadline(deadline);
		JSONArray invitations = json.getJSONArray("invitations");
		for (int i = 0; i < invitations.length(); i++) {
			JSONObject objJson = invitations.getJSONObject(i);
			int idCustomer = objJson.getInt("idCustomer");
			String firstName = objJson.getString("firstName");
			String lastName = objJson.getString("lastName");
			Customer invite = new Customer();
			invite.setIdCustomer(idCustomer);
			invite.setFirstName(firstName);
			invite.setLastName(lastName);
			listGift.addInvitations(invite);
		}

		boolean success = listGift.update();
		if (!success) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		} else {
			return Response.status(Status.NO_CONTENT).build();
		}
	}

}
