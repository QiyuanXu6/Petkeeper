package server;

public class Request {
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
