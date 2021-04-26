package com.buisness.paymentdetail.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.buisness.paymentdetail.dto.BillingRequestDto;
import com.buisness.paymentdetail.dto.BillingResponseDto;
import com.buisness.paymentdetail.entity.BillingDetail;

public interface BillingDetailService {

	public BillingDetail saveBill(BillingRequestDto billingRequestDto);
	public List<BillingResponseDto> listBills(long id,int page);
	public BillingResponseDto updateBill(BillingRequestDto billingRequestDto);
	public void test(long id);
	public List<BillingResponseDto> searchBillingDetails(long cid,String query,int page);
	public ResponseEntity<?> totalSearchBillingDeatil(long cid,String query);
	public void deleteBill(long id);
	public ResponseEntity<?> calcTotalBills(long cid); 
}
