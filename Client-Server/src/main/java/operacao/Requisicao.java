package operacao;

import java.io.Serializable;
import java.math.BigInteger;

public class Requisicao implements Serializable {
    String crud;
    BigInteger key;
    String message;

    public Requisicao(String comando,BigInteger key, String message){
        this.crud = comando;
        this.key = key;
        this.message = message;
    }

    public String getCrud() {
        return crud;
    }

    public void setCrud(String crud) {
        this.crud = crud;
    }

    public BigInteger getKey() {
        return key;
    }

    public void setKey(BigInteger key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
