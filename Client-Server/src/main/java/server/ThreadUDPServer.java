package server;

import operacao.Requisicao;
import processamento.DadosCliente;
import processamento.DadosResposta;

import java.io.*;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadUDPServer extends Thread {
	static LinkedBlockingQueue<DadosCliente> filaLog = new LinkedBlockingQueue<>();
	static LinkedBlockingQueue<DadosCliente> filaProcessamento = new LinkedBlockingQueue<>();
	public static Map<BigInteger,List<DadosCliente>> mapRegistros = new ConcurrentHashMap<>();
	
	@Override
	public void run() {
			while(true) {
				DadosCliente dados = UDPServer.filaComandos.poll();
					if(dados != null) {
						filaLog.add(dados);
						filaProcessamento.add(dados);
						sendLog();
						try {
							processRequistion();
						} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
				}
			}
	
	public void sendLog() {
		try {
			BufferedWriter  buffWrite = new BufferedWriter(new FileWriter("C:/Users/Arthur Saad/eclipse-workspace/Projeto1/Projeto1/log.txt",true));
			DadosCliente dadosLog = filaLog.poll();
			byte[] result = dadosLog.getMensagem();
			ByteArrayInputStream in = new ByteArrayInputStream(result);
			ObjectInputStream is = new ObjectInputStream(in);
			Requisicao salvar = (Requisicao) is.readObject();
			String crud = salvar.getCrud();
			//System.out.println(crud);
			BigInteger key = salvar.getKey();
			//System.out.println(key);
			String mensagem = salvar.getMessage();
			//System.out.println(mensagem);
			String completa = crud+";"+key+";"+mensagem;
			buffWrite.write(completa);
			buffWrite.newLine();
			buffWrite.flush();
			buffWrite.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void processRequistion() throws UnsupportedEncodingException {
		Requisicao data = null;
		DadosCliente dados = filaProcessamento.poll();
		byte[] process = dados.getMensagem();
 		ByteArrayInputStream in = new ByteArrayInputStream(process);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			data = (Requisicao) is.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String crud = data.getCrud();
		//System.out.println(crud);
		BigInteger chave = data.getKey();
		//System.out.println(chave);
		String mensagem = data.getMessage();
		//System.out.println(mensagem);
		
		if(mapRegistros.containsKey(chave)) {
		    //System.out.println(mapRegistros);
			List<DadosCliente> clientes = mapRegistros.get(chave);
			for(int i=0; i<clientes.size(); i++) {
				String envio = "Cliente na porta " + clientes.get(i).getPort() + " uma opera��o de " + crud + " ser� realizada na chave " + chave + " pelo cliente na porta " + dados.getPort();
				DadosResposta resposta = new DadosResposta(envio);
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
				DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,clientes.get(i).getAddress(),clientes.get(i).getPort());
				try {
					UDPServer.serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(crud.equals("CREATE")) {
			new ThreadCreate(chave,mensagem,dados).start();
		}
		
		else if(crud.equals("READ")) {
			new ThreadRead(chave,dados).start();
		}
		
		else if(crud.equals("UPDATE")) {
			new ThreadUpdate(chave,mensagem,dados).start();
		}
		
		else if(crud.equals("DELETE")){
			new ThreadDelete(chave,dados).start();
		}
		
		else if(crud.equals("REGISTRO")) {
			new ThreadRegistro(chave,dados).start();
		}
		
	}
	
	public void ProcessLog(String crud, BigInteger key, String mensagem) {
		DadosCliente dados = null;
		if(crud.equals("CREATE")) {
			new ThreadCreate(key,mensagem,dados).start();
		}
		
		else if(crud.equals("UPDATE")) {
			new ThreadUpdate(key,mensagem,dados).start();
		}
		
		else if(crud.equals("DELETE")) {
			new ThreadDelete(key,dados).start();
		}
		
		else if(crud.equals("REGISTRO")) {
			new ThreadRegistro(key,dados).start();
		}
	}

	public static Integer verificaSnapshotAtual(File v1, File v2, File v3){
		if ( v1.lastModified() > v2.lastModified()){
			if(v1.lastModified() > v3.lastModified()){
				return 1;
			} else {
				return 3;
			}
		}else {
			if(v2.lastModified() > v3.lastModified()){
				return 2;
			} else {
				return 3;
			}
		}
	}

}
