package com.donte.scale.controle;

import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.donte.scale.model.Balanca;
import com.donte.scale.model.Bl;
import com.donte.scale.model.ConfigBalanca;
import com.donte.scale.model.Veiculo;
import com.donte.scale.model.Viagem;
import com.donte.scale.model.enums.TipoEstadia;
import com.donte.scale.service.SistemaService;
import com.donte.scale.util.Aplicacao;
import com.donte.scale.util.SplashPrinting;
import com.donte.scale.view.Confirmacao;
import com.donte.scale.view.Painel;

public class PesagemController {		
	
	private final static String[] poroes = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	private SistemaService service = SistemaService.getInstance();
			
	private Balanca balanca = null;
	private Painel painel = null;
	
	public PesagemController(Painel painel) {
		this.painel = painel;
	}
	
	public void capturarPesagem(){		
		limparTelaPreCaptura();		
		Integer peso = new Integer(painel.getTxtPesagem().getText());
		validarCapturaDados(peso);		
	}

	private void validarCapturaDados(Integer peso) {		
		Veiculo veiculo = service.findVeiculoByPlaca(painel.getTxtPlaca().getText());
		
		if(veiculo == null){
			Aplicacao.errorMsg(painel, "Veículo não encontrado.");
			return;
		}
		
		balanca = service.getBalancaByVeiculo(veiculo);
					
		if(balanca == null){
			balanca = new Balanca();
			balanca.preencherDadosTara(veiculo, peso, Boolean.FALSE, painel.getUsuario().getLogin());
			painel.getTxtTara().setText(String.valueOf(peso));
		}else{				
			Integer pesoLiquido = Math.abs(peso - balanca.getTara());
			balanca.setPesoCheio(peso);
			balanca.setPesoLiquido(pesoLiquido);								
			painel.getTxtControle().setText(balanca.getId().toString());
			painel.getTxtTara().setText(String.valueOf(balanca.getTara()));
			painel.getTxtPesoCheio().setText(String.valueOf(peso));
			painel.getTxtPesoLiquido().setText(String.valueOf(pesoLiquido));
		}		
	}
	
	public void salvarPesagem(){
		try {
			validarPesagem();
			Confirmacao dialog = new Confirmacao(balanca);
			dialog.setVisible(true);
			if(dialog.hasConfirm()){
				service.salvarBalanca(balanca);
				if(balanca.getPesoCheio() > 0) imprimir();
				else Aplicacao.infoMsg(painel, "Tara registrada com sucesso.");				
				limparTela();
			}
			dialog.dispose();
		} catch (Exception e) {
			Aplicacao.errorMsg(painel,  e.getMessage());
		}		
	}

	private void validarPesagem() throws Exception {		
		if(balanca == null) throw new Exception("Código de balança não encontrado");
		if(balanca.getPesoCheio() == 0 ){
			balanca.setLoginTara(painel.getUsuario().getLogin());
			balanca.setDataTara(new Date());
			balanca.setBl(service.findProdutoIndefinido(Bl.NAO_DEFINIDO));
		}else{
			Bl bl = (Bl) painel.getCmbBl().getSelectedItem();
			if(bl.equals(Bl.naoDefinido())) throw new Exception("BL não definido");
			if(balanca.getPesoLiquido() == 0) throw new Exception("Peso liquido não pode ser zero");
			balanca.setBl(bl);
			balanca.setProduto(balanca.getBl().getDescricao());
			balanca.setLoginCheio(painel.getUsuario().getLogin());
			balanca.setDataPeso(new Date());
			balanca.setNumero(painel.getNumeroBalanca()); 
			balanca.setPorao(painel.getCmbPorao().getSelectedItem().toString());
			balanca.setTurno(service.getTurnoBalancaAtual());
		}
	}


	private void imprimir(){
		SplashPrinting splash = new SplashPrinting();
		splash.preparePrint(balanca, painel.getConfig());
	}
	
	public void imprimirByController(){		
		try{
			String valor = JOptionPane.showInputDialog(painel, "Informe o número de controle:");
			if(valor == null) return;
			long codigo = Integer.parseInt(valor);
			balanca = service.getBalancaById(codigo);
			if(balanca == null) throw new NumberFormatException("Número de controle inválido");
			imprimir();
		}catch(NumberFormatException ex){
			Aplicacao.errorMsg(painel, ex.getMessage());
		}	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void carregarCombos(){		
		painel.getCmbEstadia().setModel(new DefaultComboBoxModel(TipoEstadia.values()));
		painel.getCmbPorao().setModel(new DefaultComboBoxModel(poroes));
		painel.getCmbViagem().setModel(new DefaultComboBoxModel(service.carregarViagens().toArray()));
		loadProdutos(painel.getCmbViagem().getSelectedItem());
		bloquearReferencia();		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadProdutos(Object obj) {
		Viagem viagem = (Viagem) obj;		
		painel.getCmbBl().setModel(new DefaultComboBoxModel(service.carregarProdutos(viagem).toArray()));
		atualizarClienteNaTela();
	}

	public void bloquearReferencia() {
		TipoEstadia tipoEstadia = (TipoEstadia) painel.getCmbEstadia().getSelectedItem();	
		if(tipoEstadia == TipoEstadia.AVULSO){
			painel.getCmbViagem().setEnabled(false);
			painel.getCmbBl().setEnabled(false);
		}else{			
			painel.getCmbViagem().setEnabled(true);
			painel.getCmbBl().setEnabled(true);
		}		
	}

	public ConfigBalanca getConfiguracaoBalanca(String host) {
		return service.getConfigBalanca(host);
	}

	public void atualizarClienteNaTela() {
		Bl produto = (Bl)painel.getCmbBl().getSelectedItem();
		painel.setNomeCliente(produto.getCliente().getNome());
	}
	
	public void limparTelaPreCaptura() {
		painel.getTxtTara().setText("0");
		painel.getTxtPesoCheio().setText("0");
		painel.getTxtPesoLiquido().setText("0");		
	}
	
	public void limparTela() {
		balanca = null;
		painel.getCmbPorao().setSelectedIndex(0);
		painel.getTxtControle().setText("");
		painel.getTxtPlaca().setText("");
		limparTelaPreCaptura();
	}

	public void pesarTaraManual(String txtPlaca, String txtTara) {	
		Veiculo veiculo = service.findVeiculoByPlaca(txtPlaca);		
		
		if(veiculo == null){
			Aplicacao.errorMsg(painel, "Veículo não encontrado.");
			return;
		}
		
		int tara = 0;
		
		try{		
			tara = Integer.parseInt(txtTara);
			if(tara == 0) throw new NumberFormatException();
		}catch(NumberFormatException e){
			Aplicacao.errorMsg(painel, "Valor de tara inválida.");
			return;
		}

		balanca = service.getBalancaByVeiculo(veiculo);
		
		if(balanca != null){
			Aplicacao.errorMsg(painel, "Já existe um registro desse veículo com tara. Não é possivel gerar tara manual");
			return;
		}
		
		balanca = new Balanca();						
		balanca.preencherDadosTara(veiculo, tara, Boolean.TRUE, painel.getUsuario().getLogin());				
		
		try{
			service.salvarBalanca(balanca);		
			Aplicacao.infoMsg(painel, "Tara manual registrada com sucesso");
		}catch(Exception e){
			Aplicacao.infoMsg(painel, "Erro ao gerar tara manual\n"+ e.getMessage());
		}
		
	}

}
