package com.example.RestControllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entities.City;
import com.example.Entities.Restaurant;
import com.example.Repositories.CityRepository;
import com.example.RestControllers.ClientRestController.UserDetail;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
public class CityRestController {
	interface CityDetail extends City.Basic,City.Restaurants,Restaurant.Basic{}
	@Autowired
	private CityRepository cityRepository;
	@ResponseBody
	@JsonView(City.Basic.class)
	@RequestMapping(value = "/api/city/", method = RequestMethod.GET)
	public ResponseEntity<List<City>> getCities(){
	return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
	}
	
	@ResponseBody
	@JsonView(CityDetail.class)
	@RequestMapping(value = "/api/city/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> getCitie(@PathVariable String name){
	return new ResponseEntity<>(cityRepository.findByName(name).getCityResturants(), HttpStatus.OK);
	}
	
}


