package com.donte.scale.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.donte.scale.model.enums.TipoBalanca;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;


public class SerialBalanca {

	private Integer baudrate;
	private String porta;
	private SerialPort serialPort;
	private TipoBalanca tipoBalanca;	

	public SerialBalanca(TipoBalanca tipoBalanca,Integer baudrate, String porta) {
		this.baudrate = baudrate;
		this.porta = porta;
		this.serialPort = new SerialPort(this.porta);
		this.tipoBalanca = tipoBalanca;			
	}

	public boolean checaPorta(){
		try {
			serialPort.openPort();
			serialPort.closePort();
			return true;
		} catch (SerialPortException e){
			return false;
		} 
	}

	public Integer capturaPesoNew() throws SerialPortException, SerialPortTimeoutException{
		Integer resultado = null;
		try {
			serialPort.openPort();           
			serialPort.setParams(baudrate, 8, 1, 0);
			byte[] buffer = serialPort.readBytes(10,3000);       							
			String leitura = new String(buffer); 

			if(tipoBalanca == TipoBalanca.LIDER){
				int p1 =  leitura.lastIndexOf(".");
				String v1 = leitura.substring(p1 - 6, p1).trim();
				resultado = Integer.valueOf(v1);
			}else{
				int p2 = leitura.lastIndexOf("*");
				if(p2 == 1){
					String v2 = leitura.substring(4, 10).trim();
					resultado = Integer.valueOf(v2);
				}
			}
			
		} catch (NumberFormatException | StringIndexOutOfBoundsException | NullPointerException | ArrayIndexOutOfBoundsException ex){			
			System.err.println("Exception em serial : " + ex.getMessage());
			resultado = null;
		}finally{
			serialPort.closePort();			
		}
		return resultado;
	}

	public static List<String> getListaPortas(){
		String[] portNames = SerialPortList.getPortNames();
		return new ArrayList<String>(Arrays.asList(portNames));
	}
	
//	private Integer getBufferBalanca(TipoBalanca tipo){
//		if(tipo == TipoBalanca.LIDER){
//			int p1 =  leitura.lastIndexOf(".");
//			String v1 = leitura.substring(p1 - 6, p1).trim();
//			return Integer.valueOf(v1);
//		}else{
//			int p2 = leitura.lastIndexOf("*");
//			if(p2 != 1) return null;
//			String v2 = leitura.substring(4, 10).trim();
//			return Integer.valueOf(v2);						    		
//		}
//	}
}