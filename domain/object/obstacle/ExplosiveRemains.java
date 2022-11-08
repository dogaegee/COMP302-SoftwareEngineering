package domain.object.obstacle;

import java.util.ArrayList;
import domain.Constant;
import domain.GameHandler;
import domain.object.FreeMoveBehavior;
import domain.object.GameObject;

/**
 * ExplosiveRemains class represents the particles which falls when a ExplosiveObstacle is hit.
 * @author Softwaring
 */

public class ExplosiveRemains extends Obstacle{

	/**
	 * Constructor for ExplosiveRemains object.
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 * @return ExplosiveRemains
	 */
	public ExplosiveRemains(double x, double y, double dx, double dy) {
		this.movementBehavior = new FreeMoveBehavior();
		height = 15;
		width = 15;
		this.x = x;
		this.y = y;
		this.dy = dy;
		this.dx = dx;
		canMove = true;
		//the image for the bombUI
		path = Constant.EXPLOSIVE_REMAINS_PATH;
		ArrayList<GameObject> objectList = new ArrayList<>();
		objectList.add(GameHandler.getGame().getPaddle());
		this.horizontalList = objectList;
	}

	@Override
	/**
	 * It is stopped and the chances are decreased when it hits the paddle.
	 * @param canMove
	 * @param paddle 
	 */
	public void setCanMove(boolean canMove, boolean paddle) {
		super.setCanMove(canMove,paddle);
		if(paddle)
			Constant.chances--;
	}

}
