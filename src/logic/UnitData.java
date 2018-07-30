package logic;

import lia.Api;
import lia.api.Unit;
import org.xguzm.pathfinding.PathFinder;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;

public class UnitData {

    public int id;
    public PathFollower follower;
    public NavigationGrid<GridCell> grid;
    public PathFinder<GridCell> gridFinder;
    public boolean pathNotSet;
    public boolean goingToTopLeftCorner;

    public UnitData(int id, NavigationGrid<GridCell> grid) {
        this.id = id;
        this.grid = grid;
        this.pathNotSet = true;

        // Create pathfinder
        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = true;
        gridFinder = new ThetaStarGridFinder<>(GridCell.class, opt);
    }

    public void setPathToFollow(Unit unit, float x, float y) {
        follower = new PathFollower(grid, gridFinder, (int) unit.x, (int) unit.y, (int) x, (int) y);
        pathNotSet = false;
    }

    public void followPath(Unit unit, Api api) {
        if (follower == null) return;
        pathNotSet = follower.follow(unit, api);
    }
}
