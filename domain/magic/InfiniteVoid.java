package domain.magic;

import java.util.ArrayList;
import java.util.Random;
import domain.Constant;
import domain.Game;
import domain.GameHandler;
import domain.object.obstacle.Obstacle;

/**
 * InfiniteVoid is the class of an adversary magic which freezes
 * random 8 obstacles on the game for 15 seconds.
 * @author Softwaring
 */
public class InfiniteVoid implements Activatable{
	private int keyCode = 4;
	private ArrayList<Obstacle> frozenObstacles = new ArrayList<Obstacle>();
	@Override
	/**
	 * activate() selects 8 random obstacles and freezes them.
	 * @param
	 * @return
	 */
	public void activate() {
		// TODO Auto-generated method stub
		Constant.YMIR_SITUATION = "Infinite Void Is Active";
		Game game = GameHandler.getGame();
		ArrayList<Obstacle> obstacles = game.getObstacles();
		Random rand = new Random();
		int frozenNum = 8;
		if(obstacles.size() <= 8) {
			for(Obstacle currentObstacle : obstacles) {
				currentObstacle.setFrozen(true);
				frozenObstacles.add(currentObstacle);
			}
		} else {
			int totalObstacleNum = obstacles.size();
			ArrayList<Integer> selectedObstacleIndices = new ArrayList<Integer>();
			for(int i = 0; i < frozenNum; i++) {
				int randomObstacleNum = rand.nextInt(totalObstacleNum);
				while(selectedObstacleIndices.contains(randomObstacleNum)) {
					randomObstacleNum = rand.nextInt(totalObstacleNum);
				}
				selectedObstacleIndices.add(randomObstacleNum);
				Obstacle selectedObstacleToBeFrozen = obstacles.get(randomObstacleNum);
				frozenObstacles.add(selectedObstacleToBeFrozen);
				selectedObstacleToBeFrozen.setFrozen(true);
			}
		}
	}
	/**
	 * deactivate() unfreezes the frozen obstacles on the game.
	 * @param
	 * @return
	 */
	@Override
	public void deactivate() {
		System.out.println("ICALLED");
		for(Obstacle obs : frozenObstacles) {
			obs.setFrozen(false);
		}
		frozenObstacles = null;
	}
	
	//Getters and setters
	
	@Override
	public int getKeyCode() {
		// TODO Auto-generated method stub
		return keyCode;
	}

	@Override
	public void setKeyCode(int keyCode) {
		// TODO Auto-generated method stub
		this.keyCode = keyCode;
	}

	

}
