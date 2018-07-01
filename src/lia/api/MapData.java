package lia.api;

public class MapData {
    public long uid;
    public MessageType type;
    public float width;
    public float height;
    public Obstacle[] obstacles;

    public MapData(long uid, MessageType type, float width, float height, Obstacle[] obstacles) {
        this.uid = uid;
        this.type = type;
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }
}
