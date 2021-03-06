package com.example.Security;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.example.Entities.Restaurant;


/**
 * This class is designed to manage the information for the user while he is
 * logged in the service. This object can be used in any other @Component
 * auto-wiring it as usual.
 * 
 * Instances of this class are never sent to the user in any REST endpoint. It
 * can hold sensible information that can not be known in the client. 
 * 
 * NOTE: This class is intended to be extended by developer adding new
 * attributes. Current attributes can not be removed because they are used in
 * authentication procedures.
 */

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RestaurantComponent {

	private Restaurant restaurant;

	public Restaurant getLoggedRestaurant() {
		return restaurant;
	}

	public void setLoggedRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public boolean isLoggedRestaurant() {
		return this.restaurant != null;
	}

}
