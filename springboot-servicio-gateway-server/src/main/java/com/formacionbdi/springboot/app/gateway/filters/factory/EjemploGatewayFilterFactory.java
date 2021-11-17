package com.formacionbdi.springboot.app.gateway.filters.factory;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

//El nombre del sufijo de este filtro GatewayFilterFactory es muy importante
//Colocarlo para que springcloud lo detecte automaticamente el filtro
@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion>{
	
	private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

	

	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		// Implementamos este metodo al vuelo con una expresion lambda
		//Vamos a implementar esta interface funcional
		return (exchange,chain)->{
			logger.info("Ejecutando esto con pre gateway filter factory: " + config.mensaje);
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				//ofNullable porque (of) es para convertir un valor en un optional, Nullable si el valor es nulo o no 
				//tiene valor esta variable simplemente va a guardar un optional vacio
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				
				logger.info("Ejecutando post con  gateway filter factory: " + config.mensaje);
			}));
		};
	}
	
	

	@Override
	public List<String> shortcutFieldOrder() {
		//Regresa una lista con los nombres de los parametros que se configuran en el yml o properties del filtro
		
		return Arrays.asList("mensaje","cookieNombre","cookieValor");
	}



	public static class Configuracion {
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;
		
		public String getMensaje() {
			return mensaje;
		}
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		public String getCookieValor() {
			return cookieValor;
		}
		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}
		public String getCookieNombre() {
			return cookieNombre;
		}
		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}
		
	}
	
}
