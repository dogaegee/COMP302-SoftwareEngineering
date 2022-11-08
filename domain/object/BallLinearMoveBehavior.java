package domain.object;

import java.util.ArrayList;
import java.util.List;
import domain.Constant;
import domain.GameHandler;
import domain.object.obstacle.Obstacle;
/**
* LinearMoveBehavior implements MoveBehviour
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class BallLinearMoveBehavior implements MoveBehavior<Ball> {
	private List<GameObject> list = null;
	/**
	* Override method of MoveBehaviour
	* This behavior is for Unstoppable Enchanted Sphere magic
	* @param ball the Enchanted Sphere in our game
	*/
	@Override
	public void move(Ball o) {
		// REQUIRES:
		// MODIFIES: modifies the ball dx, dy and x and y location
		// EFFECTS: effects the ball parameters
		double additionalSpeedy = 0;
		double additionalSpeedx = 0;
		boolean dontMove = false;
		if (GameHandler.getGame().getObstacles()!=null) {
			//Reflect from the walls
			if (o.getX() <= 0)						o.setDx(-o.getDx());
			if (o.getX() >= width - o.getWidth()) 	o.setDx(-o.getDx());
			if (o.getY() <= 0)						o.setDy(-o.getDy());
			Paddle paddle = GameHandler.getGame().getPaddle();
			
			if(o.getY() >= height - o.getHeight()) { //Ball falls down restart the game!
				GameHandler.getGame().restartGame();
				dontMove = true;
			}

			
			if(!dontMove) {
			list = new ArrayList<>(); 
			ArrayList<Obstacle> temp = GameHandler.getGame().getObstacles();
			for(GameObject b : temp)
				list.add(b);
			ArrayList<GameObject> templist = new ArrayList<GameObject>();
			templist.add(GameHandler.getGame().getPaddle());
			templist.addAll(list);
			GameObject tempc =  null;
			double coordinateAngle = 0;
			double tempdx = o.getDx();
			double tempdy = o.getDy();
			boolean opposite = false;
			int i;
			//reflect from the objects first one is the paddle!
			for(i = 0; i < templist.size(); i++) {
				GameObject c = templist.get(i);
				boolean hit = false;	
				
				double startW = c.getX();
				double endW = c.getWidth() + c.getX();
				double startH = c.getY();
				double endH = c.getHeight() + c.getY();
				
				double startx = o.getX();
				double endx = o.getX() + o.getWidth();
				double starty = o.getY();
				double endy = o.getY()+o.getHeight();
				o.setDx(tempdx);
				o.setDy(tempdy);
				
				//if the object has an angle reflect accordingly
				if(c.getAngle() != 0) {
					//rotate the points
					coordinateAngle = -Math.toRadians(c.getAngle());
					double[] t_startx = turn(startx, starty, coordinateAngle);					
					double[] t_endx = turn(endx, endy, coordinateAngle);

					startx = t_startx[0];
					starty = t_startx[1];
					endx = t_endx[0];
					endy = t_endx[1];
					
					double[] t_objStart = turn(startW, startH, coordinateAngle);					
					double[] t_objEnd = turn(endW, endH, coordinateAngle);

					c.setT_StartX(t_objStart[0]);
					c.setT_StartY(t_objStart[1]);
					c.setT_EndX(t_objEnd[0]);
					c.setT_EndY(t_objEnd[1]);
					
				}
				//Hit from above
				if(endy >= startH && endy <= endH) {
					if(endx >= startW && endx <= endW) {
						//Hit from corner
						int indicator2 = 1;
						int indicator = isHitFromCorner(o, c, startx, endx, starty, endy, indicator2);
						if(indicator != 0) {
							hit = true;
							hitFromCorner(o, c, indicator);
						}
						//Hit from left
						else if(c.isCanHitFromSide()) {
							hit = true;
							additionalSpeedx = startW - endx;
							o.setDx(-o.getDx());
							c.setCanHitFromSide(false);
						}else {
							hit = true;
							opposite = hitFromLongEdge(o,c, opposite, i);
							additionalSpeedy = startH - endy;
						}

					}else if(startx >= startW && startx <= endW) {
						//Hit from corner
						int indicator2 = 2;
						int indicator = isHitFromCorner(o, c, startx, endx, starty, endy, indicator2);
						if(indicator != 0) {
							hit = true;
							hitFromCorner(o, c, indicator);
						}
						//Hit from right
						else if(c.isCanHitFromSide()) {
							hit = true;
							additionalSpeedx = endW - startx;
							o.setDx(-o.getDx());
							c.setCanHitFromSide(false);
						}else {
							hit = true;
							opposite = hitFromLongEdge(o, c, opposite, i);
							additionalSpeedy = startH - endy;
						}
					}
					else{
						//Can be hit from left in next step
						c.setCanHitFromSide(true);
					}

					//Hit from below
				}else if(starty <= endH && starty >= startH) {
					if(endx >= startW && endx <= endW) {
						//Hit from corner
						int indicator2 = 3;
						int indicator = isHitFromCorner(o, c, startx, endx, starty, endy, indicator2);
						if(indicator != 0) {
							hit = true;
							hitFromCorner(o, c, indicator);
						}
						//Hit from left
						else if(c.isCanHitFromSide()) {
							hit = true;
							additionalSpeedx = startW - endx;
							o.setDx(-o.getDx());
							c.setCanHitFromSide(false);
						}else {
							hit = true;
							opposite = hitFromLongEdge(o,c, opposite, i);
							additionalSpeedy = endH - starty;
						}

					}else if(startx>=startW && startx<=endW) {
						//Hit from corner
						int indicator2 = 4;
						int indicator = isHitFromCorner(o, c, startx, endx, starty, endy, indicator2);
						if(indicator != 0) {
							hit = true;
							hitFromCorner(o, c, indicator);
						}
						//Hit from right
						else if(c.isCanHitFromSide()) {
							hit = true;
							additionalSpeedx = endW - startx;
							o.setDx(-o.getDx());
							c.setCanHitFromSide(false);
						}else {
							hit = true;
							opposite = hitFromLongEdge(o,c, opposite, i);
							additionalSpeedy = endH - starty;
						}
					}
					else{
						//Can be hit from left in next step
						c.setCanHitFromSide(true);
					}
				}else {
					c.setCanHitFromSide(false);
				}
				if(hit) {
					tempc = c;
					break;
				}
			}
			
			if (tempc != null) { //hit - handle life
				String path = tempc.getPath();
				if(i > 0 && !((Obstacle)tempc).isFrozen() || 
						i > 0 && ((Obstacle)tempc).isFrozen() && o.isUnstoppable() ) 
					//if it is not paddle and object is not frozen
					//OR is is not paddle and object is frozen and the ball is unstoppable
				{
					if(o.isUnstoppable() && !((Obstacle)tempc).isFrozen()) tempc.setLife(0); //is Unstoppable and not frozen 
					else tempc.setLife(tempc.getLife() - 1);
					if(tempc.getLife() == 0) {
						tempc.setWillBeDeleted(true);
						//update score
						if(!path.equals(Constant.HOLLOW_PURPLE_PATH)) {
							Constant.score = Constant.score + (300/Constant.time);
						}
					}
				}
				
				//handle rotated reflection
				if(tempc.getAngle() != 0) {
						double tdx = o.getDx();
						if(tdx == -0.0) tdx = +0.0;
						
						double tdy = o.getDy();
						double alpha = tempc.getAngle();
						double beta =   Math.toDegrees(Math.atan(Math.toRadians(tdy/tdx)));
						double omega =  Math.abs(Math.abs(alpha) + beta -90);
						double beta2 = beta - 2*omega;
						double mx, ny;
	
						int sign;
						if (alpha > 0) {
							sign = 1;
						}else {
							sign = -1;
						}
						mx = sign*round( Math.sqrt((Math.pow(tdx, 2) + Math.pow(tdy, 2)))*Math.cos(Math.toRadians(beta2)),2);
						ny = round (Math.sqrt((Math.pow(tdx, 2) + Math.pow(tdy, 2)))*Math.sin(Math.toRadians(beta2)),2);
						o.setDx(mx);
						o.setDy(ny);
						additionalSpeedx = 0;
						additionalSpeedy = 0;
				}
			
				if (o.isUnstoppable() && i > 0 && !((Obstacle)tempc).isFrozen()) {
					o.setDx(tempdx);
					o.setDy(tempdy);
				}
			}
			o.setX(o.getX()+o.getDx()+additionalSpeedx); 
			o.setY(o.getY()+o.getDy()+additionalSpeedy);
			}
		}
	}
	
	/**
	* Necessary function for hitting from the sides 
	* @param ball the Enchanted Sphere in our game
	* @param object object that the ball hits
	* @param opposite stores if the speed of ball and object has opposite speeds
	* @return the new opposite boolean
	*/
	private boolean hitFromLongEdge(Ball o, GameObject c, boolean opposite, int i) {
			if(c.getDx() == -0.0 || c.getDx() == +0.0) {
				o.setDy(-o.getDy());
				//If directions are the same multiplication will be positive
			}else if(c.getDx() * o.getDx() > 0) {
				o.setDy(-o.getDy()-0.05);
				if(o.getDx() > 0) o.setDx(o.getDx()+0.05);
				else if (o.getDx() < 0)o.setDx(o.getDx()-0.05);
	
			}else if(c.getDx() * o.getDx() < 0){ //opposite speeds
				opposite = true;
				o.setDy(-o.getDy());
				o.setDx(-o.getDx());
			}else if(c.getDx() * o.getDx() == 0){ //ball Dx is 0 but not the obstacle
				o.setDx(o.getDy()/Math.sqrt(2));
				o.setDy(o.getDy()/Math.sqrt(2));
	
		}
		return opposite;
	}
	
	private int isHitFromCorner(Ball o, GameObject c, double startx, double endx, double starty, double endy, int indicator2) {
		
		double objCornerTopY = c.getY() + 2;
		double objCornerBotY = c.getHeight() + c.getY() -2;
		double objCornerLeftX = c.getX() +2;
		double objCornerRightX = c.getWidth() + c.getX() -2;
		
		int indicator = 0;
		
		if(endy <= objCornerTopY ) {
			if(endx <= objCornerLeftX && indicator2 == 1) {
				indicator= 1;
			}
			else if(startx >= objCornerRightX && indicator2 == 2) {
				indicator = 2;	
			} 
		}
		else if(starty >= objCornerBotY) { //not sure
			if(endx <= objCornerLeftX && indicator2 == 3) {
				indicator = 3;
			}
			else if(startx >= objCornerRightX && indicator2 == 4) {
				indicator = 4;
			}

		}
		return indicator;
	}
	
	private void hitFromCorner(Ball o, GameObject c, int indicator) {
			
			double angle;
			int boolHelperDy;
			int boolHelperDx;
			
			if(indicator == 1) {
				boolHelperDy = 1;
				boolHelperDx = 1;
			}
			else if(indicator == 2) {
				boolHelperDy = 1;
				boolHelperDx = -1;
			}
			
			else if(indicator == 3) {
				boolHelperDy = -1;
				boolHelperDx = 1;
			}
			else {
				boolHelperDy = -1;
				boolHelperDx = -1;
			}
			
			if (boolHelperDx * o.getDx() <= boolHelperDy * o.getDy()) {
				angle = boolHelperDx * boolHelperDy * 90;
			}else {
				angle = boolHelperDx * boolHelperDy * -90;
			}
			
			double newDy = o.getDx() * Math.sin(Math.toRadians(angle));
			double newDx = o.getDy() * Math.sin(Math.toRadians(angle)) * -1; // (-1) since pixel number for Y axis changes opposite to the arithmetic axis. 
			o.setDx(newDx);
			o.setDy(newDy);
		

	}
	
	/**
	* Necessary function for turning the ball around the paddle 
	* @requires coordinateAngle has to be in radians
	* @param  location_x location x of the ball
	* @param location_y location y of the ball
	* @param coordinateAngle coordinate angle that we turn around
	* @effects returns the new location of (location_x, location_y) point
	* when it is rotated around the paddle center with coordinateAngle degrees.
	* @returns the new x and y coordinates after the turn
	*/
	public double[] turn(double location_x, double location_y, double coordinateAngle) {
		double centerx = GameHandler.getGame().getPaddle().getX() + GameHandler.getGame().getPaddle().getWidth()/2;
		double centery = GameHandler.getGame().getPaddle().getY() + GameHandler.getGame().getPaddle().getHeight()/2;

		double x = location_x - centerx;
		double y = location_y - centery;
		
		double x_prime = x * Math.cos(coordinateAngle) - y * Math.sin(coordinateAngle);
		double y_prime = y * Math.cos(coordinateAngle) + x * Math.sin(coordinateAngle);
		
		double x_pp = x_prime + centerx;
		double y_pp = y_prime + centery;
		
		return new double[] {x_pp, y_pp};	
	}
	
	/**
	* Rounds the value  
	* @param value value to round
	* @param places most significant bits to round
	* @return the rounded value
	*/
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    double factor = Math.pow(10, places);
	    value = value * factor;
	    double tmp = Math.round(value);
	    return tmp / factor;
	}


}
