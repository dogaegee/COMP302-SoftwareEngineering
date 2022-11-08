package domain.magic;

import java.util.ArrayList;
import java.util.Random;
import domain.Constant;
import domain.Game;
import domain.GameHandler;
import domain.ObstacleFactory;
import domain.object.obstacle.Obstacle;
/**
 * HollowPurple is the class of an adversary magical ability 
 * which creates 8 HollowPurpleObstacle objects and locates
 * them randomly on the game when it is activated.
 * @author Softwaring
 */
public class HollowPurple implements Activatable{
	private int keyCode = 5;
	
	@Override
	/**
	 * activate() method creates 8 HollowPurpleObstacle objects
	 * and finds the locations which are suitable for the obstacle 
	 * addition operation. Then, choses 8 points from the suitable
	 * locations. Finally, locates the HollowPurpleObstacle objects.
	 * @param
	 * @return
	 */
	public void activate() {
		// TODO Auto-generated method stub
		Constant.YMIR_SITUATION = "Hollow Purple Is Active.";
		ObstacleFactory factory = ObstacleFactory.getInstance();
		Random rand = new Random();
		ArrayList<Obstacle> hollowPurples = new ArrayList<Obstacle>();
		Game game = GameHandler.getGame();
		while(hollowPurples.size() != 8) {
			int randomXLeft = rand.nextInt(Constant.MAX_OBSTACLE_X_LOCATION);
			int randomYTop = rand.nextInt(Constant.MAX_OBSTACLE_Y_LOCATION);
			int randomXRight = randomXLeft + 40;
			int randomYBottom = randomYTop + (int) (Constant.L/4) + 10;
			boolean illegalLocation = false;
			for(int j = randomXLeft ; j < randomXRight; j++){
				for(int k = randomYTop; k < randomYBottom; k++) {
					if(game.getObstacle(j, k + Constant.SHIFTING_CONSTANT) != null) {
						illegalLocation = true;
					}
				}
				if(illegalLocation) {
					break;
				}
			}
			for(Obstacle currentHollowPurpleObstacle : hollowPurples) {
				if(currentHollowPurpleObstacle.getX() + Constant.INTENDED_OBSTACLE_DISTANCE >= randomXLeft && 
						currentHollowPurpleObstacle.getX() - Constant.INTENDED_OBSTACLE_DISTANCE <= randomXRight &&
						currentHollowPurpleObstacle.getY() + Constant.INTENDED_OBSTACLE_DISTANCE >= randomYTop && 
						currentHollowPurpleObstacle.getY() - Constant.INTENDED_OBSTACLE_DISTANCE <= randomYBottom) {
					illegalLocation = true;
				}
			}
			if(!illegalLocation) {
				Obstacle hp = factory.getObstacle(Constant.HOLLOW_PURPLE_OBSTACLE);
				hp.setX(randomXLeft);
				hp.setY(randomYTop);
				hollowPurples.add(hp);
			}
		}
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(i != j) {
					hollowPurples.get(i).getHorizontalList().add(hollowPurples.get(j));	
				}
			}
		}
		game.getHollowPurpleObstacles().addAll(hollowPurples);
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

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

}
