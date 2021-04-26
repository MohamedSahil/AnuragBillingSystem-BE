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

import com.buisness.paymentdetail.dto.BillingRequestDto;
import com.buisness.paymentdetail.dto.BillingResponseDto;
import com.buisness.paymentdetail.entity.BillingDetail;
import com.buisness.paymentdetail.entity.Client;
import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.repositories.BillingDetailRepository;
import com.buisness.paymentdetail.repositories.ClientRepository;
import com.buisness.paymentdetail.repositories.PaymentDetailRepository;
import com.buisness.paymentdetail.repositories.RoleRepository;
import com.buisness.paymentdetail.repositories.UserRepository;

@Service
public class BillingDetailServiceImpl implements BillingDetailService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	BillingDetailRepository billingDetailRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	PaymentDetailRepository paymentDetailRepository;
	
	
	@Override
	public BillingDetail saveBill(BillingRequestDto billingRequestDto) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username="";
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		long id = 1;

		BillingDetail billingDetail = new BillingDetail();
		
//		//User will be taken from logged In user
		User u = userRepository.findByUsername(username);
		
		billingDetail.setUser(u);		
		
		//Fetching Client Id form db
		Optional<Client> opt = clientRepository.findById(billingRequestDto.getClientId());
		//System.out.println(opt);
		
		if(opt.isPresent()) {
			//Get these information from Input Json
			billingDetail.setClient(opt.get());
			billingDetail.setBillingDate(billingRequestDto.getBillingDate());
			//billingDetail.setPaymentDate(billingRequestDto.getPaymentDate());
			billingDetail.setBillingAmount(billingRequestDto.getBillingAmount());
			
			//Saving the Details
			billingDetail = billingDetailRepository.save(billingDetail);
		}
		return billingDetail;
		//return null;
	}
	@Override
	public void test(long id){
//		List<BillingDetail> billingDetails = billingDetailRepository.findByClientId(id);
//		Optional<User> u = userRepository.findById(Long.valueOf(5));
//		User user = u.get();
//		System.out.println(user.getId());
//		BillingDetail billingDetail = new BillingDetail();
//		billingDetail.setUser(user);
		//System.out.println(billingDetail);
	}
	

	@Override
	public List<BillingResponseDto> listBills(long id, int page) {
		
		int size=10;
		Pageable pagingRequest = PageRequest.of(page, size);
		
		List<BillingDetail> billingDetails = billingDetailRepository.findByClientId(id,pagingRequest);
		List<BillingResponseDto> responses = new ArrayList<>();
		for(BillingDetail billingDetail:billingDetails) {
			
			//Creating Dto
			BillingResponseDto dto = new BillingResponseDto();
			dto.setBillingId(billingDetail.getId());
			//dto.setClientName(billingDetail.getClient().getName());
			dto.setBillingDate(billingDetail.getBillingDate());
			//dto.setPaymentDate(billingDetail.getPaymentDate());
			dto.setBillingAmount(billingDetail.getBillingAmount());
			dto.setPaidAmount(billingDetail.getPaidAmount());
		
			//Adding dto to lists of responses
			responses.add(dto);
			
		}
		return responses;
	}

	@Override
	public BillingResponseDto updateBill(BillingRequestDto billingRequestDto) {
		BillingResponseDto billingResponseDto = new BillingResponseDto();
		Optional<BillingDetail> optBillDetail= billingDetailRepository.findById(billingRequestDto.getBillingId());
		if(optBillDetail.isPresent()) {
			BillingDetail billingDetail = optBillDetail.get();
			billingDetail.setBillingAmount(billingRequestDto.getBillingAmount());
			//billingDetail.setPaymentDate(billingRequestDto.getPaymentDate());
			billingDetail.setBillingDate(billingRequestDto.getBillingDate());
			billingDetailRepository.save(billingDetail);
			
			
			//Saving Detail to ResponseDto
			billingResponseDto.setBillingId(billingDetail.getId());
			billingResponseDto.setBillingAmount(billingDetail.getBillingAmount());
			//billingResponseDto.setClientName(billingDetail.getClient().getName());
			billingResponseDto.setPaidAmount(billingDetail.getPaidAmount());
			//billingResponseDto.setPaymentDate(billingDetail.getPaymentDate());
			billingResponseDto.setBillingDate(billingDetail.getBillingDate());
			
		}
		return billingResponseDto;
	}
	@Override
	public List<BillingResponseDto> searchBillingDetails(long cid,String query,int page) {
		int size=10;
		Pageable pagingRequest = PageRequest.of(page, size);
		
		List<BillingDetail> billingDetails = billingDetailRepository.findByIdAndBillingAmount(cid,query,pagingRequest);
		List<BillingResponseDto> responses = new ArrayList<>();
		for(BillingDetail billingDetail:billingDetails) {
			
			//Creating Dto
			BillingResponseDto dto = new BillingResponseDto();
			dto.setBillingId(billingDetail.getId());
			dto.setBillingDate(billingDetail.getBillingDate());
			dto.setBillingAmount(billingDetail.getBillingAmount());
			dto.setPaidAmount(billingDetail.getPaidAmount());
		
			//Adding dto to lists of responses
			responses.add(dto);
		}
		return responses;
	}
	@Override
	public void deleteBill(long id) {
		paymentDetailRepository.deleteByBillingDetailId(id);
		billingDetailRepository.deleteById(id);
		
	}
	@Override
	public ResponseEntity<?> calcTotalBills(long cid) {
		
		long count = billingDetailRepository.totalBills(cid);
		HashMap<String,Long> map = new HashMap<>();
		map.put("row",count);
		return ResponseEntity.ok(map);
		
	}
	@Override
	public ResponseEntity<?> totalSearchBillingDeatil(long cid, String query) {
		
		long count = billingDetailRepository.countByIdAndBillingAmount(cid,query);
		HashMap<String,Long> map = new HashMap<>();
		map.put("row",count);
		return ResponseEntity.ok(map);
	}
	
}
