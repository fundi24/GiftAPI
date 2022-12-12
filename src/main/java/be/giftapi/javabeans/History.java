package be.giftapi.javabeans;

import java.io.Serializable;

public class History implements Serializable {

	private static final long serialVersionUID = -4098840781809412627L;
    private int idHistory;
    private double amountPaid;
    private Customer customer;
    private Gift gift;

    public History()
    {

    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }
}
