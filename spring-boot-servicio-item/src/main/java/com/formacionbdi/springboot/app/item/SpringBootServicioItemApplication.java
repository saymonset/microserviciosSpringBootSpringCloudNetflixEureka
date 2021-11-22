package com.formacionbdi.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//Ya quitamos la libreria del pom y hay ue quitarla
/*import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;*/
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


//Tolerancia de fallos, latencia, timeout es la  Api Hystrix, la habilitamos
//Utiliza ribbon por defecto que es el balanceador de carga y l trae por defecto eureka, no hay que
//declarar ribbon en el pom
//Ya quitamos la libreria del pom y hay ue quitarla
/*@EnableCircuitBreaker*/

//Importante para habilitar nuestro cliente feigns y nos permite
//inyectar este cliente en los controladores
@EnableFeignClients
/*configuramos RibbonClient*/
/*
 * El servicio-productos debe ser exactamente igual a
 * com.formacionbdi.springboot.app.item.clientes.POroductoClienteRest
 */

/*
 * <!-- #Esto anotacion RibbonClient ya no va, ya que esta informacion nos la da eureka
 * #Solo se comunicara mediante nombre de # la aplicacion o nombre del
 * microservicio spring.application.name=servicio.item -->
 * 
 * @RibbonClient(name = "servicio-productos")
 */
//Se habilita de forma explicita el cliente eureka. 
//No es necesario porque el pom ya esta declarada.. Pero es buena practica
@EnableEurekaClient
@SpringBootApplication
public class SpringBootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServicioItemApplication.class, args);
	}

}
