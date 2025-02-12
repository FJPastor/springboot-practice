package com.javierp.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {	

	//usamos el metodo del constructor inyection para hacer autowire
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service=service;
	}
	//GET/users y tenemos que hacer el mapping
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	//probamos con solo un usuario
	
	//http://localhost:8080/users
	//EntityModel y WevMvcLinkBuilder
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		User user = service.findOne(id);
		
		if(user==null) {
			throw new UserNotFoundException("id:"+id);
		}
		
		//creamos un entity model de user
		EntityModel<User> entityModel= EntityModel.of(user);
		//añadimos el link a recuperar todos los usuarios
		WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUsers());
		//definimos el link en el entity y le damos nombre
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		 //aqui en location deberia ir el /users/id
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		//esto obtiene la uri del recurso actual, añade el id y el id lo saca del savedUser
		// el created devuelve la URi, por lo que hay que especificarla en el mensaje
		//en este caso el nuevo seria /users/4, esto se llama location header
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		service.deleteById(id);
		

	}
	
}
