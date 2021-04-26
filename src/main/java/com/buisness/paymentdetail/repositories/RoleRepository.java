package com.buisness.paymentdetail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buisness.paymentdetail.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
