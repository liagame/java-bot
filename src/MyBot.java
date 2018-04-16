import lia.AiApiMessages.*;
import lia.Callable;
import lia.NetworkingClient;
import lia.Response;

import java.net.URISyntaxException;
import java.util.Random;

/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private static final String URI = "ws://localhost:8887";
    private static final String PLAYER_ID_1 = "125haadfwe1231";
    private static final String PLAYER_ID_2 = "3r1532r32r2352";

    private long counter = 0;
    private Random random = new Random(2);

    public static void main(String[] args) throws URISyntaxException {
        //String uri = args[0];
        //String playerId = args[1];

        NetworkingClient c1 = NetworkingClient.create(URI, PLAYER_ID_1, new MyBot());
        c1.connect();

        NetworkingClient c2 = NetworkingClient.create(URI, PLAYER_ID_2, new MyBot());
        c2.connect();
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
        Player player = stateUpdate.getPlayers(0);

        int id = player.getId();

        // Rotation and thrust speed
        if (counter % 10 == 0) {

            // Handle rotation
            double rand = random.nextFloat();
            if (rand < 0.3) response.setRotationSpeed(id, Rotation.Enum.LEFT);
            if (rand < 0.8) response.setRotationSpeed(id, Rotation.Enum.RIGHT);
            else response.setRotationSpeed(id, Rotation.Enum.NONE);

            // Handle thrust speed
            rand = random.nextFloat();
            if (rand < 0.5) response.setThrustSpeed(id, ThrustSpeed.Enum.FULL_SPEED);
            else response.setThrustSpeed(id, ThrustSpeed.Enum.NONE);
        }

        // Shoot
        response.shoot(id);

        counter++;
    }
}
