package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

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
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}
}
