package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/*Marcarla como un bean de spring*/
@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter{
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		/* Si colocamos en true, ejecutara el run */
		 
		return true;
	}

	@Override
	public Object run() throws ZuulException {
 /*encontramos el client http*/
	
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		/*
		 * En pre era enrutando a post
		 * log.info(String.format("%s enrutando a post a %s", request.getMethod(),
		 * request.getRequestURL().toString()));
		 */
		log.info(String.format("entrando a post a POST FILTER"));
		Long tiempoInicio = (Long)request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		log.info(String.format("Tiempo transcurrido en segundos %s seg.", tiempoTranscurrido.doubleValue() / 1000.00));
		log.info(String.format("Tiempo transcurrido en milisegundos %s milisegundos", tiempoTranscurrido / 1000));
		
		
		
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
