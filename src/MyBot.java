import lia.AiApiMessages.MapData;
import lia.AiApiMessages.StateUpdate;
import lia.Callable;
import lia.NetworkingClient;

import java.net.URISyntaxException;


public class MyBot implements Callable {

    private static final String URI = "ws://localhost:8887";
    private static final String PLAYER_ID_1 = "125haadfwe1231";
    private static final String PLAYER_ID_2 = "3r1532r32r2352";

    public static void main(String[] args) throws URISyntaxException {
        NetworkingClient c = NetworkingClient.create(URI, PLAYER_ID_1, new MyBot());
        c.connect();
    }

    @Override
    public void process(MapData mapData) {
        // Your logic here
        System.out.println(mapData);
    }

    @Override
    public void process(StateUpdate stateUpdate) {
        // Your logic here
        System.out.println(stateUpdate);
    }
}
