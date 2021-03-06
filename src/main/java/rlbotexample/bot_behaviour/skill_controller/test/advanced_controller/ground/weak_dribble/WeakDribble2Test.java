package rlbotexample.bot_behaviour.skill_controller.test.advanced_controller.ground.weak_dribble;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.flyve.FlyveBot;
import rlbotexample.bot_behaviour.skill_controller.implementation.advanced.offense.weak_dribble.WeakDribble2;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.input.dynamic_data.goal.StandardMapGoals;
import rlbotexample.output.BotOutput;
import util.game_situation.trainning_pack.TrainingPack;

public class WeakDribble2Test extends FlyveBot {

    private WeakDribble2 dribbleController;
    private TrainingPack gameSituationHandler;

    public WeakDribble2Test() {
        //gameSituationHandler = new CircularTrainingPack();
        //gameSituationHandler.add(new GroundDribbleSetup1());
        //gameSituationHandler.add(new GroundDribbleSetup2());
        dribbleController = new WeakDribble2(this);
    }

    // called every frame
    @Override
    public BotOutput processInput(DataPacket input, GameTickPacket packet) {
        //if(gameSituationHandler.canLoad(input)) {
            //gameSituationHandler.update();
        //}

        dribbleController.setBallDestination(
                input.statePrediction.ballAsTrajectory()
                        .andThen(ballPosition -> StandardMapGoals.getOpponent(input.team)
                                .closestPointOfBallOnSurface(ballPosition).orElse(null)));
        dribbleController.updateOutput(input);

        return super.output();
    }

    @Override
    public void updateGui(Renderer renderer, DataPacket input, double currentFps, double averageFps, long botExecutionTime) {
        super.updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
        dribbleController.debug(renderer, input);
    }
}