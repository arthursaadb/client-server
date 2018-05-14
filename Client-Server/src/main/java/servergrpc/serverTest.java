/*
 * Copyright 2015, gRPC Authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package servergrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import operacao.Requisicao;
import port.RecoverPort;
import processamento.DadosResposta;
import proto.CreateRequestGrpc;
import proto.SolicitationReply;
import proto.SolicitationRequest;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class serverTest {
    private static final Logger logger = Logger.getLogger(serverTest.class.getName());
    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new grpcProcess())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                serverTest.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final serverTest server = new serverTest();
        server.start();
        server.blockUntilShutdown();
        List<String> respostas = new ArrayList<String>();
    }

    static class grpcProcess extends CreateRequestGrpc.CreateRequestImplBase {
        DatagramSocket socketGrpc = null;
        DadosResposta respost = null;


        @Override
        public void solicitation(SolicitationRequest request, StreamObserver<SolicitationReply> responseObserver) {
            RecoverPort recover = new RecoverPort();
            int port = recover.recover();
            BigInteger key = BigInteger.valueOf(request.getChave());
            Requisicao data = new Requisicao(request.getCrud(), key, request.getMensagem());

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = null;
            try {
                objStream = new ObjectOutputStream(byteStream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                objStream.writeObject(data);
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
            byte[] receiveData = new byte[1400];

            if (sendData.length <= 1400) {
                try {
                    socketGrpc = new DatagramSocket();
                    InetAddress IPAddress = null;
                    IPAddress = InetAddress.getByName("localhost");
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    socketGrpc.send(sendPacket);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    socketGrpc.receive(receivePacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] process = receivePacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(process);
                ObjectInputStream is = null;
                try {
                    is = new ObjectInputStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    respost = (DadosResposta) is.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                proto.SolicitationReply reply = proto.SolicitationReply.newBuilder().setMessage(respost.getMensagem()).build();
                responseObserver.onNext(reply);
                //responseObserver.onCompleted();
            }

        }
    }
}
