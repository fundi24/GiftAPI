package be.giftapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import be.giftapi.javabeans.Customer;

public class CustomerDAO extends DAO<Customer>{

	   public CustomerDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(Customer obj) {
	    	boolean success = false;
	    	//int newId = 0;
	        

	        String query = "{call insert_customer(?,?,?,?,?)}";

	        //verifier si l'username est dispo
	            try(CallableStatement cs= this.connect.prepareCall(query)) {
	              
	                cs.setString(1,obj.getFirstName());
	                cs.setString(2, obj.getLastName());
	                cs.setDate(3, Date.valueOf(obj.getDateOfBirth()));
	                cs.setString(4, obj.getUsername());
	                cs.setString(5,obj.getPassword());
	               
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
	        return null;
	    }

	    @Override
	    public ArrayList<Customer> findAll(int id) {
	        return null;
	    } 
	    
	    
	    
	    /*public boolean checkIfUsernameIsAvailable(String username) {
	        boolean isValid = true;

	        String query = "SELECT * FROM User WHERE Username='" + username + "'";

	        try {
	            ResultSet result = this.connect
	                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
	            if (result.first()) {
	                isValid = false;
	                JOptionPane.showMessageDialog(null, "Username is already used !");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return isValid;
	    }*/
}
