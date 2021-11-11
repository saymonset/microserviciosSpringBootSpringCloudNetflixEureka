package com.formacionbdi.springboot.app.item.models;

import java.util.Date;

public class Producto {
  private Long id;
  private String nombre;
  private Double precio;
  private Date creteAt;
  /* Para saber que balanceador de carga se esta usando */
  private Integer port;
public Integer getPort() {
	return port;
}
public void setPort(Integer port) {
	this.port = port;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public Double getPrecio() {
	return precio;
}
public void setPrecio(Double precio) {
	this.precio = precio;
}
public Date getCreteAt() {
	return creteAt;
}
public void setCreteAt(Date creteAt) {
	this.creteAt = creteAt;
}
}
