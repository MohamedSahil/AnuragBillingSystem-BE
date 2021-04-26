package com.buisness.paymentdetail.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.buisness.paymentdetail.entity.BillingDetail;
import com.buisness.paymentdetail.entity.Client;

@Repository
@CrossOrigin(value = "http://localhost:4200")
public interface BillingDetailRepository extends JpaRepository<BillingDetail, Long>{
	List<BillingDetail> findByClientId(long id,Pageable pageable);
	
	@Query(value = "SELECT * FROM billing_detail u WHERE (u.id = :query or u.billing_amount  = :query) and u.client_id=:cid", nativeQuery = true)
	List<BillingDetail> findByIdAndBillingAmount(long cid,String query,Pageable pageable);
	
	@Query(value = "SELECT count(*) FROM billing_detail u WHERE (u.id = :query or u.billing_amount  = :query) and u.client_id=:cid", nativeQuery = true)
	long countByIdAndBillingAmount(long cid,String query);
	
	@Query(value="select count(*) from billing_detail u WHERE u.client_id =:cid", nativeQuery = true)
	long totalBills(long cid);
	
}
