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
import oracle.jdbc.OracleTypes;

public class CustomerDAO extends DAO<Customer> {

	public CustomerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Customer obj) {
		boolean success = false;

		String query = "{call insert_customer(?,?,?,?,?)}";

		// verifier si l'username est dispo
		try (CallableStatement cs = this.connect.prepareCall(query)) {

			cs.setString(1, obj.getFirstName());
			cs.setString(2, obj.getLastName());
			cs.setDate(3, Date.valueOf(obj.getDateOfBirth()));
			cs.setString(4, obj.getUsername());
			cs.setString(5, obj.getPassword());

			cs.executeUpdate();

			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return success;
	}

	@Override
	public boolean delete(Customer obj) {
		return false;
	}

	@Override
	public boolean update(Customer obj) {
		return false;
	}

	@Override
	public Customer find(int id) {
		
		 Customer customer = null;
	        String query = "{? = call fselect_customer(?)}";
	        try (CallableStatement cs = this.connect.prepareCall(query)) {
	            cs.registerOutParameter(1, OracleTypes.STRUCT, "TYP_CUSTOMER");
	            cs.setInt(2, id);
	            cs.executeQuery();
	            
	            Object data = (Object) cs.getObject(1);
	            Struct row = (Struct)data;
	            Object[] values = (Object[]) row.getAttributes();
	            String firstName = String.valueOf(values[1]);
	            String lastName = String.valueOf(values[2]);
	            String DOB = String.valueOf(values[3]);
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
	            LocalDate dateOfBirth = LocalDate.parse(DOB, formatter);
	            String username = String.valueOf(values[4]);
	            String password = String.valueOf(values[5]);
	            
	            customer = new Customer(id, firstName, lastName, dateOfBirth, username, password);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return customer;
	}

	@Override
	public ArrayList<Customer> findAll() {
		ArrayList<Customer> customers = new ArrayList<Customer>();

		String query = "{? = call fselect_customers}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {
			
			cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_CUSTOMER");
			cs.executeQuery();
			
			Array arr = cs.getArray(1);
			if (arr != null) {
				Object[] data = (Object[]) arr.getArray();
				for (Object a : data) {
				    Struct row = (Struct) a;
				    Object[] values = (Object[]) row.getAttributes();
				    String id = String.valueOf(values[0]);
				    int idCustomer = Integer.parseInt(id);
				    String firstName = String.valueOf(values[1]);
				    String lastName = String.valueOf(values[2]);
				    String DOB = String.valueOf(values[3]);
				    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
					LocalDate dateOfBirth = LocalDate.parse(DOB, formatter);
					String username = String.valueOf(values[4]);
					String password = String.valueOf(values[5]);				    
				    Customer c = new Customer(idCustomer, firstName, lastName, dateOfBirth, username, password);
				    customers.add(c);
				}
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		return customers;
	}
	
	public Customer find(String username, String password) {
		System.out.println("DANS login");
        Customer customer = null;
        String query = "{? = call flogin(?,?)}";
        try (CallableStatement cs = this.connect.prepareCall(query)) {
            cs.registerOutParameter(1, OracleTypes.STRUCT, "TYP_CUSTOMER");
            cs.setString(2, username);
            cs.setString(3, password);
            cs.executeQuery();
            Object data = (Object) cs.getObject(1);
            Struct row = (Struct)data;
            Object[] values = (Object[]) row.getAttributes();
            String id = String.valueOf(values[0]);
            int idCustomer = Integer.parseInt(id);
            String firstName = String.valueOf(values[1]);
            String lastName = String.valueOf(values[2]);
            String DOB = String.valueOf(values[3]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
            LocalDate dateOfBirth = LocalDate.parse(DOB, formatter);
		    customer = new Customer(idCustomer, firstName, lastName, dateOfBirth, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

	@Override
	public ArrayList<Customer> findAll(int id) {
		
		return null;
	}
}