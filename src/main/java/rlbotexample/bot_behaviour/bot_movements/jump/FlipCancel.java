package rlbotexample.bot_behaviour.bot_movements.jump;

import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.vector.Vector3;

public class FlipCancel extends JumpType {

    private static final int JUMP_DURATION = 30;
    private static final int[] JUMP_TIME_FRAMES = {2};

    public FlipCancel() {
        super(JUMP_DURATION, JUMP_TIME_FRAMES);
    }

    @Override
    void jump(DataPacket input, BotOutput output, Vector3 desiredFrontOrientation, Vector3 desiredRoofOrientation) {
        updateCurrentJumpCallCounter();

        if(this.getCurrentJumpCallCounter() >= JUMP_TIME_FRAMES[0]) {
            Vector3 flipDirections = desiredFrontOrientation.normalized();
            output.pitch(-flipDirections.x);
            output.yaw(-flipDirections.y);
            //output.withRoll(desiredRoofOrientation.y);
        }
        if(this.getCurrentJumpCallCounter() + 1 == JUMP_TIME_FRAMES[0]) {
            // send a "no-jump" so we can jump a second time the next frame
            setJumpState(false);
        }
        else {
            setJumpState(getCurrentJumpCallCounter() <= JUMP_DURATION);
        }
    }
}
