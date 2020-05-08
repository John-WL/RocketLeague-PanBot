package parameter_search.air_dribbling;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.basic_skills.AirDribbleTest5;
import rlbotexample.bot_behaviour.basic_skills.SkillController;
import rlbotexample.bot_behaviour.car_destination.CarDestination;
import rlbotexample.bot_behaviour.panbot.PanBot;
import rlbotexample.bot_behaviour.path.PathHandler;
import rlbotexample.bot_behaviour.path.test_paths.RandomAerialPoint;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.machine_learning_models.evaluators.AirDribbleEvaluatorLogger2;
import util.machine_learning_models.evaluators.BotEvaluator;
import util.game_situation.AirDribbleSetup1;
import util.game_situation.RemoveResidualVelocity;
import util.game_situation.handlers.FiniteTrainingPack;
import util.game_situation.handlers.GameSituationHandler;
import util.machine_learning_models.hyperparameter_search.ParameterBruteForce3;

import java.awt.*;

public class AirDribbleParameterSearcher2 extends PanBot {

    private CarDestination desiredDestination;
    private SkillController skillController;
    private GameSituationHandler trainingPack;
    private PathHandler pathHandler;
    private BotEvaluator botEvaluator;
    private ParameterBruteForce3 hyperParameterBruteForce;
    private AirDribbleParameterSearcherFileData dataRepresentation;

    public AirDribbleParameterSearcher2() {
        trainingPack = new FiniteTrainingPack();
        trainingPack.add(new RemoveResidualVelocity());
        trainingPack.add(new AirDribbleSetup1());

        desiredDestination = new CarDestination();

        pathHandler = new RandomAerialPoint(desiredDestination);
        pathHandler.generateNewPath(null);
        skillController = new AirDribbleTest5(desiredDestination, this);

        dataRepresentation = new AirDribbleParameterSearcherFileData();
        hyperParameterBruteForce = new ParameterBruteForce3<>(dataRepresentation);
        botEvaluator = new AirDribbleEvaluatorLogger2(dataRepresentation.getAirDribbleEvaluatorFileParameter(), desiredDestination);
    }

    // called every frame
    @Override
    public BotOutput processInput(DataPacket input, GameTickPacket packet) {
        // game situations handling
        trainingPack.update();

        // update bot's evaluation so we can know if the updated parameters
        // are better or worse than the current best ones we have
        botEvaluator.updateEvaluation(input);

        // if we completed the pack, then we need to modify the best parameters we have yet
        // so we can restart the training pack with fresh new parameters to test.
        if(trainingPack.hasBeenCompleted()) {
            trainingPack.reset();

            // send the result of the parameter change to the binary searchHandler,
            // so it can know if the change was a great one or a bad one,
            // and act accordingly to that matter.
            double currentEvaluation = botEvaluator.getEvaluation();
            botEvaluator.resetEvaluation();
            hyperParameterBruteForce.sendSearchResult(currentEvaluation);


            // if we searched as much as we wanted
            if(!hyperParameterBruteForce.isDoneSearching()) {
                // modify slightly the parameters for the next training pack sequence
                hyperParameterBruteForce.nextHypothesis();
            }
            else {
                // isolate the best results so we can find them easily after all the file creation that happened
                dataRepresentation.isolateBestResultsInFinalDataFolder();
            }
        }

        // do the thing
        skillController.setupAndUpdateOutputs(input);

        // return the calculated bot output
        return super.output();
    }

    @Override
    public void updateGui(Renderer renderer, DataPacket input, double currentFps, double averageFps, long botExecutionTime) {
        super.updateGui(renderer, input, currentFps, averageFps, botExecutionTime);
        skillController.debug(renderer, input);
        renderer.drawRectangle3d(Color.blue, desiredDestination.getThrottleDestination(), 10, 10, true);
    }
}
