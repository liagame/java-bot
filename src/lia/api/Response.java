package lia.api;

public class Response {
    public long uid;
    public MessageType type;
    public SpeedEvent[] speedEvents;
    public RotationSpeedEvent[] rotationSpeedEvents;
    public ShootEvent[] shootEvents;
    public NavigationStartEvent[] navigationStartEvents;
    public NavigationStopEvent[] navigationStopEvents;

    public Response(long uid, MessageType type,
                    SpeedEvent[] speedEvents,
                    RotationSpeedEvent[] rotationSpeedEvents,
                    ShootEvent[] shootEvents,
                    NavigationStartEvent[] navigationStartEvents,
                    NavigationStopEvent[] navigationStopEvents) {
        this.uid = uid;
        this.type = type;
        this.speedEvents = speedEvents;
        this.rotationSpeedEvents = rotationSpeedEvents;
        this.shootEvents = shootEvents;
        this.navigationStartEvents = navigationStartEvents;
        this.navigationStopEvents = navigationStopEvents;
    }
}
