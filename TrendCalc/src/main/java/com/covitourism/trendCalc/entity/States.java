package com.covitourism.trendCalc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="states")
public class States {
	@Id
	@Column(name= "stateid")
	private int stateId;
	
	@Column(name="state")
	private String state;
	
}
