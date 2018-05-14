package server;

import processamento.DadosCliente;
import processamento.DadosResposta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.DatagramPacket;

public class ThreadRead extends Thread {
    BigInteger key;
    DadosCliente dados;

    public ThreadRead(BigInteger key, DadosCliente dados) {
        this.key = key;
        this.dados = dados;
    }

    public void run() {
        if(UDPServer.map.containsKey(key)) {
            String mensagem = UDPServer.map.get(key);
            DadosResposta resposta = new DadosResposta(mensagem);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = null;
            try {
                objStream = new ObjectOutputStream(byteStream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                objStream.writeObject(resposta);
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
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,dados.getAddress(),dados.getPort());
            try {
                UDPServer.serverSocket.send(sendPacket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }	}
        else {
            String mensagem = "N�o existe chave com esse valor";
            DadosResposta resposta = new DadosResposta(mensagem);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = null;
            try {
                objStream = new ObjectOutputStream(byteStream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                objStream.writeObject(resposta);
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
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,dados.getAddress(),dados.getPort());
            try {
                UDPServer.serverSocket.send(sendPacket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
