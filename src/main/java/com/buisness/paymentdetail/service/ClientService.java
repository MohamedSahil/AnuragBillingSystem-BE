package com.buisness.paymentdetail.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.buisness.paymentdetail.dto.ClientResponseDto;

public interface ClientService {

	public List<ClientResponseDto> listClients(int page);
	public void deleteClient(long id);
	public ClientResponseDto getClient(long id);
	public List<ClientResponseDto> searchClients(String param,int page);
	public ResponseEntity<?> totalSearchedClients(String param);
	public ResponseEntity<?> calcTotalBills();
}
