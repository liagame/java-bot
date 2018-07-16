import com.adamldavis.pathfinder.PathGrid;
import lia.Api;
import lia.Callable;
import lia.NetworkingClient;
import lia.api.*;
import logic.PlayerData;
import logic.PathFinding;

import java.util.HashMap;
import java.util.Random;


/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private PathGrid grid;
    private HashMap<Integer, PlayerData> playersData;

    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }

    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {
        grid = PathFinding.createGrid(
                (int) mapData.width,
                (int) mapData.height,
                mapData.obstacles
        );
    }

    /** Repeatedly called from game engine with game state updates.  */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Api api) {
        // When the process is called for the first time
        if (playersData == null) {
            playersData = new HashMap<>();

            // Map player ids with player data for later use
            for (Player player : stateUpdate.players) {
                PlayerData data = new PlayerData(player.id, grid);
                playersData.put(player.id, data);
            }

            // Set path of all players on coordinate (140, 2)
            for (Player player : stateUpdate.players) {
                PlayerData data = playersData.get(player.id);
                data.setPathFollower(player.x, player.y, 140, 2);
            }
        }

        // Go through all players every frame
        for (Player player : stateUpdate.players) {
            int id = player.id;

            // Make the player follow the path
            playersData.get(id).followPath(player, api);

            // Shoot if your gun is loaded and you see an opponent
            // (But you may kill your fellow player... :) )
            if (player.canShoot && player.opponentsInView.length > 0)
                api.shoot(id);
        }
    }
}
