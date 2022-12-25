package be.giftapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import be.giftapi.javabeans.Notification;

public class NotificationDAO extends DAO<Notification> {
	
	 public NotificationDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(Notification obj) {
	    	boolean success = false;
	    	
	        String query = "{call insert_notification(?,?,?)}";

	        
	        try(CallableStatement cs= this.connect.prepareCall(query)) {
	              
	        	cs.setString(1, obj.getMessage());
	        	cs.setBoolean(2, obj.isRead());
	        	cs.setInt(3, obj.getCustomer().getIdCustomer());
	        
	        	cs.executeUpdate();
	               
	            success = true;
	         } catch (SQLException e) {
	                System.out.println(e.getMessage());
	            }
	       
	        return success;
	    }

	    @Override
	    public boolean delete(Notification obj) {
	        return false;
	    }

	    @Override
	    public boolean update(Notification obj) {
	        return false;
	    }

	    @Override
	    public Notification find(int id) {
	        return null;
	    }

	    @Override
	    public ArrayList<Notification> findAll() {
	        return null;
	    }

		@Override
		public ArrayList<Notification> findAll(int id) {
			
			return null;
		}

}
