package com.covitourism.trendCalc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CovidStatsId implements Serializable{
	
	/**
	 * embedded
	 */
	private static final long serialVersionUID = 1L;

	private int sid;
	
	@Column(name="loaddate")
	private String loadDate;
	
	
}
