package com.example;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	//private Restaurant restaurant;
	@ManyToOne
	private User bookingUser = new User();
	@ManyToOne
	private Restaurant bookingRestaurant = new Restaurant();
	private Date date;
	//private User user;
	private int number;
	
	public Booking(){}
	public Booking(Date date, int number) {
		super();
		//this.restaurant = restaurant;
		this.date = date;
		//this.user = user;
		this.number = number;
	}

	public User getBookingUser() {
		return bookingUser;
	}
	public void setBookingUser(User bookingUser) {
		this.bookingUser = bookingUser;
	}
	public Restaurant getBookingRestaurant() {
		return bookingRestaurant;
	}
	public void setBookingRestaurant(Restaurant bookingRestaurant) {
		this.bookingRestaurant = bookingRestaurant;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Restaurant getRestaurant() {
		return null;//restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		//this.restaurant = restaurant;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return null;//user;
	}
	public void setUser(User user) {
		this.bookingUser = user;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
