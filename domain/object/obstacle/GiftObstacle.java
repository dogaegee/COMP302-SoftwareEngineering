package domain.object.obstacle;

import java.util.ArrayList;
import domain.Constant;
import domain.object.FreeMoveBehavior;
import domain.object.GameObject;

/**
 * GiftObstacle class represents the obtacles which store
 * the magical abilities and set free a falling magical ability
 * when it is hit.
 * @author Softwaring
 */
public class GiftObstacle extends Obstacle{
	
	/**
	 * Constructor for GiftObstacle object.
	 * @param
	 * @return GiftObstacle
	 */
	public GiftObstacle() {
		movementBehavior = new FreeMoveBehavior();
		height = 20;
		width = (int) (Constant.L/4);
		path = Constant.GIFT_PATH;
		life = 1;
		hasRemains = true;
	}

	
	@Override
	/**
	 * It creates the FallingGiftObstacle object on the desired location.
	 * Then, it keeps these in a list and return the list.
	 * @param 
	 * @return ArrayList<GameObject>
	 */
	public ArrayList<GameObject> getRemains() {
		ArrayList<GameObject> remains = new ArrayList<>();
		remains.add(new FallingGiftObstacle(x, y, 0, 2));
		return remains;
	}
	
	//Getters and setters

	@Override
	public void setFrozen(boolean isFrozen) {
	    if(!isFrozen) {
	    	path = Constant.GIFT_PATH;
	    } else {
	    	path = Constant.FROZEN_GIFT_PATH;
	    }
		this.isFrozen = isFrozen;
	}
}
