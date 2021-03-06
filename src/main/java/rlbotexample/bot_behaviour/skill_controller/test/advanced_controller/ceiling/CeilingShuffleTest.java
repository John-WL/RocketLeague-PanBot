package rlbotexample.bot_behaviour.skill_controller.test.advanced_controller.ceiling;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.flyve.FlyveBot;
import rlbotexample.bot_behaviour.skill_controller.implementation.advanced.offense.ground_bounces.CushionBouncyBall;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.game_situation.situations.throwing_player_in_the_air.ThrowingPlayer2;
import util.game_situation.trainning_pack.CircularTrainingPack;
import util.game_situation.trainning_pack.TrainingPack;

public class CeilingShuffleTest extends FlyveBot {

    private TrainingPack gameSituationHandler;

    public CeilingShuffleTest() {
        gameSituationHandler = new CircularTrainingPack();
        gameSituationHandler.add(new ThrowingPlayer2());
    }

    // called every frame
    @Override
    public BotOutput processInput(DataPacket input, GameTickPacket packet) {
        if(gameSituationHandler.updatingWontBreakBot(input)) {
            gameSituationHandler.update();
        }


        return super.output();
    }

    @Override
    public void updateGui(Renderer renderer, DataPacket input, double currentFps, double averageFps, long botExecutionTime) {
        super.updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
    }
}