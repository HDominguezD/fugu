package com.example.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Entities.Restaurant;
import com.example.Entities.User;
import com.example.Repositories.CityRepository;
import com.example.Repositories.RestaurantRepository;
import com.example.Repositories.UserRepository;
import com.example.Services.SearchWebService;

@Controller
public class SearchWebController {


	@Autowired
	private SearchWebService  searchWebService;
	@Autowired
	private MainController controller;
	
	private Facebook facebook;

	@RequestMapping(value = "/search-web/", method = { RequestMethod.GET, RequestMethod.POST })
	public String main(@RequestParam(required = false) String name, @RequestParam(required = false) String city,
			@RequestParam(required = false) String foodType, @RequestParam(required = false) Double min,
			@RequestParam(required = false) Double max, @RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice, Model model, HttpServletRequest request,
			Authentication authentication, @RequestParam(required = false) String restaurantname,
			@RequestParam(required = false) String restaurantaddress, @RequestParam(required = false) String kindoffood,
			@RequestParam(required = false) String restaurantcity,
			@RequestParam(required = false) String restaurantemail,
			@RequestParam(required = false) String restaurantphone,
			@RequestParam(required = false) String restaurantdescription,
			@RequestParam(required = false) String restaurantpassword, @RequestParam(required = false) String username,
			@RequestParam(required = false) String useremail, @RequestParam(required = false) String userage,
			@RequestParam(required = false) String favouritefood,
			@RequestParam(required = false) String userdescription,
			@RequestParam(required = false) String userpassword) {
		if (name != null) {
			model.addAttribute("restaurants", searchWebService.serviceRestaurantFindByNameIgnoreCase(name));
		} else if (city != null && foodType != null) {
			model.addAttribute("restaurants", searchWebService.serviceRestaurantFindByFoodTypeAndCityName(foodType, city));
		}
		if (name != null) {
			model.addAttribute("restaurants", searchWebService.serviceRestaurantFindByNameIgnoreCase(name));
		} else if (min != null && max != null && minPrice != null && maxPrice != null) {
			model.addAttribute("restaurants",
					searchWebService.serviceRestaurantFindByMenuPriceBetweenAndRateBetween(minPrice, maxPrice, minPrice, maxPrice));
		}
		model.addAttribute("restaurant", searchWebService.serviceRestaurantFindByRateBetweenOrderByRateDesc(new Double(0.0),
				new Double(5.0), new PageRequest(0, 4)));
		if (restaurantname != null) {
			Restaurant rest = new Restaurant(restaurantname, restaurantaddress, restaurantdescription, restaurantemail,
					kindoffood, Integer.parseInt(restaurantphone), 0, 0, restaurantpassword, true, true, true);
			rest.setCity(searchWebService.serviceCityFindByName(restaurantcity));
			searchWebService.serviceRestaurantSave(rest);
		}
		if (username != null) {
			User user = new User(username, useremail, userdescription, userpassword, Integer.parseInt(userage),
					favouritefood);
			searchWebService.serviceUserSave(user);
		}
		User userProfile=new User();
		try{
			facebook = new FacebookTemplate(controller.getAccessToken());
			String [] fields = { "id", "email","name"};
			userProfile = facebook.fetchObject("me", User.class, fields);userProfile.setRoles("USER");
			System.out.println(userProfile.getName());
		}catch(RevokedAuthorizationException e){
			System.out.println("1");
			facebook=null;
		}catch(InvalidAuthorizationException ex){
			System.out.println("2");
			facebook=null;
		}
		model.addAttribute("inSession", (request.isUserInRole("USER")||request.isUserInRole("RESTAURANT")||facebook!=null));
		model.addAttribute("outSession", !request.isUserInRole("USER")&&!request.isUserInRole("RESTAURANT")&&facebook==null);
		model.addAttribute("inNormalSession", (request.isUserInRole("USER")||request.isUserInRole("RESTAURANT")));
		model.addAttribute("inFacebookSession", facebook!=null);
		
		if(request.isUserInRole("USER")){
			System.out.println(authentication.getName());
			model.addAttribute("feedbackname", searchWebService.serviceFindByEmailUser(authentication.getName()).getName());
			model.addAttribute("feedbackemail", authentication.getName());
		}else if(request.isUserInRole("RESTAURANT")){
			model.addAttribute("feedbackname", searchWebService.serviceFindByEmailRestaurant(authentication.getName()).getName());
			model.addAttribute("feedbackemail", authentication.getName());
		}else if(facebook!=null){
			model.addAttribute("feedbackname", userProfile.getName());
			model.addAttribute("feedbackemail", "");
		}

		return "search-web";
	}
}
