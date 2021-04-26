package com.buisness.paymentdetail.service;

import org.springframework.http.ResponseEntity;

import com.buisness.paymentdetail.dto.PaymentRequestDto;
import com.buisness.paymentdetail.entity.PaymentDetail;

public interface PaymentDetailService {

	public ResponseEntity<?> savePaymentDetail(PaymentRequestDto paymentRequestDto);
	public ResponseEntity<?> listPaymentDetails(long billingId,long clientId,int page);
	public ResponseEntity<?> updatePaymentDetail(PaymentRequestDto paymentRequestDto);
	public void deletePaymentDetail(long id);

	public ResponseEntity<?> searchPaymentDetails(String query,long billingId,int page);
	public ResponseEntity<?> calcTotalPayment(long billingId);
	public ResponseEntity<?> calcTotalSearchPayment(String query,long billingId);
	
}
