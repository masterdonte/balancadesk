package com.donte.scale.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.donte.scale.model.enums.TipoEstadia;
import com.google.zxing.WriterException;

@Entity
public class Balanca implements EntidadeBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "veiculoid")
	private Veiculo veiculo;	
	
	@ManyToOne()
	@JoinColumn(name = "blid")
	private Bl bl;
	
	@ManyToOne()
	@JoinColumn(name = "turnoid")
	private Turno turno;
	
	@Column(name = "tara", nullable = true)
	private Integer tara;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datatara", nullable = true)
	private Date dataTara;
	
	@Column(name = "logintara", nullable = true)
	private String loginTara;

	@Column(name = "peso", nullable = true)
	private Integer pesoCheio;
	
	@Column(name = "pesoliq", nullable = true)
	private Integer pesoLiquido;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datapeso", nullable = true)
	private Date dataPeso;

	@Column(name = "logincheio", nullable = true)
	private String loginCheio;

	@Column(name = "numero", nullable = true)
	private String numero;
	
	@Column(name = "porao", nullable = true)
	private String porao;
	
	@Column(name = "tipoestadia", nullable = false)
	private TipoEstadia tipoEstadia;
	
	@Column(name = "produto", nullable = true)
	private String produto;

	@Column(name = "taramanual", nullable = false, columnDefinition="BIT DEFAULT 0")
	private Boolean taraManual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Bl getBl() {
		return bl;
	}

	public void setBl(Bl bl) {
		this.bl = bl;
	}

	public Integer getTara() {
		return tara;
	}

	public void setTara(Integer tara) {
		this.tara = tara;
	}

	public Date getDataTara() {
		return dataTara;
	}

	public void setDataTara(Date dataTara) {
		this.dataTara = dataTara;
	}

	public Date getDataPeso() {
		return dataPeso;
	}

	public void setDataPeso(Date dataPeso) {
		this.dataPeso = dataPeso;
	}

	public Integer getPesoCheio() {
		return pesoCheio;
	}

	public void setPesoCheio(Integer pesoCheio) {
		this.pesoCheio = pesoCheio;
	}

	public Integer getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(Integer pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public String getLoginTara() {
		return loginTara;
	}

	public void setLoginTara(String loginTara) {
		this.loginTara = loginTara;
	}

	public String getLoginCheio() {
		return loginCheio;
	}

	public void setLoginCheio(String loginCheio) {
		this.loginCheio = loginCheio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoEstadia getTipoEstadia() {
		return tipoEstadia;
	}

	public void setTipoEstadia(TipoEstadia tipoEstadia) {
		this.tipoEstadia = tipoEstadia;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getPorao() {
		return porao;
	}

	public void setPorao(String porao) {
		this.porao = porao;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	public Boolean getTaraManual() {
		return taraManual;
	}

	public void setTaraManual(Boolean taraManual) {
		this.taraManual = taraManual;
	}
		
	public InputStream getQrCodeBalanca(){
		InputStream stream = null;
		try {
			BufferedImage image = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(new com.google.zxing.qrcode.QRCodeWriter().encode(this.toString(), com.google.zxing.BarcodeFormat.QR_CODE, 350, 350));					
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( image, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();		
			stream = new ByteArrayInputStream(imageInByte);
		} catch (WriterException | IOException e) {			
			e.printStackTrace();
			stream = null;
		}			
		return stream;
	}
	
	public void preencherDadosTara(Veiculo veiculo, Integer tara, Boolean taraManual, String login){
		this.setVeiculo(veiculo);
		this.setTara(tara);			
		this.setPesoCheio(0);
		this.setPesoLiquido(0);
		this.setTaraManual(taraManual);
		this.setTipoEstadia(TipoEstadia.FLUXO);		
		this.setLoginTara(login);
		this.setDataTara(new Date());
		this.setBl(Bl.naoDefinido());
	}

	@Override
	public String toString() {
		String result = "";
		if (id != null)
			result += "CONTROLE: " + id + "\n";
		if (veiculo != null)
			result += "PLACA: " + veiculo.getPlaca() + "\n";
		result += "TARA: " + tara + "\n";
		if (dataTara != null)
			result += "DATA TARA: " + new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dataTara) + "\n";
		result += "P. BRUTO: " + pesoCheio + "\n";
		result += "P. LÍQUI: " + pesoLiquido + "\n";
		if (dataPeso != null)
			result += "DATA PESO: " + new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dataPeso) + "\n";
		if (bl != null){
			result += "NAVIO: "   + bl.getViagem().getNavio().getNome() + "\n";
			result += "CLIENTE: " + bl.getCliente().getNome() + "\n";
			result += "PRODUTO: " + bl.getDescricao() + "\n";
		}				
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Balanca)) {
			return false;
		}
		Balanca other = (Balanca) obj;
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