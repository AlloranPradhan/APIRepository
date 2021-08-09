package com.covitourism.trendCalc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covitourism.trendCalc.entity.StateCovidStats;
import com.covitourism.trendCalc.service.TrendCalcService;


@RestController
@CrossOrigin("*")
public class CoviTourismController {
	
	@Autowired
	TrendCalcService trendCalcserv;
	
	@GetMapping(value="/getPort")
	public String getPort() {
		return trendCalcserv.getport();
	}
	@GetMapping(value="/getAllStats")
	public List<StateCovidStats> getAllStateCovidStats(){
		return trendCalcserv.getAllData();
	}
	
	@GetMapping(value="/getSafe")
	public List<String> getSafePlaces(){
		return trendCalcserv.findSafePlaces();
	}
	
	@GetMapping(value="/getWarn")
	public List<String> getWarnPlaces(){
		return trendCalcserv.findWarnPlaces();
	}
}
