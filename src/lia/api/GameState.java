package lia.api;

public class GameState {
    public long uid;
    public MessageType type;
    public float time;
    public int numberOfOpponentsAlive;
    public UnitData[] units;

    public GameState(long uid, MessageType type, float time, int numberOfOpponentsAlive, UnitData[] units) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.numberOfOpponentsAlive = numberOfOpponentsAlive;
        this.units = units;
    }
}


