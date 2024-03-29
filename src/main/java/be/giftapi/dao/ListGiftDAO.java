package be.giftapi.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import be.giftapi.javabeans.Customer;
import be.giftapi.javabeans.ListGift;
import oracle.jdbc.OracleTypes;

public class ListGiftDAO extends DAO<ListGift> {

	public ListGiftDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(ListGift obj) {
		boolean success = false;

		String query = "{call insert_list_gift(?,?,?,?,?)}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {

			cs.setString(1, obj.getName());
			cs.setDate(2, Date.valueOf(obj.getDeadline()));
			cs.setBoolean(3, obj.isStatus());
			cs.setString(4, obj.getTheme());
			cs.setInt(5, obj.getOwner().getIdCustomer());

			cs.executeUpdate();

			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return success;

	}

	@Override
	public boolean delete(ListGift obj) {
		return false;
	}

	@Override
	public boolean update(ListGift obj) {
		boolean success = false;

		String query = "{call update_listgift(?,?,?)}";
		String query2 = "{call insert_invitation(?,?)}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {

			cs.setInt(1, obj.getIdListGift());
			cs.setBoolean(2, obj.isStatus());
			cs.setDate(3, Date.valueOf(obj.getDeadline()));
			cs.executeUpdate();

			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		if(obj.getInvitations().size() > 0) {
			try (CallableStatement cs = this.connect.prepareCall(query2)) {

				int last = obj.getInvitations().size();
				cs.setInt(1, obj.getInvitations().get(last-1).getIdCustomer());
				cs.setInt(2, obj.getIdListGift());
				cs.executeUpdate();

				success = true;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return success;
	}

	@Override
	public ListGift find(int id) {
		ListGift listgift = null;

		String query = "{? = call fselect_listgift(?)}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {

			cs.registerOutParameter(1, OracleTypes.STRUCT, "TYP_LISTGIFT");
			cs.setInt(2, id);
			cs.executeQuery();

			Object data = (Object) cs.getObject(1);
			Struct row = (Struct) data;
			Object[] values = (Object[]) row.getAttributes();
		
			String name = String.valueOf(values[1]);
			String strDeadline = String.valueOf(values[2]);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
			LocalDate deadline = LocalDate.parse(strDeadline, formatter);
			int intStatus = Integer.parseInt(String.valueOf(values[3]));
			boolean status = intStatus == 1 ? true : false;
			String theme = String.valueOf(values[4]);
			int idCustomer = Integer.parseInt(String.valueOf(values[5]));
			
			Customer customer = new Customer();
			customer.setIdCustomer(idCustomer);
			listgift = new ListGift(id, name, deadline, status, theme, customer);
			
		
	}
		catch(SQLException e){
		System.out.println(e.getMessage());
	}
		return listgift;
	}

	@Override
	public ArrayList<ListGift> findAll() {
		return null;
	}

	@Override
	public ArrayList<ListGift> findAll(int idCustomer) {
		ArrayList<ListGift> listgifts = new ArrayList<ListGift>();

		String query = "{? = call fselect_listgifts_from_customer(?)}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {

			cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_LISTGIFT");
			cs.setInt(2, idCustomer);
			cs.executeQuery();

			Array arr = cs.getArray(1);
			if (arr != null) {
				Object[] data = (Object[]) arr.getArray();

				for (Object a : data) {
					Struct row = (Struct) a;
					Object[] values = (Object[]) row.getAttributes();

					String id = String.valueOf(values[0]);
					int idListGift = Integer.parseInt(id);
					String name = String.valueOf(values[1]);
					String strDeadline = String.valueOf(values[2]);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
					LocalDate deadline = LocalDate.parse(strDeadline, formatter);
					int intStatus = Integer.parseInt(String.valueOf(values[3]));
					boolean status = intStatus == 1 ? true : false;
					String theme = String.valueOf(values[4]);

					ListGift listgift = new ListGift(idListGift, name, deadline, status, theme, null);
					listgifts.add(listgift);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return listgifts;
	}

	public ArrayList<Customer> getInvitationsFromListGift(int idListGift) {
		ArrayList<Customer> invitations = new ArrayList<>();

		String query = "{? = call fselect_invitations_from_listgift(?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)) {
			cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_INVITATION");
			cs.setInt(2, idListGift);
			cs.executeQuery();

			Array arr = cs.getArray(1);
			if (arr != null) {
				Object[] data = (Object[]) arr.getArray();
				for (Object a : data) {
					Struct row = (Struct) a;
					Object[] values = (Object[]) row.getAttributes();
					String idInvit = String.valueOf(values[0]);
					int idInvitation = Integer.parseInt(idInvit);
					String idCust = String.valueOf(values[1]);
					int idCustomer = Integer.parseInt(idCust);
					String firstName = String.valueOf(values[2]);
					String lastName = String.valueOf(values[3]);
					String DOB = String.valueOf(values[4]);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
					LocalDate dateOfBirth = LocalDate.parse(DOB, formatter);
					String username = String.valueOf(values[5]);
					String password = String.valueOf(values[6]);

					Customer customer = new Customer(idCustomer, firstName, lastName, dateOfBirth, username, password);
					invitations.add(customer);

				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}

		return invitations;

	}
}
