package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface GenericDB<T, ID> {
	public void inserir(T obj);

	public void modificar(T obj);

	public void excluir(T obj);

	public List<T> buscarTodos();

	public T buscarPorID(ID id);

	public void fecha(ResultSet rs, Statement stm, Connection con);

}
