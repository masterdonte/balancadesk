package com.donte.scale.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.donte.scale.model.enums.TipoBalanca;

@Entity
public class ConfigBalanca implements EntidadeBase {
	
	public static final long BALANCA_TESTE = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	private String balanca;
	
	@Enumerated(EnumType.STRING)	
	private TipoBalanca modelo;
		
	private String porta;
	
	private int baudrate;
	
	private int copias;
	
	private String relatorio;
	
	@Column(name = "leitorserver")
	private String leitorServer;
	
	@Column(name = "leitorporta")
	private int leitorPorta;
	
	@Column(name = "machost")
	private String macHost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBalanca() {
		return balanca;
	}

	public void setBalanca(String balanca) {
		this.balanca = balanca;
	}

	public TipoBalanca getModelo() {
		return modelo;
	}

	public void setModelo(TipoBalanca modelo) {
		this.modelo = modelo;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public int getBaudrate() {
		return baudrate;
	}

	public void setBaudrate(int baudrate) {
		this.baudrate = baudrate;
	}

	public int getCopias() {
		return copias;
	}

	public void setCopias(int copias) {
		this.copias = copias;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public String getLeitorServer() {
		return leitorServer;
	}

	public void setLeitorServer(String leitorServer) {
		this.leitorServer = leitorServer;
	}

	public int getLeitorPorta() {
		return leitorPorta;
	}

	public void setLeitorPorta(int leitorPorta) {
		this.leitorPorta = leitorPorta;
	}

	public String getMacHost() {
		return macHost;
	}

	public void setMacHost(String macHost) {
		this.macHost = macHost;
	}
	

//	public TipoBalanca getTipo() {
//		return TipoBalanca.valueOf(modelo);
//	}
//
//	public void setTipo(TipoBalanca tipo) {
//		this.tipo = tipo;
//	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id + ", balanca: " + balanca + ", modelo: " + modelo + ", porta: " + porta + ", baudrate: " + baudrate;
		return result;
	}
}