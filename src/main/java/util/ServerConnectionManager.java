package util;

import server.PetServer;
import server.Request;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * Provide tcp communication
 */
public class ServerConnectionManager {
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private PetServer server;




    public ServerConnectionManager(SocketAddress address, PetServer _server){
        try {
            server = _server;
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(address);
            serverChannel.configureBlocking(false);
            serverChannel.register(selector,SelectionKey.OP_ACCEPT);

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



    public void serverReceive() {
        try{
            while (true){
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()){
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()){
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector,SelectionKey.OP_READ);
                    }
                    if (key.isReadable()){
                        synchronized (server.getReceivedQueueLock()){
                            ByteBuffer buffer = ByteBuffer.allocate(500);
                            SocketChannel clientChannel = (SocketChannel)key.channel();
                            while (clientChannel.read(buffer) != -1){
                                buffer.compact();
                            }
                            server.getReceivedQueue().add(new Request(key,buffer.array()));
                        }
                    }
                }
            }
        }catch(Exception e){

        }

    }

    public void serverResponse() {
        synchronized (server.getSendingQueueLock()){
            Request request = server.getSendingQueue().poll();
            SocketChannel clientChannel = (SocketChannel)request.getKey().channel();
            ByteBuffer buffer = ByteBuffer.wrap(request.getValue());
            try {
                clientChannel.write(buffer);
            }catch (Exception e) {
            }

        }
    }

}
