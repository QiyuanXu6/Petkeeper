package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class QuorumConnectionManager {
    public static int queueLength = 1000;


    PetServer localServer;
    Map<Long,ServerView> view;

    ArrayBlockingQueue<ByteBuffer> receivedQueue;


    public QuorumConnectionManager(){

    }

    public boolean startConnectionRequest(Long peerID){
        ServerView peer = view.get(peerID);
        synchronized (peer.lock) {
            if (!peer.isConnected) {
                try {
                    peer.socket = new Socket(peer.address.getHostName(), peer.address.getPort());
                }catch(IOException e){
                    return false;
                }

                peer.isConnected = true;
                peer.sendThread = new SendThread(peer.socket);
                return true;

            }

        }

    }

    public void handleConnectionRequest(){
        while (true && !localServer.isShutDown()){
            ServerSocket socket = localServer.getSocket();
            Socket client = null;
            try {
                client = socket.accept();
            }catch(Exception e){

            }
            Long id = new Long(0);
            for (Map.Entry<Long,ServerView> entry: view.entrySet()){
                if (entry.getValue().address.equals(client.getInetAddress())){
                    id = entry.getKey();
                }
            }
            //
            if (id.equals(0))
                throw new Exception("Not found socket");
            ServerView peer = view.get(id);
            peer.isConnected = true;
            peer.socket = client;

            peer.receiveThread = new ReceiveThread();
            peer.receiveThread.start();

        }

    }



    public void sendMessage(long peerID, ByteBuffer data){
        view.get(peerID).toSendQueue.add(data);
    }



    /**
     * Other peers' views
     */
    private class ServerView{
        long serverID;
        InetSocketAddress address;
        Socket socket;
        Boolean isConnected;
        ReentrantLock lock;

        SendThread sendThread;
        ReceiveThread receiveThread;

        ArrayBlockingQueue<ByteBuffer> toSendQueue;// every other peer has one

        ServerView(){

        }







    }


    /**
     *
     */
    private class SendThread extends Thread {

        long peerID;


        SendThread(long peerID) {
            this.peerID = peerID;


        }

        @Override
        public void run() {
            ArrayBlockingQueue<ByteBuffer> queue = view.get(peerID).toSendQueue;
            // TODO: like Zookeeper. Keep sending last message when queue is empty
            Socket socket = view.get(peerID).socket;
            DataOutputStream dout = null;
            try {
                dout = new DataOutputStream(socket.getOutputStream());

                if (!queue.isEmpty()) {
                    ByteBuffer data = queue.poll();
                    dout.writeInt(data.capacity());
                    dout.

                }


            } catch (IOException e) {

            }
        }
    }


    }

    private class ReceiveThread extends Thread{

    }








}
