package edu.upenn.cis350.entities;

import java.io.Serializable;

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
	private String handicap_access;
	private String appointment;
	double averageRating;
	private Double longitude;
	private Double latitude;
	private String website;
	private String hours;
	
	public Provider(long id, String name, String address, String city, String state, String zip, String phone, 
			String accepting_new, String has_parking, String type, String credit_cards, String handicap_access,
			String appointment, double averageRating, 
			double longitude, double latitude, String website, String hours){
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.accepting_new = accepting_new;
		this.has_parking = has_parking;
		this.type = type;
		this.credit_cards = credit_cards;
		this.appointment = appointment;
		this.averageRating = averageRating;
		this.longitude = longitude;
		this.latitude = latitude;
		this.handicap_access = handicap_access;
		this.website = website;
		this.hours = hours;
	}
	
	public long getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getHours(){
		return hours;
	}
	
	public String getWebsite(){
		return website;
	}
	
	public String getHandicapAccess(){
		return handicap_access;
	}
	
	public String getAddress(){
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getPhone(){
		return phone;
	}
	public String getAccepting() {
		return accepting_new;
	}
	public String getParking() {
		return has_parking;
	}
	public String getType() {
		return type;
	}
	public String getCreditCards() {
		return credit_cards;
	}
	public String getAppointment() {
		return appointment;
	}
	public double getAverageRating(){
		return averageRating;
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
	
	public void setLatitude(double d){
		latitude = d;
	}
	
	public void setLongitude(double d){
		longitude = d;
	}
}