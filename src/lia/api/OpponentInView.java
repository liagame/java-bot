package lia.api;

public class OpponentInView {
    public int id;
    public int health;
    public float x;
    public float y;
    public float orientation;
    public Speed speed;
    public Rotation rotation;

    public OpponentInView(int id, int health, float x, float y, float orientation, Speed speed, Rotation rotation) {
        this.id = id;
        this.health = health;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.speed = speed;
        this.rotation = rotation;
    }
}
