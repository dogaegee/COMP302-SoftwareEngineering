package domain.object;

import java.util.ArrayList;
import domain.Constant;
import domain.object.obstacle.Obstacle;
/**
* CircularMoveBehavior implements MoveBehviour
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class CircularMoveBehavior implements MoveBehavior<Obstacle> {

	/**
	* Override method of MoveBehaviour
	* @param GameObject
	* This movement only handles circular movements
	*/
	@Override
	public void move(Obstacle obj) {
			double angle = obj.getExplosiveAngle();
			double radius = 1.5*Constant.L;
			double xLoc = obj.getCenterX() + Math.cos(angle)*radius;
			double yLoc = obj.getCenterY()  + Math.sin(angle)*radius;
			double change;
			ArrayList<GameObject> circularList = obj.getCircularList();
			
			if(obj.isClockwise()) {
				change = 0.003;
			} else {
				change = -0.003;
			}
			
			double startx = xLoc;
			double endx = xLoc + obj.getWidth();
			double starty = yLoc;
			double endy = yLoc+obj.getHeight();
			ArrayList<GameObject> deletedObs = new ArrayList<GameObject>();
			GameObject tempc =  null;
			
			for(GameObject c : circularList) {
				if(c.isWillBeDeleted()) {
					deletedObs.add(c);
				} else {
					boolean hit = false;
					double startW = c.getX();
					double endW = c.getWidth() + c.getX();
					double startH = c.getY();
					double endH = c.getHeight() + c.getY();
						if(endy >= startH && starty <= endH && startx <= endW && endx >= startW) {
						hit = true;
						} 
						if(hit) {
							tempc = c;
							break;
						}
				}
			}
			obj.getCircularList().removeAll(deletedObs);
			
			if (tempc != null) {
				xLoc = obj.getCenterX() + Math.cos(angle - change)*radius;
				yLoc =  obj.getCenterY()  + Math.sin(angle - change)*radius;
				obj.setClockwise(! obj.isClockwise());
				obj.setExplosiveAngle(angle - change);
				obj.setIsOutCount(obj.getIsOutCount() + 1);
				return;
			}
			
			if(obj.getIsOutCount() >= 1 && (xLoc <= 0 || xLoc >= width - obj.getWidth() ||
					yLoc <= 0 || yLoc >= height - obj.getHeight())) {
				obj.setClockwise(! obj.isClockwise());
				if(obj.isClockwise()) {
					change = 0.01;
				} else {
					change = -0.01;
				}
				while(yLoc <= 0) {
					xLoc = obj.getCenterX() + Math.cos(obj.getExplosiveAngle() + change)*radius;
					yLoc =  obj.getCenterY()  + Math.sin(obj.getExplosiveAngle() + change)*radius;
					obj.setExplosiveAngle(obj.getExplosiveAngle() + change);
				}
				obj.setIsOutCount(0);
			}else {
				if (xLoc <= 0 || xLoc >= width - obj.getWidth() ||
						yLoc <= 0 || yLoc >= height - obj.getHeight()) {
					xLoc = obj.getCenterX() + Math.cos(angle - change)*radius;
					yLoc =  obj.getCenterY()  + Math.sin(angle - change)*radius;
					obj.setClockwise(! obj.isClockwise());
					obj.setExplosiveAngle(angle - change);
					obj.setIsOutCount(obj.getIsOutCount() + 1);
					
				} else {
					obj.setExplosiveAngle(angle + change);
					obj.setIsOutCount(0);
				}
			}
			obj.setX(xLoc);
			obj.setY(yLoc);
	}

}
