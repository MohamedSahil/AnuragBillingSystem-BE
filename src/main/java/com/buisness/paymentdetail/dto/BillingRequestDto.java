package com.buisness.paymentdetail.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BillingRequestDto {

	private long billingId;
	
	private long clientId;
	
//	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//	@JsonFormat(pattern = "MM-dd-yyyy",timezone="IST")
//	private Date paymentDate;

	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "MM-dd-yyyy",timezone="IST")
	private Date billingDate;
	
	private Double billingAmount;
	
}
