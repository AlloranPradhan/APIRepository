package com.covitourism.trendCalc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covitourism.trendCalc.repository.TouristDao;

@Service
public class TouristsPlacesImpl implements TouristsPlaces {
	
	@Autowired
	TouristDao touristDao;
	
	/**
	 * Gets tourist places based on state
	 * @param String state
	 * @return List of places
	 */
	public List<String> getTouristPlaces(String state){
		return touristDao.findByLocation(state);
	}
}
