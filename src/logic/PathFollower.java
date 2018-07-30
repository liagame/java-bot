package logic;

import lia.Api;
import lia.api.Unit;
import lia.api.Rotation;
import lia.api.ThrustSpeed;
import org.xguzm.pathfinding.PathFinder;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

import java.util.List;

import static logic.Constants.RADIANS_TO_DEGREES;

public class PathFollower {

    private static final float ALLOWED_UNIT_OFFSET = 2f;
    private static final float ALLOWED_ANGLE_OFFSET = 15f;

    private List<GridCell> cells;
    private int nextCellIndex = 0;

    /**
     * Find and create a path from (x1, y1) to (x2, y2) on specified grid.
     * */
    public PathFollower(NavigationGrid<GridCell> grid,
                        PathFinder<GridCell> finder,
                        int x1, int y1,
                        int x2, int y2) {

        cells = finder.findPath(grid.getCell(x1, y1), grid.getCell(x2, y2), grid);

        optimizePathPoints();
    }

    private void optimizePathPoints() {
        // Removes redundant cells on vertical and horizontal lines
        for (int i = 1; i < cells.size() - 1; i++) {
            GridCell c1 = cells.get(i - 1);
            GridCell c2 = cells.get(i);
            GridCell c3 = cells.get(i + 1);

            if (angle(c1, c2) == angle(c2, c3)) {
                cells.remove(i);
                i--;
            }
        }
    }

    /**
     * Follows the previously chosen path and writes
     * the needed step to api.
     * @return true if the unit is at the last point.
     * */
    public boolean follow(Unit unit, Api api) {
        int x = (int) unit.x;
        int y = (int) unit.y;

        GridCell nextCell;

        // If unit is close enough to the nextCell then
        // move to the next
        while (true) {
            if (nextCellIndex >= cells.size()) return true;

            int oldIndex = nextCellIndex;
            nextCell = cells.get(nextCellIndex);

            if (Math.abs(nextCell.x - x) < ALLOWED_UNIT_OFFSET &&
                    Math.abs(nextCell.y - y) < ALLOWED_UNIT_OFFSET) {

                nextCellIndex++;

                if (nextCellIndex == cells.size()) {
                    // No more cells to visit
                    return true;
                }
            }
            if (oldIndex == nextCellIndex) {
                break;
            }
        }

        // Get angle between current point and nextCell
        float angle = getAngleToRotate(unit, nextCell);

        // If the angle is small enough are close move straight
        if (Math.abs(angle) < ALLOWED_ANGLE_OFFSET) {
            api.setRotationSpeed(unit.id, Rotation.NONE);
            api.setThrustSpeed(unit.id, ThrustSpeed.FORWARD);
        }
        // Else rotate to the needed direction
        else {
            if (Math.abs(angle) > ALLOWED_ANGLE_OFFSET * 1.3f) {
                api.setThrustSpeed(unit.id, ThrustSpeed.NONE);
            } else if (unit.thrustSpeed == ThrustSpeed.NONE) {
                api.setThrustSpeed(unit.id, ThrustSpeed.FORWARD);
            }
            if (angle < 0f) {
                api.setRotationSpeed(unit.id, Rotation.RIGHT);
            } else {
                api.setRotationSpeed(unit.id, Rotation.LEFT);
            }
        }

        return false;
    }

    private float getAngleToRotate(Unit unit, GridCell c) {
        int x = c.x - (int) unit.x;
        int y = c.y - (int) unit.y;

        double angleUnitToCell = Math.atan2(y, x) * RADIANS_TO_DEGREES;
        if (angleUnitToCell < 0) angleUnitToCell += 360f;

        float angle = (float) angleUnitToCell - unit.orientation;
        if (angle > 180) angle -= 360;
        else if (angle < -180) angle += 360;

        return angle;
    }

    private float angle(GridCell c1, GridCell c2) {
        int x = c2.x - c1.x;
        int y = c2.y - c1.y;

        double angle = Math.atan2(y, x) * RADIANS_TO_DEGREES;
        if (angle < 0) angle += 360f;
        return (float) angle;
    }
}
