package com.buisness.paymentdetail.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buisness.paymentdetail.dto.LoginDto;
import com.buisness.paymentdetail.dto.UserDto;
import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.service.CustomUserService;
import com.buisness.paymentdetail.service.UserService;
import com.buisness.paymentdetail.util.JwtTokenUtil;


@RestController
public class UserController {

	@Autowired
	UserService userService;
	

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test")
	public String testFunc() {
		return "TEsting Successful";
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User u = userService.saveUser(user);
		return ResponseEntity.ok(u);
	}
	@PostMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestParam("password") String password){
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String,Boolean> map = new HashMap<String,Boolean>();
		if(obj instanceof UserDetails) {
			String email = ((UserDetails) obj).getUsername();
			User user = userService.findByUsername(email);
			user.setPassword(bCryptPasswordEncoder.encode(password));
			userService.updateUser(user);
			map.put("success",true);
				
		}else {
			map.put("success", false);
		}
		return ResponseEntity.ok(map);
	}
	
	@PostMapping("/updateProfile")
	public ResponseEntity<?> updateProfile(@RequestBody UserDto u){
		//System.out.println(u);
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String,Boolean> map = new HashMap<String,Boolean>();
		if(obj instanceof UserDetails) {
			String email = ((UserDetails) obj).getUsername();
			User user = userService.findByUsername(email);
			user.setName(u.getName());
			user.setUsername(u.getUsername().trim());
			user.setPhoneNumber(u.getPhoneNumber());
			user.setAddress(u.getAddress());
			
			userService.updateUser(user);
			map.put("success",true);
			
			
		}else {
			map.put("success", false);
		}
		return ResponseEntity.ok(map);
	}
	@GetMapping("/fetchUserDetails")
	public ResponseEntity<?> fetchUserDetails(){
		UserDto dto = new UserDto() ;
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(obj instanceof UserDetails) {
			String email = ((UserDetails) obj).getUsername();
			User user = userService.findByUsername(email);
			dto.setName(user.getName());
			//dto.setUsername(user.getUsername());
			dto.setPhoneNumber(user.getPhoneNumber());
			dto.setAddress(user.getAddress());
		}
		return ResponseEntity.ok(dto);
	}
	
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomUserService userDetailsService;
		
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody LoginDto request) throws Exception {
		System.out.println(request.getUsername());
		System.out.println(request.getPassword());
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(request.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		HashMap<String,String> map = new HashMap<String,String>();
		map.put("token", token);
		return ResponseEntity.ok(map);
	}
		
}
