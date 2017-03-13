package com.example;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class DefaultController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
    @RequestMapping("/default/")
    public String defaultAfterLogin(HttpServletRequest request,Authentication authentication) {
    	try{
    		if (request.isUserInRole("USER"+userRepository.findByEmail(authentication.getName()).getName())) {
            	User user=userRepository.findByEmail(authentication.getName());
                return "redirect:/private-client/"+user.getName();}
    	}catch(NullPointerException e){
    		
    	}
    	try{
    		if (request.isUserInRole("RESTAURANT"+restaurantRepository.findByEmail(authentication.getName()).getName())) {
            	Restaurant restaurant=restaurantRepository.findByEmail(authentication.getName());
                return "redirect:/private-restaurant/"+restaurant.getName();}
    	}catch(NullPointerException e){
    		
    	}
    		

    	return "redirect:/main/";
    }
}