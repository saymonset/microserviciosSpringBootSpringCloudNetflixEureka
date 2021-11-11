package com.formacionbdi.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//Importante para habilitar nuestro cliente feigns y nos permite
//inyectar este cliente en los controladores
@EnableFeignClients
/*configuramos RibbonClient*/
/*
 * El servicio-productos debe ser exactamente igual a
 * com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest
 */
@RibbonClient(name = "servicio-productos")
@SpringBootApplication
public class SpringBootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServicioItemApplication.class, args);
	}

}
