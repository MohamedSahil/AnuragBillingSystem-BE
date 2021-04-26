package com.buisness.paymentdetail.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.buisness.paymentdetail.audit.Auditable;

import lombok.Data;

@Data
@Entity
@Table(name="client")
@EntityListeners(AuditingEntityListener.class)
//public class Client extends Auditable<String>{
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column
    private String name;
    
    @Column(name="contact_no")
    private String contactNumber;
	
    @Column
    private String address;
    
    @Column(columnDefinition = "boolean default true")
    private boolean active;
    
    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    private List<BillingDetail> list = new ArrayList<>();

    
}
