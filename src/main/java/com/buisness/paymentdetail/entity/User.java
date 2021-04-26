package com.buisness.paymentdetail.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.buisness.paymentdetail.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
//public class User extends Auditable<String> {

public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", unique=true)
	private String username;
	
	@Column
	private String password;
	
	@Column(name="phone_no")
	private String phoneNumber;
	
	@Column
	private String name;
	
	@Column
	private String address;
	
	@Column(name="is_enabled", columnDefinition = "boolean default true", nullable = false)
	private Boolean isEnabled=true;
	
	@Column(name = "reset_token")
	private String resetToken;
	 
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( joinColumns = { @JoinColumn(name="user_id",referencedColumnName = "id")},
				inverseJoinColumns = {@JoinColumn(name="role_id",referencedColumnName = "id")}
			)
	private List<Role> roles = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<BillingDetail> billingDetails = new ArrayList();
	
}
