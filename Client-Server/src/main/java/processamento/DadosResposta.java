package processamento;

import java.io.Serializable;

public class DadosResposta implements Serializable {

    String mensagem;

    public DadosResposta(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }



}
