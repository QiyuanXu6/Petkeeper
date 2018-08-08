package server;

import java.nio.channels.SelectionKey;

public class Request {
    private SelectionKey key;
    private byte[] value;

    public Request(SelectionKey key, byte[] value){
        this.key = key;
        this.value = value;
    }

    public byte[] getValue(){
        return value;
    };
    public SelectionKey getKey(){
        return key;
    }




    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public enum RequestType{
        ClientToServer, ResponseToClient, PeerProposal,PeerResponse;
    }
    private RequestType type;
}
