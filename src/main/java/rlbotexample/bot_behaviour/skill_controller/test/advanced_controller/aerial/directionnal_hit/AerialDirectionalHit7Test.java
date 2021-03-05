package rlbotexample.bot_behaviour.skill_controller.test.advanced_controller.aerial.directionnal_hit;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.flyve.FlyveBot;
import rlbotexample.bot_behaviour.flyve.debug.ball_prediction.DebugCustomBallPrediction;
import rlbotexample.bot_behaviour.skill_controller.implementation.advanced.aerial.directionnal_hit.parabola.aerial_directional_hit_7.AerialDirectionalHit7;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.input.prediction.Predictions;
import rlbotexample.output.BotOutput;
import util.game_situation.trainning_pack.TrainingPack;

public class AerialDirectionalHit7Test extends FlyveBot {

    private AerialDirectionalHit7 aerialDirectionalHitController;
    private TrainingPack gameSituationHandler;
    private Predictions predictions;

    public AerialDirectionalHit7Test() {
        aerialDirectionalHitController = new AerialDirectionalHit7(this);
        /*gameSituationHandler = new CircularTrainingPack();
        gameSituationHandler.add(new RemoveResidualVelocity());
        gameSituationHandler.add(new AerialHitSetup1());
        gameSituationHandler.add(new RemoveResidualVelocity());
        gameSituationHandler.add(new AerialHitSetup2());
        gameSituationHandler.add(new RemoveResidualVelocity());
        gameSituationHandler.add(new AerialHitSetup3());
        gameSituationHandler.add(new RemoveResidualVelocity());
        gameSituationHandler.add(new AerialHitSetup4());*/
    }

    // called every frame
    @Override
    public BotOutput processInput(DataPacket input, GameTickPacket packet) {
        // game situation handling
        //gameSituationHandler.update();

        // do the thing
        if (input.team == 1) {
            //aerialDirectionalHitController.setBallDestination(new Vector3(0, -5200, 300));
        } else {
            //aerialDirectionalHitController.setBallDestination(new Vector3(0, 5200, 300));
        }
        aerialDirectionalHitController.updateOutput(input);

        // return the calculated bot output
        return super.output();
    }

    @Override
    public void updateGui(Renderer renderer, DataPacket input, double currentFps, double averageFps, long botExecutionTime) {
        super.updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
        aerialDirectionalHitController.debug(renderer, input);
        new DebugCustomBallPrediction().updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
    }
}
