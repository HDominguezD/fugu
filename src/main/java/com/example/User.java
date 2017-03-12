package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class User {
	
	private String name;
	private String email;
	private String description;
	private String favouriteFood;
	private String password;
	private int age;
	
	private String roles;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToMany
	private List<Restaurant> restaurants = new ArrayList<>();
	
	@ManyToMany
	private List<User> following = new ArrayList<>();
	
	@OneToMany(mappedBy = "reviewUser")
	private List<Review> reviews = new ArrayList<>();
	
	@OneToMany(mappedBy = "bookingUser")
	private List<Booking> bookings = new ArrayList<>();
	
	@ManyToMany(mappedBy="voucherUsers")
	private List<Voucher> userVouchers = new ArrayList<>();

	public User() {}
	public User(String name, String email, String description, String password, int age, String favouriteFood, String roles) {
		this.name = name;
		this.email = email;
		this.description = description;
		this.password =  new BCryptPasswordEncoder().encode(password);
		this.age = age;
		this.favouriteFood = favouriteFood;
		this.roles = roles;

	}
	public List<User> getFollowing() {
		return following;
	}
	public void setFollowing(List<User> following) {
		this.following = following;
	}
	public String getFavouriteFood() {
		return favouriteFood;
	}
	public void setFavouriteFood(String favouriteFood) {
		this.favouriteFood = favouriteFood;
	}
	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	public List<Voucher> getUserVouchers() {
		return userVouchers;
	}
	public void setUserVouchers(List<Voucher> userVouchers) {
		this.userVouchers = userVouchers;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
