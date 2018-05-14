package client;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient{
	public static void main(String args[]) throws Exception{
	
 		//Pegando o ip do servidor
 		InetAddress IPAddress = InetAddress.getByName("localhost");
 		
		String data = null;
 		//Classe para ler do teclado
 		Scanner s = new Scanner(System.in);
 		
		while(true) {
 			System.out.println("Bem-vindo cliente - Qual opera��o voc� deseja fazer? CREATE - READ - UPDATE - DELETE - REGISTRO");
 			String comando = s.nextLine();
 			if(comando.equals("READ") || comando.equals("DELETE") || comando.equals("REGISTRO")){
 				System.out.println("Digite de que chave voc� gostaria de realizar a opera��o");
 				BigInteger chave = new BigInteger(s.nextLine());
 				new ThreadUDPClient(comando,chave,"").start();;
 			}
 			else {
 				System.out.println("Digite a chave que voc� gostaria de colocar");
 				BigInteger chave = new BigInteger(s.nextLine());
 				System.out.println("Digite a mensagem que voc� gostaria de mandar");
 				String mensagem = s.nextLine();
 				new ThreadUDPClient(comando,chave,mensagem).start();;
 			}
		
 		}
	}
 }
