package com.buisness.paymentdetail.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PaymentRequestDto {

	private long paymentDetailId;
	
	private long clientId;
		
	private long billingId;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "MM-dd-yyyy",timezone="IST")
	private Date paymentDate;

//	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//	@JsonFormat(pattern = "MM-dd-yyyy")
//	private Date billingDate;
//	
	private Double paidAmount;
	
	private String customBillingId;
	
	private String wayOfPayment;
	
	private String paymentInfo;
	
}
