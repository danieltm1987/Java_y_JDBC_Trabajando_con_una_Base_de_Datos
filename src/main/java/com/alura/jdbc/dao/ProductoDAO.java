package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	
	final private Connection con;
	
	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto) throws SQLException {
		
		//var nombre = producto.getNombre();
		//var descripcion = producto.getDescripcion();
		//var cantidad = producto.getCantidad();

		//final var maximoCantidad = 50;		

		try (con) {
			con.setAutoCommit(false);

			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTO(NOMBRE, DESCRIPCION, CANTIDAD) VALUES(?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			try(statement) {
				//do {
				//	int cantidadParaGuadar = Math.min(cantidad, maximoCantidad);

					ejecutaRegistros(producto, statement);

				//	cantidad -= maximoCantidad;
				//} while (cantidad > 0);
				con.commit();
				JOptionPane.showMessageDialog(null, "Registrado con éxito!");
			} catch (Exception e) {
				con.rollback();
				JOptionPane.showMessageDialog(null,
						"Error durante la " + "transaccion Guardar\n Cantidad debe ser mayor a 50!");
			}
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

}
