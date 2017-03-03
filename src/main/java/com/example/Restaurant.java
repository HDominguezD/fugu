package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String address;
	private String description;
	private String email;
	private String foodTypes;
	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public List<Review> getRestaurantReviews() {
		return restaurantReviews;
	}

	public void setRestaurantReviews(List<Review> restaurantReviews) {
		this.restaurantReviews = restaurantReviews;
	}
	private long phone;
	private String password;
	@ManyToMany (mappedBy="restaurants")
	private List<User> users = new ArrayList<>();
	@OneToMany (mappedBy="restaurantMenu")
	private List<Menu> menus = new ArrayList<>();
	@OneToMany(mappedBy="restaurant")
	private List<Voucher> vouchers = new ArrayList<>();
	@OneToMany(mappedBy="bookingRestaurant")
	private List<Booking> bookings = new ArrayList<>();
	@ManyToOne
	 private City city;
	@OneToMany(mappedBy="reviewRestaurant")
	 private List<Review> restaurantReviews = new ArrayList<>();

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	public Restaurant (){}

	public Restaurant( String name, String address, String description, String email, String foodTypes,
			long phone, String password) {
		super();
		
		this.name = name;
		this.address = address;
		this.description = description;
		this.email = email;
		this.foodTypes = foodTypes;
		this.phone = phone;
		this.password = password;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFoodTypes() {
		return foodTypes;
	}
	public void setFoodTypes(String foodTypes) {
		this.foodTypes = foodTypes;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}