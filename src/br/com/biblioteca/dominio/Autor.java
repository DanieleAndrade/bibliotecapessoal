package br.com.biblioteca.dominio;

public class Autor implements Comparable<Autor>{
	private int id;
	private String nome;
	
	public Autor(){}
	
	public Autor(String nome){
		this.nome = nome;
	}
	
	public Autor(String nome, int id){
		this.id = id;
		this.nome = nome;
	}

	public Autor(Integer id2, String nome2) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString(){
		return "Autor[id="+id+",nome"+nome+"]";
	}
	
	@Override
	public boolean equals(Object obj){
		return true;
		
	}
	
	public int compareTo(Autor autor){
		return this.nome.compareTo(autor.getNome());
	}
	

}
