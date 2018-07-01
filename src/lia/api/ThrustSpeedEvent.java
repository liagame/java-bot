package lia.api;

public class ThrustSpeedEvent {
    public EventType type;
    public int playerId;
    public ThrustSpeed speed;

    public ThrustSpeedEvent(EventType type, int playerId, ThrustSpeed speed) {
        this.type = type;
        this.playerId = playerId;
        this.speed = speed;
    }
}
