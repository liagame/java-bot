package lia.api;

public class PlayerLocation {
    public int id;
    public float x;
    public float y;
    public float orientation;

    public PlayerLocation(int id, float x, float y, float orientation) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
}
