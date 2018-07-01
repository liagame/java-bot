import lia.Api;
import lia.Callable;
import lia.NetworkingClient;
import lia.api.*;

import java.util.Random;


/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private long counter = 0;
    private Random random = new Random(15);
    private boolean shouldShoot = true;

    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
        //NetworkingClient.connectNew(args, new MyBot());
    }

    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {}

    /** Repeatedly called from game engine with game status updates.  */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Api api) {
        /*
        * This is a sample code that moves players randomly and shoots when the
        * opponent is in sight.
        **/
        Player[] players = stateUpdate.players;

        for (Player player : players) {
           int id = player.id;

            // Rotation and thrust speed
            if (counter % 10 == 0 || counter % 11 == 0) {

                // Handle rotation
                double rand = random.nextFloat();
                if (rand < 0.4) api.setRotationSpeed(id, Rotation.LEFT);
                else if (rand < 0.8) api.setRotationSpeed(id, Rotation.RIGHT);
                else api.setRotationSpeed(id, Rotation.NONE);

                // Handle thrust speed
                rand = random.nextFloat();
                if (rand < 0.9) {
                    api.setThrustSpeed(id, ThrustSpeed.BACKWARD);
                }
                else {
                    api.setThrustSpeed(id, ThrustSpeed.NONE);
                }
            }
            counter++;

            if (player.nBullets == 3) shouldShoot = true;
            if (player.nBullets == 0) shouldShoot = false;

            // Shoot
            //if (player.canShoot && player.opponentsInView.length > 0)
            if (player.canShoot && shouldShoot)
                api.shoot(id);
        }
    }
}
