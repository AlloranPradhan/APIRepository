package com.covitourism.trendCalc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.covitourism.trendCalc.entity.StateCovidStats;
import com.covitourism.trendCalc.repository.TrendCalcRepository;

@Service
public class TrendCalcServiceImpl implements TrendCalcService{
	
	@Autowired
	TrendCalcRepository trendRepo;
	
    @Value("${constants.safeBound.yml.int}")
    private int safe;
    
    @Value("${constants.mediumBound.yml.int}")
    private int medium;
    
    @Value("${constants.total.yml.string}")
    private String total;
    
    @Value("${constants.stateUnassigned.yml.string}")
    private String state_unassigned;
    
    @Value("${constants.avg_num.yml.int}")
    private int avg_num;
    
    @Value("${constants.prev.yml.int}")
    private int prev;
    
    @Value("${server.port}")
    private int port;
    
    public String getport() {
    	return "Running on port " + port;
    }

    /**
     * Loads data from source table to target table
     */
	public void loadDataTarget() {
		trendRepo.loadData();
	}
	
	/**
	 * Updates newCases in the target table
	 */
	public void updateData() {
		trendRepo.updateNewCase(prev);
	}
	
	/**
	 * updates trend based on the new cases 
	 */
	public void updateTrend() {
			trendRepo.updateTrend(avg_num);
	}
	
	/**
	 * Gets all Data from the table 
	 * @return List of StateCovidStats
	 */
	public List<StateCovidStats> getAllData() {
		return trendRepo.findAll();
	}
	
	/**
	 * Finds all Safe Places
	 * @return List of Safe places
	 */
	public List<String> findSafePlaces(){
		return trendRepo.findSafeStates(safe,total,state_unassigned);
	}
	
	/**
	 * Finds all Warned Places
	 * @return List of Warned places
	 */
	public List<String> findWarnPlaces(){
		return trendRepo.findWarnStates(safe,medium,total,state_unassigned);
	}
}
