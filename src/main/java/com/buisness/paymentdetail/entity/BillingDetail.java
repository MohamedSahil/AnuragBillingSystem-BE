package com.buisness.paymentdetail.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.buisness.paymentdetail.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="billing_detail")
public class BillingDetail  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName = "id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="client_id",referencedColumnName = "id")
	private Client client;
	
	
	@Column(name="billing_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "MM-dd-yyyy",timezone="IST")
	private Date billingDate;
	
	@Column(name="billing_amount",columnDefinition="Decimal(10,2) default '0.00'")
	
	private double billingAmount;
	
	@Column(name="paid_amount",columnDefinition="Decimal(10,2) default '0.00'")
	private double paidAmount;
	
	
	
	
}

