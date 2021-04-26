package com.buisness.paymentdetail.audit;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.repositories.UserRepository;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO Auto-generated method stub
		String name = "SYSTEM";
//		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
//			name = SecurityContextHolder.getContext().getAuthentication().getName();
//		}
//		return Optional.ofNullable(name);
		Optional<User> opt = userRepository.findById(Long.valueOf(1));
		if(opt.isPresent()) {
			User u = opt.get();
			name= u.getUsername();
		}
		return Optional.ofNullable(name);
	}

}
