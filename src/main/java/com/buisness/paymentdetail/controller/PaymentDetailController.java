package com.buisness.paymentdetail.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buisness.paymentdetail.dto.BillingRequestDto;
import com.buisness.paymentdetail.dto.BillingResponseDto;
import com.buisness.paymentdetail.dto.PaymentRequestDto;
import com.buisness.paymentdetail.dto.PaymentResponseDto;
import com.buisness.paymentdetail.entity.BillingDetail;
import com.buisness.paymentdetail.entity.PaymentDetail;
import com.buisness.paymentdetail.service.BillingDetailServiceImpl;
import com.buisness.paymentdetail.service.PaymentDetailService;

@RestController
public class PaymentDetailController {

	@Autowired
	PaymentDetailService paymentDetailService; 
	
	@GetMapping("/totalPayments/{billingId}")
	public ResponseEntity<?> calcTotalPayment(@PathVariable long billingId){
		return paymentDetailService.calcTotalPayment(billingId);
	}
	
	
	@PostMapping("/addPaymentDetail")
	public ResponseEntity<?> addPaymentDetail(@RequestBody PaymentRequestDto paymentRequestDto) {
		
		return paymentDetailService.savePaymentDetail(paymentRequestDto);
		
	}
	@GetMapping("/listPaymentDetails/{clientId}/{billingId}")
	public ResponseEntity<?> listPaymentDetails(@PathVariable long clientId, @PathVariable long billingId,@RequestParam("page") int page ){
		
		return paymentDetailService.listPaymentDetails(billingId, clientId,page);
		//return null;
	}
	@PutMapping("/updatePaymentDetail")
	public ResponseEntity<?> updatePaymentDetail(@RequestBody PaymentRequestDto paymentRequestDto) {
		return paymentDetailService.updatePaymentDetail(paymentRequestDto);
	}
	
	@GetMapping("/searchPayments/{bid}")
	public ResponseEntity<?> searchPayments(@PathVariable long bid, @RequestParam("query") String query,@RequestParam("page")int page){
		return paymentDetailService.searchPaymentDetails(query,bid,page);
	}
	@GetMapping("/totalSearchPayments/{bid}")
	public ResponseEntity<?> searchPayments(@PathVariable long bid, @RequestParam("query") String query){
		return paymentDetailService.calcTotalSearchPayment(query,bid);
	}
	
	@DeleteMapping("/deletePayment/{id}")
	public ResponseEntity<?> deletePaymentDetail(@PathVariable long id) {
		
		try {
			paymentDetailService.deletePaymentDetail(id);
			HashMap<String,Boolean> response = new HashMap<String,Boolean>();
			response.put("Success", true);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			
			HashMap<String,Boolean> response = new HashMap<String,Boolean>();
			response.put("Success", false);
			return ResponseEntity.ok(response);
		}
		
	}
	
}
