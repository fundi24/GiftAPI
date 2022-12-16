package be.giftapi.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.giftapi.javabeans.ListGift;

public class ListGiftDAO extends DAO<ListGift> {

	 public ListGiftDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(ListGift obj) {
	        return false;
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
	    public ArrayList<ListGift> findAll(int id) {
	        return null;
	    }
}
