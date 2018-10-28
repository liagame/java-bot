package lia.api;

public class StateUpdate {
    public long uid;
    public MessageType type;
    public float time;
    public int nOpponentsAlive;
    public UnitData[] units;

    public StateUpdate(long uid, MessageType type, float time, int nOpponentsAlive, UnitData[] units) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.nOpponentsAlive = nOpponentsAlive;
        this.units = units;
    }
}


