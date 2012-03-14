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
	private String phone;
	private ArrayList<Rating> ratings = new ArrayList<Rating>();
	private ArrayList<String> comments = new ArrayList<String>();
	private Double longitude;
	private Double latitude;
	//private GeoPoint location;
	
	public Provider(long id, String name, String address, String phone, ArrayList<Rating> rates, 
			double longitude, double latitude){
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.ratings = rates;
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