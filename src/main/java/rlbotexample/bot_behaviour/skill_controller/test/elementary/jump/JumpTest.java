package rlbotexample.bot_behaviour.skill_controller.test.elementary.jump;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.flyve.FlyveBot;
import rlbotexample.bot_behaviour.flyve.debug.player_prediction.DebugPlayerPredictedTrajectory;
import rlbotexample.bot_behaviour.skill_controller.implementation.elementary.jump.JumpController;
import rlbotexample.bot_behaviour.skill_controller.implementation.elementary.jump.types.*;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.game_constants.RlConstants;
import util.math.vector.Vector;
import util.math.vector.Vector3;

public class JumpTest extends FlyveBot {

    private JumpController jumpController;

    public JumpTest() {
        jumpController = new JumpController(this);
    }

    // called every frame
    @Override
    public BotOutput processInput(DataPacket input, GameTickPacket packet) {
        // do the thing

        //Vector3 destination = input.ball.position;
        Vector3 destination = new Vector3();

        //if(System.currentTimeMillis() % 3000 < 1500) {
            jumpController.setFirstJumpType(new SimpleJump(), input);
            DoubleWaveDash doubleWaveDash = new DoubleWaveDash(this, input);
            //waveDash.setDesiredFrontOrientation(destination);
            jumpController.setSecondJumpType(doubleWaveDash, input);
            jumpController.setJumpDestination(destination);
            jumpController.updateOutput(input);
            output().drift(true);
        //}

        //System.out.println("First Jump: " + input.allCars.get(1-input.car.playerIndex).hasFirstJump);
        //System.out.println("Second Jump: " + input.allCars.get(1-input.car.playerIndex).hasSecondJump);

        // return the calculated bot output
        return super.output();
    }

    @Override
    public void updateGui(Renderer renderer, DataPacket input, double currentFps, double averageFps, long botExecutionTime) {
        super.updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
        jumpController.debug(renderer, input);
        new DebugPlayerPredictedTrajectory().updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
    }
}