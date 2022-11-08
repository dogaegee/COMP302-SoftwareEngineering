package domain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import domain.bm.BMAction;
import domain.object.Ball;
import domain.object.Paddle;
import domain.object.obstacle.Obstacle;
import domain.saveload.Load;
import domain.saveload.Save;

public class Game {
	private Drawable drawable;
	private Ball ball;
	private Paddle paddle;
	private Timer timer;
	private TimerTask timerTask;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private ArrayList<Obstacle> fallingObstacles = new ArrayList<Obstacle>();
	private ArrayList<Obstacle> hollowPurpleObstacles = new ArrayList<Obstacle>();
	private ArrayList<Integer> suitableXLocations = new ArrayList<Integer>();
	private ArrayList<Integer> suitableYLocations = new ArrayList<Integer>();
	private boolean isStarted;
	/**
	 * Adds the drawable objects and invokes the timer task.
	 * @param drawable
	 */
	public void addDrawable (Drawable drawable) {
		this.drawable = drawable;
		timerTaskStart();
	}

	/**
	 * Constructor
	 * @param d
	 */
	public Game(Drawable d) {
		this.isStarted = false;
		this.ball = new Ball();
		this.paddle = new Paddle();
		this.drawable = d;
	}
	

	public void initialize() {
		if (BMAction.getObstacles().size()==0) {
			Load.loadGame();
			ball = Load.getBall();
			paddle = Load.getPaddle();
			setFallingObstacles(Load.getFalling());

			ArrayList<Obstacle> temp = Load.getObstacles();
			for(Obstacle b : temp)
				obstacles.add(b);
		}else {
			this.obstacles = BMAction.getObstacles();
		}
		for(Obstacle obj:obstacles) {
			if(obj.getPath().equals(Constant.EXPLOSIVE_PATH)) { //it is definitly an obstacle
				obj.setCenterX(obj.getX());
				obj.setCenterY(obj.getY() - 1.5*Constant.L);
			}
		}
		addDrawable(this.drawable);	
	}
	/**
	 * This method starts the task for timer.
	 */
	private void timerTaskStart() {
		timerTask = new ActionTask(ball,paddle,drawable, obstacles, this);     
		timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, 10);
	}
	

	/**
	 * Handles the changes in paddles speed
	 * @param speedX
	 */
	public void acceleratePaddle(double direction) {
		if(direction == 0) {
			paddle.setDx(0);
			paddle.setMoveOnce(false);
		} else if(!paddle.isMoveOnce()) {
			paddle.setDx(direction*(Constant.L /200));
			paddle.setMoveOnce(true);
		} else {
			paddle.setDx(direction*(2 * Constant.L / 100));
		}
		
	}

	/**
	 * Pauses the thread
	 */
	public void pauseGame() {
		this.isStarted = false;
		timerTask.cancel();	
	}
	
	/**
	 * Resumes the game thread
	 */
	public void resumeGame() {
		if(!this.isStarted && Constant.chances > 0) {
			this.isStarted = true;

			timerTaskStart();
		}
	}

	/**
	 * Saves the required game parameters
	 * @param username
	 */
	public void save(String username) {
		Save.save(this.getObstacles(), getFallingObstacles(), ball, paddle, username);
	}	

	/**
	 * This method enables the paddle to turn gradually
	 * @param angle
	 * @param speed
	 */
	public void turnPaddle(int angle, int speed) {
		this.paddle.setDesiredAngle(angle);
		this.paddle.setTurnSpeed(speed);
	}

	/**
	 * This method enables the paddle to return to its original angle
	 */
	public void returnPaddle() {
		this.paddle.setDesiredAngle(0);
		this.paddle.setAngle(0);
	}

	public Obstacle getObstacle(int x, int y) {
		// REQUIRES: 
		// MODIFIES: 
		// EFFECTS: If the point(x,y) obtained from x and y parameters is in the area of any obstacle in the given obstacle list,
		// this function returns the obstacle in that point.
		Obstacle obstacle = null;
		y -= Constant.SHIFTING_CONSTANT;
		ArrayList<Obstacle> obstacleList = getObstacles();

		for(Obstacle obs: obstacleList) {
			int obsXLeft = (int) obs.getX();
			int obsYTop = (int) obs.getY();
			int obsXRight = obsXLeft + obs.getWidth();
			int obsYBottom = obsYTop + obs.getHeight();

			if(x >= obsXLeft && x <= obsXRight && y >= obsYTop && y <= obsYBottom) {
				return obs;
			}
		}
		return obstacle;
	}
	
	public void setObjList(ArrayList<Obstacle> obstacleList) {
		this.obstacles = new ArrayList<Obstacle>();
		this.obstacles.addAll(obstacleList);
	}
	
	public Paddle getPaddle() {
		return paddle;
	}
	
	public ArrayList<Obstacle> getFallingObstacles() {
		return fallingObstacles;
	}

	public Ball getBall() {
		return ball;
	}

	public void setFallingObstacles(ArrayList<Obstacle> fallingObstacles) {
		this.fallingObstacles = fallingObstacles;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public ArrayList<Obstacle> getHollowPurpleObstacles() {
		return hollowPurpleObstacles;
	}

	public void setHollowPurpleObstacles(ArrayList<Obstacle> hollowPurpleObstacles) {
		this.hollowPurpleObstacles = hollowPurpleObstacles;
	}

	public ArrayList<Integer> getSuitableXLocations() {
		return suitableXLocations;
	}

	public void setSuitableXLocations(ArrayList<Integer> suitableXLocations) {
		this.suitableXLocations = suitableXLocations;
	}

	public ArrayList<Integer> getSuitableYLocations() {
		return suitableYLocations;
	}

	public void setSuitableYLocations(ArrayList<Integer> suitableYLocations) {
		this.suitableYLocations = suitableYLocations;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public void restartGame() {
		ball.setX((Constant.width - ball.getWidth())/2);
		ball.setY(Constant.height - (ball.getHeight() + Constant.L / 2 + 20));
		ball.setDx(0);
		ball.setDy(-3);
		Constant.chances--;
		paddle.setX((Constant.width - paddle.getWidth())/2);
		this.setFallingObstacles(new ArrayList<Obstacle>());
		if(Constant.chances != 0) {
			pauseGame();
		}
		returnPaddle();		
	}

	
}