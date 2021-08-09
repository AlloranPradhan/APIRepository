package com.covitourism.trendCalc.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covitourism.trendCalc.entity.CovidStatsId;
import com.covitourism.trendCalc.entity.StateCovidStats;
@Repository
public interface TrendCalcRepository extends JpaRepository<StateCovidStats, CovidStatsId>{
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO statecovidstats (sid,state, confcase,lastupdatedtime,loaddate)\r\n" + 
			"(SELECT S.stateid,C.state, conf_case,lastupdatedtime,load_dt \r\n" + 
			"FROM src_coviddata C LEFT JOIN states S ON C.state=S.state WHERE load_dt = CURRENT_DATE)",nativeQuery=true)
	public void loadData();
	
	@Transactional
	@Modifying
	@Query(value="UPDATE statecovidstats,\r\n" + 
			"(SELECT sid,state,confcase,newcase1\r\n" + 
			" FROM (\r\n" + 
			"SELECT A.sid,A.state,A.confcase,\r\n" + 
			" COALESCE(A.confcase,0)-COALESCE(B.confcase,0) As newcase1\r\n" + 
			"FROM \r\n" + 
			"(SELECT * FROM statecovidstats WHERE loaddate=current_date)  A\r\n" + 
			"LEFT JOIN\r\n" + 
			"(SELECT * FROM statecovidstats WHERE loaddate=current_date - interval :prev day)  B\r\n" + 
			"ON A.sid=B.sid )C)E\r\n" + 
			"SET newcase=E.newcase1\r\n" + 
			"WHERE statecovidstats.sid=E.sid AND statecovidstats.loaddate =current_date",nativeQuery=true)
	public void updateNewCase(@Param ("prev") int prev);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE statecovidstats,\r\n" + 
			"(SELECT sid, state,AVG(newcase) OVER (PARTITION BY state) AS trend\r\n" + 
			"FROM statecovidstats WHERE loaddate>(current_date - interval 6 day)) AA\r\n" + 
			"SET statecovidstats.trend =AA.trend\r\n" + 
			"WHERE statecovidstats.sid=AA.sid AND statecovidstats.loaddate =current_date", nativeQuery=true)
	public void updateTrend(@Param ("number") int number);
	
	@Query(nativeQuery=true, value="select state from statecovidstats where trend < :safe AND state <> :total "
			+ "and state <> :stateUnassigned and loaddate = (SELECT MAX(loaddate) FROM statecovidstats)")
	public List<String> findSafeStates(@Param("safe") int safe, @Param("total") String total, @Param("stateUnassigned") String stateUnassigned);
	
	@Query(nativeQuery=true, value="select state from statecovidstats where trend >= :safe and trend < :medium "
			+ "AND state <> :total and state <> :stateUnassigned and loaddate = (SELECT MAX(loaddate) FROM statecovidstats)")
	public List<String> findWarnStates(@Param("safe") int safe, @Param("medium") int medium, @Param("total") String total, @Param("stateUnassigned") String stateUnassigned);
	
	@Query(value="select * from statecovidstats WHERE loaddate = (SELECT MAX(loaddate) FROM statecovidstats)",nativeQuery=true)
	public List<StateCovidStats> findAll();
}
