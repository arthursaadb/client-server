package server;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadSnapShot extends Thread {
    int versao = 0;
    public long TIME_DELAY = 45;
    public long TIME_INTERVAL = 45;
    public void run() {
        System.out.println("Salvando no snapshot");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                versao++;
                if(versao > 3){
                    versao = 1;
                }

                try {
                    montaSnapShoot(versao);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,TIME_DELAY,TIME_INTERVAL,TimeUnit.SECONDS);
    }

    public static void montaSnapShoot(int versao) throws IOException {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/snapShoot"+versao+".txt", false));
            List<BigInteger> temp = new ArrayList<>();
            for (BigInteger key : UDPServer.map.keySet()) {
                temp.add(key);
            }
            for(int i = 0; i < temp.size(); i++){
                if(UDPServer.map.containsKey(temp.get(i))){
                    buffWrite.write(temp.get(i) + " " + UDPServer.map.get(temp.get(i)));
                    buffWrite.newLine();
                    buffWrite.flush();
                }
            }
            buffWrite.close();
            File arquivo = new File("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/log.txt");
            PrintWriter writer = new PrintWriter(arquivo);
            writer.print("");
            writer.close();
        }
    }
