package util;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionTest {
    @Test
    public void requestAndResposeTest(){
        final InetSocketAddress serveraddress = new InetSocketAddress("localhost",33333);
        final ServerConnectionManager servermanager = new ServerConnectionManager(serveraddress);
        final ClientConnectionManager clientmanager = new ClientConnectionManager();
        Runnable client = new Runnable() {
            public void run() {
                String response = clientmanager.clientSendRequest("HelloZY",serveraddress);
                assertTrue("HelloToo".equals(response));



            }
        };
        new Thread(client).start();

        Runnable server = new Runnable() {
            public void run() {
                SocketChannel clientchannel = servermanager.getClientChannel();

                String response = servermanager.serverReceive(clientchannel);
                assertTrue(response.equals("HelloZY"));

                servermanager.serverResponse("HelloToo",clientchannel);

            }
        };
        new Thread(server).start();






    }

}