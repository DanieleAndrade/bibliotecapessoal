package br.com.biblioteca.persistencia;

import br.com.biblioteca.dominio.Emprestimo;
import br.com.biblioteca.dominio.Livro;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmprestimoDB implements GenericDB<Emprestimo, Integer> {

	private Connection con;

	private ResultSet rs;

	private Statement stm;

	// define o formato do campo Data

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public EmprestimoDB() {

		try {

			con = Conexao.criarConexao();

			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela EMPRESTIMO já existe no BD

			rs = dbmd.getTables(null, null, "EMPRESTIMO", new String[] { "TABLE" });

			if (!rs.next()) {

				stm = con.createStatement();

				// se não houver uma tabela, ela é criada

				String s = "CREATE TABLE EMPRESTIMO (ID INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
						+ " ID_LIVRO INT REFERENCES LIVRO, NOME_PESSOA VARCHAR(40), DATA_EMPRESTIMO DATE, DATA_DEVOLUCAO DATE)";

				stm.executeUpdate(s);

			}

		}

		catch (SQLException e) {

			System.out.println("Erro ao criar conexão/tabela EMPRESTIMO");

		}

		finally {

			// fecha os recursos e a conexão com o BD

			this.fecha(rs, stm, con);

		}

	}

	public void inserir(Emprestimo emprestimo) {

		// cria um comando INSERT com os atributos de Emprestimo

		String s = "insert into emprestimo (id, id_livro, nome_pessoa, data_emprestimo)values(DEFAULT,"
				+ emprestimo.getLivro().getId()

				+ ", '" + emprestimo.getNomePessoa()

				+ "', '" + new String(dateFormat.format(emprestimo.getDataEmprestimo().getTime()))

				+ "')";

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para inserir os dados na tabela

			stm.executeUpdate(s);

		}

		catch (SQLException e) {

			System.out.println("Erro ao inserir na tabela Emprestimo");

		}

		finally {

			this.fecha(rs, stm, con);

		}

	}

	public void excluir(Emprestimo emprestimo) {

		// cria um comando DELETE usando o id de Emprestimo

		String s = "delete from emprestimo where id = " + emprestimo.getId();

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para excluir o autor da tabela

			stm.executeUpdate(s);

		}

		catch (SQLException e) {

			System.out.println("Erro ao tentar excluir na tabela");

		}

		finally {

			this.fecha(rs, stm, con);

		}

	}

	public void modificar(Emprestimo emprestimo) {

		// cria um comando UPDATE usando os atributos de Emprestimo

		String s = "update emprestimo set nome_pessoa = '"

				+ emprestimo.getNomePessoa()

				+ "', data_emprestimo = '" + new String(dateFormat.format

				(emprestimo.getDataEmprestimo().getTime()));

		if (emprestimo.getDataDevolucao() == null) {

			s += "' where id = " + emprestimo.getId();

		} else {

			s += "', data_devolucao = '" + new String(dateFormat.format

			(emprestimo.getDataDevolucao().getTime()))

					+ "' where id = " + emprestimo.getId();

		}

		System.out.println(s);

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa o comando para modificar os dados na tabela

			stm.executeUpdate(s);

		}

		catch (SQLException e) {

			System.out.println("Erro ao modificar a tabela");

		}

		finally {

			this.fecha(rs, stm, con);

		}

	}

	public List<Emprestimo> buscarTodos() {

		// declara um ArrayList para receber os dados da tabela

		List<Emprestimo> lista = new ArrayList<Emprestimo>();

		String s = "select * from emprestimo";

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa a consulta de todos os registros

			rs = stm.executeQuery(s);

			// percorre o ResultSet lendo os dados de Emprestimo

			while (rs.next()) {

				int id = rs.getInt("id");

				int idLivro = rs.getInt("id_livro");

				String nomePessoa = rs.getString("nome_pessoa");

				Date dataEmprestimo = rs.getDate("data_emprestimo");

				Date dataDevolucao = rs.getDate("data_devolucao");

				Livro livro = new LivroDB().buscarPorID(idLivro);

				// cria um Emprestimo com os dados de um registro

				Emprestimo emprestimo = new Emprestimo(id, nomePessoa,

						dataEmprestimo, dataDevolucao, livro);

				// adiciona o Emprestimo no ArrayList

				lista.add(emprestimo);

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

	public Emprestimo buscarPorID(Integer id) {

		// cria um SELECT para retornar um Emprestimo pelo id

		String s = "select * from Emprestimo where id = " + id;

		Emprestimo emprestimo = null;

		try {

			con = Conexao.criarConexao();

			stm = con.createStatement();

			// executa a consulta

			rs = stm.executeQuery(s);

			// cria um objeto Emprestimo com os dados retornados

			if (rs.next()) {

				id = rs.getInt("ID");

				int idLivro = rs.getInt("id_livro");

				String nomePessoa = rs.getString("nome_pessoa");

				Date dataEmprestimo = rs.getDate("data_emprestimo");

				Date dataDevolucao = rs.getDate("data_devolucao");

				Livro livro = new LivroDB().buscarPorID(idLivro);

				// cria um Emprestimo com os dados de um registro

				emprestimo = new Emprestimo(id, nomePessoa, dataEmprestimo,

						dataDevolucao, livro);

			}

		}

		catch (SQLException e) {

			System.out.println("Erro ao consultar na tabela");

		}

		finally {

			this.fecha(rs, stm, con);

		}

		return emprestimo;

	}

	@Override
	public void fecha(ResultSet rs, Statement stm, Connection con) {
		// TODO Auto-generated method stub

	}

}
