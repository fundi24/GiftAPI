package be.giftapi.dao;

import be.giftapi.javabeans.*;

public abstract class AbstractDAOFactory {

    public abstract DAO<Customer> getCustomerDAO();

    public abstract DAO<ListGift> getListGiftDAO();

    public abstract DAO<Gift> getGiftDAO();

    public abstract DAO<Notification> getNotificationDAO();

    public abstract DAO<Participation> getParticipationDAO();
}
