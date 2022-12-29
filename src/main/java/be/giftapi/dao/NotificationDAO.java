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

import be.giftapi.javabeans.ListGift;
import be.giftapi.javabeans.Notification;
import oracle.jdbc.OracleTypes;

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
		public ArrayList<Notification> findAll(int idCustomer) {
			ArrayList<Notification> notifications = new ArrayList<>();
			
			String query = "{? = call fselect_notifications_from_customer(?)}";

			try (CallableStatement cs = this.connect.prepareCall(query)) {
				
				cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_NOTIFICATION");
				cs.setInt(2, idCustomer);
				cs.executeQuery();
				
				Array arr = cs.getArray(1);
				if (arr != null) {
					Object[] data = (Object[]) arr.getArray();
					
					for (Object a : data) {
					    Struct row = (Struct) a;
					    Object[] values = (Object[]) row.getAttributes();
					    
					    String id = String.valueOf(values[0]);
					    int idNotification = Integer.parseInt(id);
					    String message = String.valueOf(values[1]);
					    int intRead = Integer.parseInt(String.valueOf(values[2]));
					    boolean read = intRead == 1 ? true : false;
							    
					    Notification notification = new Notification(idNotification, message, read, null);
					    notifications.add(notification);
					}
				}
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
			return notifications;
		}

}
