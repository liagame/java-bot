import lia.Api;
import lia.Callable;
import lia.NetworkingClient;
import lia.api.*;
import logic.UnitData;
import logic.PathFinding;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

import java.util.HashMap;


/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    private HashMap<Integer, UnitData> unitsData = new HashMap<>();

    private int[] bottomRightCorner = new int[]{110, 2};
    private int[] topLeftCorner =  new int[]{2, 60};

    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {
        // Convert map with obstacles to a grid that will be used for
        // path finding algorithm
        NavigationGrid<GridCell> grid = PathFinding.createGridNavigator(
                (int) mapData.width,
                (int) mapData.height,
                mapData.obstacles
        );

        // Store data related to each unit in UnitData object.
        // All UnitData objects will be accessible through unitsData map.
        for (UnitLocation unit : mapData.unitLocations) {
            UnitData data = new UnitData(unit.id, grid);
            unitsData.put(unit.id, data);
        }
    }

    /** Repeatedly called from game engine with game state updates.  */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Api api) {
        // Go through all units
        for (Unit unit : stateUpdate.units) {
            // Get data for this unit
            UnitData unitData = unitsData.get(unit.id);

            // If the unit is not following any path, set it
            if (unitData.pathNotSet) {
                if (unitData.goingToTopLeftCorner) {
                    unitData.setPathToFollow(unit, topLeftCorner[0],  topLeftCorner[1]);
                } else {
                    unitData.setPathToFollow(unit, bottomRightCorner[0],  bottomRightCorner[1]);
                }
                unitData.goingToTopLeftCorner = !unitData.goingToTopLeftCorner;
            }

            // Make the unit follow the path
            unitData.followPath(unit, api);

            // Shoot if you see an opponent and your gun is loaded
            if (unit.opponentsInView.length > 0 && unit.canShoot)
                api.shoot(unit.id);
        }
    }

    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }
}
