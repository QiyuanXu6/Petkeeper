package server;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.nio.channels.SelectionKey;

public class Request implements Serializable {
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

    public byte[] Serialize(){
        return SerializationUtils.serialize(this);
    }

    public Request Deserialize(byte[] data){
        return SerializationUtils.deserialize(data);
    }


}
