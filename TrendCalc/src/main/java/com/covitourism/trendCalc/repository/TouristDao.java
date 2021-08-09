package com.covitourism.trendCalc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.covitourism.trendCalc.entity.Tourists;

@Repository
public interface TouristDao extends JpaRepository<Tourists, Integer>{

	@Query(value="SELECT placestovisit FROM tourists T JOIN states S ON T.stateid=S.stateid WHERE state=:state", nativeQuery=true)
	List<String> findByLocation(String state);
}
