package com.donte.scale.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Viagem implements EntidadeBase {
	
	private static final int NAO_DEFINIDO = 551;
	
	@Id	
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "navioid")
	private Navio navio;

	@Column(name = "statusviagem", nullable = true)
	private Integer statusViagem;
	
	@Column(name = "datapartida", nullable = true)
	private Date dataPartida;
		
	@Column(name = "dataatracacao", nullable = true)
	private Date dataAtracacao;
	
	public Viagem(){}

    private Viagem(long id) {
        this.id = id;
    }
      
    public static Viagem naoDefinido() {
        return new Viagem(NAO_DEFINIDO);
    }
		
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Navio getNavio() {
		return navio;
	}

	public void setNavio(Navio navio) {
		this.navio = navio;
	}

	public Integer getStatusViagem() {
		return statusViagem;
	}

	public void setStatusViagem(Integer statusViagem) {
		this.statusViagem = statusViagem;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Date getDataAtracacao() {
		return dataAtracacao;
	}

	public void setDataAtracacao(Date dataAtracacao) {
		this.dataAtracacao = dataAtracacao;
	}

	@Override
	public String toString() {
		//System.out.println("Chamando aqui");
		return navio.getNome();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Viagem)) {
			return false;
		}
		Viagem other = (Viagem) obj;
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