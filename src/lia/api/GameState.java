package lia.api;

public class GameState {
    public long uid;
    public MessageType type;
    public float time;
    public int nOpponentsAlive;
    public UnitData[] units;

    public GameState(long uid, MessageType type, float time, int nOpponentsAlive, UnitData[] units) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.nOpponentsAlive = nOpponentsAlive;
        this.units = units;
    }
}


