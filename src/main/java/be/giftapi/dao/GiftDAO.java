package be.giftapi.dao;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import be.giftapi.javabeans.Gift;
import be.giftapi.javabeans.ListGift;
import oracle.jdbc.OracleTypes;

public class GiftDAO extends DAO<Gift>{

	  public GiftDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(Gift obj) {
	    	boolean success = false;
	    	
	        String query = "{call insert_gift(?,?,?,?,?,?,?,?,?)}";
	       
	        
	        byte[] byteArray = obj.getPicture().getBytes();
	        InputStream pictureStream = new ByteArrayInputStream(byteArray);
	        
	        try(CallableStatement cs= this.connect.prepareCall(query)) {
	              
	        	cs.setString(1, obj.getName());
	        	cs.setString(2, obj.getDescription());
	        	cs.setDouble(3, obj.getPrice());
	        	cs.setInt(4, obj.getPriority());
	        	cs.setBinaryStream(5, pictureStream);
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
	    public ArrayList<Gift> findAll() {
	        return null;
	    }

		@Override
		public ArrayList<Gift> findAll(int idListGift) {
			ArrayList<Gift> gifts= new ArrayList<Gift>();
			
			String query = "{? = call fselect_gifts_from_listgift(?)}";

			try (CallableStatement cs = this.connect.prepareCall(query)) {
				
				cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_GIFT");
				cs.setInt(2, idListGift);
				cs.executeQuery();
				
				Array arr = cs.getArray(1);
				if (arr != null) {
					Object[] data = (Object[]) arr.getArray();
					
					for (Object a : data) {
					    Struct row = (Struct) a;
					    Object[] values = (Object[]) row.getAttributes();
					   
					    int idGift = Integer.parseInt(String.valueOf(values[0]));
					    String name = String.valueOf(values[1]);
					    String description = String.valueOf(values[2]);
					    double price = Double.valueOf((String.valueOf(values[3])));
					    int priority = Integer.parseInt(String.valueOf(values[4]));
					    String picture = String.valueOf(values[5]);
					    int intBooked = Integer.parseInt(String.valueOf(values[6]));
					    boolean booked = intBooked == 1 ? true : false;
					    int intMultiplePayment = Integer.parseInt(String.valueOf(values[7]));
					    boolean multiplePayment = intMultiplePayment == 1 ? true : false;
					    String linkToWebsite = String.valueOf(values[8]);
					    
							    
					    Gift gift = new Gift(idGift, name, description, price, priority, picture, booked, multiplePayment, linkToWebsite, null);
					    gifts.add(gift);
					}
				}
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
			return gifts;
			
		}

}
