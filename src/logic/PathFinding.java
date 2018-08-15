package logic;

import lia.api.ObstacleData;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

public class PathFinding {

    private static final int OFFSET_FROM_OBSTACLE = 1;


    public static NavigationGrid<GridCell> createGridNavigator(int mapWidth, int mapHeight, ObstacleData[] obstacles) {
        // Initialize grid and set every cell to walkable
        GridCell[][] cells = new GridCell[mapWidth][mapHeight];
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                cells[x][y] = new GridCell(x, y, true);
            }
        }

        // Set grid to false where we don't want for units to move (based on the
        // positions of obstacles)
        for (ObstacleData obstacle : obstacles) {
            int x1 = (int) obstacle.x - OFFSET_FROM_OBSTACLE;
            int x2 = (int) (obstacle.x + obstacle.width) + OFFSET_FROM_OBSTACLE;

            for (int x = x1; x < x2; x++) {
                int y1 = (int) obstacle.y - OFFSET_FROM_OBSTACLE;
                int y2 = (int) (obstacle.y + obstacle.height) + OFFSET_FROM_OBSTACLE;

                for (int y = y1; y < y2; y++) {
                    // If x and y are in obstacle or very close to it, set cell to not walkable
                    if (x < mapWidth && x >= 0 && y < mapHeight && y >= 0) {
                        cells[x][y].setWalkable(false);
                    }
                }
            }
        }

        // Create a navigation grid with the cells you just created
        return new NavigationGrid<>(cells, true);
    }
}
