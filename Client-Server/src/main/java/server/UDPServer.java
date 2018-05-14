package server;
import port.RecoverPort;
import processamento.DadosCliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class UDPServer {
    static LinkedBlockingQueue<DadosCliente> filaComandos = new LinkedBlockingQueue<>();
    static Map<BigInteger,String> map = new ConcurrentHashMap<>();
    static byte[] receiveComands = new byte[1400];
    static DatagramSocket serverSocket;
    static DatagramPacket receivedPacket;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Servidor inciado ----");
        RecoverPort recover = new RecoverPort();
        int porta = recover.recover();
        ThreadUDPServer thread = new ThreadUDPServer();


        //Inciando o socket do servidor
        serverSocket = new DatagramSocket(porta);

        File arquivo = new File("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/log.txt");
        FileReader in = new FileReader("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/log.txt");
        BufferedReader br = new BufferedReader(in);
        if(arquivo.length()!=0) {
            String line;
            while((line = br.readLine()) != null) {
                String[] divisao = line.split(";");
                String crud = divisao[0];
                BigInteger key = new BigInteger(divisao[1]);
                String mensagem;
                if(crud.equals("READ") || crud.equals("DELETE") || crud.equals("REGISTRO")) {
                    mensagem = "";
                }
                else
                    mensagem = divisao[2];

                thread.ProcessLog(crud, key, mensagem);
            }
        }

        thread.start();

        //Recebendo v�rios clientes
        while(true) {
            receivedPacket = new DatagramPacket(receiveComands,receiveComands.length);
            serverSocket.receive(receivedPacket);
            DadosCliente dados = new DadosCliente(receivedPacket.getAddress(),receivedPacket.getPort(),receivedPacket.getData());
            System.out.println("Cliente conectado");
            System.out.println("Porta = "+receivedPacket.getPort());
            System.out.println("Endere�o = "+receivedPacket.getAddress());
            System.out.println("\n");
            filaComandos.add(dados);
        }
    }

}
