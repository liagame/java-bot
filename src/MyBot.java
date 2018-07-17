import com.adamldavis.pathfinder.PathGrid;
import lia.Api;
import lia.Callable;
import lia.NetworkingClient;
import lia.api.*;
import logic.PlayerData;
import logic.PathFinding;
import logic.Vector2;
import java.util.HashMap;


/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private HashMap<Integer, PlayerData> playersData = new HashMap<>();

    private Vector2 bottomRightCorner = new Vector2(140, 2);
    private Vector2 topLeftCorner = new Vector2(2, 78);

    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {
            // Convert map with obstacles to a grid that will be used for
            // path finding algorithm
            PathGrid grid = PathFinding.createGrid(
                    (int) mapData.width,
                    (int) mapData.height,
                    mapData.obstacles
            );

            // Store data related to each player in PlayerData object.
            // All PlayerData objects will be accessible through playersData map.
            for (PlayerLocation player : mapData.playerLocations) {
                PlayerData data = new PlayerData(player.id, grid);
                playersData.put(player.id, data);
            }
    }

    /** Repeatedly called from game engine with game state updates.  */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Api api) {
        // Go through all players
        for (Player player : stateUpdate.players) {
            // Get data for this player
            PlayerData playerData = playersData.get(player.id);

            // If the player is not following any path, set it
            if (playerData.pathNotSet) {
                if (playerData.goingToTopLeftCorner) {
                    playerData.setPathToFollow(player, topLeftCorner.x,  topLeftCorner.y);
                } else {
                    playerData.setPathToFollow(player, bottomRightCorner.x,  bottomRightCorner.y);
                }
                playerData.goingToTopLeftCorner = !playerData.goingToTopLeftCorner;
            }

            // Make the player follow the path
            playerData.followPath(player, api);

            // Shoot if you see an opponent and your gun is loaded
            if (player.opponentsInView.length > 0 && player.canShoot)
                api.shoot(player.id);
        }
    }

    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }
}
