package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class QuorumConnectionManager {
    public static int queueLength = 1000;


    private PetServer localServer;
    private Map<Long,ServerView> view;

    private ArrayBlockingQueue<ReceivedDataAndID> receivedQueue;

    private class ReceivedDataAndID {
        byte[] data;
        long id;
        ReceivedDataAndID(byte[] data, long id) {
            this.data = data;
            this.id = id;
        }
    }

    private ReentrantLock receivedQueueLock;

    public Map<Long,ServerView> getView(){
        return view;
    }
    public QuorumConnectionManager(PetServer localServer){
        this.localServer = localServer;
        receivedQueue = new ArrayBlockingQueue<ReceivedDataAndID>(queueLength);
        view = new HashMap<Long, ServerView>();
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
                peer.sendThread = new SendThread(peerID);


            }

        }
        return true;

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
//            if (id.equals(0))
//                throw new Exception("Not found socket"); Todo: be to added
            ServerView peer = view.get(id);
            peer.isConnected = true;
            peer.socket = client;

            peer.receiveThread = new ReceiveThread(id);
            peer.receiveThread.start();

        }

    }


    public void sendMessage(long peerID, byte[] data){
        view.get(peerID).toSendQueue.add(data);
    }


    /**
     * Other peers' views
     */
    public static class ServerView{
        long serverID;
        InetSocketAddress address;
        Socket socket;
        Boolean isConnected;
        ReentrantLock lock;

        SendThread sendThread;
        ReceiveThread receiveThread;

        ArrayBlockingQueue<byte[]> toSendQueue;// every other peer has one

        public ServerView(long serverID, InetSocketAddress address){
            this.serverID = serverID;
            this.address = address;
            isConnected = false;
            lock = new ReentrantLock();
            toSendQueue = new ArrayBlockingQueue<byte[]>(queueLength);


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
            ArrayBlockingQueue<byte[]> queue = view.get(peerID).toSendQueue;
            // TODO: like Zookeeper. Keep sending last message when queue is empty
            Socket socket = view.get(peerID).socket;
            DataOutputStream dout = null;
            try {
                dout = new DataOutputStream(socket.getOutputStream());

                if (!queue.isEmpty()) {
                    byte[] data = queue.poll();
                    dout.writeInt(data.length);
                    dout.write(data);

                }


            } catch (IOException e) {

            }
        }
    }



    private class ReceiveThread extends Thread{
        long peerID;

        ReceiveThread(long peerID){
            this.peerID = peerID;
        }

        @Override
        public void run() {

            Socket socket = view.get(peerID).socket;
            DataInputStream din = null;
            try {
                din = new DataInputStream(socket.getInputStream());
                byte len = din.readByte();

                byte[] data = new byte[len];
                din.readFully(data);

                synchronized (receivedQueueLock) {
                    receivedQueue.add(new ReceivedDataAndID(data,peerID));
                }



            }catch(IOException e) {
            }
        }

    }
}

