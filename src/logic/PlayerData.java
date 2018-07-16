package logic;

import com.adamldavis.pathfinder.PathGrid;
import lia.Api;
import lia.api.Player;

public class PlayerData {

    public int id;
    public PathFollower follower;
    public PathGrid grid;
    public Vector2 position;

    public PlayerData(int id, PathGrid grid) {
        this.id = id;
        this.grid = grid;
        this.position = new Vector2();
    }

    public void setPathFollower(float xPlayer, float yPlayer, float x, float y) {
        follower = new PathFollower(grid, (int) xPlayer, (int) yPlayer, (int) x, (int) y);
    }

    public boolean followPath(Player player, Api api) {
        return follower.follow(player, api);
    }
}
