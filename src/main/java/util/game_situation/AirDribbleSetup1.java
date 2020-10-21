package util.game_situation;

import rlbot.gamestate.*;
import util.timer.FrameTimer;
import util.timer.Timer;

public class AirDribbleSetup1 extends GameSituation {

    public AirDribbleSetup1() {
        super(new FrameTimer(10*30));
    }

    @Override
    public void loadGameState() {
        GameState gameState = getCurrentGameState();
        gameState.withBallState(new BallState(new PhysicsState()
                .withLocation(new DesiredVector3(0f, 0f, 300f))
                .withVelocity(new DesiredVector3(0f, 0f, 0f))
                .withRotation(new DesiredRotation(0f, 0f, 0f))
                .withAngularVelocity(new DesiredVector3(0f, 0f, 0f))));

        gameState.withCarState(0, new CarState()
                .withPhysics(new PhysicsState()
                        .withLocation(new DesiredVector3(0f, 0f, 50f))
                        .withVelocity(new DesiredVector3(0f, 0f, 500f))
                        .withRotation(new DesiredRotation((float)Math.PI/2, (float)-Math.PI/2, 0f))
                        .withAngularVelocity(new DesiredVector3(0f, 0f, 0f)))
                .withBoostAmount(100f));

        applyGameState(gameState);
    }
}
