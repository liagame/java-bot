import lia.AiApiMessages.*;
import lia.Callable;
import lia.NetworkingClient;
import lia.Response;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private static final String URI = "ws://localhost:8887";
    private static final String PLAYER_ID_1 = "125haadfwe1231";
    private static final String PLAYER_ID_2 = "3r1532r32r2352";

    private static long seed = 15;

    private long counter = 0;
    private Random random = new Random(seed++);

    public static void main(String[] args) throws URISyntaxException {
        //String uri = args[0];
        //String playerId = args[1];

        // TODO try connecting few times before quit
        NetworkingClient.connectNew(URI, PLAYER_ID_1, new MyBot());
        NetworkingClient.connectNew(URI, PLAYER_ID_2, new MyBot());
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
        List<Player> players = stateUpdate.getPlayersList();

        for (Player player : players) {
            int id = player.getId();

            // Rotation and thrust speed
            if (counter % 30 == 0 || counter % 31 == 0) {

                // Handle rotation
                double rand = random.nextFloat();
                if (rand < 0.4) response.setRotationSpeed(id, Rotation.Enum.LEFT);
                else if (rand < 0.8) response.setRotationSpeed(id, Rotation.Enum.RIGHT);
                else response.setRotationSpeed(id, Rotation.Enum.NONE);

                // Handle thrust speed
                rand = random.nextFloat();
                if (rand < 0.9) {
                    response.setThrustSpeed(id, ThrustSpeed.Enum.FULL_SPEED);
                }
                else {
                    response.setThrustSpeed(id, ThrustSpeed.Enum.NONE);
                }
            }
            counter++;

            // Shoot
            if (player.getWeaponLoaded() && player.getEntititesInViewCount() > 0)
                response.shoot(id);
        }
    }
}
