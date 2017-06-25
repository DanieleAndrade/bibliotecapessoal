package br.com.biblioteca.dominio;

import java.util.Date;

	public class Emprestimo {
		private int id;
		private String nomePessoa;
		private Date dataEmprestimo;
		private Date dataDevolucao;
		private Livro livro;

	public Emprestimo() {}

	public Emprestimo(String nomePessoa, Date dataEmprestimo, Date dataDevolucao, Livro livro) {
		this.nomePessoa = nomePessoa;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
		this.livro = livro;

	}

	public Emprestimo(int id, String nomePessoa, Date dataEmprestimo, Date dataDevolucao, Livro livro) {
		this.id = id;
		this.nomePessoa = nomePessoa;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
		this.livro = livro;

	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	@Override
	public String toString() {
		return "Emprestimo [nomePessoa=" + nomePessoa + ", dataEmprestimo=" + dataEmprestimo + ", dataDevolucao="
				+ dataDevolucao + "]";

	}

}
