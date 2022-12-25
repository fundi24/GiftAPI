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

		@Override
		public ArrayList<ListGift> findAll(int idCustomer) {
			ArrayList<ListGift> listgifts= new ArrayList<ListGift>();
			
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
					    System.out.println("name listgift" + name);
					    String strDeadline = String.valueOf(values[2]);
					    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.n");
						LocalDate deadline= LocalDate.parse(strDeadline, formatter);
					    String strStatus = String.valueOf(values[3]);
					    boolean status;
					    status = Integer.parseInt(strStatus) == 1 ? true : false;
						String theme = String.valueOf(values[4]);
							    
					    ListGift listgift = new ListGift(idListGift, name, deadline, status, theme, null);
					    listgifts.add(listgift);
					}
				}
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
			return listgifts;
		}

}
