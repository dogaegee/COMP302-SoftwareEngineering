package domain.object.obstacle;

import java.util.ArrayList;
import java.util.Random;
import domain.Constant;
import domain.object.FreeMoveBehavior;
import domain.object.GameObject;
import domain.object.HorizontalObstacleMoveBehavior;

/**
 * FirmObstacle class represents the obstacles which are destructed
 * by more than one hit. It also moves horizontally with probability.
 * @author Softwaring
 */
public class FirmObstacle extends Obstacle{
	
	/**
	 * Constructor for FirmObstacle object.
	 * @param
	 * @return FirmObstacle
	 */
	public FirmObstacle() {
		Random random = new Random();
		probability = random.nextInt(10);
		changeMoveBehavior();
		height = 20;
		width = (int) (Constant.L/4);
		//the image for the firm obstacle (with only one chance)
		path = Constant.FIRM1_PATH;
		//to get firm obstacles with different chances
		Random rand = new Random();
		life = rand.nextInt(4) + 1;
		//to change the firmObstacleUI
		this.horizontalList = new ArrayList<GameObject>();
		change_image();
	}

	
	/**
	 * change_image() method gives different image paths for firm obstacles with different chances
	 * @param
	 * @return
	 */
	private void change_image() {
		switch(life){
		case(1):
			this.path = Constant.FIRM1_PATH;
			break;
		case(2):
			this.path = Constant.FIRM2_PATH;
			break;
		case(3):
			this.path = Constant.FIRM3_PATH;
			break;
		case(4):
			this.path = Constant.FIRM4_PATH;
			break;
		case(5):
			this.path = Constant.FIRM5_PATH;
			break;

		}
	}
	
	//Getters and setters
	

	@Override
	public void setLife(int life) {
		// TODO Auto-generated method stub
		this.life = life;
		change_image();
	}
	
	public ArrayList<GameObject> getHorizontalList() {
		return this.horizontalList;
	}
	
	@Override
	public void setFrozen(boolean isFrozen) {
	    if(!isFrozen) {
	    	change_image();
	    } else {
	    	switch(life){
			case(1):
				this.path = Constant.FROZEN_FIRM1_PATH;
				break;
			case(2):
				this.path = Constant.FROZEN_FIRM2_PATH;
				break;
			case(3):
				this.path = Constant.FROZEN_FIRM3_PATH;
				break;
			case(4):
				this.path = Constant.FROZEN_FIRM4_PATH;
				break;
			case(5):
				this.path = Constant.FROZEN_FIRM5_PATH;
				break;

			}
	    }
		this.isFrozen = isFrozen;
	}
	
	@Override
	public void changeMoveBehavior() {
		if(probability <= 1) {
			movementBehavior = new HorizontalObstacleMoveBehavior(); //It moves horizontally with probability.
			dx = 1;
			dy = 0;
			this.canMove = true;
		} else {
			movementBehavior = new FreeMoveBehavior();
		}
	}
}
