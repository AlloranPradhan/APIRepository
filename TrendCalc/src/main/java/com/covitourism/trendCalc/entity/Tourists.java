package com.covitourism.trendCalc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tourists")
public class Tourists {
	
	@Id
	@Column(name="touristid")
	private int touristId;
	
	@Column(name="placestovisit")
	private String placesToVisit;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="stateid")
	private States stateId;
	
}
