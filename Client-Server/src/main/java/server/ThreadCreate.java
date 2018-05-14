package server;

import processamento.DadosCliente;
import processamento.DadosResposta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.DatagramPacket;

public class ThreadCreate extends Thread {
    BigInteger key;
    String mensagem;
    byte[] sendResult;
    DadosCliente dados;
    DatagramPacket sendPacket;
    public ThreadCreate(BigInteger key,String mensagem,DadosCliente dados) {
        this.key = key;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    @Override
    public void run() {
        if(UDPServer.map.containsKey(key) == true) {
            if(dados != null) {
                String mensagem = "A chave solicitada j� foi inserida no map";
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
        else {
            UDPServer.map.put(key, mensagem);
            if(dados != null) {
                String mensagem = "Comando efetuado com sucesso";
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
}
