package domain.object;

import domain.Constant;
import domain.object.obstacle.Obstacle;
/**
* FreeMoveBehavior implements MoveBehviour
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class FreeMoveBehavior implements MoveBehavior<Obstacle> {

	/**
	* Override method of MoveBehaviour
	* @param GameObject
	* This movement handles free falls for the remains
	*/
	@Override
	public void move(Obstacle obj) {
		double nextLocY = obj.getY() + obj.getDy();
		double nextLocX = obj.getX() + obj.getDx();
		
		if(obj.getY() > Constant.height + 2*obj.getHeight()) {
			obj.setCanMove(false,false);
		}else {
			obj.setY(nextLocY);
			obj.setX(nextLocX);
		}		
		if(obj.getHorizontalList() != null) {
			for(GameObject c : obj.getHorizontalList()) {
				double cx = c.getX();
				double cy = c.getY();
				double cHeight = c.getHeight();
				double cWidth = c.getWidth();
				if(obj.getX() - obj.getWidth() >= cx && obj.getX() <= cx + cWidth
						&& obj.getY() + obj.getHeight() >= cy && obj.getY() <= cy + cHeight) {
					obj.setCanMove(false,true);
				}
			}
		}
	}
}
