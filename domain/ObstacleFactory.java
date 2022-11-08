package domain;

import domain.object.obstacle.ExplosiveObstacle;
import domain.object.obstacle.FirmObstacle;
import domain.object.obstacle.GiftObstacle;
import domain.object.obstacle.HollowPurpleObstacle;
import domain.object.obstacle.Obstacle;
import domain.object.obstacle.SimpleObstacle;

/**
 * This method creates obstacles according to the given code.
 * @author Softwaring
 *
 */
public class ObstacleFactory {
	private Obstacle obstacleCreated;
private static ObstacleFactory factory;
	
	private ObstacleFactory() {
	}
	
	public static ObstacleFactory getInstance() {
		if (factory == null) 
			factory = new ObstacleFactory();
		return factory;
	}
	public Obstacle getObstacle(int obstacleChosen)  {
		if (obstacleChosen == Constant.SIMPLE_OBSTACLE) {
			obstacleCreated = new  SimpleObstacle();
		} else if(obstacleChosen == Constant.FIRM_OBSTACLE) {
			obstacleCreated = new  FirmObstacle();
		} else if(obstacleChosen == Constant.GIFT_OBSTACLE) {
			obstacleCreated = new  GiftObstacle();
		} else if(obstacleChosen == Constant.EXPLOSIVE_OBSTACLE) {
			obstacleCreated = new  ExplosiveObstacle();
		} else if(obstacleChosen == Constant.HOLLOW_PURPLE_OBSTACLE) {
			obstacleCreated = new  HollowPurpleObstacle();
		}
		return obstacleCreated;
	}
}
