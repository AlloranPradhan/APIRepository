package com.covitourism.trendCalc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.covitourism.trendCalc.entity.CovidStatsId;
import com.covitourism.trendCalc.entity.StateCovidStats;
import com.covitourism.trendCalc.repository.TouristDao;
import com.covitourism.trendCalc.repository.TrendCalcRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TrendCalcServiceTests {

	@Mock
	private TrendCalcRepository trendcalcRepo;
	
	@Mock
	private TouristDao touristDao;
	
	@InjectMocks
	TrendCalcServiceImpl serv;
	
	@InjectMocks
	TouristsPlacesImpl touristServ;
	
	@Test
	public void findSafePlacesTest(){
//		trendRepo.findSafeStates(safe,total,state_unassigned);
		try {
			List<String> safeList = new ArrayList<>();
			safeList.add("Odisha");
			int safe = 400;
			String total= "Total";
			String stateUn = "State Unassigned";
			Mockito.when(trendcalcRepo.findSafeStates(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(safeList);
			assertEquals(safeList, serv.findSafePlaces(safe, total, stateUn), "Values are same");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	@Test
	public void getAllDataTest() {
		List<StateCovidStats> allData = new ArrayList<>();
		CovidStatsId a = new CovidStatsId(1,"2021-08-24");
		StateCovidStats s = new StateCovidStats(a, "Andaman and Nicobar Islands", 7659, 7, 6.50f, "2021-08-13 23:27:22");
		allData.add(s);
		Mockito.when(trendcalcRepo.findAll()).thenReturn(allData);
		assertEquals(allData, serv.getAllData(), "Values are same");
	}
	
	@Test
	public void findWarnPlacesTest(){
		List<String> warnplaces = new ArrayList<>();
		warnplaces.add("Karnataka");
		int safe = 400;
		int medium = 1200;
		String total= "Total";
		String stateUn = "State Unassigned";
		Mockito.when(trendcalcRepo.findWarnStates(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(warnplaces);
		assertEquals(warnplaces, serv.findWarnPlaces(safe, medium, total, stateUn));
	}
	
	@Test
	public void getportTest() {
		String port = "Running on port " + 0;
		assertEquals(port,serv.getport());
    }
	@Test
	public void loadDataTargetTest() {

		serv.loadDataTarget();
		verify(trendcalcRepo, times(1)).loadData();
	}
	@Test
	public void updateDataTest() {
//		trendRepo.updateNewCase(prev);
		doNothing().when(trendcalcRepo).updateNewCase(Mockito.anyInt());
		serv.updateData();
		verify(trendcalcRepo, times(1)).updateNewCase(Mockito.anyInt());
		
	}
	@Test
	public void updateTrendTest() {
//		trendRepo.updateTrend(avg_num);
		doNothing().when(trendcalcRepo).updateTrend(Mockito.anyInt());
		
		serv.updateTrend();
		verify(trendcalcRepo, times(1)).updateTrend(Mockito.anyInt());
    }
	@Test
	public void getTouristPlacesTest(){
		List<String> places = new ArrayList<>();
		String state = "Hyderabad";
		places.add("Charminar");
		Mockito.when(touristDao.findByLocation(Mockito.anyString())).thenReturn(places);
		assertEquals(places, touristServ.getTouristPlaces(state));
	}
	
}
