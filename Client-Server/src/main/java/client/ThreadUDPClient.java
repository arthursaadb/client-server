package client;

import operacao.Requisicao;
import port.RecoverPort;
import processamento.DadosResposta;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

public class ThreadUDPClient extends Thread {
	DatagramSocket clientSocket = null;
	Requisicao data;
	String crud;
	BigInteger key;
	String mensagem;
	DadosResposta respost;
	
	public ThreadUDPClient(String crud, BigInteger key, String mensagem) {
		this.crud = crud;
		this.key = key;
		this.mensagem = mensagem;
	}
	
	@Override
	public void run() {
		Scanner s = new Scanner(System.in);
		Requisicao data = new Requisicao(crud,key,mensagem);
		
		//Recuperando a porta do arquivo properties
		RecoverPort recover = new RecoverPort();
		int port = recover.recover();
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	    ObjectOutputStream objStream = null;
		try {
			objStream = new ObjectOutputStream(byteStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objStream.writeObject(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			objStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			objStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    byte[] sendData = byteStream.toByteArray();
	    byte[] receiveData = new byte[1400];
	    try {
	    	if(sendData.length <= 1400) {
				clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
				clientSocket.send(sendPacket);
	    	}
	    	else
	    		System.out.println("Digite novamente a mensagem, tamanho inv�lido");
			
			
			while(true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
				clientSocket.receive(receivePacket);
			    byte[] process = receivePacket.getData();
		 		ByteArrayInputStream in = new ByteArrayInputStream(process);
				try {
					ObjectInputStream is = new ObjectInputStream(in);
					respost = (DadosResposta) is.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(respost.getMensagem());
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}
		




