package domain.object.obstacle;

import java.util.ArrayList;
import domain.object.GameObject;

public class Obstacle extends GameObject{
	protected double explosiveAngle;
	protected double centerX;
	protected double centerY;
	protected double radius;
	protected boolean clockwise;
	protected boolean isFrozen;
	protected int isOutCount;
	protected int probability = -1;

	protected ArrayList<GameObject> circularList = new ArrayList<GameObject>();
	protected ArrayList<GameObject> horizontalList = new ArrayList<GameObject>();
	
	
	public double getExplosiveAngle() {
		return explosiveAngle;
	}
	public void setExplosiveAngle(double explosiveAngle) {
		this.explosiveAngle = explosiveAngle;
	}
	public ArrayList<GameObject> getCircularList() {
		return circularList;
	}
	public void setCircularList(ArrayList<GameObject> circularList) {
		this.circularList = circularList;
	}
	public int getIsOutCount() {
		return isOutCount;
	}
	public void setIsOutCount(int isOutCount) {
		this.isOutCount = isOutCount;
	}
	public boolean isClockwise() {
		return clockwise;
	}
	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}
	public double getCenterX() {
		return this.centerX;
	};
	public double getCenterY() {
		return this.centerY;
	}
	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
	public ArrayList<GameObject> getHorizontalList() {
		return horizontalList;
	}
	public void setHorizontalList(ArrayList<GameObject> horizontalList) {
		this.horizontalList = horizontalList;
	}
	public boolean isFrozen() {
		return isFrozen;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public void setFrozen(boolean isFrozen) {
	}
	public void changeMoveBehavior() {
		
	}
	
}
