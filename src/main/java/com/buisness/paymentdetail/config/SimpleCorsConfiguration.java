package com.buisness.paymentdetail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableWebMvc
@Configuration
public class SimpleCorsConfiguration {

	/*
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub
		super.configureRepositoryRestConfiguration(config);
		 config.getCorsRegistry().addMapping("/**")
		 .allowedMethods("*")
         .allowedOrigins("*")
         ;
	}
	*/
	
	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
		 return new WebMvcConfigurerAdapter() {
			 @Override
			 public void addCorsMappings(CorsRegistry registry) {
				 registry.addMapping("/**")
				 .allowedMethods("*")
				 .allowedOrigins("*")
				 ;
			 }
		 };
	 }
	
}
