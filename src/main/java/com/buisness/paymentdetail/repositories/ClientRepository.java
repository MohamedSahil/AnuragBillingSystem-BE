package com.buisness.paymentdetail.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.buisness.paymentdetail.entity.Client;

@Repository
@CrossOrigin(value = "http://localhost:4200")
public interface ClientRepository extends JpaRepository<Client, Long>{
	
	@Query(value = "SELECT * FROM client u WHERE (u.name like CONCAT('%', :query, '%') or u.id = :query or u.address like CONCAT('%', :query, '%')) and u.active=true", nativeQuery = true)
	List<Client> findByNameAndId(String query,Pageable pageable);

	@Query(value="select count(*) from client u where u.active=true", nativeQuery = true)
	long countClients();
	
	@Query(value="select * from client u where u.active=true", nativeQuery = true)
	Page<Client> findClient(Pageable pageable);
	
	@Query(value = "SELECT count(*) FROM client u WHERE (u.name like CONCAT('%', :query, '%') or u.id = :query or u.address like CONCAT('%', :query, '%')) and u.active=true ", nativeQuery = true)
	long countByNameAndId(String query);

}
