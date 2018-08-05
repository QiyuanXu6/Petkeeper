package util;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * Provide tcp communication
 */
public class ServerConnectionManager {
    private ServerSocketChannel serverChannel;

    public ServerConnectionManager(SocketAddress address){
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(address);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SocketAddress getServerAddress(){
        try{
            return serverChannel.getLocalAddress();
        }catch (Exception e){
            return null;
        }

    }


    public SocketChannel getClientChannel() {
        try {
            return serverChannel.accept();
        }catch(Exception e){
            return null;
        }
    }



    public byte[] serverReceive(SocketChannel clientChannel) {

        try{
            ByteBuffer buffer = ByteBuffer.allocate(9000);
            int numRead = clientChannel.read(buffer);
            if (numRead == -1) {
                throw new Exception("readSignal = 1");
            }
            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(),0,data,0,numRead);
            return data;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void serverResponse(byte[] data, SocketChannel clientChannel) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        try {
            clientChannel.write(buffer);
        }catch (Exception e) {
        }


    }

}
