package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.com.biblioteca.dominio.Autor;
import br.com.biblioteca.dominio.Livro;

public class LivroDB implements GenericDB<Livro, Integer> {
	private Connection con;

	private ResultSet rs;

	private Statement stm;

	public LivroDB() {

		try {

			con = Conexao.criarConexao();

			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela LIVRO já existe no BD

			rs = dbmd.getTables(null, null, "LIVRO", new String[] { "TABLE" });

			if (!rs.next()) {

				stm = con.createStatement();

				// se não houver uma tabela, ela é criada

				String s = "CREATE TABLE livro (id int not null generated always as identity primary key, isbn varchar(20), "
						+ "titulo varchar(100), anoedicao int,edicao int, editora varchar(100), situacao char(1))";

				stm.executeUpdate(s);

				// verifica se a tabela LIVROAUTOR já existe no BD

				rs = dbmd.getTables(null, null, "AUTORLIVRO", new String[] { "TABLE" });

				if (!rs.next()) {

					stm = con.createStatement();

					// se não houver uma tabela, ela é criada

					s = "CREATE TABLE AUTORLIVRO (ID_AUTOR INT REFERENCES AUTOR, " + "ID_LIVRO INT REFERENCES LIVRO)";

					stm.executeUpdate(s);

				}

			}

		} catch (SQLException e) {

			System.out.println("Erro ao criar conexão/tabela LIVRO");

		}

		finally {

			// fecha os recursos e a conexão com o BD

			this.fecha(rs, stm, con);

		}

	}

	public void inserir(Livro livro) {

		// cria um comando INSERT com os atributos de Livro

		String s = "insert into livro values(DEFAULT,'" + livro.getIsbn() + "', '" + livro.getTitulo() + "',"
				+ livro.getAnoEdicao() + ", " + livro.getEdicao() + ", '" + livro.getEditora() + "', '"
				+ livro.getSituacao() + "')";

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para inserir os dados na tabela LIVRO

			stm.executeUpdate(s);

			// grava autores do livro na tabela AUTORLIVRO

			Livro novoLivro = this.buscarPorISBN(livro.getIsbn());

			Set<Autor> autores = livro.getAutores();

			Iterator<Autor> it = autores.iterator();

			while (it.hasNext()) {

				Autor autor = it.next();

				s = "INSERT INTO AUTORLIVRO VALUES (" + autor.getId() + "," + novoLivro.getId() + ")";

				stm.executeUpdate(s);

			}

		}

		catch (SQLException e) {

			System.out.println("Erro ao inserir na tabela LIVRO/AUTORLIVRO");

		}

		finally {

			this.fecha(rs, stm, con);

		}

	}

	public void excluir(Livro livro) {

		// cria um comando DELETE usando o id do Livro

		String s = "delete from livro where id_livro = " + livro.getId();

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para excluir o livro da tabela

			stm.executeUpdate(s);

		}

		catch (SQLException e) {

			System.out.println("Erro ao tentar excluir na tabela");

		}

		finally {

			this.fecha(rs, stm, con);

		}

	}

	public void modificar(Livro livro) {

		// cria um comando para excluir todos os autores do livro

		String s1 = "DELETE FROM autorlivro where id_livro = " + livro.getId();

		// cria um comando UPDATE usando os atributos de livro

		String s2 = "UPDATE livro set isbn = '" + livro.getIsbn()

				+ "', titulo = '" + livro.getTitulo()

				+ "', anoedicao = " + livro.getAnoEdicao()

				+ ", edicao = " + livro.getEdicao()

				+ ", editora = '" + livro.getEditora()

				+ "', situacao = '" + livro.getSituacao()

				+ "' where id = "

				+ livro.getId();

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// exclui autores do livro

			stm.executeUpdate(s1);

			// executa o comando para modificar os dados na tabela

			stm.executeUpdate(s2);

			// grava autores do livro na tabela AUTORLIVRO

			Set<Autor> autores = livro.getAutores();

			Iterator<Autor> it = autores.iterator();

			while (it.hasNext()) {

				Autor autor = it.next();

				s1 = "INSERT INTO AUTORLIVRO VALUES (" + autor.getId() + ", " + livro.getId() + ")";

				stm.executeUpdate(s1);

			}

		}

		catch (SQLException e) {

			System.out.println("Erro ao modificar a tabela");

		}

		finally {

			this.fecha(rs, stm, con);

		}

	}

	public List<Livro> buscarTodos() {

		// declara um ArrayList para receber os dados da tabela

		List<Livro> lista = new ArrayList<Livro>();

		String s = "select * from livro";

		Livro livro = null;

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa a consulta de todos os registros

			rs = stm.executeQuery(s);

			// percorre o ResultSet lendo os dados de Livro

			while (rs.next()) {

				// cria um Autor com os dados de um registro

				int id = rs.getInt("id");

				String isbn = rs.getString("isbn");

				String titulo = rs.getString("titulo");

				int anoEdicao = rs.getInt("anoedicao");

				int edicao = rs.getInt("edicao");

				String editora = rs.getString("editora");

				Set<Autor> autores = new HashSet<Autor>();

				String situacao = rs.getString("situacao");

				// busca os autores do livro

				s = "select b.id, b.nome from autorlivro a, autor b where a.id_autor = b.id and a.id_livro = " + id;

				rs = stm.executeQuery(s);

				while (rs.next()) {

					int idAutor = rs.getInt("id");

					String nomeAutor = rs.getString("nome");

					autores.add(new Autor(idAutor, nomeAutor));

				}

				livro = new Livro(id, isbn, titulo, anoEdicao, edicao, editora, autores, situacao);

				// adiciona o Livro no ArrayList

				lista.add(livro);

			}

		}

		catch (SQLException e) {

			System.out.println("Erro ao consultar tabela");

		}

		finally {

			this.fecha(rs, stm, con);

		}

		return lista;

	}

	// código do método fecha()

	public Livro buscarPorID(Integer id) {

		// cria um SELECT para retornar um Livro pelo id

		String s = "select * from livro where id = " + id;

		Livro livro = null;

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa a consulta

			rs = stm.executeQuery(s);

			// cria um objeto Autor com os dados retornados

			if (rs.next()) {

				// cria um Autor com os dados de um registro

				String isbn = rs.getString("isbn");

				String titulo = rs.getString("titulo");

				int anoEdicao = rs.getInt("anoedicao");

				int edicao = rs.getInt("edicao");

				String editora = rs.getString("editora");

				Set<Autor> autores = new HashSet<Autor>();

				String situacao = rs.getString("situacao");

				// busca os autores do livro

				s = "select b.id, b.nome from autorlivro a, autor b where a.id_autor = b.id and a.id_livro = " + id;

				rs = stm.executeQuery(s);

				while (rs.next()) {

					int idAutor = rs.getInt("id");

					String nomeAutor = rs.getString("nome");

					autores.add(new Autor(idAutor, nomeAutor));

				}

				livro = new Livro(id, isbn, titulo, anoEdicao, edicao, editora, autores, situacao);

			}

		}

		catch (SQLException e) {

			System.out.println("Erro ao consultar na tabela LIVRO");

		}

		finally {

			this.fecha(rs, stm, con);

		}

		return livro;

	}

	private Livro buscarPorISBN(String isbn) {

		// cria um SELECT para retornar um Livro pelo isbn

		String s = "select * from livro where isbn = '" + isbn + "'";

		Livro livro = null;

		try {

			// executa a consulta

			rs = stm.executeQuery(s);

			// cria um objeto Autor com os dados retornados

			if (rs.next()) {

				// cria um Autor com os dados de um registro

				int id = rs.getInt("id");

				String titulo = rs.getString("titulo");

				int anoEdicao = rs.getInt("anoedicao");

				int edicao = rs.getInt("edicao");

				String editora = rs.getString("editora");

				String situacao = rs.getString("situacao");

				livro = new Livro(id, isbn, titulo, anoEdicao, edicao, editora,

						null, situacao);

			}

		}

		catch (SQLException e) {

			System.out.println("Erro ao consultar na tabela LIVRO");

		}

		return livro;

	}

	@Override
	public void fecha(ResultSet rs, Statement stm, Connection con) {
		// TODO Auto-generated method stub

	}

}
