package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;


//Tenemos dos servicios que implementan la interface ItemService
//Una implementada con RestTemplate y la otra con Feign


//Si quiero usar Feign.. En ves de RestTemplae entonces colcamos primary
//Se inyecta por defecto en el controlador
//Primary se utiliza cuando no se especifica el nombre del componente
//Otra forma es usar los qualificadores a traves del nombre 
//o identificador del componente
//Nombre que  se coloca de forma explicita
@Service("serviceFeign")
//@Primary
public class ItemServiceFeign implements ItemService {

	// Otra implementacion alternativa a lo que teniamos con restTemplate
	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		// TODO Auto-generated method stub
		return new Item(clienteFeign.detlle(id), cantidad);
	}

}
