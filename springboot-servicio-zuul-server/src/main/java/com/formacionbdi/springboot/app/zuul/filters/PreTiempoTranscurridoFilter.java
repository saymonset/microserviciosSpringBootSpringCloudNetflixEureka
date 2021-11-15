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
public class PreTiempoTranscurridoFilter extends ZuulFilter{
	private static Logger log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);

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
		log.info(String.format("%s request entrando a %s", request.getMethod(), request.getRequestURL().toString()));
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
