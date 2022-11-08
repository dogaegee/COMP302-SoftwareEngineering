package domain.object;

import domain.Constant;

/**
* Ball is a singleton class and stores the information for the ball
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/

public class Ball extends GameObject{
	private boolean isUnstoppable = false;;

	/**
	* Constructor for Ball class
	* This is a public constructor
	*/
	public Ball() {
		//this.movementBehavior = new LinearMoveBehavior();
		dx = 0;
		dy = -3;
		width = 16;
		height = 16;
		this.canMove = true;
		this.x = (Constant.width - this.width)/2;
		double gap = this.height + Constant.L / 2 + 20;
		this.y = Constant.height - gap;
		path = Constant.BALL_PATH; //sphere looking is changed.
		this.movementBehavior = new BallLinearMoveBehavior();
	}
	

	
	public boolean isUnstoppable() {
		return isUnstoppable;
	}

	public void setUnstoppable(boolean isUnstoppable) {
		this.isUnstoppable = isUnstoppable;
	}

}
