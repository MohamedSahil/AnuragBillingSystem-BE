package com.buisness.paymentdetail.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class BillingResponseDto {
	

	private long billingId;
	
	//private String clientName;
	
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//	@JsonFormat(pattern = "MM-dd-yyyy",timezone="IST")
//	private Date paymentDate;

	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "MM-dd-yyyy",timezone="IST")
	private Date billingDate;
	
	private Double billingAmount;
	
	private Double paidAmount;
	

}
