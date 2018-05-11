import lia.AiApiMessages.*;
import lia.Callable;
import lia.NetworkingClient;
import lia.Response;

import java.util.List;
import java.util.Random;

/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private long counter = 0;
    private Random random = new Random(15);

    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
        //NetworkingClient.connectNew(args, new MyBot());
    }

    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {
        System.out.println(mapData);
    }

    /** Repeatedly called from game engine with game status updates.  */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Response response) {
        /*
        * This is a sample code that moves players randomly and shoots when the
        * opponent is in sight.
        **/

        List<Player> players = stateUpdate.getPlayersList();

        for (Player player : players) {
            int id = player.getId();

            // Rotation and thrust speed
            if (counter % 10 == 0 || counter % 11 == 0) {

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
            if (player.getWeaponLoaded() && player.getOpponentsInViewCount() > 0)
                response.shoot(id);
        }
    }
}
