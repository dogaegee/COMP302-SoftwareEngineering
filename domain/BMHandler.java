package domain;

import java.util.ArrayList;
import domain.bm.BMAction;
import domain.object.GameObject;
import domain.object.obstacle.Obstacle;
import domain.welcomewindow.WelcomeListener;

/**
 * This class is a controller to handle situations between the domain and ui parts of the building mode.
 * @author Softwaring
 */
public class BMHandler {
	
	private BMAction bmAction;
	private boolean isLoaded = false;
	
			
	/**
	 * The constructor
	 */
	public BMHandler() {
			bmAction = new BMAction();
		}
	
	public void stopTimer() {
		bmAction.stop();
	}
	//Getter and setter methods for the class
	public boolean getIsLoaded() {
		return isLoaded;
	}
	
	public void setIsLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}
	
	public BMAction getBMAction() {
		return bmAction;
	}
	
	public void setBMAction(BMAction bmAction) {
		this.bmAction = bmAction;
	}
	
	/**
	 * Adds the ball instance.
	 * @param d
	 */
	public void addObjects(Drawable d) {
		bmAction.addDrawable(d);
	}
	
	/**
	 * Handles the changes in the obstacle locations
	 * @param x
	 * @param y
	 * @param obstacle
	 */
	public void handleObstacleLocation(double x, double y, Obstacle obstacle) {
		this.getBMAction().updateObstacleLocation(x, y,obstacle);
	}
	
	/**
	 * Handles the obstacle creation
	 * @param x
	 * @param y
	 * @param obstacle
	 * @param isGridLocating 
	 */
	public void handleObstacleCreation(double x, double y, Obstacle obstacle, boolean isGridLocating) {
		this.getBMAction().createObstacle(x, y, obstacle, isGridLocating);
	}
	
	/**
	 * Handles the obstacle removal
	 * @param obstacle
	 */
	public void handleObstacleRemoval(int xLoc, int yLoc) {
		bmAction.removeObstacle(xLoc, yLoc);
	}
	
	/**
	 * This method specifies the operations in the domain for the building mode window
	 */
	public void createWindow() {
		bmAction.setBMActionOption();
	}
	
	/**
	 * This method adds listeners to the building modes action class
	 * @param lis
	 */
	public void actOnButtons(WelcomeListener lis) {
		bmAction.addListener(lis);
	}

	public void save(String username) {
		bmAction.save(username);
	}
	
	public GameObject createObstacle(int obstacleNum) {
		increaseObstacleNum(obstacleNum);
		return bmAction.selectObstacle(obstacleNum);
	}
	
	public void createHorizontalObstacleList() {
		this.getBMAction().addHorizontalListToObstacles();
		
	}
	
	public void createCircularObstacleList() {
		this.getBMAction().addCircularListToObstacles();
	}
	
	public ArrayList<GameObject> getHorizontalList(GameObject obstacleToMove) {
		
		return ((Obstacle)obstacleToMove).getHorizontalList();
	}
	
	public boolean checkMaxObstacleCriteria(int simple, int firm, int gift, int explosive) {
		return bmAction.checkMaxCriteria(simple, firm, gift, explosive);
	}
	
	public boolean checkMinObstacleCriteria(int simple, int firm, int gift, int explosive) {
		return bmAction.checkMinCriteria(simple, firm, gift, explosive);
	}
	
	public boolean checkMinObstacleCriteriaFromList() {
		return bmAction.checkMinCriteriaFromList();
	}
	public void setObstacles() {
		bmAction.locateObstacles();
	}

	public Obstacle getSelectedObstacleRelocate(int xLoc, int yLoc) {
		Obstacle selectedObs = bmAction.getObstacle(xLoc, yLoc);
		bmAction.setCurrentObstacleToRelocate(selectedObs);
		return selectedObs;
	}
	
	public void increaseObstacleNum(int chosenObstacle) {
		ArrayList<Integer> obstacles = bmAction.getObstacleNums();
		obstacles.set(chosenObstacle, obstacles.get(chosenObstacle) + 1);
	}

	public Obstacle getSelectedObstacleToAdd() {
		return bmAction.getCurrentObstacleToCreate();
	}
	
	public void dragObstacle(double x, double y) {
		Obstacle obstacleToRelocate = bmAction.getCurrentObstacleToRelocate();
		if(obstacleToRelocate != null)
			handleObstacleLocation(x, y-Constant.menu_height, obstacleToRelocate);	
		bmAction.setCurrentObstacleToRelocate(null);
	}
	
	public boolean checkTotalObstacleNum() {
		if(bmAction.getTotalObstacleNum() > Constant.MAX_OBSTACLES) {
			return true;
		}
		return false;
	}

	public void setObstacleNums(ArrayList<Integer> obstacleNums) {
		
		bmAction.setObstacleNums(obstacleNums);
	}
}
