package com.covitourism.trendCalc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="statecovidstats")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StateCovidStats implements Serializable{
	
	/**
	 * from source table (SrcCoviddata) to this with few other columns 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CovidStatsId covidStatsId;
		
	@Column(name="state")
	private String state;
	
	@Column(name="confcase")
	private long confCase;
	
	@Column(name="newcase")
	private int newCase;

	@Column(name="trend")
	private float trend;
	
	@Column(name="lastupdatedtime")
	private String lastUpdatedTime;
	
	
}
