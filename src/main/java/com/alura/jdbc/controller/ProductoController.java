package com.alura.jdbc.controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	public ProductoController() {
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}
	

	public int modificar(Producto producto){
		return productoDAO.modificar(producto);		
	}

	public int eliminar(Integer ID){
		return productoDAO.eliminar(ID);
	}

	public List<Producto> listar(){
		return productoDAO.listar();
	}

	public void guardar(Producto producto){
		productoDAO.guardar(producto);
	}
	
}
