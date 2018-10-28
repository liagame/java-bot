package lia.api;

public class MapData {
    public long uid;
    public MessageType type;
    public boolean[][] map;
    public UnitLocation[] unitLocations;

    public MapData(long uid, MessageType type, boolean[][] map, UnitLocation[] unitLocations) {
        this.uid = uid;
        this.type = type;
        this.map = map;
        this.unitLocations = unitLocations;
    }
}
