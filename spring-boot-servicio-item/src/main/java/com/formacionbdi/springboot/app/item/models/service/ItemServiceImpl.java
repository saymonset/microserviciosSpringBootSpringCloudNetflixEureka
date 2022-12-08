package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

//	como lo tenemos registrado , lo podemos injectar con @Autowired
	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays.asList(
				this.clienteRest.getForObject("http://servicio-productos/listar",
						Producto[].class));
//		Con java 8, convertinos una collecion en un flujo y por cada flujo a traves de un map
//				convertimos ese objeto en otro objeto y luego lo transformamos a una nuevaa lista con collect(Collectors.toList())
		return productos.stream().map((p)-> new Item(p, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Producto producto = this.clienteRest.
				getForObject("http://servicio-productos/ver/{id}",
				            Producto.class,
				            pathVariables);
		return new Item(producto, cantidad);
	}

}
