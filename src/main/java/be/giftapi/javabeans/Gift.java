package be.giftapi.javabeans;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;

public class Gift implements Serializable{
	private static final long serialVersionUID = -375971532475389184L;
	private int idGift;
	private String name;
	private String description;
	private Image picture;
	private double price;
	private int priority;
	private boolean booked;
	private boolean multiplePayment;
	private String linkToWebstite;
	private ListGift listGift;
	private ArrayList<Participation> participations;
	
	public Gift ()
	{
		
	}

	public int getIdGift() {
		return idGift;
	}

	public void setIdGift(int idGift) {
		this.idGift = idGift;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public boolean isMultiplePayment() {
		return multiplePayment;
	}

	public void setMultiplePayment(boolean multiplePayment) {
		this.multiplePayment = multiplePayment;
	}

	public String getLinkToWebstite() {
		return linkToWebstite;
	}

	public void setLinkToWebstite(String linkToWebstite) {
		this.linkToWebstite = linkToWebstite;
	}

	public ListGift getListGift() {
		return listGift;
	}

	public void setListGift(ListGift listGift) {
		this.listGift = listGift;
	}

	public ArrayList<Participation> getParticipations() {
		return participations;
	}

	public void setHistories(ArrayList<Participation> participations) {
		this.participations= participations;
	}
}
