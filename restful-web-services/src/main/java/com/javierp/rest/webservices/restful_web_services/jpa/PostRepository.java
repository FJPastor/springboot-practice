package com.javierp.rest.webservices.restful_web_services.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javierp.rest.webservices.restful_web_services.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
//Integer se refiere al tipo de la PK
	
}
