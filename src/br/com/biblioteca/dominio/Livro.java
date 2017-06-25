package br.com.biblioteca.dominio;

import java.util.HashSet;
import java.util.Set;

public class Livro {
	private int id;
	private String isbn;
	private String titulo;
	private int anoEdicao;
	private int edicao;
	private String editora;
	private Set<Autor> autores;
	private char situacao;

	public Livro() {
		this.autores = new HashSet<Autor>();

	}

	public Livro(String isbn, String titulo, int anoEdicao, int edicao, String editora, char situacao) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.anoEdicao = anoEdicao;
		this.edicao = edicao;
		this.editora = editora;
		this.autores = new HashSet<Autor>();
		this.situacao = situacao;

	}

	public Livro(int id, String isbn, String titulo, int anoEdicao, int edicao, String editora, Set<Autor> autores,
			char situacao) {
		this.id = id;
		this.isbn = isbn;
		this.titulo = titulo;
		this.anoEdicao = anoEdicao;
		this.edicao = edicao;
		this.editora = editora;
		this.autores = new HashSet<Autor>();
		this.situacao = situacao;

	}

	public Livro(Integer id2, String isbn2, String titulo2, int anoEdicao2, int edicao2, String editora2,
			Set<Autor> autores2, String situacao2) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoEdicao() {
		return anoEdicao;
	}

	public void setAnoEdicao(int anoEdicao) {
		this.anoEdicao = anoEdicao;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public Set<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Set<Autor> autores) {
		this.autores = autores;
	}

	public char getSituacao() {
		return situacao;
	}

	public void setSituacao(char situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + "]";

	}

}
