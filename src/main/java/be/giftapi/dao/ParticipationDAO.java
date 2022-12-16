package be.giftapi.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.giftapi.javabeans.Participation;

public class ParticipationDAO extends DAO<Participation> {
	
	public ParticipationDAO (Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(Participation obj) {
        return false;
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
    public ArrayList<Participation> findAll(int id) {
        return null;
    }
}
