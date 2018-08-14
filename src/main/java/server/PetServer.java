package server;

import util.ServerConnectionManager;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class PetServer {
    private long serverID;
    private InetSocketAddress address;
    private long zxid;
    private ZNodeTree tree;
    //private Constant.ServerState state;
    private Database db;
    private ServerState state;
    private ServerSocket socket;


    public PetServer(long severID){
        long

    }


    public ServerSocket getSocket(){
        return socket;
    }


    public enum ServerState{
        on, off
    }

    public Boolean isShutDown(){
        return state == ServerState.off;
    }




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








}
