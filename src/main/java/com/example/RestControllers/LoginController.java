package com.example.RestControllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entities.Restaurant;
import com.example.Entities.User;
import com.example.Security.RestaurantComponent;
import com.example.Security.UserComponent;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * This class is used to provide REST endpoints to logIn and logOut to the
 * service. These endpoints are used by Angular 2 SPA client application.
 * 
 * NOTE: This class is not intended to be modified by app developer.
 */
@RestController
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private RestaurantComponent restaurantComponent;

	@JsonView(User.Basic.class)
	@RequestMapping("/api/logIn/user")
	public ResponseEntity<User> logInUser() {
		if (!userComponent.isLoggedUser()) {
			log.info("Not user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			User loggedUser = userComponent.getLoggedUser();
			log.info("Logged as " + loggedUser.getName());
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		}
	}
	
	@JsonView(Restaurant.Basic.class)
	@RequestMapping("/api/logIn/restaurant")
	public ResponseEntity<Restaurant> logInRestaurant() {
		if (!restaurantComponent.isLoggedRestaurant()) {
			log.info("Not restaurant logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else{
			Restaurant loggedRestaurant = restaurantComponent.getLoggedRestaurant();
			log.info("Logged as " + loggedRestaurant.getName());
			return new ResponseEntity<>(loggedRestaurant, HttpStatus.OK);
		}
	}

	@RequestMapping("/api/logOut")
	public ResponseEntity<Boolean> logOut(HttpSession session) {

		if (!userComponent.isLoggedUser()&&!restaurantComponent.isLoggedRestaurant()) {
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}

}