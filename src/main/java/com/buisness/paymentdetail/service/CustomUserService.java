package com.buisness.paymentdetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.repositories.UserRepository;



@Service
public class CustomUserService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User obj =  userRepository.findByUsername(username);
		if(obj!=null) {
		UserDetails userDetails = new CustomUserDetailsClass(obj);
		return userDetails;
		}
		return null;
	}

}
