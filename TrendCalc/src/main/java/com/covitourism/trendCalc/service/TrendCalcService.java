package com.covitourism.trendCalc.service;

import java.util.List;

import com.covitourism.trendCalc.entity.StateCovidStats;

public interface TrendCalcService {
	
	public List<StateCovidStats> getAllData();
//	public void alterSeq();
	public void loadDataTarget();
	public void updateData();
	public List<String> findSafePlaces();
	
	public List<String> findWarnPlaces();
	
	public void updateTrend();
	public String getport();
}
