package lia.api;

public class StateUpdate {
    public long uid;
    public MessageType type;
    public float time;
    public UnitData[] units;

    public StateUpdate(long uid, MessageType type, float time, UnitData[] units) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.units = units;
    }
}


