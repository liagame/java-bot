package lia;

import com.google.protobuf.InvalidProtocolBufferException;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static lia.AiApiMessages.*;

public class NetworkingClient extends WebSocketClient {

    private Callable myBot;

    public static NetworkingClient create(String uri, String playerId, Callable myBot) throws URISyntaxException {
        Map<String,String> httpHeaders = new HashMap<>();
        httpHeaders.put("Id", playerId);
        return new NetworkingClient(new URI(uri), httpHeaders, myBot);
    }

    private NetworkingClient(URI serverUri, Map<String, String> httpHeaders, Callable myBot) {
        super(serverUri, httpHeaders);
        this.myBot = myBot;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("opened connection");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        try {
            Message msg = Message.parseFrom(bytes.array());

            if (msg.hasMapData()) {
                MapData mapData = msg.getMapData();
                myBot.process(mapData);
            }
            else if (msg.hasStateUpdate()) {
                StateUpdate stateUpdate = msg.getStateUpdate();
                myBot.process(stateUpdate);
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

}