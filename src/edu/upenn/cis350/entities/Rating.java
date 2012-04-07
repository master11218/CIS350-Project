package edu.upenn.cis350.entities;

import java.io.Serializable;

public class Rating implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long user_id;
	private Long provider_id;
	private String date;
	private String review;
	private Integer rating;
	
	public Rating(long user, long provider, String date, String desc, int rate){
		user_id = user;
		provider_id = provider;
		this.date = date;
		review = desc;
		rating = rate;
	}
	
	public Long getUser(){
		return user_id;
	}
	public Long getProvider(){
		return provider_id;
	}
	public String getDate(){
		return date;
	}
	public String getReview(){
		return review;
	}
	public Integer getRating(){
		return rating;
	}
}