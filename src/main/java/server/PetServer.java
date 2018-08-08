package server;

import util.ServerConnectionManager;

import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class PetServer {
    private int serverID;
    private long zxid;
    private ZNodeTree tree;
    //private Constant.ServerState state;
    private Database db;

    private Queue<Request> sendingQueue;
    private Queue<Request> receivedQueue;
    private ReentrantLock sendingQueueLock;
    private ReentrantLock receivedQueueLock;

    public ReentrantLock getSendingQueueLock(){
        return sendingQueueLock;
    }

    public ReentrantLock getReceivedQueueLock(){
        return receivedQueueLock;
    }

    public Queue<Request> getSendingQueue(){
        return sendingQueue;
    }

    public Queue<Request> getReceivedQueue(){
        return receivedQueue;
    }

    /*
    Initialize
     */
    public PetServer(){
        //ServerConnectionManager a = new ServerConnectionManager(null,this);

    }


    public void serverStart(){

        final InetSocketAddress serveraddress = new InetSocketAddress("localhost",33333); // TODO
        final ServerConnectionManager serverManager = new ServerConnectionManager(serveraddress,this);

        Runnable receiveThread = new Runnable() {
            public void run() {
                serverManager.serverReceive();
            }
        };
        Runnable sendThread = new Runnable() {
            public void run() {
                serverManager.serverReceive();
            }
        };
        new Thread(receiveThread).start();
        new Thread(sendThread).start();

    }


//    public void running(){
//        while (true){
//            while (receivedRequests.isEmpty());//TODO: blocking;
//            Request request= receivedRequests.poll();
//            switch (request.getType()){
//                case PeerProposal: ...// TODO
//            }
//
//
//        }
//    }
//    //TODO:How to express a running server?







}
