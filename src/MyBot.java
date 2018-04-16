import lia.AiApiMessages.MapData;
import lia.AiApiMessages.StateUpdate;
import lia.Callable;
import lia.NetworkingClient;
import lia.Response;

import java.net.URISyntaxException;

/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private static final String URI = "ws://localhost:8887";
    private static final String PLAYER_ID_1 = "125haadfwe1231";
    private static final String PLAYER_ID_2 = "3r1532r32r2352";

    public static void main(String[] args) throws URISyntaxException {
        //String uri = args[0];
        //String playerId = args[1];

        NetworkingClient c = NetworkingClient.create(URI, PLAYER_ID_1, new MyBot());
        c.connect();
    }

    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {
        // TODO: Your logic here
        System.out.println(mapData);
    }

    /** Repeatedly called from game engine with game status updates.  */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Response response) {
        // TODO: Your logic here
        System.out.println(stateUpdate);
    }
}
