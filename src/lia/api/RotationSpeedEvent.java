package lia.api;

public class RotationSpeedEvent {
    public int index;
    public int unitId;
    public RotationSpeed rotationSpeed;

    public RotationSpeedEvent(int index, int unitId, RotationSpeed rotationSpeed) {
        this.index = index;
        this.unitId = unitId;
        this.rotationSpeed = rotationSpeed;
    }
}
