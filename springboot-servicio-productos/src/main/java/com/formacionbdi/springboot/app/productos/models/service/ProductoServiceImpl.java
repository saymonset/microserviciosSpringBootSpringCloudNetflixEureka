package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.productos.models.dao.ProductoDao;
import com.formacionbdi.springboot.app.productos.models.entity.Producto;
 

//Service es un stereotipo de component
@Service
public class ProductoServiceImpl implements IProductoService{
	@Autowired
	private ProductoDao productoDao;

	@Transactional()
	public List<Producto> findAll() {
		return (List<Producto>)productoDao.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

}
