package server;

import port.RecoverPort;
import processamento.DadosCliente;

import java.io.*;
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

        File snapShoot1 = new File("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/snapShoot1.txt");
        File snapShoot2 = new File("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/snapShoot2.txt");
        File snapShoot3 = new File("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/snapShoot3.txt");
        File arquivo = new File("C:\\Users\\Arthur Saad\\eclipse-workspace\\Projeto1\\Projeto1");

        //Inciando o socket do servidor
        serverSocket = new DatagramSocket(porta);

        if(snapShoot1.length() != 0 || snapShoot2.length() != 0 || snapShoot3.length() != 0 ){
            reloadSnapshot(snapShoot1, snapShoot2, snapShoot3);
        }
        FileReader in = new FileReader("C:\\Users\\Arthur Saad\\eclipse-workspace\\Projeto1\\Projeto1\\log.txt");
        BufferedReader br = new BufferedReader(in);
        if (arquivo.length() != 0) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] divisao = line.split(";");
                    String crud = divisao[0];
                    BigInteger key = new BigInteger(divisao[1]);
                    String mensagem;
                    if (crud.equals("READ") || crud.equals("DELETE") || crud.equals("REGISTRO")) {
                        mensagem = "";
                    } else
                        mensagem = divisao[2];
                    thread.ProcessLog(crud, key, mensagem);
                }
            }


        thread.start();
        new ThreadSnapShot().start();

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

    public static void reloadSnapshot(File v1, File v2, File v3) throws IOException {
        File snapshoot = verifyCurrentSnapshot(v1, v2, v3); //snapshoot mais atual
        FileReader in = null;
        try {
            in = new FileReader(snapshoot.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(in);
        try {
            processSnapshot(br, snapshoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File verifyCurrentSnapshot(File v1, File v2, File v3){
        if ( v1.lastModified() > v2.lastModified()){
            if(v1.lastModified() > v3.lastModified()){
                return v1;
            } else {
                return v3;
            }
        }else {
            if(v2.lastModified() > v3.lastModified()){
                return v2;
            } else {
                return v3;
            }
        }
    }

    public static void processSnapshot(BufferedReader br, File snapshoot) throws IOException {
        if(snapshoot.length()!=0) {
            String line;
            while((line = br.readLine()) != null) {
                String[] divisao = line.split("\\s+");
                String key = divisao[0];
                String value = divisao[1];
                map.put(new BigInteger(key),value);
            }
        }
    }

}
