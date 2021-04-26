package com.buisness.paymentdetail.repositories;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.buisness.paymentdetail.entity.Client;
import com.buisness.paymentdetail.entity.PaymentDetail;
import org.springframework.data.domain.Pageable;

@Repository
@CrossOrigin(value = "http://localhost:4200")
@Transactional
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long>{
	
	List<PaymentDetail> findByBillingDetailIdAndClientId(long billingId, long clientId,Pageable pageable);
	
	@Query(value = "SELECT * FROM payment_detail u WHERE  (u.id = :query or u.custom_billing_id = :query or u.way_of_payment = :query or u.paid_amount = :query) and u.billing_id =:bid", nativeQuery = true)
	List<PaymentDetail> findByAttributes(String query,long bid,Pageable pageables);

	@Query(value = "SELECT count(*) FROM payment_detail u WHERE  (u.id = :query or u.custom_billing_id = :query or u.way_of_payment = :query or u.paid_amount = :query) and u.billing_id =:bid", nativeQuery = true)
	long countBySearchAttributes(String query,long bid);
	
	@Modifying
	@Query(value="delete FROM payment_detail WHERE  billing_id = :billingId", nativeQuery = true)	
	void deleteByBillingDetailId(long billingId);
	
	@Query(value="select count(*) from payment_detail where billing_id =:billingId",nativeQuery=true)
	long calcTotalPayments(long billingId);

}
