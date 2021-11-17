package com.formacionbdi.springboot.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*Para hacerlo exlicito con eureka client, no es necesario porque ya esta reg en el pom
pero para adoptar buenas practicas*/
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioGatewayServerApplication.class, args);
	}

}
