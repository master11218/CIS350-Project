package edu.upenn.cis350;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

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
	private float avgRating;
	private ArrayList<String> comments = new ArrayList<String>();
	private GeoPoint location;
	
	public Provider(long id, String name, String address, String phone, ArrayList<Rating> rates, 
			double longitude, double latitude){
		this.id = id;
		this.name = name;
		this.address =address;
		this.phone = phone;
		this.ratings = rates;
		GeoPoint x = new GeoPoint((int)(latitude * (10^6)), (int)(longitude * 6));
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
	public float getAvgRating(){
		return avgRating;
	}
	public ArrayList<String> comments(){
		return comments;
	}
	public GeoPoint getLocation(){
		return location;
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
		avgRating = ((avgRating * ratings.size()) + n.getRating())/(ratings.size()+1); 
		ratings.add(n);
	}
	
	public void addComment(String n){
		comments.add(n);
	}
	
}