import lia.Api;
import lia.Bot;
import lia.NetworkingClient;
import lia.api.GameEnvironment;
import lia.api.GameState;
import lia.api.UnitData;

/**
 * This is your bot that will play the game for you.
 *
 * Implementation below keeps sending units to random locations on the map
 * and makes them shoot when they see opponents.
 */
public class MyBot implements Bot {

    // Here we will store the map that the game will be played on. Map has values set
    // to True where there is an obstacle. You can access (x,y) coordinate by calling
    // map[x][y] and x=0, y=0 is placed at the bottom left corner.
    boolean[][] map;

    // This method is called only once at the beginning of the game and holds the basic
    // information about the game environment.
    // - GameEnvironment reference: TODO link
    @Override
    public synchronized void processGameEnvironment(GameEnvironment gameEnvironment) {

        // Here we store the map that is used in current game. We will use it later.
        map = gameEnvironment.map;
    }

    // This method is the main part of your bot. It is called 10 times per game seconds and
    // it holds game's current state and the Api object which you use to control your units.
    // - GameState reference: TODO link
    // - Api reference:       TODO link
    @Override
    public synchronized void processGameState(GameState gameState, Api api) {

        // First we iterate through all of our units that are still alive.
        for (int i = 0; i < gameState.units.length; i++) {
            UnitData unit = gameState.units[i];

            // With api.navigationStart(...) method you can send your units to go to a
            // specified point on the map all by themselves. If they are currently going
            // somewhere then their path is stored in navigationPath field. If the field
            // is empty the unit is not going anywhere automatically. Here, if the unit
            // is not going anywhere we choose a new location and send it there.
            if (unit.navigationPath.length == 0) {

                // Generate a point on the map where there is no obstacle.
                int[] point = randomValidPointOnMap();

                // Make the unit go to the chosen x (point[0]) and y (point[1]).
                api.navigationStart(unit.id, point[0], point[1]);
            }

            // If the unit sees an opponent then make it shoot.
            if (unit.opponentsInView.length > 0) {
                api.shoot(unit.id);
            }
        }
    }

    // Finds a random point on the map where there is no obstacle.
    int[] randomValidPointOnMap() {
        // Generate new x and y until you get a position that is not placed on an obstacle.
        while (true) {
            int x = (int) (Math.random() * (map.length));
            int y = (int) (Math.random() * (map[0].length));
            // False means that on (x,y) there is no obstacle.
            if (!map[x][y]) {
                return new int[]{x, y};
            }
        }
    }

    // This connects your bot to Lia game engine, don't change it.
    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }
}
