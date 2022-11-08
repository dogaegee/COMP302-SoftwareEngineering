package domain.object;

import java.util.ArrayList;
/**
* GameObject is the super class for all objects in our game
* example: Paddle, Ball and Obstacles
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public abstract class GameObject {
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected String path;
	protected double dx = 0;
	protected double dy = 0;
	protected MoveBehavior movementBehavior;
	protected double angle;
	protected double desiredAngle = 0;
	protected double turnSpeed = 0;
	protected boolean canHitFromSide = false;
	protected int life;
	protected boolean canMove = false;
	protected boolean willBeDeleted = false;
	protected boolean hasRemains = false;
	
	protected double startX = x;
	protected double startY = y;
	protected double endX = x + width;
	protected double endY = y + height;
	
	protected double t_StartX;
	protected double t_StartY;
	protected double t_EndX; 
	protected double t_EndY; 


	public boolean isHasRemains() {
		return hasRemains;
	}
	public void setHasRemains(boolean hasRemains) {
		this.hasRemains = hasRemains;
	}
	public boolean isWillBeDeleted() {
		return willBeDeleted;
	}
	public void setWillBeDeleted(boolean willBeDeleted) {
		this.willBeDeleted = willBeDeleted;
	}
	public boolean isCanMove() {
		return canMove;
	}
	public void setCanMove(boolean canMove, boolean paddle) {
		this.canMove = canMove;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public MoveBehavior getMovementBehavior() {
		return movementBehavior;
	}
	public void setMovementBehavior(MoveBehavior movementBehavior) {
		this.movementBehavior = movementBehavior;
	}
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public boolean isCanHitFromSide() {
		return canHitFromSide;
	}
	public void setCanHitFromSide(boolean canHitFromSide) {
		this.canHitFromSide = canHitFromSide;
	}
	public ArrayList<GameObject> getRemains() {
		//overwrite it if any obstacle drops something when it vanishes
		return null;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public double getTurnSpeed() {
		return turnSpeed;
	}
	public void setTurnSpeed(double turnSpeed) {
		this.turnSpeed = turnSpeed;
	}
	public double getDesiredAngle() {
		return desiredAngle;
	}
	public void setDesiredAngle(double desiredAngle) {
		this.desiredAngle = desiredAngle;
	}
	
	public double getStartX() {
		return startX;
	}
	public double getStartY() {
		return startY;
	}
	public double getEndX() {
		return endX;
	}
	public double getEndY() {
		return endY;
	}
	public void setStartX(double startX) {
		this.startX = startX;
	}
	public void setStartY(double startY) {
		this.startY = startY;
	}
	public void setEndX(double endX) {
		this.endX = endX;
	}
	public void setEndY(double endY) {
		this.endY = endY;
	}
	
	
	
	public double getT_StartX() {
		return t_StartX;
	}
	public double getT_StartY() {
		return t_StartY;
	}
	public double getT_EndX() {
		return t_EndX;
	}
	public double getT_EndY() {
		return t_EndY;
	}
	public void setT_StartX(double t_StartX) {
		this.t_StartX = t_StartX;
	}
	public void setT_StartY(double t_StartY) {
		this.t_StartY = t_StartY;
	}
	public void setT_EndX(double t_EndX) {
		this.t_EndX = t_EndX;
	}
	public void setT_EndY(double t_EndY) {
		this.t_EndY = t_EndY;
	}
	
	
	

}


