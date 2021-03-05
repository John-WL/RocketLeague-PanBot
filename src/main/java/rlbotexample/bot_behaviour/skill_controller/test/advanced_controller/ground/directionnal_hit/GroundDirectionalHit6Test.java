package rlbotexample.bot_behaviour.skill_controller.test.advanced_controller.ground.directionnal_hit;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.flyve.FlyveBot;
import rlbotexample.bot_behaviour.skill_controller.implementation.advanced.offense.ground_hit.ground_directional_hit_4.GroundDirectionalHit4;
import rlbotexample.bot_behaviour.skill_controller.implementation.advanced.offense.ground_hit.ground_directional_hit_6.GroundDirectionalHit6;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.math.vector.Vector3;

public class GroundDirectionalHit6Test extends FlyveBot {

    private GroundDirectionalHit6 groundDirectionalHit;
    private Vector3 ballDestination;

    public GroundDirectionalHit6Test() {
        groundDirectionalHit = new GroundDirectionalHit6(this);
        ballDestination = new Vector3(0, -6000, 100);
    }

    // called every frame
    @Override
    public BotOutput processInput(DataPacket input, GameTickPacket packet) {
        groundDirectionalHit.setDestinationTrajectory(input.statePrediction.ballAsTrajectory());
        groundDirectionalHit.setOrientationTrajectory(time -> ballDestination.minus(input.statePrediction.ballAtTime(time).position).normalized());
        groundDirectionalHit.updateOutput(input);

        return super.output();
    }

    @Override
    public void updateGui(Renderer renderer, DataPacket input, double currentFps, double averageFps, long botExecutionTime) {
        super.updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
        groundDirectionalHit.debug(renderer, input);
    }
}
