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

package clientgrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import proto.SolicitationReply;
import proto.SolicitationRequest;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class clientGrpc {
    private static final Logger logger = Logger.getLogger(clientGrpc.class.getName());

    private final ManagedChannel channel;
    private final proto.CreateRequestGrpc.CreateRequestBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public clientGrpc(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true)
                .build());
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    clientGrpc(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = proto.CreateRequestGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public void createRequest(String crud, BigInteger key, String message) {
        Integer chave = new Integer(key.intValue());
        SolicitationRequest request = proto.SolicitationRequest.newBuilder().setCrud(crud).setChave(chave).setMensagem(message).build();
        SolicitationReply response;
        try {
            response = blockingStub.solicitation(request);
        } catch (StatusRuntimeException e) {
            return;
        }
        logger.info(response.getMessage());
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        clientGrpc client = new clientGrpc("localhost", 50051);
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Bem vindo cliente GRPC, qual operação deseja realizar?");
            String comando = scanner.nextLine();
            if (comando.equals("READ") || comando.equals("DELETE") || comando.equals("REGISTRO")) {
                System.out.println("Digite de que chave você gostaria de realizar a operação");
                BigInteger chave = new BigInteger(scanner.nextLine());
                client.createRequest(comando, chave, "");
            } else {
                System.out.println("Digite a chave que você gostaria de colocar");
                BigInteger chave = new BigInteger(scanner.nextLine());
                System.out.println("Digite a mensagem que você gostaria de mandar");
                String mensagem = scanner.nextLine();
                client.createRequest(comando, chave, mensagem);
            }
        }
    }
}
