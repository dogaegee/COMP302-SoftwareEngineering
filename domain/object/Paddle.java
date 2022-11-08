package domain.object;

import java.util.ArrayList;
import domain.Constant;
/**
* Paddle is a singleton class and stores the information for the paddle 
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class Paddle extends GameObject{
	private MoveBehavior movementBehavior;	
	private boolean isMagicalHexActive;
	private boolean moveOnce;
	private ArrayList<Hex> hexes = new ArrayList<Hex>();

	/**
	* Constructor for Paddle class
	* This is a public constructor
	*/
	public Paddle() {
		this.canMove = true;
		this.movementBehavior = new HorizontalMoveBehavior();
		this.height = 20;
		this.width = (int) Constant.L;
		this.x = (Constant.width - this.width)/2;
		int gap = this.height + width/2;
		this.y = Constant.height - gap;
		this.path = Constant.PADDLE_PATH;
		this.isMagicalHexActive = false;
		this.moveOnce = false;
	}
	


	public MoveBehavior getMovementBehavior() {
		return movementBehavior;
	}
	public boolean isMagicalHexActive() {
		return isMagicalHexActive;
	}
	public void setMagicalHexActive(boolean isMagicalHexActive) {
		this.isMagicalHexActive = isMagicalHexActive;
	}
	public ArrayList<Hex> getHexes() {
		return hexes;
	}
	public void setHexes(ArrayList<Hex> hexes) {
		this.hexes = hexes;
	}
	public boolean isMoveOnce() {
		return moveOnce;
	}
	public void setMoveOnce(boolean moveOnce) {
		this.moveOnce = moveOnce;
	}
	
}
