package rlbotexample.bot_behaviour.skill_controller.test.advanced_controller.ground.dribble;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.flyve.FlyveBot;
import rlbotexample.bot_behaviour.skill_controller.implementation.advanced.offense.ground_dribble.dribble5.Dribble5;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.game_situation.trainning_pack.TrainingPack;

public class Dribble5Test extends FlyveBot {

    private Dribble5 dribbleController;
    private TrainingPack gameSituationHandler;

    public Dribble5Test() {
        //gameSituationHandler = new CircularTrainingPack();
        //gameSituationHandler.add(new GroundDribbleSetup1());
        //gameSituationHandler.add(new GroundDribbleSetup2());
        dribbleController = new Dribble5(this);
    }

    // called every frame
    @Override
    public BotOutput processInput(DataPacket input, GameTickPacket packet) {
        //if(gameSituationHandler.canLoad(input)) {
            //gameSituationHandler.update();
        //}

        dribbleController.throttle(0);
        dribbleController.steer(1);
        dribbleController.updateOutput(input);

        return super.output();
    }

    @Override
    public void updateGui(Renderer renderer, DataPacket input, double currentFps, double averageFps, long botExecutionTime) {
        super.updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
        dribbleController.debug(renderer, input);

    }
}