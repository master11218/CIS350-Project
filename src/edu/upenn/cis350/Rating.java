package edu.upenn.cis350;

import java.util.Date;

public class Rating {

	private long user_id;
	private long provider_id;
	private Date date;
	private String review;
	private int rating;
	
	public Rating(long user, long provider, Date date, String desc, int rate){
		user_id = user;
		provider_id = provider;
		this.date = date;
		review = desc;
		rating = rate;
	}
	public long getUser(){
		return user_id;
	}
	public long getProvider(){
		return provider_id;
	}
	public Date getDate(){
		return date;
	}
	public String getReview(){
		return review;
	}
	public int getRating(){
		return rating;
	}
}
