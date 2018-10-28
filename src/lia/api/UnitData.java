package lia.api;

public class UnitData {
    public int id;
    public int health;
    public float x;
    public float y;
    public float orientation;
    public Speed speed;
    public RotationSpeed rotationSpeed;
    public boolean canShoot;
    public int nBullets;
    public OpponentInView[] opponentsInView;
    public BulletInView[] bulletsInView;
    public Point[] navigationPath;

    public UnitData(int id,
                    int health,
                    float x, float y,
                    float orientation,
                    Speed speed,
                    RotationSpeed rotationSpeed,
                    boolean canShoot,
                    int nBullets,
                    OpponentInView[] opponentsInView,
                    BulletInView[] bulletsInView,
                    Point[] navigationPath) {
        this.id = id;
        this.health = health;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.canShoot = canShoot;
        this.nBullets = nBullets;
        this.opponentsInView = opponentsInView;
        this.bulletsInView = bulletsInView;
        this.navigationPath = navigationPath;
    }
}

