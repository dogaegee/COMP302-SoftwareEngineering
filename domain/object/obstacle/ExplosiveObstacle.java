package domain.object.obstacle;

import java.util.ArrayList;
import java.util.Random;
import domain.Constant;
import domain.object.CircularMoveBehavior;
import domain.object.FreeMoveBehavior;
import domain.object.GameObject;

/**
 * ExplosiveObstacle class represents the obstacles which seperated into
 * 3 particles when it is hit. It also moves circularly with probability.
 * @author Softwaring
 */
public class ExplosiveObstacle extends Obstacle{

	/**
	 * Constructor for ExplosiveObstacle object.
	 * @param
	 * @return ExplosiveObstacle
	 */
	public ExplosiveObstacle() {
		Random random = new Random();
		probability = random.nextInt(10);
		changeMoveBehavior();
		this.explosiveAngle = Math.toRadians(90); //This is the turning angle.
		dx = 0;
		dy = 0;
		this.canMove = true;
		this.clockwise = true;
		isOutCount = 0;
		height = 30;
		width = 30;
		//the image for the bombUI
		path = Constant.EXPLOSIVE_PATH;
		life = 1;
		hasRemains = true;
		
		
	}
	
	@Override
	/**
	 * When the ExplosiveObstacle is hit, it creates 3 ExplosiveRemains object
	 * and stores them in a list. The returns the list.
	 * @param
	 * @return ArrayList<GameObject>
	 */
	public ArrayList<GameObject> getRemains() {
		ArrayList<GameObject> remains = new ArrayList<>();
		remains.add(new ExplosiveRemains(x, y, 0, 2));
		remains.add(new ExplosiveRemains(x, y, 0.5, 2));
		remains.add(new ExplosiveRemains(x, y, -0.5, 2));
		return remains;
	}

//	//Getters and setters

	@Override
	public void setFrozen(boolean isFrozen) {
	    if(!isFrozen) {
	    	path = Constant.EXPLOSIVE_PATH;
	    } else {
	    	path = Constant.FROZEN_EXPLOSIVE_PATH;
	    }
		this.isFrozen = isFrozen;
	}
	
	@Override
	public void changeMoveBehavior() {
		if(probability <= 1) {
			movementBehavior = new CircularMoveBehavior(); //It moves circularly with probability.
			dx = 1;
			dy = 0;
			this.canMove = true;
		} else {
			movementBehavior = new FreeMoveBehavior();
		}
	}
}


