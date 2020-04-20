package com.donte.scale.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import org.hibernate.internal.SessionImpl;

import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.Balanca;
import com.donte.scale.model.ConfigBalanca;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioUtil {

	public static final String RELATORIO = "/relatorios/bilhete.jasper";	
	private Connection connection;
	private EntityManager manager;
	
	private Balanca balanca;
	private ConfigBalanca config;

	public RelatorioUtil(Balanca balanca, ConfigBalanca config) {
		this.balanca = balanca;
		this.config = config;
	}

	private void criarConexao() throws Exception {
		manager = JPAUtil.getEntityManager();	
		connection = manager.unwrap(SessionImpl.class).connection();		
	}

	private void fecharConexao(){
		try {			
			connection.close();
			/*if(manager.isOpen()){						
				manager.close();
			}*/
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	private void diretoImpressora(PrintService impressora){		
		try {									
			InputStream in = RelatorioUtil.class.getResourceAsStream(RELATORIO);
			JasperPrint jp = JasperFillManager.fillReport(in, getParamsReport(), connection);													
			PrintRequestAttributeSet attSet = new HashPrintRequestAttributeSet(); 						        	       	  
			attSet.add(new Copies(1));	     						        
			JRPrintServiceExporter exporter = new JRPrintServiceExporter ();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);  
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, attSet);  
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, impressora.getAttributes()); 			 
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,  Boolean.FALSE);  
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);		
			exporter.exportReport();
		} catch (Exception e) {										
			Aplicacao.errorMsg(null, "Falha ao imprimir bilhete!");
			System.err.println(e.getMessage());		
			e.printStackTrace();		
		} 	
	}

	public void imprimirCopias(){
		PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();
		if (impressora == null) throw new RuntimeException("Impressora não encontrada !");
		try {
			criarConexao();			
			for(int i = 0 ; i < config.getCopias() ; i ++){
				diretoImpressora(impressora);
				Thread.sleep(1000);
			}			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			Aplicacao.errorMsg(null, "Erro ao gerar conexão do banco para o relatório.");
		}finally{
			fecharConexao();
		}	
	}
	
	private Map<String,Object> getParamsReport(){		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ID_BALANCA",  balanca.getId().intValue());			
		params.put("IMG_LOGO"  ,  getClass().getResourceAsStream("/images/novo-logo.png"));	
		params.put("IMG_S2GPI" ,  getClass().getResourceAsStream("/images/logo_s2gpi.png"));	
		//params.put("IMG_QRCODE" , getClass().getResourceAsStream("/images/qrcode.jpg"));	
		params.put("IMG_QRCODE", balanca.getQrCodeBalanca());			
		return params;				
	}
	
	public static void containerTester(Integer controle) {
		EntityManager emf = null;
		Connection conexao = null;
		try {
			emf = JPAUtil.getEntityManager();
			conexao = emf.unwrap(SessionImpl.class).connection();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ID_BALANCA", controle);			
			params.put("IMG_LOGO"  , RelatorioUtil.class.getResourceAsStream("/images/novo-logo.png"));	
			params.put("IMG_S2GPI" , RelatorioUtil.class.getResourceAsStream("/images/logo_s2gpi.png"));	
			params.put("IMG_QRCODE", RelatorioUtil.class.getResourceAsStream("/images/qrcode.jpg"));						
			InputStream  stream  = RelatorioUtil.class.getResourceAsStream(RELATORIO);			
			JasperPrint  printer = JasperFillManager.fillReport(stream, params, conexao);
			JasperViewer viewer  = new JasperViewer(printer, false);
			viewer.setTitle("SISTEMA BALANÇA");
			viewer.setVisible(true);
		} catch (Exception e){
			System.err.println(e.getMessage());			
			Aplicacao.errorMsg(null, "Falha ao imprimir bilhete!");
		} finally {
			try {			
				conexao.close();
				/*if(emf.isOpen()){
					System.out.println("A berto evai ser fechado");					
					emf.close();
				}else{
					System.out.println("Ja estava fechado");		
				}*/
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}	
	}

}
