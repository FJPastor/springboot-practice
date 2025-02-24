package com.javierp.rest.webservices.restful_web_services.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//1, todas las request tienen q estar autenticadas
		http.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
		//2, si no esta autenticada . Se mueestra por defecto una pagina web del login
//para importarlo usamos ctrl+shit+t buscamos Customizer de spring security vamos al metodo, boton derecho copy qualified name y lo pegamos arriba con el import static
		http.httpBasic(withDefaults());
		//3.3, CSRF -> POST. PUT afecta a estas dos, para poder usarlas tenemos que hacer esto:
		http.csrf().disable();
		
		return http.build();
	}
	
}
