package be.giftapi.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import be.giftapi.javabeans.Customer;
import be.giftapi.javabeans.Participation;
import oracle.jdbc.OracleTypes;

public class ParticipationDAO extends DAO<Participation> {

	public ParticipationDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Participation obj) {
		boolean success = false;

		String query = "{call insert_participation(?,?,?)}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {

			cs.setDouble(1, obj.getAmountPaid());
			cs.setInt(2, obj.getGift().getIdGift());
			cs.setInt(3, obj.getCustomer().getIdCustomer());

			cs.executeUpdate();

			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return success;
	}

	@Override
	public boolean delete(Participation obj) {
		return false;
	}

	@Override
	public boolean update(Participation obj) {
		return false;
	}

	@Override
	public Participation find(int id) {
		return null;
	}

	@Override
	public ArrayList<Participation> findAll() {
		return null;
	}

	@Override
	public ArrayList<Participation> findAll(int idGift) {
		ArrayList<Participation> participations = new ArrayList<>();

		String query = "{? = call fselect_participations_from_gift(?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)) {
			cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_PARTICIPATION");
			cs.setInt(2, idGift);
			cs.executeQuery();

			Array arr = cs.getArray(1);
			if (arr != null) {
				Object[] data = (Object[]) arr.getArray();
				for (Object a : data) {
					Struct row = (Struct) a;
					Object[] values = (Object[]) row.getAttributes();
					String idParticip = String.valueOf(values[0]);
					int idParticipation = Integer.parseInt(idParticip);
					double amountPaid = Double.valueOf((String.valueOf(values[1])));
					String idCust = String.valueOf(values[3]);
					int idCustomer = Integer.parseInt(idCust);
					String firstName = String.valueOf(values[4]);
					String lastName = String.valueOf(values[5]);
					String DOB = String.valueOf(values[6]);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
					LocalDate dateOfBirth = LocalDate.parse(DOB, formatter);
					String username = String.valueOf(values[7]);
					String password = String.valueOf(values[8]);

					Customer customer = new Customer(idCustomer, firstName, lastName, dateOfBirth, username, password);
					Participation participation = new Participation(idParticipation, amountPaid, customer, null);
					participations.add(participation);

				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return participations;
	}

}
