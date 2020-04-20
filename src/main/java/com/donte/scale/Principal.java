package com.donte.scale;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.donte.scale.view.Login;

public class Principal {
	
	public static void lookAndFeel() {		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("ARIAL", Font.BOLD, 23)));
			UIManager.put("OptionPane.messageForeground", new Color(0,102,153));
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL", Font.PLAIN, 15)));        
			UIManager.put("ComboBox.background", new Color(255,255,102));
		} catch (Exception e){
			System.err.println(e.getMessage());
		}		
	}
		
	public static void main(String[] args){
		lookAndFeel();
		Login login = new Login();
		login.setVisible(true);
	}
	
//	public static void main(String[] args){
//		RelatorioUtil.containerTester(1);
//	}
}
