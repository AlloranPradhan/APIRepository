package com.covitourism.trendCalc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="src_coviddata")
public class SrcCoviddata {
	/**
	 * Source Table- from excel directly it is persisted 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="state")
	private String state;
	
	@Column(name="conf_case")
	private long confCase;
	
	@Column(name="lastupdatedtime")
	private String lastUpdatedTime;
	
	@Column(name="load_dt")
	private String loadDt;
	
}
