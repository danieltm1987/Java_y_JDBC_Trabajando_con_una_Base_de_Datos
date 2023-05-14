package com.alura.jdbc.modelo;

public class Producto {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	
	public Producto(String nombre, String descripcion, Integer cantidad) {		
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return String.format("Id: %d, Nombre: %s, Descripcion: %s, Cantidad: %d",
				this.id, this.nombre, this.descripcion, this.cantidad);
	}
	
}
