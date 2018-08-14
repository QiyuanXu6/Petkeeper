package util;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 * Provide tcp communication
 */
public class ClientConnectionManager {

    public String clientSendRequest(String data,  SocketAddress serverAddress){
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        SocketChannel channel ;
        StringBuilder strBuilder = new StringBuilder();
        try{
            channel = SocketChannel.open();
            channel.connect(serverAddress);
            channel.write(buffer);
            buffer.clear();
            while (channel.read(buffer) != -1){
                strBuilder.append(new String(buffer.array()));
            }

            return strBuilder.toString();

        }catch(Exception e){
            return null;// TODO
        }
    }
}
