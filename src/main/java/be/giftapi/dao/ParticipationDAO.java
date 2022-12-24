package be.giftapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import be.giftapi.javabeans.Participation;

public class ParticipationDAO extends DAO<Participation> {
	
	public ParticipationDAO (Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(Participation obj) {
    	boolean success = false;
    	
        String query = "{call insert_participation(?,?,?)}";

        
        try(CallableStatement cs= this.connect.prepareCall(query)) {
              
        	cs.setDouble(1, obj.getAmountPaid());
        	cs.setInt(2,  obj.getGift().getIdGift());
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

}
