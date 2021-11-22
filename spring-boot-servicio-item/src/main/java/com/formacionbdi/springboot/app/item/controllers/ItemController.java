package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//Ya quitamos la libreria del pom y hay ue quitarla
/*import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;*/

@RestController
public class ItemController {
	
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private CircuitBreakerFactory cbFactory; //cb es de circuitBraker
  
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
	//Como recibir un parametro del request? 
		//Hay dos formas.. Una es Injectando HttpServletRequest y con el request.getParameter("name") , obtenemos el valor del parametro
		//Otra forma es usando anotaciones como por ejemplo.... con el @ .. simplemente @RequestParam
	//aca realiza injection de dependencia con @RequestParam(name="nombre") String nombre, injecta el prametro nombre que viene del request
	//Por defecto este @RequestParam(name="nombre") es requerido, lo colocamos opcional
	//Si no lanzamos estos dos parametros como el nombre y token-request , no dara error y serian nulos porque son opcionales
	public List<Item> listar(@RequestParam(name="nombre", required=false) String nombre,
			@RequestHeader(name="token-request", required=false) String token){
		System.out.println(nombre);
		System.out.println(token);
		return itemService.findAll();
	}
	
	
	/*
	 * // simulamos un error para usar Tolerancia de fallos, latencia, timeout es la
	 * // Api Hystrix
	 */	
	/* Creamos un camino alternativo si falla */
	//Ya quitamos la libreria del pom y hay ue quitarla
	/* @HystrixCommand(fallbackMethod = "metodoAlternativo") */
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
 		
		//item es el nombre del CircuitBreaker qe vamos a implementar
		//Es una expresion Lambda de java 8. Se trabaja mucho con estas expresiones
		return cbFactory.create("items")
				//Aqui tenemos el camino alternativo que seria el segundo argumento
				//Si en el circuitoBreaker su estado pasa de cerrado a abierto, se va por el e de error y su camino alternativo
				//estamos usando resilencia pero de la forma programaica sin usar anotaciones, antes con hystric, usabamos un metodo callback
				.run(()-> itemService.findById(id, cantidad), e -> metodoAlternativo(id, cantidad, e));
	}
	
//	Siempre que vallamos hacer anotaciones, sus variables como items, hay que configurarls en properties o yaml
	//Este name="items" esta configurado en el yml
	@CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver2/{id}/cantidad/{cantidad}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
 		
		return itemService.findById(id, cantidad);
	}
//	Tiene que se r un metodo igul al original el metodo lternativo
	public Item metodoAlternativo(@PathVariable Long id, Integer cantidad, Throwable e) {
		logger.info(e.getMessage());
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
