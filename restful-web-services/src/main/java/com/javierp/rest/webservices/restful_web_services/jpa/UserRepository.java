package com.javierp.rest.webservices.restful_web_services.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javierp.rest.webservices.restful_web_services.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{
//Integer se refiere al tipo de la PK
	
}
