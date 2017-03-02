package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class User {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	private String name;
	private String email;
	private String description;
	private String password;
	private int age;

	@ManyToMany
	private List<Restaurant> restaurants = new ArrayList<>();
	@OneToMany(mappedBy = "user")
	private List<Review> reviews = new ArrayList<>();
	@OneToMany(mappedBy = "bookingUser")
	private List<Booking> bookings = new ArrayList<>();
	@ManyToMany(mappedBy="voucherUsers")
	 private List<Voucher> userVouchers = new ArrayList<>();

	public User() {
	}

	public User(String name, String email, String description, String password, int age) {
		this.name = name;
		this.email = email;
		this.description = description;
		this.password = password;
		this.age = age;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Restaurant> getRestaurant() {
		return restaurants;
	}

	public void setRestaurant(ArrayList<Restaurant> Restaurant) {
		this.restaurants = Restaurant;
	}

}
