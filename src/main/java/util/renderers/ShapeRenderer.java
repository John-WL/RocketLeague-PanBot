package util.renderers;

import rlbot.render.Renderer;
import rlbotexample.input.dynamic_data.car.HitBox;
import rlbotexample.input.dynamic_data.RlUtils;
import rlbotexample.input.dynamic_data.ground.GroundTrajectory2DInfo;
import rlbotexample.input.prediction.Trajectory3D;
import rlbotexample.input.prediction.gamestate_prediction.GameStatePrediction;
import util.math.vector.Vector2;
import util.shapes.Circle;
import util.shapes.Circle3D;
import util.shapes.Triangle3D;
import util.math.vector.Vector3;

import java.awt.*;

public class ShapeRenderer {

    private final Renderer renderer;

    public ShapeRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void renderCross(Vector3 position, Color color) {
        renderer.drawLine3d(color, position.plus(new Vector3(20, 20, 20)), position.plus(new Vector3(-20, -20, -20)));
        renderer.drawLine3d(color, position.plus(new Vector3(-20, 20, 20)), position.plus(new Vector3(20, -20, -20)));
        renderer.drawLine3d(color, position.plus(new Vector3(20, -20, 20)), position.plus(new Vector3(-20, 20, -20)));
        renderer.drawLine3d(color, position.plus(new Vector3(20, 20, -20)), position.plus(new Vector3(-20, -20, 20)));
    }

    public void renderTriangle(Triangle3D triangle, Color color) {
        renderer.drawLine3d(color, triangle.point0, triangle.point1);
        renderer.drawLine3d(color, triangle.point1, triangle.point2);
        renderer.drawLine3d(color, triangle.point2, triangle.point0);
    }

    public void renderCircle(Circle circle, double zOffset, Color color) {
        int amountOfPoints = 100;
        double precision = Math.PI*2/amountOfPoints;
        Vector2 point = circle.findPointOnCircle(0);
        Vector2 previousPoint;

        for(int i = 1; i < amountOfPoints; i++) {
            previousPoint = point;
            point = circle.findPointOnCircle(i*precision);
            renderer.drawLine3d(color, new Vector3(previousPoint, zOffset), new Vector3(point, zOffset));
        }
    }

    public void renderGroundTrajectory2D(GroundTrajectory2DInfo groundTrajectory, double zOffset, Color color) {
        int amountOfPoints = 100;
        double precision = Math.PI*2/amountOfPoints;
        double speed = 1000;
        Vector2 point = groundTrajectory.findPointFromElapsedTimeAndSpeed(0, speed);
        Vector2 previousPoint;

        for(int i = 1; i < amountOfPoints; i++) {
            previousPoint = point;
            point = groundTrajectory.findPointFromElapsedTimeAndSpeed(i*precision, speed);
            renderer.drawLine3d(color, new Vector3(previousPoint, zOffset), new Vector3(point, zOffset));
        }
    }

    public void renderCircle3D(Circle3D circle, Color color) {
        int amountOfPoints = 100;
        double precision = Math.PI*2/amountOfPoints;
        Vector3 point = circle.findPointOnCircle(0);
        Vector3 previousPoint;

        for(int i = 1; i < amountOfPoints; i++) {
            previousPoint = point;
            point = circle.findPointOnCircle(i*precision);
            renderer.drawLine3d(color, previousPoint, point);
        }
    }

    public void renderTrajectory(Trajectory3D parabola, double amountOfTimeToRender, Color color) {
        Vector3 previousPosition = parabola.compute(0);
        for(int i = 1; i < 40; i++) {
            Vector3 nextPosition = parabola.compute(i*amountOfTimeToRender/40);
            renderer.drawLine3d(color, nextPosition, previousPosition);
            previousPosition = nextPosition;
        }
    }

    public void renderTrajectory(Trajectory3D parabola, double fromTime, double toTime, Color color) {
        Vector3 previousPosition = parabola.compute(fromTime);
        for(int i = 1; i < 40; i++) {
            double timeToCompute = fromTime + ((i/40.0)*(toTime-fromTime));
            Vector3 nextPosition = parabola.compute(timeToCompute);
            renderer.drawLine3d(color, nextPosition, previousPosition);
            previousPosition = nextPosition;
        }
    }

    public void renderBallPrediction(GameStatePrediction ballPrediction, double amountOfTimeToRender, Color color) {
        Vector3 previousPosition = ballPrediction.ballAtTime(0).position;
        for(int i = 1; i < amountOfTimeToRender*RlUtils.BALL_PREDICTION_REFRESH_RATE; i++) {
            Vector3 nextPosition = ballPrediction.ballAtTime(i/RlUtils.BALL_PREDICTION_REFRESH_RATE).position;
            renderer.drawLine3d(color, nextPosition, previousPosition);
            previousPosition = nextPosition;
        }
    }

    public void renderHitBox(HitBox hitBox, Color color) {
        Vector3 opponentNoseOrientation = hitBox.frontOrientation;
        Vector3 opponentRoofOrientation = hitBox.roofOrientation;
        Vector3 opponentRightOrientation = opponentNoseOrientation.crossProduct(opponentRoofOrientation);

        Vector3 hitBoxCorner111 = hitBox.projectPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation).plus(opponentRightOrientation).scaled(300).plus(hitBox.centerPosition));
        Vector3 hitBoxCorner110 = hitBox.projectPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(hitBox.centerPosition));
        Vector3 hitBoxCorner101 = hitBox.projectPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation).scaled(300).plus(hitBox.centerPosition));
        Vector3 hitBoxCorner100 = hitBox.projectPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(hitBox.centerPosition));
        Vector3 hitBoxCorner011 = hitBox.projectPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation).plus(opponentRightOrientation).scaled(300).plus(hitBox.centerPosition));
        Vector3 hitBoxCorner010 = hitBox.projectPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(hitBox.centerPosition));
        Vector3 hitBoxCorner001 = hitBox.projectPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation).scaled(300).plus(hitBox.centerPosition));
        Vector3 hitBoxCorner000 = hitBox.projectPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(hitBox.centerPosition));

        renderer.drawLine3d(color, hitBoxCorner111, hitBoxCorner110);
        renderer.drawLine3d(color, hitBoxCorner111, hitBoxCorner101);
        renderer.drawLine3d(color, hitBoxCorner111, hitBoxCorner011);

        renderer.drawLine3d(color, hitBoxCorner010, hitBoxCorner011);
        renderer.drawLine3d(color, hitBoxCorner010, hitBoxCorner000);
        renderer.drawLine3d(color, hitBoxCorner010, hitBoxCorner110);

        renderer.drawLine3d(color, hitBoxCorner001, hitBoxCorner000);
        renderer.drawLine3d(color, hitBoxCorner001, hitBoxCorner011);
        renderer.drawLine3d(color, hitBoxCorner001, hitBoxCorner101);

        renderer.drawLine3d(color, hitBoxCorner100, hitBoxCorner101);
        renderer.drawLine3d(color, hitBoxCorner100, hitBoxCorner110);
        renderer.drawLine3d(color, hitBoxCorner100, hitBoxCorner000);
    }
}
