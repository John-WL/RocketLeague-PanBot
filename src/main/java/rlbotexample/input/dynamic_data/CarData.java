package rlbotexample.input.dynamic_data;

import util.vector.Vector3;

public class CarData {

    public final Vector3 position;
    public final Vector3 velocity;
    public final Vector3 spin;
    public final double boost;
    public final HitBox hitBox;
    public final double elapsedSeconds;

    public CarData(Vector3 position, Vector3 velocity, Vector3 spin, double boostAmount, HitBox hitBox, double time) {
        this.position = position;
        this.velocity = velocity;
        this.spin = spin;
        this.boost = boostAmount;
        this.hitBox = hitBox;
        this.elapsedSeconds = time;
    }

    public CarData(rlbot.flat.PlayerInfo playerInfo, float elapsedSeconds) {
        this.position = new Vector3(playerInfo.physics().location());
        this.velocity = new Vector3(playerInfo.physics().velocity());
        this.spin = new Vector3(playerInfo.physics().angularVelocity());
        this.boost = playerInfo.boost();
        final CarOrientation orientation = CarOrientation.fromFlatbuffer(playerInfo);
        this.hitBox = new HitBox(position, playerInfo.hitboxOffset(), playerInfo.hitbox(), orientation.noseVector, orientation.roofVector);
        this.elapsedSeconds = elapsedSeconds;
    }
}
