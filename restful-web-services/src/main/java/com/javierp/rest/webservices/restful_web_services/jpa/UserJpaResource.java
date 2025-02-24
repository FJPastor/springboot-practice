package com.javierp.rest.webservices.restful_web_services.jpa;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.javierp.rest.webservices.restful_web_services.user.Post;
import com.javierp.rest.webservices.restful_web_services.user.User;
import com.javierp.rest.webservices.restful_web_services.user.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {	

	//usamos el metodo del constructor inyection para hacer autowire
	//usamos el userrepository
	private UserRepository userRepository;

	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository userRepository,PostRepository postRepository) {
		this.userRepository=userRepository;
		this.postRepository=postRepository;
	}
	//GET/users y tenemos que hacer el mapping
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	//probamos con solo un usuario
	
	//http://localhost:8080/users
	//EntityModel y WevMvcLinkBuilder
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id);
		}
		
		//creamos un entity model de user
		EntityModel<User> entityModel= EntityModel.of(user.get());
		//añadimos el link a recuperar todos los usuarios
		WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUsers());
		//definimos el link en el entity y le damos nombre
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		 //aqui en location deberia ir el /users/id
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		//esto obtiene la uri del recurso actual, añade el id y el id lo saca del savedUser
		// el created devuelve la URi, por lo que hay que especificarla en el mensaje
		//en este caso el nuevo seria /users/4, esto se llama location header
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	//queremos obtener el post para un usuario
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id);
		}
		return user.get().getPosts();
	}
	
	//crear post
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostsForUser(@PathVariable int id,@Valid @RequestBody Post post){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id);
		}
		post.setUser(user.get());
		//creamos post
		Post savedPost=postRepository.save(post);
		//guardamos la uri del post
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
}
