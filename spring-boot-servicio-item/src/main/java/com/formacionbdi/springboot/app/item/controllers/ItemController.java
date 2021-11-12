package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {
  
	//Si quiero usar Feign.. En ves de RestTemplae entonces colcamos primary
	//Se inyecta por defecto en el controlador
	 
  @Autowired
//Como alternativa a primry.. Usamos qualifier con el nombre del bean en misnuscula
//	la primera letra
  //o el uso de un nombre que  se coloca de forma explicita

//  Podemos suichear entre RestTemplate y Feign con el nombre de Qualifier
  @Qualifier("serviceFeign")
  //@Qualifier("serviceRestTemplate")
  private ItemService itemService;
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	
	/*
	 * // simulamos un error para usar Tolerancia de fallos, latencia, timeout es la
	 * // Api Hystrix
	 */	
	/* Creamos un camino alternativo si falla */
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}
	
//	Tiene que se r un metodo igul al original el metodo lternativo
	public Item metodoAlternativo(@PathVariable Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Camar Sony Metodo Alternativo");
		producto.setPrecio(500.00);
		item.setProducto(producto);
		return item;
		
	}
}
