package util;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 * Provide tcp communication
 */
public class ClientConnectionManager {

    public byte[] clientSendRequest(byte[] data,  SocketAddress serverAddress){
        ByteBuffer buffer = ByteBuffer.wrap(data);
        SocketChannel channel ;
        try{
            channel = SocketChannel.open();
            channel.connect(serverAddress);
            channel.write(buffer);
            buffer.clear();
            Thread.sleep(1000);
            int numRead = channel.read(buffer);

            if (numRead==-1){
                return null;// TODO
            }else{
                byte[] response = new byte[numRead];
                System.arraycopy(buffer.array(), 0, response,0,numRead);
                return response;
            }
        }catch(Exception e){
            return null;// TODO
        }
    }
}
