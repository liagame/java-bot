package lia.api;

public class StateUpdate {
    public long uid;
    public MessageType type;
    public float time;
    public Player[] players;

    public StateUpdate(long uid, MessageType type, float time, Player[] players) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.players = players;
    }
}


