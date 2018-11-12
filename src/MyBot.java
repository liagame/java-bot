import lia.api.*;
import lia.*;

/**
 * Here is where you control your bot. Initial implementation keeps sending units
 * to random locations on the map and makes them shoot when they see opponents.
 */
public class MyBot implements Bot {

    // Here we store the map as a 2D array of booleans. If map[x][y] equals True that
    // means that at (x,y) there is an obstacle. x=0, y=0 points to bottom left corner.
    boolean[][] map;

    // In this method we receive the basic information about the game environment.
    // - GameEnvironment reference: TODO link
    @Override
    public synchronized void processGameEnvironment(GameEnvironment gameEnvironment) {

        // We store the game map so that we can use it later.
        map = gameEnvironment.map;
    }

    // This is the main method where you control your bot. 10 times per game second it receives
    // current game state. Use Api object to call actions on your units.
    // - GameState reference: TODO link
    // - Api reference:       TODO link
    @Override
    public synchronized void processGameState(GameState gameState, Api api) {

        // We iterate through all of our units that are still alive.
        for (int i = 0; i < gameState.units.length; i++) {
            UnitData unit = gameState.units[i];

            // If the unit is not going anywhere, we choose a new valid point on the
            // map and send the unit there.
            if (unit.navigationPath.length == 0) {

                int x, y;

                // Generate new x and y until you get a position on the map where there
                // is no obstacle.
                while (true) {
                    x = (int) (Math.random() * (map.length));
                    y = (int) (Math.random() * (map[0].length));
                    // False means that on (x,y) there is no obstacle.
                    if (!map[x][y]) {
                        break;
                    }
                }

                // Make the unit go to the chosen x and y.
                api.navigationStart(unit.id, x, y);
            }

            // If the unit sees an opponent then make it shoot.
            if (unit.opponentsInView.length > 0) {
                OpponentInView opponent = unit.opponentsInView[0];
                float angle = MathUtil.angleBetweenUnitAndPoint(unit, opponent.x, opponent.y);

                api.setSpeed(unit.id, Speed.NONE);

                if (angle < 0) {
                    api.setRotation(unit.id, Rotation.RIGHT);
                } else {
                    api.setRotation(unit.id, Rotation.LEFT);
                }

                api.shoot(unit.id);

                // Don't forget to make your unit brag. :)
                api.saySomething(unit.id, "I see you!");
            }

            if (unit.opponentsInView.length == 0) {

                // Check if some teammate detected an opponent near the unit. If
                // it did then send the unit to the location of the opponent.
                for (UnitData teammate : gameState.units) {
                    boolean found = false;

                    for (OpponentInView opponent : teammate.opponentsInView) {
                        // Calculate the distance between the unit and opponent.
                        float dst = MathUtil.distance(unit.x, unit.y, opponent.x, opponent.y);

                        if (dst < 25) {
                            // We have detected an opponent that is very close!
                            api.navigationStart(unit.id, opponent.x, opponent.y);
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
            }
        }
    }

    // Connects your bot to Lia game engine, don't change it.
    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }
}
