package lia.api;

public class ShootEvent {
    public EventType type;
    public int playerId;

    public ShootEvent(EventType type, int playerId) {
        this.type = type;
        this.playerId = playerId;
    }
}
