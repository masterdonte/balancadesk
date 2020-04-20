package com.donte.scale.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingWorker;

import com.donte.scale.model.Balanca;
import com.donte.scale.model.ConfigBalanca;

public class SplashPrinting extends JWindow{

	private static final long serialVersionUID = 1L;

	private JLabel imagem = null;
	private ImageIcon figura = null;

	public SplashPrinting() {
		figura = new ImageIcon(getClass().getResource("/images/printing.gif"));
		imagem = new JLabel(figura);
		imagem.setBorder(BorderFactory.createLineBorder(Color.blue, 1));		
		getContentPane().add(imagem);		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		if (size.width > screenSize.width)	 size.width = screenSize.width;
		if (size.height > screenSize.height) size.height = screenSize.height;	
		this.setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
		this.pack();
		this.setVisible(true);
	}

	public void descarregarTela(){
		this.setVisible(false);
		this.dispose();
	}

	@SuppressWarnings("rawtypes")
	public void preparePrint(final Balanca balanca, final ConfigBalanca config) {
		SwingWorker worker = new SwingWorker() {
			@Override
			protected Object doInBackground() {				
				RelatorioUtil relatorio = new RelatorioUtil(balanca, config);
				relatorio.imprimirCopias();
				return relatorio;
			}
			@Override
			protected void done() {
				descarregarTela();							
			}
		};
		worker.execute();
	}

}