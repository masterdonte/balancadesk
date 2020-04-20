package com.donte.scale.util;

import java.awt.Component;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Aplicacao {

	public static void errorMsg(Component parent, String msg) {
		JOptionPane.showMessageDialog(parent, msg, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public static void infoMsg(Component parent, String msg) {
		JOptionPane.showMessageDialog(parent, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String MD5(String s) {
		return gerarHash(s, "MD5");
	}

	public static String SHA256(String s) {	
		return gerarHash(s, "SHA-256");	// SHA-1 // SHA-256
	}

	private static String gerarHash(String frase, String algoritmo) {
		try {
			MessageDigest md = MessageDigest.getInstance(algoritmo);
			md.update(frase.getBytes());
			return stringHexa(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			int parteBaixa = bytes[i] & 0xf;
			if (parteAlta == 0)
				s.append('0');
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		return s.toString();
	}

	public static String getMacHost(){
		String result = null;
		try {         
			InetAddress address = InetAddress.getLocalHost();  
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);  
			byte[] mac = ni.getHardwareAddress();
			String macAddress = "";
			for (int i = 0; i < mac.length; i++) {             
				macAddress += (String.format("%02X-", mac[i]));  
			}		
			result =  macAddress.substring(0, macAddress.length()-1);
		} catch (UnknownHostException e) {  
			e.printStackTrace();
		} catch (SocketException e) {  
			e.printStackTrace();  
		}
		return result;
	}
	
	public static String horaAtual() {
		Calendar c = Calendar.getInstance();
		String horas   = String.format("%02d", c.get(Calendar.HOUR_OF_DAY));
		String minutos = String.format("%02d", c.get(Calendar.MINUTE));	
		return horas + ":" + minutos;
	}
	
	public static PlainDocument numberField(final int limit) {
		PlainDocument doc = new PlainDocument() {
			private static final long serialVersionUID = 1L;

			@Override
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				for (int i = 0; i < str.length(); i++)
					if (Character.isDigit(str.charAt(i)) == false)
						return;
				if (limit <= 0) // aceitara qualquer no. de caracteres
				{
					super.insertString(offs, str, a);
					return;
				}

				int ilen = (getLength() + str.length());
				if (ilen <= limit) // se o comprimento final for menor...
					super.insertString(offs, str, a); // ...aceita str
			}
		};
		return doc;
	}
	
	public static PlainDocument upperCaseField(final int limit) {
		PlainDocument doc = new PlainDocument() {
			private static final long serialVersionUID = 1L;
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null) return;
				if (limit <= 0){
					super.insertString(offs, str.toUpperCase(), a);
					return;
				}
				int ilen = (getLength() + str.length());
				if (ilen <= limit)  
					super.insertString(offs, str.toUpperCase(), a); 
			}
		};
		return doc;
	}
	
}
