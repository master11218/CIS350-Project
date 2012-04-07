package edu.upenn.cis350.entities;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private long _id;
	private String _name;
	private String _email;
	private String _address;
	private String _gender;
	private String _phone;

	public User (long id, String name, String email, String address, String gender, String phone){
		_id = id;
		_name = name;
		_email = email;
		_address = address;
		_gender = gender;
		_phone = phone;
	}

	public Long getId(){
		return this._id;
	}

	public String getName(){
		return this._name;
	}

	public String getEmail(){
		return this._email;
	}

	public String getAddress(){
		return this._address;
	}

	public String getGender(){
		return this._gender;
	}

	public String getPhone(){
		return this._phone;
	}
}
