package com.donte.scale.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.donte.scale.model.Balanca;

public class Confirmacao extends JDialog {

	private static final long serialVersionUID = 1L;	
	private boolean confirmou = false;	
	private Balanca balanca;
	private JLabel controle;
	private JLabel estadia;
	private JLabel tara;
	private JLabel pesoCheio;
	private JLabel navio;
	private JLabel txtCliente;
	private JTextArea txtProduto;
	private JLabel placa;
	private JLabel porao;
	private JLabel pesoLiquido;
	private JLabel txtBalanca;

	public Confirmacao(Balanca balanca) {
		this.balanca = balanca;
		inicializar();
		preencherDados();
	}

	private void inicializar(){
		this.setBounds(100, 100, 469, 346);				
		this.setLocationRelativeTo(null);
		this.setModal(true);
		getContentPane().setLayout(new BorderLayout());		
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
	}

	public JPanel getMainPanel(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		Font fontLabel = new Font("Courier New", Font.BOLD, 13);
		Font fontDados = new Font("Tahoma", Font.BOLD, 13);

		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		titlePanel.setBounds(10, 11, 430, 42);		
		titlePanel.setLayout(null);

		JLabel label = new JLabel("DADOS DA PESAGEM");
		label.setBounds(10, 10, 394, 24);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		titlePanel.add(label);

		JPanel bodyPanel = new JPanel();
		bodyPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		bodyPanel.setBounds(10, 55, 430, 209);
		bodyPanel.setLayout(null);

		JLabel lblControle = new JLabel("CONTROLE:");
		lblControle.setFont(fontLabel);
		lblControle.setBounds(10, 3, 80, 20);
		bodyPanel.add(lblControle);

		JLabel lblPlaca = new JLabel("PLACA   :");
		lblPlaca.setFont(fontLabel);
		lblPlaca.setBounds(210, 3, 80, 20);
		bodyPanel.add(lblPlaca);

		JLabel lblEstadia = new JLabel("ESTADIA :");
		lblEstadia.setFont(fontLabel);
		lblEstadia.setBounds(10, 28, 80, 20);
		bodyPanel.add(lblEstadia);

		JLabel lblPorao = new JLabel("PORÃO   :");
		lblPorao.setFont(fontLabel);
		lblPorao.setBounds(210, 28, 80, 20);
		bodyPanel.add(lblPorao);

		JLabel lblTara = new JLabel("TARA    :");
		lblTara.setFont(fontLabel);
		lblTara.setBounds(10, 53, 80, 20);
		bodyPanel.add(lblTara);

		JLabel lblPesoLiquido = new JLabel("P. LIQ  :");
		lblPesoLiquido.setFont(fontLabel);
		lblPesoLiquido.setBounds(210, 53, 80, 20);
		bodyPanel.add(lblPesoLiquido);

		JLabel lblCliente = new JLabel("CLIENTE :");
		lblCliente.setFont(fontLabel);
		lblCliente.setBounds(10, 128, 80, 20);
		bodyPanel.add(lblCliente);

		JLabel lblPesoCheio = new JLabel("P. BRUTO:");
		lblPesoCheio.setFont(fontLabel);
		lblPesoCheio.setBounds(10, 78, 80, 20);
		bodyPanel.add(lblPesoCheio);

		JLabel lblBalanca = new JLabel("BALANÇA :");
		lblBalanca.setFont(fontLabel);
		lblBalanca.setBounds(210, 78, 80, 20);
		bodyPanel.add(lblBalanca);

		JLabel lblProduto = new JLabel("PRODUTO :");
		lblProduto.setFont(fontLabel);
		lblProduto.setBounds(10, 153, 80, 30);
		bodyPanel.add(lblProduto);

		JLabel lblNavio = new JLabel("NAVIO   :");
		lblNavio.setFont(fontLabel);
		lblNavio.setBounds(10, 103, 80, 20);
		bodyPanel.add(lblNavio);

		controle = new JLabel();
		controle.setForeground(Color.BLUE);
		controle.setFont(fontDados);
		controle.setBounds(89, 3, 111, 20);
		bodyPanel.add(controle);

		estadia = new JLabel();
		estadia.setForeground(Color.BLUE);
		estadia.setFont(fontDados);
		estadia.setBounds(89, 28, 111, 20);
		bodyPanel.add(estadia);

		tara = new JLabel();
		tara.setForeground(Color.BLUE);
		tara.setFont(fontDados);
		tara.setBounds(89, 53, 111, 20);
		bodyPanel.add(tara);

		placa = new JLabel();
		placa.setForeground(Color.BLUE);
		placa.setFont(fontDados);
		placa.setBounds(294, 3, 111, 20);
		bodyPanel.add(placa);

		porao = new JLabel();
		porao.setForeground(Color.BLUE);
		porao.setFont(fontDados);
		porao.setBounds(294, 28, 111, 20);
		bodyPanel.add(porao);

		pesoLiquido = new JLabel();
		pesoLiquido.setForeground(Color.BLUE);
		pesoLiquido.setFont(fontDados);
		pesoLiquido.setBounds(294, 53, 111, 20);
		bodyPanel.add(pesoLiquido);

		pesoCheio = new JLabel();
		pesoCheio.setForeground(Color.BLUE);
		pesoCheio.setFont(fontDados);
		pesoCheio.setBounds(89, 78, 111, 20);
		bodyPanel.add(pesoCheio);			

		txtBalanca = new JLabel();
		txtBalanca.setForeground(Color.BLUE);
		txtBalanca.setFont(fontDados);
		txtBalanca.setBounds(294, 78, 111, 20);
		bodyPanel.add(txtBalanca);

		navio = new JLabel();
		navio.setForeground(Color.BLUE);
		navio.setFont(fontDados);
		navio.setBounds(89, 103, 327, 20);
		bodyPanel.add(navio);

		txtCliente = new JLabel();
		txtCliente.setForeground(Color.BLUE);
		txtCliente.setFont(fontDados);
		txtCliente.setBounds(89, 128, 327, 20);
		bodyPanel.add(txtCliente);

		txtProduto = new JTextArea();		
		txtProduto.setWrapStyleWord(true);
		txtProduto.setLineWrap(true);
		txtProduto.setForeground(Color.BLUE);
		txtProduto.setFont(fontDados);
		txtProduto.setEditable(false);
		txtProduto.setBackground(SystemColor.menu);
		txtProduto.setBounds(89, 153, 331, 50);
		bodyPanel.add(txtProduto);

		JPanel footerPanel = new JPanel();
		footerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		footerPanel.setBounds(10, 266, 430, 35);		
		footerPanel.setLayout(null);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmou = false;
				dispose();
			}
		});
		btnCancelar.setBounds(10, 7, 89, 20);
		footerPanel.add(btnCancelar);

		JButton btnConfirmar = new JButton("SALVAR");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmou = true;
				dispose();
			}
		});
		btnConfirmar.setBounds(331, 6, 89, 20);
		footerPanel.add(btnConfirmar);

		mainPanel.add(titlePanel);
		mainPanel.add(footerPanel);
		mainPanel.add(bodyPanel);

		return mainPanel;
	}

	private void preencherDados(){
		try{				
			estadia.setText(balanca.getTipoEstadia().name());
			tara.setText(balanca.getTara().toString());
			placa.setText(balanca.getVeiculo().getPlaca());
			porao.setText(balanca.getPorao());
			pesoLiquido.setText(balanca.getPesoLiquido().toString());
			pesoCheio.setText(balanca.getPesoCheio().toString());
			txtBalanca.setText(balanca.getNumero());
			
			if(balanca.getId() != null){
				controle.setText(balanca.getId().toString());
			}	
			if(balanca.getBl() != null){
				navio.setText(balanca.getBl().getViagem().getNavio().getNome());
				txtCliente.setText(balanca.getBl().getCliente().getNome());
				txtProduto.setText(balanca.getBl().getDescricao());
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public boolean hasConfirm(){
		return confirmou;
	}
}
