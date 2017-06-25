package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Conexao {
	public static final String URL = "jdbc:derby:biblioteca;create=true";

	public static Connection criarConexao() throws SQLException {

		Connection con = (Connection) DriverManager.getConnection(URL);

		return con;

	}

}
