package server;

import processamento.DadosCliente;
import processamento.DadosResposta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class ThreadRegistro extends Thread {

    BigInteger key;
    DadosCliente dados;

    public ThreadRegistro(BigInteger key,DadosCliente dados) {
        this.key = key;
        this.dados = dados;
    }


    public void run() {
        if(ThreadUDPServer.mapRegistros.containsKey(key)) {
            ThreadUDPServer.mapRegistros.get(key).add(dados);
            String mensagem = "Agora o cliente na porta " + dados.getPort() + " est� monitorando a chave " + key;
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
        else if(UDPServer.map.containsKey(key)) {
            if(dados!=null) {
                List<DadosCliente> cliente = new ArrayList<DadosCliente>();
                cliente.add(dados);
                ThreadUDPServer.mapRegistros.put(key, cliente);
                String mensagem = "Agora o cliente na porta " + dados.getPort() + " est� monitorando a chave " + key;
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
            if(dados != null) {
                String mensagem = "A chave que voc� deseja ficar monitorando n�o existe";
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
