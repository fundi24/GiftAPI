package be.giftapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import be.giftapi.javabeans.ListGift;

public class ListGiftDAO extends DAO<ListGift> {

	 public ListGiftDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(ListGift obj) {
	    	boolean success = false;
	    	
	        String query = "{call insert_list_gift(?,?,?,?,?)}";

	        
	        try(CallableStatement cs= this.connect.prepareCall(query)) {
	              
	        	cs.setString(1,obj.getName());
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
	        return false;
	    }

	    @Override
	    public ListGift find(int id) {
	        return null;
	    }

	    @Override
	    public ArrayList<ListGift> findAll() {
	        return null;
	    }
}
