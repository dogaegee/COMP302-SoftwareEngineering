package domain.object.obstacle;

import java.util.Random;
import domain.Constant;
import domain.object.FreeMoveBehavior;
import domain.object.HorizontalObstacleMoveBehavior;

/**
 * SimpleObstacle class represents the obstacles which can be
 * destructed by 1 hitting. Also, it can move horizontally with
 * a specified probability.
 * @author Softwaring
 */
public class SimpleObstacle extends Obstacle{
	private int life = 1;
	
	/**
	 * Constructor for SimpleObstacle object.
	 * @param
	 * @return SimpleObstacle
	 */
	public SimpleObstacle() {
		Random random = new Random();
		probability = random.nextInt(10);
		height = 20;
		width = (int) (Constant.L/4);
		path = Constant.SIMPLE_PATH;
		life = 1;
	}
	
	//Getters and setters
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	@Override
	public void setFrozen(boolean isFrozen) {
	    if(!isFrozen) {
	    	path = Constant.SIMPLE_PATH;
	    } else {
	    	path = Constant.FROZEN_SIMPLE_PATH;
	    }
		this.isFrozen = isFrozen;
	}
	@Override
	public void changeMoveBehavior() {
		if(probability <= 1) {
			movementBehavior = new HorizontalObstacleMoveBehavior();
			dx = 1;
			dy = 0;
			this.canMove = true;
		} else {
			movementBehavior = new FreeMoveBehavior();
		}
	}
}
