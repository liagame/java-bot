import lia.Api;
import lia.Callable;
import lia.NetworkingClient;
import lia.api.MapData;
import lia.api.StateUpdate;
import lia.api.UnitData;

/**
 * This is your bot. Implement it with your logic.
 *
 * In this example bot keeps sending units to random locations on map
 * and is able to shoot when it sees an opponent, it does not aim at
 * him though!
 */
public class MyBot implements Callable {

    // Here we will store a map that we will receive from game engine at the
    // start of the game.
    // x=0, y=0 presents a bottom left corner of the map, and the value on
    // map is true if there is an obstacle
    boolean[][] map;

    // Called only once when the game is initialized holding data about the map.
    @Override
    public synchronized void process(MapData mapData) {
        // We store the map that game uses in our variable.
        // https://docs.liagame.com/api/#mapdata for the data you receive
        map = mapData.map;
    }

    // Repeatedly called 10 times per second from game engine with
    // game state updates. Here is where all the things happen.
    @Override
    public synchronized void process(StateUpdate stateUpdate, Api api) {

        // Iterate through the data of all of your units
        for (int i = 0; i < stateUpdate.units.length; i++) {
            UnitData unit = stateUpdate.units[i];

            // navigationPath is a field of your unit that shows the path
            // on which the unit is currently using if you have sent it
            // to a location using a api.navigationStart method.
            // If it is empty it means there is no path set. In this case
            // we choose a new destination and send the unit there.
            if (unit.navigationPath.length == 0) {
                int[] point = randomValidPointOnMap();

                api.navigationStart(unit.id, point[0], point[1]); // x and y
            }

            // If the unit has an opponent in it's viewing area then
            // make it shoot
            if (unit.opponentsInView.length > 0) {
                api.shoot(unit.id);
            }
        }
    }

    // Finds a random point on the map that is not on the obstacle
    int[] randomValidPointOnMap() {
        int minDistanceToMapEdge = 2;
        int x, y;

        // Generate new x and y until you get one that is not placed on an obstacle
        do {
            x = (int) (Math.random() * (map.length - 2 * minDistanceToMapEdge)) + minDistanceToMapEdge;
            y = (int) (Math.random() * (map[0].length - 2 * minDistanceToMapEdge)) + minDistanceToMapEdge;
        } while (map[x][y]); // if true it means at (x,y) in map there is an obstacle

        return new int[]{x, y};
    }

    // This connects your bot to Lia game engine
    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }
}
