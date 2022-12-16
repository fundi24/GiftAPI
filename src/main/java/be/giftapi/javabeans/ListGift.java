package be.giftapi.javabeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class ListGift implements Serializable {
	
	private static final long serialVersionUID = 99181030694279507L;
    private int idListGift;
    private String name;
    private LocalDate deadline;
    private boolean status;
    private String theme;
    private Customer owner;
    private ArrayList<Customer> participants;
    private ArrayList<Gift> gifts;

    public ListGift()
    {

    }

    public int getIdListGift() {
        return idListGift;
    }

    public void setIdListGift(int idListGift) {
        this.idListGift = idListGift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public ArrayList<Customer> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Customer> participants) {
        this.participants = participants;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(ArrayList<Gift> gifts) {
        this.gifts = gifts;
    }


}
