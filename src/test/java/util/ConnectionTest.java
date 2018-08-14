package util;

import org.junit.jupiter.api.Test;
import server.QuorumConnectionManager;

import java.net.InetSocketAddress;

class ConnectionTest {
    @Test
    public void PeerCommunicationTest(){
        QuorumConnectionManager manager1 = new QuorumConnectionManager();
        QuorumConnectionManager manager2 = new QuorumConnectionManager();
        QuorumConnectionManager manager3 = new QuorumConnectionManager();

        manager1.getView().put(Long.valueOf(2),new QuorumConnectionManager.ServerView(2,
                new InetSocketAddress("localhost",22222)));
        manager1.getView().put(Long.valueOf(3),new QuorumConnectionManager.ServerView(3,
                new InetSocketAddress("localhost",33333)));

        manager2.getView().put(Long.valueOf(1),new QuorumConnectionManager.ServerView(1,
                new InetSocketAddress("localhost",11111)));
        manager2.getView().put(Long.valueOf(3),new QuorumConnectionManager.ServerView(3,
                new InetSocketAddress("localhost",33333)));

//        manager3.getView().put(Long.valueOf(1),new QuorumConnectionManager.ServerView(1,
//                new InetSocketAddress("localhost",11111)));
//        manager3.getView().put(Long.valueOf(3),new QuorumConnectionManager.ServerView(3,
//                new InetSocketAddress("localhost",33333)));

        manager1.startConnectionRequest()







//        PetServer petServer = new PetServer();
//        final InetSocketAddress serveraddress = new InetSocketAddress("localhost",33333);
//        final ServerConnectionManager servermanager = new ServerConnectionManager(serveraddress,petServer);
//        final ClientConnectionManager clientmanager = new ClientConnectionManager();
//        Runnable c
//
//
//lient = new Runnable() {
//            public void run() {
//                String response = clientmanager.clientSendRequest("HelloZY",serveraddress);
//                assertTrue("HelloToo".equals(response));
//
//
//
//            }
//        };
//        new Thread(client).start();
//
//        Runnable serverThread = new Runnable() {
//            public void run() {
//                servermanager.serverReceive();
//
////                SocketChannel clientchannel = servermanager.getClientChannel();
////
////                String response = servermanager.serverReceive(clientchannel);
////                assertTrue(response.equals("HelloZY"));
////
////                servermanager.serverResponse("HelloToo",clientchannel);
//
//            }
//        };
//        new Thread(server).start();






    }

}