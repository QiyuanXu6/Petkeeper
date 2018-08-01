package server;

import java.net.Socket;
import java.nio.ByteBuffer;

public class PeerCxnManager {
    public Socket peer;
    public int messagetype;
//    public enum MessageType{
//        StartCxn,Proposal//TODO
//    }
    public PeerCxnManager(){

    }

     public void startConnection(){


     }

     public ByteBuffer Serialize(int type, int serverID){
        byte request[] = new byte[50]; //TODO: specific length
        ByteBuffer buffer = ByteBuffer.wrap(request);
        buffer.clear();
        buffer.putInt(type);
        buffer.putInt(serverID);

        }

     }
}
