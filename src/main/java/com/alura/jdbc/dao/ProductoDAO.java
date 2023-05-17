package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

import scala.Product;

public class ProductoDAO {
	
	final private Connection con;
	
	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto){
		
		//var nombre = producto.getNombre();
		//var descripcion = producto.getDescripcion();
		//var cantidad = producto.getCantidad();
		//final var maximoCantidad = 50;

		try (con) {
			
			//con.setAutoCommit(false);

			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTO(NOMBRE, DESCRIPCION, CANTIDAD) VALUES(?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			try(statement) {
				//do {
				//	int cantidadParaGuadar = Math.min(cantidad, maximoCantidad);

					ejecutaRegistros(producto, statement);

				//	cantidad -= maximoCantidad;
				//} while (cantidad > 0);
				//con.commit();
				JOptionPane.showMessageDialog(null, "Registrado con éxito!");
			} 
		}catch (SQLException e) {
				//con.rollback();
				JOptionPane.showMessageDialog(null,
						"Error durante la " + "transaccion Guardar\n Cantidad debe ser mayor a 50!");
				throw new RuntimeException(e);
			
		}
	}
	
	private void ejecutaRegistros(Producto producto, PreparedStatement statement)
			throws SQLException {

		/*if (cantidad < 50) {
			throw new RuntimeException("Ocurrio un error");
		}*/

		statement.setString(1, producto.getNombre() );
		statement.setString(2, producto.getDescripcion() );
		statement.setInt(3,producto.getCantidad());

		statement.execute();

		// Try con recursos version 7 de JAVA

		/*
		 * try(ResultSet resulSet = statement.getGeneratedKeys();){
		 * while(resulSet.next()) { System.out.println( String.format(
		 * "Fue insertado el producto de ID %d", resulSet.getInt(1))); } }
		 */

		// Try con recursos version 9 de JAVA

		final ResultSet resulSet = statement.getGeneratedKeys();

		try (resulSet) {
			while (resulSet.next()) {
				producto.setId(resulSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto %s", producto));
			}
		}

	}

	public List<Producto> listar() {
		List<Producto> resultado = new ArrayList<>();

		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();

		try (con) {			
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			
			try (statement) {
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				
				try(resultSet){
					while (resultSet.next()) {
						Producto fila = new Producto(resultSet.getInt("ID"), 
											resultSet.getString("NOMBRE"),
											resultSet.getString("DESCRIPCION"),
											resultSet.getInt("CANTIDAD"));
						/*
						Map<String, String> fila = new HashMap<>();
						fila.put("ID", String.valueOf(resultSet.getInt("ID")));
						fila.put("NOMBRE", resultSet.getString("NOMBRE"));
						fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
						fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
						*/
	
						resultado.add(fila);
					}
				}				
			}
			return resultado;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(Integer id) {
		try{
			
			final PreparedStatement statement = 
					con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				
				int updatecount = statement.getUpdateCount();
				
				return updatecount;
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int modificar(Producto producto) {
		try {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE PRODUCTO SET" 
							+ " NOMBRE = ?, "
							+ "DESCRIPCION = ?,"
							+ " CANTIDAD = ? "
							+ "WHERE ID = ?;");

			try (statement) {
				statement.setString(1,producto.getNombre());
				statement.setString(2,producto.getDescripcion() );
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getId());
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
