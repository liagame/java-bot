package logic;

import lia.Api;
import org.xguzm.pathfinding.PathFinder;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;

public class Unit {

    public int id;
    public PathFollower follower;
    public NavigationGrid<GridCell> grid;
    public PathFinder<GridCell> gridFinder;
    public boolean pathNotSet;
    public boolean goingToTopLeftCorner;

    public Unit(int id, NavigationGrid<GridCell> grid) {
        this.id = id;
        this.grid = grid;
        this.pathNotSet = true;

        // Create pathfinder
        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = true;
        gridFinder = new ThetaStarGridFinder<>(GridCell.class, opt);
    }

    public void setPathToFollow(lia.api.UnitData unit, float x, float y) {
        follower = new PathFollower(grid, gridFinder, (int) unit.x, (int) unit.y, (int) x, (int) y);
        pathNotSet = false;
    }

    public void followPath(lia.api.UnitData unit, Api api) {
        if (follower == null) return;
        pathNotSet = follower.follow(unit, api);
    }
}
