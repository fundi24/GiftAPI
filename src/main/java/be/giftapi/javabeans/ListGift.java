package be.giftapi.javabeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import be.giftapi.dao.AbstractDAOFactory;
import be.giftapi.dao.DAO;
import be.giftapi.dao.ListGiftDAO;


public class ListGift implements Serializable {
	
	private static final long serialVersionUID = 99181030694279507L;
	private static final AbstractDAOFactory adf =  AbstractDAOFactory.getFactory();;
	private static final DAO<ListGift> listGiftDAO = adf.getListGiftDAO();
	
	
	 private int idListGift;
	    private String name;
	    private LocalDate deadline;
	    private boolean status;
	    private String theme;
	    private Customer owner;
	    private ArrayList<Customer> invitations;
	    private ArrayList<Gift> gifts;

	    public ListGift()
	    {
	    	invitations = new ArrayList<>();
			gifts = new ArrayList<>();
	    }
	    
		public ListGift(int idListGift, String name, LocalDate deadline, boolean status, String theme, Customer owner) {
			this.idListGift = idListGift;
			this.name = name;
			this.deadline = deadline;
			this.status = status;
			this.theme = theme;
			this.owner = owner;
			invitations = new ArrayList<>();
			gifts = new ArrayList<>();
		}

		
		//Getters and Setters

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

	    public ArrayList<Customer> getInvitations() {
	        return invitations;
	    }

	    public void setInvitations(ArrayList<Customer> invitations) {
	        this.invitations = invitations;
	    }

	    public ArrayList<Gift> getGifts() {
	        return gifts;
	    }

	    public void setGifts(ArrayList<Gift> gifts) {
	        this.gifts = gifts;
	    }
	    
	    
	    //Add and remove for lists
	    
	    public void addInvitations(Customer customer) {
	    	invitations.add(customer);
		}
		
	    
	    public void removeInvitations(Customer customer) {
	    	invitations.remove(customer);
		}
	    
	    public void addGift(Gift gift) {
	    	gifts.add(gift);
	    }
	    
	    public void removeGift(Gift gift) {
	    	gifts.remove(gift);
	    }
		
	    
	    //Call to DAO
	    
	    public boolean insert() {
			return listGiftDAO.create(this);
	    }
	    
		public static ListGift getListGift(int id) {
			return listGiftDAO.find(id);
		}

		public static ArrayList<ListGift> getListGiftFromCustomer(int id){
			return listGiftDAO.findAll(id);
		}
		
		public static ArrayList<Customer> getInvitationsFromListGift(int id){
			ListGiftDAO listgiftDao = (ListGiftDAO) adf.getListGiftDAO();
			return listgiftDao.getInvitationsFromListGift(id);	
		}
		
		public boolean update() {
			return listGiftDAO.update(this);
		}
		
		
	
}
