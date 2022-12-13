package be.giftapi.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.giftapi.javabeans.Customer;

public class CustomerDAO extends DAO<Customer>{

	   public CustomerDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(Customer obj) {
	        return false;
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
}
