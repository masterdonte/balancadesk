package com.donte.scale.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;

import com.donte.scale.model.Usuario;
import com.donte.scale.service.SistemaService;
import com.donte.scale.util.Aplicacao;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;	
	private JTextField txtLogin;
	private JPasswordField txtPass;
	private JButton btnLogin;
	private JButton btnCancel;

	public Login() {
		setResizable(false);		
		initialize();
	}
	
	private void initialize() {		
		getContentPane().setLayout(null);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/images/s2gpi-truck.png")));
		
		JLabel lblLogin = new JLabel("LOGIN :");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setFont(new Font("Courier New", Font.BOLD, 16));
		lblLogin.setBounds(28, 74, 110, 20);
		getContentPane().add(lblLogin);

		JLabel lblPass = new JLabel("PASSWORD :");
		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setFont(new Font("Courier New", Font.BOLD, 16));
		lblPass.setBounds(28, 111, 110, 20);
		getContentPane().add(lblPass);

		txtLogin = new JTextField();
		txtLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtLogin.setBounds(142, 72, 225, 25);
		getContentPane().add(txtLogin);
		//txtLogin.addActionListener(new TrataEventoBotao());
		txtLogin.setColumns(10);

		txtPass = new JPasswordField();
		txtPass.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPass.setBounds(142, 110, 225, 25);
		getContentPane().add(txtPass);
		//txtPass.addActionListener(new TrataEventoBotao());
		txtPass.addKeyListener(new TrataEventoEnter());

		btnCancel = new JButton("FECHAR");
		btnCancel.addActionListener(new TrataEventoBotao());
		btnCancel.setForeground(Color.RED);
		btnCancel.setFont(new Font("Arial Black", Font.BOLD, 12));
		btnCancel.setBounds(28, 174, 110, 30);
		getContentPane().add(btnCancel);

		btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new TrataEventoBotao());
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setFont(new Font("Arial Black", Font.BOLD, 12));
		btnLogin.setBounds(268, 174, 100, 30);
		getContentPane().add(btnLogin);

		JLabel lblTitle = new JLabel("SISTEMA BALANÇA");
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitle.setBounds(10, 22, 390, 36);
		getContentPane().add(lblTitle);	
		setBounds(100, 100, 404, 261);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	class TrataEventoEnter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				efetuarLogin();
			}			
		}		
	}
	
	class TrataEventoBotao implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			if (e.getSource() != btnCancel){
				efetuarLogin();	
			}else{				
				System.exit(0);
			}			
		}
	}

	protected void efetuarLogin() {
		try {
			String usuario = txtLogin.getText().toLowerCase();
			String senha = new String(txtPass.getPassword());
			if(StringUtils.isBlank(usuario))throw new Exception("Informe o campo usuário.");
			if(StringUtils.isBlank(senha))  throw new Exception("Informe o campo senha.");			
			Usuario user = SistemaService.getInstance().autenticar(usuario, senha);			
			if(user == null) 
				throw new Exception("USUÁRIO E/OU SENHA INVÁLIDOS");						
			this.dispose();				
			Painel main = new Painel(user);		
			main.setVisible(true);							
		} catch (Exception e) {			
			Aplicacao.errorMsg(this, e.getMessage());			
		}

	}
}
