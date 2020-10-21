package rlbotexample.bot_behaviour.skill_controller.implementation.elementary.jump;

import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.math.vector.Vector3;

public abstract class JumpType {
    private final int jumpDuration;
    private int currentJumpCallCounter;

    public JumpType(int jumpDuration) {
        this.jumpDuration = jumpDuration;
        this.currentJumpCallCounter = 0;
    }

    public abstract void jump(DataPacket input, BotOutput output, Vector3 jumpDestination);

    public void updateCurrentJumpCallCounter() {
        currentJumpCallCounter++;
    }

    public int getCurrentJumpCallCounter() {
        return currentJumpCallCounter;
    }

    public boolean isJumpFinished() {
        return currentJumpCallCounter > jumpDuration;
    }
}
