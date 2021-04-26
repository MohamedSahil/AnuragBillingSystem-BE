package com.buisness.paymentdetail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.buisness.paymentdetail.dto.ClientResponseDto;
import com.buisness.paymentdetail.entity.Client;
import com.buisness.paymentdetail.repositories.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService{

	
	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public List<ClientResponseDto> listClients(int page) {
		int size=10;
		Pageable pagingRequest = PageRequest.of(page, size);

		System.out.println(pagingRequest);
		Page<Client> clients = clientRepository.findClient(pagingRequest);
		List<ClientResponseDto> list = new ArrayList<ClientResponseDto>();
		for(Client c : clients) {
			if(c.isActive()) {
				ClientResponseDto dto = new ClientResponseDto();
				dto.setId(c.getId());
				dto.setName(c.getName());
				dto.setContactNumber(c.getContactNumber());
				dto.setAddress(c.getAddress());
				list.add(dto);
			}
		}
		return list;
	}

	@Override
	public void deleteClient(long id) {
		Optional<Client> obs = clientRepository.findById(id);
		ClientResponseDto dto = null;
		if(obs.isPresent()) {
			Client client = obs.get();
			client.setActive(false);
			clientRepository.saveAndFlush(client);
			
		}
//		clientRepository.deleteById(id);
	}

	@Override
	public ClientResponseDto getClient(long id) {
		Optional<Client> obs = clientRepository.findById(id);
		ClientResponseDto dto = null;
		if(obs.isPresent()) {
			Client client = obs.get();
			if(client.isActive()) {
				dto = new ClientResponseDto();
				dto.setId(client.getId());
				dto.setName(client.getName());
				dto.setAddress(client.getAddress());
				dto.setContactNumber(client.getContactNumber());
			}
		}
		return dto;
	}

	@Override
	public List<ClientResponseDto> searchClients(String param,int page) {

		int size=10;
		Pageable pagingRequest = PageRequest.of(page, size);
		
		List<ClientResponseDto> list = new ArrayList<ClientResponseDto>();

		List<Client> clients = clientRepository.findByNameAndId(param,pagingRequest);
		for(Client c : clients) {
			if(c.isActive()) {
				ClientResponseDto dto = new ClientResponseDto();
				dto.setId(c.getId());
				dto.setName(c.getName());
				dto.setContactNumber(c.getContactNumber());
				dto.setAddress(c.getAddress());
				list.add(dto);
			}
		}
		return list;
	}
	@Override
	public ResponseEntity<?> calcTotalBills() {
		long count = clientRepository.countClients();
		HashMap<String,Long> map = new HashMap<>();
		map.put("row",count);
		return ResponseEntity.ok(map);
	}

	@Override
	public ResponseEntity<?> totalSearchedClients(String param) {
		long count = clientRepository.countByNameAndId(param);
		HashMap<String,Long> map = new HashMap<>();
		map.put("row",count);
		return ResponseEntity.ok(map);
	}

}
