package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import domain.object.Ball;
import domain.object.GameObject;
import domain.object.Hex;
import domain.object.Paddle;
import domain.object.obstacle.Obstacle;

/**
 * This class specifies the actions that will take place in every time period of the thread.
 * @author Softwaring
 */

public class ActionTask extends TimerTask{
	private Ball ball;
	private Paddle paddle;
	private Drawable drawable;
	private Ymir ymir = new Ymir();
	//The following lists holds the specified instances that are on the game board. 
	private ArrayList <GameObject> objList = new ArrayList<>();
	private ArrayList <Obstacle> fallingObstacles = new ArrayList<Obstacle>();
	private ArrayList<Hex> hexesOnGame = new ArrayList<>();
	
	
	public ActionTask(Ball ball, Paddle paddle, Drawable drawable, ArrayList<Obstacle> obstacles, Game g) {
		this.drawable = drawable;
		this.ball = ball;
		this.paddle = paddle;
		for(GameObject o : obstacles)
			objList.add(o);
		this.fallingObstacles = g.getFallingObstacles();
		this.hexesOnGame = paddle.getHexes();
		objList.add(this.paddle);
		objList.add(this.ball);
		drawable.triggerDraw(objList);
	}


	/**
	 * This is the run method that we call in the timer based thread in every period. 
	 * @Override
	 * @author Softwaring
	 * @param 
	 * @return 
	 * 
	 */
	public void run() {
		GameHandler.publishGameEvent();
		//Gets the ball instance
		Ball sphere = this.ball;
		//Generates the current object list
		objList = new ArrayList<>();
		ArrayList<Obstacle> obstacleList = GameHandler.getGame().getObstacles();
		Iterator<Obstacle> obstacles = obstacleList.iterator();
		while(obstacles.hasNext()) {
			Obstacle currentObstacle = obstacles.next();
			Iterator<Obstacle> hollowObstacles = GameHandler.getGame().getHollowPurpleObstacles().iterator();
			while(hollowObstacles.hasNext()) {
				Obstacle currentHollowPurple = hollowObstacles.next();
				currentObstacle.getHorizontalList().add(currentHollowPurple);
				currentObstacle.getCircularList().add(currentHollowPurple);
				currentHollowPurple.getHorizontalList().add(currentObstacle);
			}
		}
		obstacleList.addAll(GameHandler.getGame().getHollowPurpleObstacles());
		GameHandler.getGame().getHollowPurpleObstacles().clear();
		objList.addAll(obstacleList);
		objList.add(this.paddle);
		objList.add(sphere);
		objList.addAll(fallingObstacles);
		objList.addAll(hexesOnGame);
		//The following list holds the information on the objects that will be deleted in the next period.
		
		ArrayList <GameObject> removeList = new ArrayList<>();
		
		for(GameObject g : objList) {
			if(g.isCanMove()) {	
				g.getMovementBehavior().move(g);
			} 
			if(g.isWillBeDeleted()) {
				removeList.add(g);
				if(g.isHasRemains()) {
					for(GameObject b : g.getRemains())
						fallingObstacles.add((Obstacle) b);
				}
			}
			if(g.getPath().equals(Constant.EXPLOSIVE_PATH)) {
				g.setCanMove(true, true);
			}
		}
		obstacleList.removeAll(removeList);
		//The current state of the game is redrawn.
		drawable.triggerDraw(objList);
		
		Constant.incrementTimeByLoop();
		removeList = new ArrayList<>();
		for (GameObject f : fallingObstacles) {
			if(!f.isCanMove())
				removeList.add(f);
		}
		fallingObstacles.removeAll(removeList);
		GameHandler.getGame().setFallingObstacles(fallingObstacles);
		//This part handles the coin flipping of Ymir
		if(Constant.time - Constant.ymirTimeCounter >= Constant.YMIR_REINCARNATION_TIME) {
			Constant.ymirTimeCounter = Constant.time;
			ymir.reincarnate();
		} else if ((Constant.time - Constant.ymirTimeCounter >= Constant.YMIR_SLEEP_TIME) && ymir.getActive()) {
			ymir.sleep();
		} 
		
		
	}

}
