package com.formacionbdi.springboot.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

//Lo autoregistramos como un bean
@Component
public class EjemploGlobalFilter implements GlobalFilter, Ordered{
	private final Logger logger = LoggerFactory.getLogger(EjemploGlobalFilter.class);

	//Trabaja con observablez porque es programacion reactiva
//	Con el exchange podemos aceder al request y response
	//chain es la cadena del filtro
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Ejecutando filter pre");
		//exchange contiene la respuesta y el request o peticion
		//Nos permite crear un objeto reactivo, un Mono<void>
//		Todo lo que esta en chain es el Pre
//		Vamos a utar la peticion antes que sea procesada
		Optional.ofNullable(exchange.getRequest().mutate().headers( h -> h.add("token", "123456")))
		.ifPresent(valor -> {
			exchange.getResponse().getHeaders().add("token", null);
		});
	
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			//Obtenemos la cabecera del request
			exchange.getRequest().getHeaders().getFirst("token");
//			Modificamos cuando tenemos la respuesta del servicio 
//			Todo lo qe esta en esta expresion lambda es el post
			logger.info("Ejecutando filter post");
			exchange.getResponse().getCookies().add("color", 
					ResponseCookie.from("color", "rojo").build());
			//exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		//Da error con -1 , entonces aqui debemos quitar el -1 porque con -1 da alta prioridad 
		// y el response es solo lectura y no podemos escribir
//		return -1;
		//Lo cambiamos con 1
		return 1;
	}

}
