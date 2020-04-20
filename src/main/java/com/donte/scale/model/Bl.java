package com.donte.scale.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Bl implements EntidadeBase {
	
	public static final long NAO_DEFINIDO = 154;

	@Id	
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "viagemid")
	private Viagem viagem;
	
	@Column(name = "numero", nullable = true)
	private String numero;
	
	@Column(name = "descricao", nullable = true)
	private String descricao;
	
	@ManyToOne()
	@JoinColumn(name = "clienteid")
	private Cliente cliente;
	
	public Bl() {}
	
	private Bl(long id) {
		this.id = id;
	}

	public static Bl naoDefinido() {
		return new Bl(NAO_DEFINIDO);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {		
		String desc = (descricao == null)? "INDEFINIDO" : descricao;
		String nome = (cliente   == null)? "INDEFINIDO" : cliente.getNome();
		return desc + " • " + nome;
		//return desc;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Bl)) {
			return false;
		}
		Bl other = (Bl) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}