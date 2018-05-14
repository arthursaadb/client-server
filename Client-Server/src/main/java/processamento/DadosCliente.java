package processamento;

import java.net.InetAddress;

public class DadosCliente {


    private InetAddress address;
    private int port;
    byte[] mensagem;

    public DadosCliente(InetAddress address, int port, byte[] mensagem){
        this.address = address;
        this.port = port;
        this.mensagem = mensagem;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public byte[] getMensagem() {
        return mensagem;
    }

    public void setMensagem(byte[] mensagem) {
        this.mensagem = mensagem;
    }


}