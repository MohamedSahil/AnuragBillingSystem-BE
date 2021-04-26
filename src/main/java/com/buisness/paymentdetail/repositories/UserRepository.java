package com.buisness.paymentdetail.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buisness.paymentdetail.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	public User findByUsername(String username);
	public Optional<User> findByResetToken(String resetToken);

}
