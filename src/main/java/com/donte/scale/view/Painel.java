package com.donte.scale.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.donte.scale.controle.PesagemController;
import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.ConfigBalanca;
import com.donte.scale.model.Usuario;
import com.donte.scale.util.Aplicacao;
import com.donte.scale.util.SerialBalanca;

public class Painel extends JFrame {

	private static final long serialVersionUID = 1L;

	private PesagemController controller = new PesagemController(this);
	private Usuario usuario = null;
	private ConfigBalanca config = null;
	private JTextField txtPlaca;
	private JTextField txtControle;
	private JTextField txtPesagem;

	private JLabel msgSistema = new JLabel();
	private JComboBox<?> cmbBl;
	private JComboBox<?> cmbViagem;
	private JComboBox<?> cmbEstadia;
	private JComboBox<?> cmbPorao;
	private JButton btnGravar;
	private Timer timerMessage;
	private Timer timerCaptura;
	private SerialBalanca serial;
	private Integer pesoCapturado;
	private JTextField txtCliente;
	private JTextField txtTara;
	private JTextField txtPesoLiquido;
	private JTextField txtPesoCheio;
	private JButton btnCapturar;

	public Painel(Usuario user) {
		loadConfiguracoes(user);		
		initialize();				
		initCapturaPeso();		
	}

	private void initialize() {		
		setResizable(false);
		setTitle("PESAGEM BALANÇA - USUÁRIO: " + usuario.getLogin().toUpperCase());
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/s2gpi-truck.png")));		
		setBounds(100, 100, 860, 430);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JPanel panelHead = new JPanel();
		panelHead.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelHead.setBounds(12, 14, 830, 63);
		getContentPane().add(panelHead);
		panelHead.setLayout(new BorderLayout(0, 0));

		msgSistema.setForeground(new Color(178, 34, 34));
		msgSistema.setHorizontalAlignment(SwingConstants.CENTER);
		msgSistema.setFont(new Font("Tahoma", Font.BOLD, 17));
		panelHead.add(msgSistema);

		JPanel panelBody = new JPanel();
		panelBody.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelBody.setBounds(12, 88, 830, 245);
		getContentPane().add(panelBody);
		panelBody.setLayout(null);

		JLabel lblPlaca = new JLabel("PLACA :");
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlaca.setBounds(14, 10, 100, 30);
		panelBody.add(lblPlaca);

		JLabel lblEstadia = new JLabel("ESTADIA :");
		lblEstadia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEstadia.setBounds(14, 50, 100, 30);
		panelBody.add(lblEstadia);

		JLabel lblCodRef = new JLabel("Nº CONTROLE :");
		lblCodRef.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodRef.setBounds(300, 10, 100, 30);
		panelBody.add(lblCodRef);

		JLabel lblPesagem = new JLabel("PESAGEM :");
		lblPesagem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesagem.setBounds(567, 10, 110, 30);
		panelBody.add(lblPesagem);

		JLabel lblPorao = new JLabel("PORÃO :");
		lblPorao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPorao.setBounds(300, 50, 90, 30);
		panelBody.add(lblPorao);

		JLabel lblViagem = new JLabel("VIAGEM :");
		lblViagem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblViagem.setBounds(14, 90, 100, 30);
		panelBody.add(lblViagem);

		JLabel lblCliente = new JLabel("CLIENTE :");
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCliente.setBounds(14, 130, 100, 30);
		panelBody.add(lblCliente);

		cmbBl = new JComboBox<>();
		cmbBl.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				controller.atualizarClienteNaTela();
			}
		});
		cmbBl.setBounds(100, 170, 711, 30);
		panelBody.add(cmbBl);

		cmbViagem = new JComboBox<>();
		cmbViagem.setBounds(100, 90, 454, 30);
		cmbViagem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				controller.loadProdutos(cmbViagem.getSelectedItem());			
			}
		});
		panelBody.add(cmbViagem);

		cmbEstadia = new JComboBox<>();
		cmbEstadia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bloquearReferencia();
			}
		});
		cmbEstadia.setBounds(100, 50, 150, 30);
		panelBody.add(cmbEstadia);

		cmbPorao = new JComboBox<>();		
		cmbPorao.setBounds(404, 52, 150, 30);
		panelBody.add(cmbPorao);

		txtPlaca = new JTextField();
		txtPlaca.setBounds(100, 10, 150, 30);
		txtPlaca.setFont(new Font("Tahoma", Font.BOLD, 22));
		txtPlaca.setForeground(Color.BLUE);
		txtPlaca.setColumns(8);
		txtPlaca.setDocument(Aplicacao.upperCaseField(7));
		panelBody.add(txtPlaca);

		txtControle = new JTextField();
		txtControle.setEditable(false);
		txtControle.setBounds(404, 10, 150, 30);		
		txtControle.setDocument(Aplicacao.numberField(6));
		panelBody.add(txtControle);

		txtPesagem = new JTextField();
		txtPesagem.setBackground(Color.WHITE);
		txtPesagem.setForeground(Color.RED);
		txtPesagem.setEditable(false);
		txtPesagem.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPesagem.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtPesagem.setBounds(681, 10, 130, 30);			
		txtPesagem.setText("0");		

		panelBody.add(txtPesagem);

		JLabel lblProduto01 = new JLabel("PRODUTO :");
		lblProduto01.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProduto01.setBounds(14, 170, 100, 30);
		panelBody.add(lblProduto01);

		txtCliente = new JTextField();		
		txtCliente.setEditable(false);
		txtCliente.setBounds(100, 130, 454, 30);
		panelBody.add(txtCliente);

		JLabel lblTara = new JLabel("TARA :");
		lblTara.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTara.setBounds(567, 50, 110, 30);
		panelBody.add(lblTara);

		JLabel lblPesoLquido = new JLabel("PESO LÍQUIDO :");
		lblPesoLquido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesoLquido.setBounds(567, 90, 110, 30);
		panelBody.add(lblPesoLquido);

		JLabel lblPesoBruto = new JLabel("PESO BRUTO :");
		lblPesoBruto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesoBruto.setBounds(567, 130, 110, 30);
		panelBody.add(lblPesoBruto);

		txtTara = new JTextField();
		txtTara.setText("0");
		txtTara.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTara.setForeground(Color.BLUE);
		txtTara.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtTara.setEditable(false);
		txtTara.setBackground(Color.WHITE);
		txtTara.setBounds(681, 50, 130, 30);

		txtTara.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				checaPesoInicial();
			}			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				checaPesoInicial();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				checaPesoInicial();
			}	
		});

		panelBody.add(txtTara);

		txtPesoLiquido = new JTextField();
		txtPesoLiquido.setText("0");
		txtPesoLiquido.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPesoLiquido.setForeground(Color.BLUE);
		txtPesoLiquido.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtPesoLiquido.setEditable(false);
		txtPesoLiquido.setBackground(Color.WHITE);
		txtPesoLiquido.setBounds(681, 90, 130, 30);
		panelBody.add(txtPesoLiquido);

		txtPesoCheio = new JTextField();
		txtPesoCheio.setText("0");
		txtPesoCheio.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPesoCheio.setForeground(Color.BLUE);
		txtPesoCheio.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtPesoCheio.setEditable(false);
		txtPesoCheio.setBackground(Color.WHITE);
		txtPesoCheio.setBounds(681, 130, 130, 30);
		panelBody.add(txtPesoCheio);

		btnCapturar = new JButton("CAPTURAR");
		btnCapturar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.capturarPesagem();
			}
		});
		btnCapturar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCapturar.setBounds(567, 210, 245, 30);
		panelBody.add(btnCapturar);

		JPanel panelFooter = new JPanel();
		panelFooter.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelFooter.setBounds(12, 340, 830, 50);
		getContentPane().add(panelFooter);
		panelFooter.setLayout(null);

		JButton btnFechar = new JButton("FECHAR");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questaoFecharFrame();
			}
		});		
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFechar.setBounds(5, 10, 140, 30);
		panelFooter.add(btnFechar);

		JButton btnImprimir = new JButton("IMPRIMIR");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.imprimirByController();
			}
		});		
		btnImprimir.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnImprimir.setBounds(515, 10, 140, 30);
		panelFooter.add(btnImprimir);

		btnGravar = new JButton("GRAVAR");		
		btnGravar.setEnabled(false);
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.salvarPesagem();
			}
		});
		btnGravar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnGravar.setBounds(685, 10, 140, 30);
		panelFooter.add(btnGravar);

		JButton btnTaraManual = new JButton("TARA MANUAL");
		btnTaraManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				capturarTaraManual();
			}
		});
		btnTaraManual.setEnabled(usuario.getLogin().equals("ccoemap"));
		btnTaraManual.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTaraManual.setBounds(345, 10, 140, 30);
		panelFooter.add(btnTaraManual);

		JButton btnLimpar = new JButton("LIMPAR");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.limparTela();
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLimpar.setBounds(172, 10, 140, 30);
		panelFooter.add(btnLimpar);
		addWindowListener(new WindowAdapter() {	public void windowClosing(WindowEvent evt) { questaoFecharFrame();} });		
		controller.carregarCombos();
		initMensagemSistema();
	}		

	private void initMensagemSistema(){		
		timerMessage = new Timer();		
		timerMessage.schedule(new TimerTask() {			
			public void run() {
				if (msgSistema.isVisible() == true) {  
					msgSistema.setVisible(false);  
				}else{  
					msgSistema.setVisible(true);  
				}    				
			}
		},0, 600);
	}

	private void loadConfiguracoes(Usuario user){			
		config = controller.getConfiguracaoBalanca(Aplicacao.getMacHost());		
		if(config == null){
			Aplicacao.errorMsg(this, "Configuração de balança não encontrada");
			System.exit(0);
		}		
		serial = new SerialBalanca(config.getModelo(), config.getBaudrate(), config.getPorta());
		setUsuario(user);
	}

	private void initCapturaPeso(){		
		timerCaptura = new java.util.Timer();
		timerCaptura.schedule(new ProcessaCaptura(),0,600);
	}

	class ProcessaCaptura extends TimerTask {		
		public void run() {
			try {
				Integer peso = serial.capturaPesoNew();
				if (peso != null)	pesoCapturado = peso;				
				verificaDadosCaptura(pesoCapturado);										
			} catch (Exception e) {	
				pesoCapturado = new Integer(0);
				verificaDadosCaptura(-1);									
			} finally{
				txtPesagem.setText(String.format("%05d", pesoCapturado));
			}
		}
	}

	private void verificaDadosCaptura(int peso){
		if(peso > 0){			
			btnCapturar.setEnabled(true);
			msgSistema.setText("REALIZANDO PESAGEM");
		}else if(peso == 0){			
			btnCapturar.setEnabled(false);
			msgSistema.setText("AGUARDANDO PESAGEM");
		}else{			
			btnCapturar.setEnabled(false);		
			msgSistema.setText("ERRO AO CONECTAR COM A BALANÇA");	
		}				
	}

	protected void questaoFecharFrame(){
		if (JOptionPane.showConfirmDialog(null,"Tem certeza que deseja fechar a aplicação?")==JOptionPane.OK_OPTION){
			fecharFrame();
		}
	}

	protected void fecharFrame(){
		try {			
			if ( timerMessage != null) timerMessage.cancel();
			if ( timerCaptura != null) timerCaptura.cancel();
			JPAUtil.shutdownEntityManagerFactory();
			System.exit(0);
		} catch (Exception ex){
			System.err.println(ex.getMessage());
		}			
	}

	private void checaPesoInicial(){		
		try {
			Integer peso = Integer.parseInt(txtTara.getText());
			if (peso > 0){
				btnGravar.setEnabled(true);
			} else {
				btnGravar.setEnabled(false);
			}
		} catch(Exception e){
			btnGravar.setEnabled(false);
		}
	}

	protected void capturarTaraManual() {		
		JTextField txtPlacaManual = new JTextField(6);
		txtPlacaManual.setDocument(Aplicacao.upperCaseField(7));		
		JTextField txtTaraManual = new JTextField(6);
		txtTaraManual.setDocument(Aplicacao.numberField(6));

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("PLACA:"));
		myPanel.add(txtPlacaManual);
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		myPanel.add(new JLabel("TARA:"));
		myPanel.add(txtTaraManual);

		ImageIcon icon = new ImageIcon(getClass().getResource("/images/s2gpi-truck.png"));

		int result = JOptionPane.showConfirmDialog(this, myPanel, "Entre com a placa do veículo e a tara", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE, icon);
		
		if (result == JOptionPane.OK_OPTION) {
			controller.pesarTaraManual(txtPlacaManual.getText(), txtTaraManual.getText());			
		}

	}

	public JTextField getTxtPlaca() {
		return txtPlaca;
	}

	public JTextField getTxtControle() {
		return txtControle;
	}

	public JTextField getTxtPesagem() {
		return txtPesagem;
	}

	public JComboBox<?> getCmbBl() {
		return cmbBl;
	}

	public JComboBox<?> getCmbViagem() {
		return cmbViagem;
	}

	public JComboBox<?> getCmbEstadia() {
		return cmbEstadia;
	}

	public void setCmbEstadia(JComboBox<?> cmbEstadia) {
		this.cmbEstadia = cmbEstadia;
	}

	public JComboBox<?> getCmbPorao() {
		return cmbPorao;
	}

	public void setCmbTipo(JComboBox<?> cmbTipo) {
		this.cmbPorao = cmbTipo;
	}

	public String getNumeroBalanca() {
		return config.getBalanca(); //txtNumBal.getText();
	}		

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setNomeCliente(String nome){
		txtCliente.setText(nome);
	}

	public JTextField getTxtTara() {
		return txtTara;
	}

	public JTextField getTxtPesoLiquido() {
		return txtPesoLiquido;
	}

	public JTextField getTxtPesoCheio() {
		return txtPesoCheio;
	}

	public ConfigBalanca getConfig() {
		return config;
	}
}
