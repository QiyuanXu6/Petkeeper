package util;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionTest {
    @Test
    public void requestAndResposeTest(){
        final InetSocketAddress serveraddress = new InetSocketAddress("localhost",33333);
        final ServerConnectionManager servermanager = new ServerConnectionManager(serveraddress);
       // SocketAddress serveraddress = servermanager.getServerAddress();
        final ClientConnectionManager clientmanager = new ClientConnectionManager();
        Runnable client = new Runnable() {
            public void run() {
                byte[] response = clientmanager.clientSendRequest("HelloZY".getBytes(),serveraddress);
                String s1 = new String(response);
                String s2 = new String("HelloToo".getBytes());
                assertTrue(Arrays.equals("HelloToo".getBytes(),response));
                String output = "Client gets: ";
                for(int i = 0; i < response.length; i++)
                    output += response[i];
                System.out.println(output);


            }
        };
        new Thread(client).start();

        Runnable server = new Runnable() {
            public void run() {
                SocketChannel clientchannel = servermanager.getClientChannel();

                byte[] response = servermanager.serverReceive(clientchannel);
                String output = "Server gets: ";
                for(int i = 0; i < response.length; i++)
                    output += response[i];
                assertTrue(Arrays.equals(response,"HelloZY".getBytes()));

                servermanager.serverResponse("HelloToo".getBytes(),clientchannel);

            }
        };
        new Thread(server).start();






    }

}