package com.formacionbdi.springboot.app.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

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
	
	
//	. Vamos a configurar estos parametros con otros valores 
//	  slidingWindowSize(100), failureRateThreshold(50), waitDurationOpenState(60000 ms), 
//	  permittedNumberOfCallsInHalfOpenState(10),showCallRateThreshold(100), slowCallDurationThreshold(60000ms).
//
//	  El resilience4j esta configurado por default y su nombre actual es items.
   @Bean 
   public Customizer<Resilience4JCircuitBreakerFactory> getDefaultCustomizer(){
	   //Este id se aplica a  cualquier circuitBreaker que tengamos en nuetra aplicacion
	   //Esto confiura cada corto Circuito
	   return factory -> factory.configureDefault(id -> {
		   return new Resilience4JConfigBuilder(id)
				   //Con custom personalizamos y es necesasrio usar el build
				   .circuitBreakerConfig(CircuitBreakerConfig.custom()
				    		  //Por defecto es 100 request, vamos a configurar 10
				    		  .slidingWindowSize(10)
				    		  //Tolerancia de falla lo dejamos igual al 50%
				    		  //Taza de falla.. el umbral
				    		  .failureRateThreshold(50)
				    		  //Tiempo de espera en el estado abierto
				    		  //Aqui usamos la clase de java8 , java time duration
				    		  .waitDurationInOpenState(Duration.ofSeconds(10l))
				    		  //Permitido numero de llamadas en estado semiabierto..Por defecto son 10, colocamos 5
				    		  //Semiabierto, permote probar 5 veces y si hay error.o mandaos nuevamente al estado abierto, si no hay error
				    		  //lo mandamos al estado cerrado
				    		  .permittedNumberOfCallsInHalfOpenState(5)
				    		  //Esto configura el porcentaje de umbral en llamadas lentas
				    		  //Configuramos la llamada lenta al 50%.. Por default es 100%
				    		  //Si el umbral de 50% es alcanado en llmdas lentas a 2l que es dos segundos slowCallDurationThreshold configurado
				    		  //en slowCallDurationThreshold, entonces pasa al estado abierto por ser una llamada lenta
				    		  .slowCallRateThreshold(50)
				    		  //configuramos llamada lenta en 2 seg
				    		  //Configuramos el tiempo maximo en durar una llamada lenta en particular
				    		  //Debe ser esta menor al del timeout , porque si no, se dispara primero eel timeout
				    		  .slowCallDurationThreshold(Duration.ofSeconds(2l))
				    		  .build())
				      //Con ofDefaults.. dejamos los valores por defecto  y no es necesasrio usar el build
				   //Configuramos el timeout o tiempo limite en el fondo
				   //El timeout por defecto es de un segundo.. Si tarda la llamada mas de 1 seg, se dispara el timeout errr
//				      .timeLimiterConfig(TimeLimiterConfig.ofDefaults())
				   //Configuramos personalizadamente el timeout
				   .timeLimiterConfig(TimeLimiterConfig.custom()
						   //Timeout Debe ser esta mayor al de la llamada lenta  , porque si no, se dispara primero el timeout
						     .timeoutDuration(Duration.ofSeconds(6l)).build())
				      //Este build construye todo (cutom y ofdefaults)
				      .build();
				      
				              
	   });
   }
	
}
