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

import javax.management.RuntimeErrorException;
import javax.swing.JOptionPane;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {
				
		Connection con = new ConnectionFactory().recuperaConexion();
		PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
				+ " NOMBRE = ?, "
				+ " DESCRIPCION = ?,"
				+ " CANTIDAD = ?"
				+ " WHERE ID = ?;");
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad );
		statement.setInt(4, id );
		
		statement.execute();
		
		return statement.getUpdateCount();
	}

	public int eliminar(Integer id )throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();		
		PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");		
		statement.setInt(1, id);
		statement.execute();
		
		return statement.getUpdateCount();
		
	}

	public List<Map<String, String>> listar() throws SQLException {
		
		Connection con = new ConnectionFactory().recuperaConexion();		
		PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");		
		statement.execute();
		
		ResultSet resultSet = statement.getResultSet();
		
		List<Map<String, String>> resultado = new ArrayList<>();
		
		while(resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE", resultSet.getString("NOMBRE"));
			fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
			
			resultado.add(fila);
		}

		con.close();
		
		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
    	String nombre = producto.get("NOMBRE");
    	String descripcion = producto.get("DESCRIPCION");
    	Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
    	Integer maximoCantidad = 50;
    	
    	ConnectionFactory factory = new ConnectionFactory();
    	Connection con = factory.recuperaConexion();
    	con.setAutoCommit(false);
    	
    	PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(NOMBRE, DESCRIPCION, CANTIDAD) "
    			+ " VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
    	
    	try {
			do {
				int cantidadParaGuadar = Math.min(cantidad, maximoCantidad);
						
				ejecutaRegistros(nombre, descripcion, cantidadParaGuadar, statement);
				
				cantidad-=maximoCantidad;
				
			}while(cantidad>0);
			con.commit();
			JOptionPane.showMessageDialog(null, "Registrado con éxito!");
		} catch (Exception e) {			
			con.rollback();
			JOptionPane.showMessageDialog(null, "Error durante la transaccion Guardar\n Cantidad debe ser mayor a 50!");
		}
    	
    	statement.close();
    	con.close();
	}

	private void ejecutaRegistros(String nombre, String descripcion, Integer cantidad,
			PreparedStatement statement) throws SQLException {
		
		if(cantidad < 50) {
			throw new RuntimeException("Ocurrio un error");			
		}
		
		statement.setString(1,nombre );
    	statement.setString(2, descripcion);
    	statement.setInt(3, cantidad);
    	
    	statement.execute();
    	
    	// Try con recursos version 7 de JAVA
    	try(final ResultSet resulSet = statement.getGeneratedKeys();){
    		while(resulSet.next()) {
        		System.out.println(
        				String.format(
        						"Fue insertado el producto de ID %d",
        						resulSet.getInt(1)));
        	}
    	}
    			
    	
    	
    	
    	
    	
	}
    
}
