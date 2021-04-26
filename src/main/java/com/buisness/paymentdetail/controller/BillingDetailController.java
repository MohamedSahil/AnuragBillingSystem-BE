package com.buisness.paymentdetail.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buisness.paymentdetail.dto.BillingRequestDto;
import com.buisness.paymentdetail.dto.BillingResponseDto;
import com.buisness.paymentdetail.entity.BillingDetail;
import com.buisness.paymentdetail.service.BillingDetailService;
import com.buisness.paymentdetail.service.BillingDetailServiceImpl;

@RestController
public class BillingDetailController {

	@Autowired
	BillingDetailService billingDetailService; 
	
	@PostMapping("/updateBillingDetail")
	public ResponseEntity<?> updateBillingDetail(@RequestBody BillingRequestDto billingRequestDto){
		
		BillingResponseDto billingResponseDto = billingDetailService.updateBill(billingRequestDto);
		return ResponseEntity.ok(billingResponseDto);
	}
	
	@PostMapping("/addBillingDetail")
	public ResponseEntity<?> addBillingDetails(@RequestBody BillingRequestDto billingRequestDto) {
				
		BillingDetail billingDetail = billingDetailService.saveBill(billingRequestDto);

		if(billingDetail!=null) {
			BillingResponseDto dto = new BillingResponseDto();
		
			dto.setBillingId(billingDetail.getId());
			dto.setBillingDate(billingDetail.getBillingDate());
			dto.setBillingAmount(billingDetail.getBillingAmount());
			dto.setPaidAmount(billingDetail.getPaidAmount());
			return ResponseEntity.ok(dto);
		}else {
			HashMap<String,Boolean> response = new HashMap<String,Boolean>();
			response.put("Success", false);
			return ResponseEntity.ok(response);
			
		}
	}
	@GetMapping("/listBillingDetail/{id}")
	public ResponseEntity<?> listBillingDetails(@PathVariable int id,@RequestParam("page")int page){
		
		List<BillingResponseDto> lists = billingDetailService.listBills(id,page);
		return ResponseEntity.ok(lists);
	}
	@GetMapping("/searchBills/{cid}")
	public ResponseEntity<?> searchBills(@PathVariable long cid,@RequestParam("query") String query,@RequestParam("page") int page){
		
		List<BillingResponseDto> lists = billingDetailService.searchBillingDetails(cid,query,page);
		return ResponseEntity.ok(lists);
	}
	@DeleteMapping("/deleteBill/{id}")
	public ResponseEntity<?> searchBills(@PathVariable long id){
		try {
			billingDetailService.deleteBill(id);
			HashMap<String,Boolean> response = new HashMap<String,Boolean>();
			response.put("Success", true);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			HashMap<String,Boolean> response = new HashMap<String,Boolean>();
			response.put("Success", false);
			return ResponseEntity.ok(response);
		}
		
	}
	@GetMapping("/totalBills/{id}")
	public ResponseEntity<?> totalBills(@PathVariable long id){
		return billingDetailService.calcTotalBills(id);
	}
	@GetMapping("/totalSearchBills/{cid}")
	public ResponseEntity<?> totalSearchBills(@PathVariable long cid,@RequestParam String query){
		return billingDetailService.totalSearchBillingDeatil(cid, query);
	}
	
}
