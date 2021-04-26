package com.buisness.paymentdetail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.buisness.paymentdetail.dto.BillingResponseDto;
import com.buisness.paymentdetail.dto.PaymentRequestDto;
import com.buisness.paymentdetail.dto.PaymentResponseDto;
import com.buisness.paymentdetail.entity.BillingDetail;
import com.buisness.paymentdetail.entity.Client;
import com.buisness.paymentdetail.entity.PaymentDetail;
import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.repositories.BillingDetailRepository;
import com.buisness.paymentdetail.repositories.ClientRepository;
import com.buisness.paymentdetail.repositories.PaymentDetailRepository;
import com.buisness.paymentdetail.repositories.UserRepository;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService{

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentDetailRepository paymentDetailRepository;
	
	@Autowired
	BillingDetailRepository billingDetailRepository;
	
	@Autowired
	ClientRepository clientRepository;

	
	@Override
	public ResponseEntity<?> savePaymentDetail(PaymentRequestDto paymentRequestDto) {
		
		PaymentDetail paymentDetail = new PaymentDetail();	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username="";
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		


		//User will be taken from logged In user
		User u = userRepository.findByUsername(username);
		paymentDetail.setUser(u);
		
		//Fetching Client Id form db
		Optional<Client> optClient = clientRepository.findById(paymentRequestDto.getClientId());
		System.out.println(paymentRequestDto);
		if(optClient.isPresent()) {
			
			Optional<BillingDetail> optBillingDetail = billingDetailRepository.findById(paymentRequestDto.getBillingId());
			
			if(optBillingDetail.isPresent()) {

				
				//Get these information from Input Json
				paymentDetail.setClient(optClient.get());
				paymentDetail.setBillingDetail(optBillingDetail.get());
				paymentDetail.setPaymentDate(paymentRequestDto.getPaymentDate());
				paymentDetail.setPaidAmount(paymentRequestDto.getPaidAmount());
				paymentDetail.setCustomBillingId(paymentRequestDto.getCustomBillingId());
				paymentDetail.setWayOfPayment(paymentRequestDto.getWayOfPayment());
				paymentDetail.setPaymentInfo(paymentRequestDto.getPaymentInfo());
				
				paymentDetail = paymentDetailRepository.save(paymentDetail);
				paymentRequestDto.setPaymentDetailId(paymentDetail.getId());
				//Updating billing table paid amount
				BillingDetail billingDetail = optBillingDetail.get();
				double paidAmount = billingDetail.getPaidAmount();
				paidAmount+=paymentRequestDto.getPaidAmount();
				billingDetail.setPaidAmount(paidAmount);
				
				billingDetailRepository.save(billingDetail);
			}
		}
		return ResponseEntity.ok(paymentRequestDto);
	}


	@Override
	public ResponseEntity<?> listPaymentDetails(long billingId, long clientId,int pageNo) {

		List<PaymentResponseDto> response = new ArrayList<>();
		
		int size=10;
		Pageable pagingRequest = PageRequest.of(pageNo, size);
		
		List<PaymentDetail> lists = paymentDetailRepository.findByBillingDetailIdAndClientId(billingId,clientId,pagingRequest);
		for(PaymentDetail list:lists) {
			PaymentResponseDto dto = new PaymentResponseDto();
			
			dto.setPaymentDetailId(list.getId());
			dto.setClientId(clientId);
			dto.setPaymentDate(list.getPaymentDate());
			dto.setCustomBillingId(list.getCustomBillingId());
			dto.setPaidAmount(list.getPaidAmount());
			dto.setPaymentInfo(list.getPaymentInfo());
			dto.setWayOfPayment(list.getWayOfPayment());
		
			response.add(dto);
		}
		
		//System.out.println(lists);
		return ResponseEntity.ok(response);
	}


	@Override
	public ResponseEntity<?> updatePaymentDetail(PaymentRequestDto paymentRequestDto) {
		PaymentDetail paymentDetail=null;
		Optional<PaymentDetail> opt = paymentDetailRepository.findById(paymentRequestDto.getPaymentDetailId());
		if(opt.isPresent()) {
			
			paymentDetail = opt.get();
			
			//Removing the payment amount from billing page whatever previously was there and will add the new payment amount.
			BillingDetail billingDetail = paymentDetail.getBillingDetail();
			double paidAmount = billingDetail.getPaidAmount();
			paidAmount=paidAmount-paymentDetail.getPaidAmount();
			paidAmount+=paymentRequestDto.getPaidAmount();
			billingDetail.setPaidAmount(paidAmount);
			
			
			
			paymentDetail.setCustomBillingId(paymentRequestDto.getCustomBillingId());
			paymentDetail.setPaymentDate(paymentRequestDto.getPaymentDate());
			paymentDetail.setPaidAmount(paymentRequestDto.getPaidAmount());
			paymentDetail.setPaymentInfo(paymentRequestDto.getPaymentInfo());
			paymentDetail.setWayOfPayment(paymentRequestDto.getWayOfPayment());
		
			paymentDetail = paymentDetailRepository.save(paymentDetail);
				
			
			
			
			billingDetailRepository.save(billingDetail);
		}
		return ResponseEntity.ok(paymentRequestDto);
	}


	@Override
	public void  deletePaymentDetail(long id) {
		System.out.println("Calling");
		PaymentDetail paymentDetail=null;
		Optional<PaymentDetail> opt = paymentDetailRepository.findById(id);
		if(opt.isPresent()) {
			paymentDetail=opt.get();
			BillingDetail billingDetail = paymentDetail.getBillingDetail();
			double paidAmount = billingDetail.getPaidAmount();
			paidAmount=paidAmount - paymentDetail.getPaidAmount();
			billingDetail.setPaidAmount(paidAmount);
			billingDetailRepository.save(billingDetail);
			paymentDetailRepository.deleteById(opt.get().getId());
			
			
		}
		
	}


	@Override
	public ResponseEntity<?> searchPaymentDetails(String query,long bid,int page) {
		
		int size=10;
		Pageable pagingRequest = PageRequest.of(page, size);
		
		List<PaymentResponseDto> response = new ArrayList<>();
		
		List<PaymentDetail> lists = paymentDetailRepository.findByAttributes(query,bid,pagingRequest);
		for(PaymentDetail list:lists) {
			PaymentResponseDto dto = new PaymentResponseDto();
			
			dto.setPaymentDetailId(list.getId());
			dto.setClientId(Long.valueOf(list.getClient().getId()));
			dto.setPaymentDate(list.getPaymentDate());
			dto.setCustomBillingId(list.getCustomBillingId());
			dto.setPaidAmount(list.getPaidAmount());
			dto.setPaymentInfo(list.getPaymentInfo());
			dto.setWayOfPayment(list.getWayOfPayment());
		
			response.add(dto);
		}
		
		//System.out.println(lists);
		return ResponseEntity.ok(response);
	}


	@Override
	public ResponseEntity<?> calcTotalPayment(long billingId) {
		long count = paymentDetailRepository.calcTotalPayments(billingId);
		HashMap<String,Long> map = new HashMap<>();
		map.put("row",count);
		return ResponseEntity.ok(map);
	}


	@Override
	public ResponseEntity<?> calcTotalSearchPayment(String query, long billingId) {
		long count = paymentDetailRepository.countBySearchAttributes(query, billingId);
		HashMap<String,Long> map = new HashMap<>();
		map.put("row",count);
		return ResponseEntity.ok(map);
	}

	
}
