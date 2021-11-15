package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment env;
	
	/* Vamos a usar la anotacion Value, mucho mas simple */
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
   private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream()
				.map((producto)->{
					/* Con este local.server.port va a tomar el puerto real dinamicamente */
			     producto.setPort(Integer.parseInt(env.getProperty("local.server.port"))); 
				//	producto.setPort(port);
					return producto;
				}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detlle(@PathVariable Long id) {
		
		Producto producto = productoService.findById(id);
		/* Con este local.server.port va a tomar el puerto real dinamicamente */
		 producto.setPort(Integer.parseInt(env.getProperty("local.server.port"))); 
		//producto.setPort(port);
		
		/*
		 * // simulamos un error para usar Tolerancia de fallos, latencia, timeout es la
		 * // Api Hystrix
		 */		boolean ok = false;
//		if (!ok) {
//			throw new RuntimeException("No se pudo cargar el producto.");
//		}
		/* end */
		 
//		 El tiempo de timeout para ribbon y hystrix es de 1 seg
//		 Despues de 1 seg va alanzar un error
		 
//		 vamos a probar una falla timeout
//		 Esto va a fallar porque el limite de tiempo max es de 1segundo
//		 En el cliente , fresolvemos el timeout con esta  delaclaracion en el
//		 propertie  item
//		 #Hystrix(Camino alternativo) envuelve  a ribbon (Balanceador de carga),
//		 # Entonces o ideal es que hystrix tenga un tiempo mayor a ribbon
//		 #Para  subir archivos sta bien 60000 ms
//		 #hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds: 60000
//		 hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds: 13000
//		 #La suma  de ribbon, no debe pasar a hystrix .. En este caso 13000 que 
//		 #es la suma de 10000 + 3000
//		 ribbon.ConnectTimeout: 3000
//		 ribbon.ReadTimeout: 100000
//		 try {
//			Thread.sleep(2000l);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return producto;
	}
}
