package com.buisness.paymentdetail;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.buisness.paymentdetail.audit.AuditorAwareImpl;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class PaymentDetailApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentDetailApplication.class, args);
	}
	@PostConstruct
	public void init(){    
	    TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}


}
