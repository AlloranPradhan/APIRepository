package com.covitourism.trendCalc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.covitourism.trendCalc.service.TouristsPlaces;

@RestController
@CrossOrigin("*")
public class TouristsPlacesController {
	
	@Autowired
	TouristsPlaces touristServ;
	
	@GetMapping("/getTourist/{state}")
	public List<String> getTouristPlaces(@PathVariable String state){
		return touristServ.getTouristPlaces(state);
	}
}
