package be.giftapi.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.giftapi.javabeans.Notification;

public class NotificationDAO extends DAO<Notification> {
	
	 public NotificationDAO(Connection conn) {
	        super(conn);
	    }

	    @Override
	    public boolean create(Notification obj) {
	        return false;
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
	    public ArrayList<Notification> findAll(int id) {
	        return null;
	    }
}