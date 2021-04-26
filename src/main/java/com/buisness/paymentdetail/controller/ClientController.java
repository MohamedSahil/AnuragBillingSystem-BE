package com.buisness.paymentdetail.controller;

import java.util.HashMap;
import java.util.List;

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

import com.buisness.paymentdetail.dto.ClientResponseDto;
import com.buisness.paymentdetail.entity.Client;
import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.repositories.ClientRepository;
import com.buisness.paymentdetail.repositories.UserRepository;
import com.buisness.paymentdetail.service.ClientService;

@RestController
public class ClientController {

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClientService clientService;
	
	@PostMapping("/saveClient")
	public ResponseEntity<?> saveClient(@RequestBody Client clientEntity) {
		
		clientEntity.setId(Long.valueOf(0));
		clientEntity.setActive(true);
		System.out.println(clientEntity);
		//User u = userRepository.findById(Long.valueOf(1)).get();
		//clientEntity.setCreatedBy("System");
		clientEntity = clientRepository.save(clientEntity);
		return ResponseEntity.ok(clientEntity);
	}
	@PutMapping("/updateClient")
	public ResponseEntity<?> updateClient(@RequestBody Client clientEntity){
		//User u = userRepository.findById(Long.valueOf(1)).get();
		//System.out.println(u.getPhoneNumber());

		Client client = clientRepository.findById(clientEntity.getId()).get();
//		client.setUpdatedBy(u.getUsername());
		client.setName(clientEntity.getName());
		client.setAddress(clientEntity.getAddress());
		client.setContactNumber(clientEntity.getContactNumber());
		clientRepository.saveAndFlush(client);
		HashMap<String,Boolean> response = new HashMap<String,Boolean>();
		response.put("Success", true);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/totalClients")
	public ResponseEntity<?> calcTotalClients(){
		return clientService.calcTotalBills();
	}
	
	@GetMapping("/listClients")
	public List<ClientResponseDto> listClient(@RequestParam("page")int page) {
		return clientService.listClients(page);
	}
	@DeleteMapping("/deleteClient/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable long id){
		clientService.deleteClient(id);
		HashMap<String,Boolean> response = new HashMap<String,Boolean>();
		response.put("Success", true);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/getClient/{id}")
	public ClientResponseDto getClient(@PathVariable int id) {
		return clientService.getClient(id);
	}
	
	@GetMapping("/searchClient")
	public List<ClientResponseDto> searchClient(@RequestParam("q") String q,@RequestParam("page")int page){
		List<ClientResponseDto> lists = clientService.searchClients(q,page);
		return lists;
	}
	@GetMapping("/totalSearchClients")
	public ResponseEntity<?> countSearchedClient(@RequestParam String q){
		return clientService.totalSearchedClients(q);
	}
	
}
