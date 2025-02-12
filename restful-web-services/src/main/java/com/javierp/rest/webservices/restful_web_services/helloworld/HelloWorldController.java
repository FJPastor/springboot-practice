package com.javierp.rest.webservices.restful_web_services.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//REST API
@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
	// dar la URL: /hello-world
	//que devuelva "Hello World
	//@RequestMapping(method= RequestMethod.GET,path="/hello-world")
	//otra forma de hacerlo
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	//vamos a probar a devolver un json, asi devolvemos una instancia de nuestra propia clase
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	//vamos a crear un hello-world con path parameters : /hello-world/path-variable/{name}
//imaginemos que alguien quiere encontrar la variable "Javi"
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorlPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s",name)); //seria igual q lo de abajo
		//return new HelloWorldBean("Hello World "+ name);
	}
	
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized() {
		//el locale es para utilizar el contexto del hilo actual. Es para que coja el que tengamos elegido en el API tester
        Locale locale = LocaleContextHolder.getLocale();
		System.out.println("Locale actual: " + locale);
	    String mensaje=messageSource.getMessage("good.morning.message", null, "Default Message", locale);
	    System.out.println("Mensaje obtenido: " + mensaje);
	    return mensaje;
	}
	//1:good.morning.message
	//1:good.morning.message
	//1:good.morning.message
	//1:good.morning.message
	//-Example: "en" - English (Good Morning)
	//-Example: "nl" - Dutch (Goedemorgen)
	//-Example: "fr" - English (Bonjour)
	//-Example: "de" - English (Guten Morgen)


	
	
}
