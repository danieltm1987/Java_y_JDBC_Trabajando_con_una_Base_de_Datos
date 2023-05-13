package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaPoolDeConexiones {

	public static void main(String[] args) throws SQLException {
		System.out.println("Prueba Pool de Conexiones MCHANGE c3p0\n");
		
		ConnectionFactory factory = new ConnectionFactory();
		
		for (int i = 0; i < 20; i++) {
			Connection con = factory.recuperaConexion();
			System.out.println("Abriendo la conexion numero :"+(i+1));
		}

	}

}
