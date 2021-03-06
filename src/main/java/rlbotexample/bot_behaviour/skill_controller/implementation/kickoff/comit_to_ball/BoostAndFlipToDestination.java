package rlbotexample.bot_behaviour.skill_controller.implementation.kickoff.comit_to_ball;

import rlbot.render.Renderer;
import rlbotexample.bot_behaviour.flyve.BotBehaviour;
import rlbotexample.bot_behaviour.skill_controller.SkillController;
import rlbotexample.bot_behaviour.skill_controller.implementation.elementary.jump.JumpController;
import rlbotexample.input.dynamic_data.DataPacket;
import rlbotexample.output.BotOutput;
import util.math.vector.Vector3;

public class BoostAndFlipToDestination extends SkillController {

    private Vector3 destination;
    private final BotBehaviour bot;

    private JumpController jumpHandler;
    private boolean hasJumped;

    private int callCounter;

    public BoostAndFlipToDestination(BotBehaviour bot) {
        this.bot = bot;
        this.jumpHandler = new JumpController(bot);
        this.callCounter = 0;
    }

    public void setDestination(Vector3 destination) {
        this.destination = destination;
    }

    @Override
    public void updateOutput(DataPacket input) {
        updateJumpBehaviour(input);
    }

    private void updateJumpBehaviour(DataPacket input) {
        BotOutput output = bot.output();
        Vector3 playerPosition = input.car.position;
        Vector3 playerNoseVector = input.car.orientation.noseVector;
        Vector3 playerRoofVector = input.car.orientation.roofVector;
        Vector3 ballPosition = input.ball.position;

        Vector3 localDestination = ballPosition.minus(playerPosition).orderedMinusAngle(playerNoseVector);

        callCounter++;
    }

    @Override
    public void setupController() {

    }

    @Override
    public void debug(Renderer renderer, DataPacket input) {
    }
}