package com.buisness.paymentdetail.service;

import java.util.Optional;

import com.buisness.paymentdetail.entity.User;

public interface UserService {
	public User saveUser(User user) ;
	public User findByUsername(String email);
	public void createPasswordResetTokenForUser(User user, String token);
	public Optional<User> findUserByResetToken(String resetToken);
	public void updateUser(User user);
}
