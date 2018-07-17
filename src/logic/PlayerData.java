package logic;

import com.adamldavis.pathfinder.PathGrid;
import lia.Api;
import lia.api.Player;

public class PlayerData {

    public int id;
    public PathFollower follower;
    public PathGrid grid;
    public Vector2 position;
    public boolean pathNotSet;
    public boolean goingToTopLeftCorner;

    public PlayerData(int id, PathGrid grid) {
        this.id = id;
        this.grid = grid;
        this.position = new Vector2();
        this.pathNotSet = true;
    }

    public void setPathToFollow(Player player, float x, float y) {
        follower = new PathFollower(grid, (int) player.x, (int) player.y, (int) x, (int) y);
        pathNotSet = false;
    }

    public void followPath(Player player, Api api) {
        if (follower == null) return;
        pathNotSet = follower.follow(player, api);
    }
}
