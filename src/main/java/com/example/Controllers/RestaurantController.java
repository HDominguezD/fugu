package com.example.Controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Entities.Booking;
import com.example.Entities.Menu;
import com.example.Entities.Restaurant;
import com.example.Entities.Review;
import com.example.Entities.User;
import com.example.Entities.Voucher;
import com.example.Repositories.BookingRepository;
import com.example.Repositories.MenuRepository;
import com.example.Repositories.RestaurantRepository;
import com.example.Repositories.ReviewRepository;
import com.example.Repositories.UserRepository;
import com.example.Repositories.VoucherRepository;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
public class RestaurantController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private VoucherRepository voucherRepository;
	
	/*@ResponseBody
	@JsonView(Restaurant.Basic.class)
	@RequestMapping(value="/restaurants/", method=RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> getRestaurants(HttpSession session) {
		session.setMaxInactiveInterval(-1);
		return new ResponseEntity<>(restaurantRepository.findAll(), HttpStatus.OK);
	}*/
	
	@ResponseBody
	@JsonView(Restaurant.Basic.class)
	@RequestMapping(value="/restaurants/{id}", method=RequestMethod.GET)
	public ResponseEntity<Restaurant> getRestaurant(HttpSession session, @PathVariable long id) {
		session.setMaxInactiveInterval(-1);
		return new ResponseEntity<>(restaurantRepository.findOne(id), HttpStatus.OK);
	}
	
	@ResponseBody
	@JsonView(Restaurant.Basic.class)
	@RequestMapping(value="/restaurants/", method=RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> getRestaurants(HttpSession session,@RequestParam(required=false) String pagenumber) {
		session.setMaxInactiveInterval(-1);
		if(pagenumber==null)pagenumber="0";
		Pageable page = new PageRequest(Integer.parseInt(pagenumber), 2);
		Page<Restaurant> restaurants = restaurantRepository.findAll(page);
		List<Restaurant> resList=new ArrayList<>();
		for(Restaurant restaurant:restaurants){
			resList.add(restaurant);
		}
		System.out.println(restaurants.getTotalPages());
		return new ResponseEntity<>(resList, HttpStatus.OK);
	}
	
	/*@ResponseBody
	@JsonView(User.Basic.class)
	@RequestMapping(value="/restaurants/", method=RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> getRestaurants(HttpSession session,@RequestParam(required=false) String pagenumber) {
		session.setMaxInactiveInterval(-1);
		//Pageable page = new PageRequest(Integer.parseInt(pagenumber), 2);
		return  new ResponseEntity<>(restaurantRepository.findAll(), HttpStatus.OK);
	}*/
	
	@JsonView(Restaurant.Basic.class)
	@RequestMapping("/public-restaurant/{name}")
	public String publicRestaurant(Model model,HttpServletRequest request,Authentication authentication, @PathVariable String name,@RequestParam(required=false) String bookingday,
			@RequestParam(required=false) String bookinghour,@RequestParam(required=false) String guests,
			@RequestParam(required=false) String specialRequirements,
			@RequestParam(required=false) Integer rate, @RequestParam(required=false) String content,
			@RequestParam(required=false) String unfavPulsed,@RequestParam(required=false) String favPulsed) {
		if(bookingday!=null && bookinghour!=null){
			System.out.println(bookingday+" "+bookinghour);
			Date date=new Date();
			try {
				date= new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2017-03-"+bookingday+" "+bookinghour);
		     } catch (ParseException e) {
		         return null;
		     }
			Booking booking=new Booking(date,Integer.parseInt(guests),specialRequirements);
			booking.setBookingRestaurant(restaurantRepository.findByName(name));
			long id=1;
			booking.setBookingUser(userRepository.findOne(id));
			bookingRepository.save(booking);
		}	
		model.addAttribute("restaurant", restaurantRepository.findByName(name));
		model.addAttribute("menu", restaurantRepository.findByName(name).getMenus());
		model.addAttribute("vouchers", restaurantRepository.findByName(name).getVouchers());
		model.addAttribute("reviews", restaurantRepository.findByName(name).getRestaurantReviews());
		if(request.isUserInRole("USER")){
			String userloggin = authentication.getName();
			if (rate!=null) {	
				Review review = new Review(content,rate,new Date());
				review.setReviewRestaurant(restaurantRepository.findByName(name));
				review.setReviewUser(userRepository.findByEmail(userloggin));
				reviewRepository.save(review);
				}
		}
		if(request.isUserInRole("USER")){
			String userloggin = authentication.getName();
			if (favPulsed!=null) {	
				
				userRepository.findByEmail(userloggin).getRestaurants().add(restaurantRepository.findByName(name));
				userRepository.save(userRepository.findByEmail(userloggin));
				restaurantRepository.findByName(name).getUsers().add(userRepository.findByEmail(userloggin));
				restaurantRepository.save(restaurantRepository.findByName(name));
			}else if (unfavPulsed!=null) {
				userRepository.findByEmail(userloggin).getRestaurants().remove(restaurantRepository.findByName(name));
				userRepository.save(userRepository.findByEmail(userloggin));
				restaurantRepository.findByName(name).getUsers().remove(userRepository.findByEmail(userloggin));
				restaurantRepository.save(restaurantRepository.findByName(name));
			}
		}
		return "public-restaurant";
	}
	
	@JsonView(Restaurant.Basic.class)
	@RequestMapping("/private-restaurant/")
	public String privateRestaurant(Model model, HttpServletRequest request,Authentication authentication, @RequestParam(required=false) String type,
	@RequestParam(required=false) Integer max, @RequestParam(required=false) Integer min,
	@RequestParam(required=false) String vouchername, @RequestParam(required=false) String voucherdescription,
	@RequestParam(required=false) String menudescription,@RequestParam(required=false) String menuname,
	@RequestParam(required=false) Double menuprice, @RequestParam(required=false)String namerest,
	@RequestParam(required=false)String location, @RequestParam(required=false)Integer telephone,
	@RequestParam(required=false)String descriptionrest,@RequestParam(required=false)String emailrest,
	@RequestParam(required=false)String pwd,@RequestParam(required=false)String confirmpwd,
	@RequestParam(required=false)Boolean Breakfast,@RequestParam(required=false)Boolean Lunch,
	@RequestParam(required=false)Boolean Dinner) {
		try{
			if(request.isUserInRole("RESTAURANT")){
		
				String restaurantloggin = authentication.getName();
				model.addAttribute("restaurant", restaurantRepository.findByEmail(restaurantloggin));
				model.addAttribute("menu", restaurantRepository.findByEmail(restaurantloggin).getMenus());
				model.addAttribute("bookings", restaurantRepository.findByEmail(restaurantloggin).getBookings());
				model.addAttribute("vouchers", restaurantRepository.findByEmail(restaurantloggin).getVouchers());
				model.addAttribute("reviews", restaurantRepository.findByEmail(restaurantloggin).getRestaurantReviews());
				if (namerest!=null){
					Restaurant restaurant = restaurantRepository.findByEmail(restaurantloggin);
					restaurant.setName(namerest);
					restaurant.setAddress(location);
					restaurant.setDescription(descriptionrest);
					restaurant.setPhone(telephone);
					restaurant.setEmail(emailrest);
					if (pwd.equals(confirmpwd)){
						restaurant.setPassword(pwd);
					}
					if (Breakfast != null)
						restaurant.setBreakfast(true);
					else 
						restaurant.setBreakfast(false);
					if (Lunch != null)
						restaurant.setLunch(true);
					else 
						restaurant.setLunch(false);
					if (Dinner != null)
						restaurant.setDinner(true);
					else 
						restaurant.setDinner(false);
					restaurantRepository.save(restaurant);
				
				}
				if (vouchername!=null){
					Voucher voucher= new Voucher(vouchername,voucherdescription,new Date());
					voucher.setVoucherUsers(userRepository.findByAgeBetween(min,max));
					voucher.setRestaurant(restaurantRepository.findByEmail(restaurantloggin));
					voucherRepository.save(voucher);}
				if (menuname!=null){
					Menu menu= new Menu(menuname,menuprice,menudescription);
					menu.setRestaurantMenu(restaurantRepository.findByEmail(restaurantloggin));
					menuRepository.save(menu);}
			}
		}catch(NullPointerException ex){
			ex.printStackTrace();
			
		}
		return "private-restaurant";
			}
}
