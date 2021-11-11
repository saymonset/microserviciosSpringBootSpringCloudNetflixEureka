package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.formacionbdi.springboot.app.item.models.Producto;

//Con esta declaracion se declara que esta interfaz es un clinte feign 
//.El cliente va hacer una peticopn http a los servicios de productos
//Debemos ir a la configuracion del servicio de productos y tomar e nombre del servicio igual a 
//como esta declarado en el prperties que es servicio-productos
//spring.application.name=servicio-productos
//server.port=8001 de servicios-productos
@FeignClient(name= "servicio-productos", url="localhost:8001")

public interface ProductoClienteRest {
	//Indicamos la ruta de este endpoint para consumir el servico del apiRest
	//y obtener los datos del json pero convertidos a nuestros objetos
	//debe Colocar el mismo GetMapping del ProductoController del servicio-productos
	//@GetMapping("/listar")
	@GetMapping("/listar")
	public List<Producto> listar();
	
	//Nos conecamos a microservicios-productos..mismo path y url de ese produto Controlador
	//Para conectarnos a detalle , seria exactamente igual
	@GetMapping("/ver/{id}")
	public Producto detlle(@PathVariable Long id);

}
