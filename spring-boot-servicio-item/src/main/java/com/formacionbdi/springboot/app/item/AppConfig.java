package com.formacionbdi.springboot.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

//	como lo tenemos registrado , lo podemos injectar con @Autowired
	@Bean("clienteRest")
	/* Ver balanceo de carga usando con RestTemplate.. */
	/* Se coloca esta etiqueta @LoadBalanced */
	/* Con esto de forma automatica va a usar Ribbon para el balanceo de carga */
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
}
