package rlbotexample.input.dynamic_data;


import rlbot.flat.BallInfo;
import rlbotexample.input.prediction.AdvancedBallPrediction;
import rlbotexample.input.prediction.Predictions;
import util.game_constants.RlConstants;
import util.vector.Vector3;

import java.util.List;

/**
 * Basic information about the getNativeBallPrediction.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it.
 */
public class BallData {
    public final Vector3 position;
    public final Vector3 velocity;
    public final Vector3 spin;
    public final double time;

    public BallData(final BallInfo ball) {
        this.position = new Vector3(ball.physics().location());
        this.velocity = new Vector3(ball.physics().velocity());
        this.spin = new Vector3(ball.physics().angularVelocity());
        this.time = 0;
    }

    public BallData(final Vector3 position, final Vector3 velocity, final Vector3 spin, final double time) {
        this.position = position;
        this.velocity = velocity;
        this.spin = spin;
        this.time = time;
    }

    public final Vector3 surfaceVelocity(final Vector3 normal) {
        return normal.crossProduct(spin).scaled(RlConstants.BALL_RADIUS);
    }
}
