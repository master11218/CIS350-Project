package edu.upenn.cis350;

import java.io.Serializable;
import java.util.ArrayList;

public class Provider implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String accepting_new;
	private String has_parking;
	private String type;
	private String credit_cards;
	private String appointment;
	private ArrayList<Rating> ratings = new ArrayList<Rating>();
	private ArrayList<String> comments = new ArrayList<String>();
	private Double longitude;
	private Double latitude;
	//private GeoPoint location;
	
	public Provider(long id, String name, String address, String city, String state, String zip, String phone, 
			String accepting_new, String has_parking, String type, String credit_cards,
			String appointment, ArrayList<Rating> rates, 
			double longitude, double latitude){
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.accepting_new = accepting_new;
		this.has_parking = has_parking;
		this.type = type;
		this.credit_cards = credit_cards;
		this.appointment = appointment;
		//if rates is not null,
		if(rates != null) this.ratings = rates;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public long getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public ArrayList<Rating> getRatings(){
		return ratings;
	}
	
	public Double getAvgRating(){
		double totalRating = 0;
		if(this.getRatings().size() == 0)
			return 0.0;
		for(Rating r: this.getRatings())
			totalRating += r.getRating();
		return totalRating/(double)this.getRatings().size();
	}
	
	public ArrayList<String> comments(){
		return comments;
	}
	
	public Double getLatitude(){
		return this.latitude;
	}
	
	public Double getLongitude(){
		return this.longitude;
	}
	
	public void setID(long n){
		id = n;
	}
	
	public void setName(String n){
		name = n;
	}

	public void setAddress(String n){
		address = n;
	}

	public void setPhone(String n){
		phone = n;
	}

	public void addRating(Rating n){
		ratings.add(n);
	}
	
	public void addComment(String n){
		comments.add(n);
	}
	
	public void setLatitude(double d){
		latitude = d;
	}
	
	public void setLongitude(double d){
		longitude = d;
	}
}