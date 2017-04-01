package com.example.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entities.Booking;
import com.example.Entities.Restaurant;
import com.example.Entities.User;
import com.example.Repositories.BookingRepository;
import com.example.Repositories.RestaurantRepository;
import com.example.Repositories.UserRepository;
@Service
public class ClientService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	public User userRepositoryfindByName(String name){
		return userRepository.findByName(name);
	}
	public User userRestaurantfindByEmail(String email){
		return userRepository.findByEmail(email);
	}
	public Iterable<Restaurant> restaurantRepositoryfindAll(){
		return  restaurantRepository.findAll();
	}
	public void userRepositorysave(User user){
		 userRepository.save(user);
	}
	public List<Booking> bookingRepositoryfindByStateAndBookingUser(String state,User user){
		return bookingRepository.findByStateAndBookingUser(state, user);
	}
}
