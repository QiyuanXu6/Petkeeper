package util;

import org.junit.jupiter.api.Test;
import server.PetServer;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionTest {
    @Test
    public void requestAndResposeTest(){
        PetServer petServer = new PetServer();
        final InetSocketAddress serveraddress = new InetSocketAddress("localhost",33333);
        final ServerConnectionManager servermanager = new ServerConnectionManager(serveraddress,petServer);
        final ClientConnectionManager clientmanager = new ClientConnectionManager();
        Runnable client = new Runnable() {
            public void run() {
                String response = clientmanager.clientSendRequest("HelloZY",serveraddress);
                assertTrue("HelloToo".equals(response));



            }
        };
        new Thread(client).start();

        Runnable serverThread = new Runnable() {
            public void run() {
                servermanager.serverReceive();

//                SocketChannel clientchannel = servermanager.getClientChannel();
//
//                String response = servermanager.serverReceive(clientchannel);
//                assertTrue(response.equals("HelloZY"));
//
//                servermanager.serverResponse("HelloToo",clientchannel);

            }
        };
        new Thread(server).start();






    }

}