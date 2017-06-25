package br.com.biblioteca.persistencia;

import br.com.biblioteca.persistencia.Conexao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.dominio.Autor;

public class AutorDB implements GenericDB<Autor, Integer> {
	private Connection con;

	private ResultSet rs;

	private Statement stm;

	public AutorDB() {

		try {

			con = Conexao.criarConexao();

			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela AUTOR já existe no BD

			rs = dbmd.getTables(null, null, "AUTOR", new String[] { "TABLE" });

			if (!rs.next()) {

				stm = con.createStatement();

				// se não houver uma tabela, ela é criada

				stm.executeUpdate("CREATE TABLE autor (id int generated always as identity,nome VARCHAR(40))");

			}

		} catch (SQLException e) {

			System.out.println("Erro ao criar conexão/tabela");

		} finally {

			// fecha os recursos e a conexão com o BD

			this.fecha(rs, stm, con);

		}

	}

	public void inserir(Autor autor) {

		// cria um comando INSERT com os atributos de Autor

		String s = "insert into autor values(DEFAULT,'" + autor.getNome() + "')";

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para inserir os dados na tabela

			stm.executeUpdate(s);

		} catch (SQLException e) {

			System.out.println("Erro ao inserir na tabela");

		} finally {

			this.fecha(rs, stm, con);

		}

	}

	public void excluir(Autor autor) {

		// cria um comando DELETE usando o id do Autor

		String s = "delete from autor where id = " + autor.getId();

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para excluir o autor da tabela

			stm.executeUpdate(s);

		} catch (SQLException e) {

			System.out.println("Erro ao tentar excluir na tabela");

		} finally {

			this.fecha(rs, stm, con);

		}

	}

	public void modificar(Autor autor) {

		// cria um comando UPDATE usando os atributos de Autor

		String s = "update autor set nome = '" + autor.getNome() + "' where id =" + autor.getId();

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para modificar os dados na tabela

			stm.executeUpdate(s);

		} catch (SQLException e) {

			System.out.println("Erro ao inserir na tabela");

		} finally {

			this.fecha(rs, stm, con);

		}

	}

	public List<Autor> buscarTodos() {

		// declara um ArrayList para receber os dados da tabela

		List<Autor> lista = new ArrayList<Autor>();

		String s = "select * from autor";

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa a consulta de todos os registros

			rs = stm.executeQuery(s);

			// percorre o ResultSet lendo os dados de Autor

			while (rs.next()) {

				int id = rs.getInt("id");

				String nome = rs.getString("nome");

				// cria um Autor com os dados de um registro

				Autor autor = new Autor(id, nome);

				// adiciona o Autor no ArrayList

				lista.add(autor);

			}

		} catch (SQLException e) {

			System.out.println("Erro ao consultar tabela");

		} finally {

			this.fecha(rs, stm, con);

		}

		return lista;

	}

	public void fecha(ResultSet rs, Statement stm, Connection con) {

		if (rs != null) {

			try {

				rs.close();

			} catch (SQLException e) {

			}

		}

		if (stm != null) {

			try {

				stm.close();

			} catch (SQLException e) {

			}

		}

		if (con != null) {

			try {

				con.close();

			} catch (SQLException e) {

			}

		}

	}

	public Autor buscarPorID(Integer id) {

		// cria um SELECT para retornar um Autor pelo id

		String s = "select * from autor where id = " + id;

		String nome;

		Autor autor = null;

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa a consulta

			rs = stm.executeQuery(s);

			// cria um objeto Autor com os dados retornados

			if (rs.next()) {

				id = rs.getInt("ID");

				nome = rs.getString("NOME");

				autor = new Autor(id, nome);

			}

		} catch (SQLException e) {

			System.out.println("Erro ao consultar na tabela");

		} finally {

			this.fecha(rs, stm, con);

		}

		return autor;

	}

}