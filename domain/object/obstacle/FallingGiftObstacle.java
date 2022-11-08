package domain.object.obstacle;

import java.util.ArrayList;
import java.util.Random;
import domain.Constant;
import domain.GameHandler;
import domain.MagicHandler;
import domain.object.FreeMoveBehavior;
import domain.object.GameObject;

/**
 * FallingGiftObstacle class represents the particles which falls 
 * when a GiftObstacle is hit. It also symbolizes a magical ability.
 * @author Softwaring
 */
public class FallingGiftObstacle extends Obstacle{
	
	/**
	 * Constructor for FallingGiftObstacle object.
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 * @return FallingGiftObstacle
	 */
	public FallingGiftObstacle(double x, double y, double dx, double dy) {
		canMove = true; 
		this.movementBehavior = new FreeMoveBehavior(); //It falls downwards.
		height = 15;
		width = 15;
		this.x = x;
		this.y = y;
		this.dy = dy;
		this.dx = dx;
		//the image for the bombUI
		path = Constant.FALLING_GIFT_PATH;
		ArrayList<GameObject> objectList = new ArrayList<>();
		objectList.add(GameHandler.getGame().getPaddle()); //To check it hits the paddle or not.
		this.horizontalList = objectList;
	}
	
	@Override
	/**
	 * If the object hits the paddle, it is stopped and randomly decides the gained magic.
	 * @param canMove
	 * @param paddle 
	 * @return
	 */
	public void setCanMove(boolean canMove, boolean paddle) {
		super.setCanMove(canMove,paddle);
		if(paddle) {
			Random rand = new Random();
			int i = rand.nextInt(4); //decide which magic it gives
			MagicHandler magicHandler = MagicHandler.getInstance();
			magicHandler.addMagic(i);
			magicHandler.publishMagicEvent(i, "Not active. You have " + Constant.remainingMagics.get(i) + " left");
			if(i == 0) {
				magicHandler.activateMagic(0);
			}
		}
	}
}
