package edu.upenn.cis350;

import java.util.ArrayList;

public class Provider {
	private long id;
	private String name;
	private String address;
	private String phone;
	private ArrayList<Float> ratings = new ArrayList<Float>();
	private float avgRating;
	private ArrayList<String> comments = new ArrayList<String>();
	private float longitude;
	private float latitude;
	
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
	public ArrayList<Float> getRatings(){
		return ratings;
	}
	public float getAvgRating(){
		return avgRating;
	}
	public ArrayList<String> comments(){
		return comments;
	}
	public float getLongitude(){
		return longitude;
	}
	public float getLatitude(){
		return latitude;
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

	public void addRating(float n){
		avgRating = ((avgRating * ratings.size()) + n)/(ratings.size()+1); 
		ratings.add(n);
	}
	
	public void addComment(String n){
		comments.add(n);
	}
	
	public void setLongitude(float n){
		longitude = n;
	}

	public void setLatitude(float n){
		latitude = n;
	}
}

