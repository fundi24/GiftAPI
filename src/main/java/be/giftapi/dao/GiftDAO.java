package be.giftapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import be.giftapi.javabeans.Gift;

public class GiftDAO extends DAO<Gift>{

	  public GiftDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(Gift obj) {
	    	boolean success = false;
	    	
	        String query = "{call insert_gift(?,?,?,?,?,?,?,?,?)}";

	        
	        try(CallableStatement cs= this.connect.prepareCall(query)) {
	              
	        	cs.setString(1, obj.getName());
	        	cs.setString(2, obj.getDescription());
	        	cs.setDouble(3, obj.getPrice());
	        	cs.setInt(4, obj.getPriority());
	        	//cs.setString(5, obj.getPicture());
	        	cs.setBoolean(6, obj.isBooked());
	        	cs.setBoolean(7, obj.isMultiplePayment());
	        	cs.setString(8, obj.getLinkToWebsite());
	        	cs.setInt(9, obj.getListGift().getIdListGift());
	        
	        	cs.executeUpdate();
	               
	            success = true;
	         } catch (SQLException e) {
	                System.out.println(e.getMessage());
	            }
	       
	        return success;
	    }

	    @Override
	    public boolean delete(Gift obj) {
	        return false;
	    }

	    @Override
	    public boolean update(Gift obj) {
	        return false;
	    }

	    @Override
	    public Gift find(int id) {
	        return null;
	    }

	    @Override
	    public ArrayList<Gift> findAll(int id) {
	        return null;
	    }
}
