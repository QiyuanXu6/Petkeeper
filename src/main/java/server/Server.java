package server;

import java.util.Queue;

public class Server {
    private int serverID;
    private long zxid;
    private ZNodeTree tree;
    private Constant.ServerState state;
    private Database db;
    private Queue<Request> receivedRequests;

    /*
    Initialize
     */
    public Server(){

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
