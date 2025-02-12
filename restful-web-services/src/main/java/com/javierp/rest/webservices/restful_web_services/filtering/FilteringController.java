package com.javierp.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		SomeBean someBean= new SomeBean("value1", "value2","value3");

		//MappingJacksonValue para filtrar, añade logica de serializacion
		MappingJacksonValue mappingJacksonValue= new MappingJacksonValue(someBean);
		//creamos el filtro, filtra todo menos field 1 y 3
		SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		//damos nombre al filtro y aplicamos el filtro
		FilterProvider filters=new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		
		
		return mappingJacksonValue;
	}

	@GetMapping("/filtering-list") //no queremos devolver field 1
	public MappingJacksonValue filteringList() {
		
		List <SomeBean> list= Arrays.asList(new SomeBean("value1", "value2","value3"),new SomeBean("value4", "value5","value6"));
		
		MappingJacksonValue mappingJacksonValue =  new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}

}
