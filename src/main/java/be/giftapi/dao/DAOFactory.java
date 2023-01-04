package be.giftapi.dao;

import java.sql.Connection;

import be.giftapi.connection.DatabaseConnection;
import be.giftapi.javabeans.Customer;
import be.giftapi.javabeans.Gift;
import be.giftapi.javabeans.ListGift;
import be.giftapi.javabeans.Notification;
import be.giftapi.javabeans.Participation;

public class DAOFactory extends AbstractDAOFactory {

	protected static final Connection conn = DatabaseConnection.getInstance();

	@Override
	public DAO<Customer> getCustomerDAO() {
		
		return new CustomerDAO(conn);
	}

	@Override
	public DAO<ListGift> getListGiftDAO() {
		
		return new ListGiftDAO(conn);
	}

	@Override
	public DAO<Gift> getGiftDAO() {
		
		return new GiftDAO(conn);
	}

	@Override
	public DAO<Notification> getNotificationDAO() {
		
		return new NotificationDAO(conn);
	}

	@Override
	public DAO<Participation> getParticipationDAO() {
		
		return new ParticipationDAO(conn);
	}
	
	
		

}
