package domain.object;

import java.util.ArrayList;
import domain.Constant;
import domain.GameHandler;
import domain.object.obstacle.Obstacle;
/**
* Hex is a class for the magical hex
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class Hex extends Obstacle{
	
	/**
	* Constructor for Hex class
	* @param two doubles for dx and dy, two doubles for location: x and y
	*/
	public Hex(double dx, double dy, double x, double y) {
		this.dx = dx;
		this.dy = dy;
		this.x = x;
		this.y = y;
		this.canMove = true;
		this.width = 10;
		this.height = 15;
		path = Constant.FIREBALL_PATH;
		this.movementBehavior =  new ShootingMoveBehavior();
		ArrayList<GameObject> list = new ArrayList<>(); 
		ArrayList<Obstacle> temp = GameHandler.getGame().getObstacles();
		for(GameObject b : temp)
			list.add(b);
		list.remove(GameHandler.getGame().getPaddle()); 
		this.setHorizontalList(list);
	}
	

	public MoveBehavior getMovementBehavior() {
		return movementBehavior;
	}

	public void setMovementBehavior(MoveBehavior movementBehavior) {
		this.movementBehavior = movementBehavior;
	}
	
}
