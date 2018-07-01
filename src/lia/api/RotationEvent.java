package lia.api;

public class RotationEvent {
    public EventType type;
    public int playerId;
    public Rotation rotation;

    public RotationEvent(EventType type, int playerId, Rotation rotation) {
        this.type = type;
        this.playerId = playerId;
        this.rotation = rotation;
    }
}
