package port;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RecoverPort {
    Properties prop;
    public int recover() {
        prop = new Properties();
        InputStream input = null;
        try {
            input= new FileInputStream("config.properties");
            prop.load(input);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        int porta = Integer.parseInt(prop.getProperty("port"));
        return porta;
    }

}
