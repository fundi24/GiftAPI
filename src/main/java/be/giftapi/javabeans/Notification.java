package be.giftapi.javabeans;

import java.io.Serializable;

import be.giftapi.dao.AbstractDAOFactory;
import be.giftapi.dao.DAO;

public class Notification implements Serializable{
	
	private static final long serialVersionUID = 3306630235932605381L;
    private int idNotification;
    private String message;
    private boolean read;
    private Customer customer;

    public Notification()
    {

    }
    
    public Notification(int idNotification, String message, boolean read, Customer customer) {
		super();
		this.idNotification = idNotification;
		this.message = message;
		this.read = read;
		this.customer = customer;
	}

	public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public boolean insert() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory();
		DAO<Notification> notificationDAO = adf.getNotificationDAO();
		return notificationDAO.create(this);
    }
}
