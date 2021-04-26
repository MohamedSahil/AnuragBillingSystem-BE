package com.buisness.paymentdetail.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buisness.paymentdetail.entity.PasswordResetToken;
import com.buisness.paymentdetail.entity.Role;
import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.repositories.PasswordResetTokenRepository;
import com.buisness.paymentdetail.repositories.RoleRepository;
import com.buisness.paymentdetail.repositories.UserRepository;

@Service 
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public User saveUser(User user) {
		user.setId(Long.valueOf(0));
		ArrayList<Role> roles = new ArrayList<Role>();
		
		Role r = roleRepository.findById(Long.valueOf(1)).get();
		roles.add(r);
		user.setRoles(roles);
		user.setIsEnabled(true);
		//User createdByUser = userRepository.findById(Long.valueOf(1)).get();
		//user.setCreatedBy("system");
		User u=userRepository.save(user);
		return u;
	}


	@Override
	public User findByUsername(String email) {
		return userRepository.findByUsername(email);
	}
	
	@Override
	//@Transaction
	public void createPasswordResetTokenForUser(User user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken();
	    myToken.setToken(token);
	    myToken.setUser(user);
	    passwordResetTokenRepository.save(myToken);
	}


	@Override
	public Optional<User> findUserByResetToken(String resetToken) {
		Optional<User> u = userRepository.findByResetToken(resetToken);
		return u;
	}


	@Override
	public void updateUser(User user) {
		userRepository.save(user);
		
	}
}
