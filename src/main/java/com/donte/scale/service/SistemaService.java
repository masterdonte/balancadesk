package com.donte.scale.service;

import java.util.List;

import com.donte.scale.dao.BalancaDAO;
import com.donte.scale.dao.BlDAO;
import com.donte.scale.dao.ConfigBalancaDAO;
import com.donte.scale.dao.TurnoDAO;
import com.donte.scale.dao.UsuarioDAO;
import com.donte.scale.dao.VeiculoDAO;
import com.donte.scale.dao.ViagemDAO;
import com.donte.scale.model.Balanca;
import com.donte.scale.model.Bl;
import com.donte.scale.model.ConfigBalanca;
import com.donte.scale.model.Turno;
import com.donte.scale.model.Usuario;
import com.donte.scale.model.Veiculo;
import com.donte.scale.model.Viagem;
import com.donte.scale.util.Aplicacao;

public class SistemaService {
	
	private static SistemaService instance;
	
	private UsuarioDAO userDao    = new UsuarioDAO();
	private ViagemDAO  viagemDao  = new ViagemDAO();
	private VeiculoDAO veiculoDao = new VeiculoDAO();
	private BlDAO produtoDao      = new BlDAO();
	private BalancaDAO balancaDao = new BalancaDAO();
	private TurnoDAO turnoDao = new TurnoDAO();
	private ConfigBalancaDAO configDao = new ConfigBalancaDAO();
	
	public static SistemaService getInstance(){
		if(instance == null)
			instance = new SistemaService();
		return instance;
	}
	
	public void testarMetodo(){
		System.out.println("TESTANDO METOD");
	}
	
	public Usuario autenticar(String user, String senha){
		String md5 = Aplicacao.MD5(senha);
		return userDao.findUsuarioByLoginESenha(user, md5);
	}
	
	public List<Viagem> carregarViagens(){
		List<Viagem> viagens = viagemDao.findViagensComNaviosAtracados();
		//viagens.add(0,Viagem.naoDefinido());		
		return viagens;				
	}
	
	public List<Bl> carregarProdutos(Viagem viagem){
		List<Bl> produtos = produtoDao.findProdutosDaViagem(viagem);
		//produtos.add(0,Bl.naoDefinido());
		if(produtos.size() == 0){
			produtos.add(produtoDao.consultaPorId(Bl.NAO_DEFINIDO));
		}
		return produtos;				
	}
	
	public Veiculo findVeiculoByPlaca(String placa){
		Veiculo result = veiculoDao.findOneByExample(new Veiculo(placa));
		return result;		
	}
	
	public Balanca getBalancaById(Long id){
		Balanca balanca = balancaDao.consultaPorId(id);
		return balanca;		
	}
	
	public Balanca getBalancaByVeiculo(Veiculo veiculo){
		Balanca balanca = balancaDao.getRegistroComTaraSemPesoCheio(veiculo);
		return balanca;		
	}
	
	public Balanca salvarBalanca(Balanca b){
		Balanca balanca = balancaDao.salvar(b);
		return balanca;		
	}

	public ConfigBalanca getConfigBalanca(String host) {
		return configDao.findByHost(host);		
	}
	
	public Turno getTurnoBalancaAtual(){
		return turnoDao.findTurnoByHora(Aplicacao.horaAtual());		
	}

	public Bl findProdutoIndefinido(long naoDefinido) {		
		return produtoDao.consultaPorId(naoDefinido);
	}
}
