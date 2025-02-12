package com.javierp.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class UserDaoService {
	//Usamos JPA/Hibernate para "Hablar" con la bbdd
	//Crearemos una static List, para tener el DAO hablando con el ArrayList estatico por ahora
	
	private static List<User> users = new ArrayList<>(); 
	//creamos unos usuarios
	
	//para añadir un id que se creer automaticamente de forma dinamica, creamos un contador
	
	private static int usersCount=0;
	
	static {
		users.add(new User(++usersCount,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount,"Eve",LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount,"Jim",LocalDate.now().minusYears(20)));

	}
	
	//3 metodos
	//public Lis<User> findAll()
	
	public List<User> findAll(){
		return users;
	}

	//public User findOne(int id)
	
	//El lo hizo asi, pero es raro de cojones
//	public User findOne(int id) {
//		Predicate<? super User> predicate = user -> user.getId().equals(id); 
//		return users.stream().filter(predicate).findFirst().get();
//	}
//	
	public User findOne(int id) {
	    for (User user : users) {
	        if (user.getId().equals(id)) {
	            return user;
	        }

	    }
	    return null; // Si no se encuentra el usuario
	}
	
	//public User save (User user)
	//POST para crear nuevo usuario
	public User save(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	public void deleteById(int id) {
	    boolean removed = users.removeIf(user -> user.getId().equals(id));
	    if (!removed) {
	        throw new UserNotFoundException("Usuario con ID " + id + " no encontrado.");
	    }
	}

	
}
