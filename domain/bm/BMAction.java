package domain.bm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;
import domain.Constant;
import domain.Drawable;
import domain.ObstacleFactory;
import domain.object.GameObject;
import domain.object.obstacle.Obstacle;
import domain.saveload.Save;
import domain.welcomewindow.WelcomeListener;

/**
* BMAction is a class to create and handle to Domain part of Building Mode in Need For Spear Game 
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/

public class BMAction {
	private Drawable drawable;
	private Obstacle obstacle;
	private List<WelcomeListener> listeners = new ArrayList<>(); //Listener List (Observer Pattern)
	private static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(); //Arraylist to store obstacle
	private static int totalObstacleNum; //Stores total number of obstacles
	private ArrayList<Integer> obstacleNums = new ArrayList<Integer>();
	private ObstacleFactory factory = ObstacleFactory.getInstance();
	private Obstacle currentObstacleToCreate;
	private Obstacle currentObstacleToRelocate;
	private TimerTask timerTask;
	/**
	* Adds listener to establish communication with UI (Observer Pattern)
	* @param WelcomeListener
	*/
	public void addListener(WelcomeListener l) {
		listeners.add(l);
	}




	/**
	* listeners listen for onWelcomeEvent()
	*/
	public void setBMActionOption() {
		for(WelcomeListener l: listeners)
			l.onWelcomeEvent();
	}
	
	/**
	* @param Drawable
	*/
	public void addDrawable (Drawable drawable) {
		this.drawable = drawable;
		timerTaskStart();
	}
	
	/**
	* Creates the BMActionTask which is used for animations to take place.
	*/
	private void timerTaskStart() {
        timerTask = new BMActionTask(obstacles, drawable);     
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 10);
	}
	
	/**
	* createObstacle method is responsible for creating obstacle when location of new obstacle is determined via random or dragging.
	* @param double, double, GameObject
	*/
	public void createObstacle(double x, double y, Obstacle obstacleRep, boolean isGridLocating) {
		obstacle = obstacleRep;
		if (null != obstacle) {
			boolean valid_place = true;
			double startingX = Constant.menu_height/2;
			double endingX = Constant.width - Constant.menu_height;
			double endingY = Constant.height - 6*Constant.menu_height;
			if(!isGridLocating) {
				for(GameObject o : obstacles) {
					if (x >= o.getX()- obstacle.getWidth()/2 && x <= o.getX() + o.getWidth() + obstacle.getWidth()/2 
							&& y >= o.getY() && y <= o.getY() + 2*obstacle.getHeight()) {
							valid_place = false;
							break;
					}
				}
			}
			
			if(valid_place && x >= startingX && x <= endingX 
					&& y >= Constant.barHeight && y <= endingY) {
				x = x-obstacle.getWidth()/2;
				y = y-obstacle.getHeight()/2;
				if(obstacle.getPath().equals(Constant.EXPLOSIVE_PATH)) {
					x += 5;
					y -= 5;
				}
				obstacle.setX(x);
				obstacle.setY(y);
				obstacle.setCenterX(obstacle.getX());
				obstacle.setCenterY(obstacle.getY() - 1.5*Constant.L);
				obstacles.add(obstacle);	
			}	
		}
	}
	
	/**
	* Used to update total obstacle number
	* @param int
	*/
	private void setTotalObstacleNum(int totalNum) {
		// TODO Auto-generated method stub
		BMAction.totalObstacleNum = totalNum;
	}

	/**
	* Used to removal of obstacles
	* @param GameObject
	*/
	public void removeObstacle(int xLoc, int yLoc) {
		GameObject remObstacle = getObstacle(xLoc, yLoc);
		if(remObstacle != null) {
			String className = remObstacle.getClass().getName();
			if(className.equals("domain.object.obstacle.SimpleObstacle")) {
				obstacleNums.set(0, obstacleNums.get(0)-1);
			}else if(className.equals("domain.object.obstacle.FirmObstacle")) {
				obstacleNums.set(1, obstacleNums.get(1)-1);
			}else if(className.equals("domain.object.obstacle.GiftObstacle")) {
				obstacleNums.set(2, obstacleNums.get(2)-1);
			}else if(className.equals("domain.object.obstacle.ExplosiveObstacle")){
				obstacleNums.set(3, obstacleNums.get(3)-1);
			} 
		}
		else {
			System.out.println("NullPointerException thrown!");
		}
		obstacles.remove(remObstacle);
		setTotalObstacleNum(getTotalObstacleNum() - 1);
	}
	
	/**
	 * Returns the object at point p
	 * @param x the x coordinate of the point
	 * @param y the y coordinate 
	 * @return	the obstacle located in the given point
	 */
	public Obstacle getObstacle(int x, int y) {
		// REQUIRES: 
		// MODIFIES: 
		// EFFECTS: If the point(x,y) obtained from x and y parameters is in the area of any obstacle in the given obstacle list,
		// this function returns the obstacle in that point.
		Obstacle obstacle = null;
		y -= 110;
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

	/**
	* It updates the location of the given obstacle with the given x and y
	* coordinates if the given x and y is not occupied by another obstacle.
	* @param double, double, Obstacle
	* @return
	*/
	public void updateObstacleLocation(double x, double y, Obstacle obstacleRep) {
		// REQUIRES: obstacleRep is not null.
		// MODIFIES: Updates the obstacleRep.x and obstacleRep.y
	    // EFFECTS: If the x and y parameters are in an area which is occupied by another obstacle in
		// 			the obstacles list, or x and y parameters are out of the obstacle placeable area, 
		// 			it does not update the obstacleRep.x and obstacle.Rep.y
		//			Otherwise, it sets obstacleRep.x and obstacle.Rep.y as x and y, respectively.
		obstacle = obstacleRep;
		boolean valid_place = true;
		
		double startingX = Constant.menu_height/2;
		double endingX = Constant.width - Constant.menu_height;
		double endingY = Constant.height - 6*Constant.menu_height;
		
		for(GameObject o : obstacles) {
			if (x >= o.getX()- obstacle.getWidth()/2 && x <= o.getX() + o.getWidth() + obstacle.getWidth()/2 
					&& y >= o.getY() && y <= o.getY() + 2*obstacle.getHeight()) {
					valid_place = false;
					break;
			}
		}
		if(valid_place && x >= startingX && x <= endingX 
				&& y >= Constant.barHeight && y <= endingY) {
			x = x-obstacle.getWidth()/2;
			y = y-obstacle.getHeight();
			if(obstacle.getPath().equals(Constant.EXPLOSIVE_PATH)) {
				x += 5;
				y -= 5;
			}
			obstacle.setX(x);
			obstacle.setY(y);
		}	
	}

	public static ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}
	public void save(String username) {
		ArrayList<Obstacle> temp = new ArrayList<Obstacle>();
		for(Obstacle b : obstacles)
			temp.add(b);
		Save.save(temp,null, null, null, username);
	}
	
	/**
	* addition of obstacles that can move horizontally to obstacles list.
	*/
	public void addHorizontalListToObstacles() {
		for(Obstacle currentObstacle: obstacles) {
			double yStartingRange = currentObstacle.getY();
			double yEndingRange = currentObstacle.getY() + currentObstacle.getHeight();
			for(Obstacle obs: obstacles) {
				if((obs.getY() + obs.getHeight() > yStartingRange && obs.getY() < yEndingRange
						&& obs.getX() != currentObstacle.getX() && !obs.equals(null)) || obs.getPath().equals("jpeg/bomb.png")) {
					currentObstacle.getHorizontalList().add(obs);
				}
			}
		}
		
	}
	
	/**
	* addition of obstacles that can move circular to obstacles list.
	*/
	public void addCircularListToObstacles() {
		for(Obstacle currentObstacle: obstacles) {
			if(currentObstacle.getPath().equals("jpeg/bomb.png")) {
				double xStart = currentObstacle.getCenterX() - 1.5*Constant.L;
				double xEnd = currentObstacle.getCenterX() + 1.5*Constant.L + currentObstacle.getWidth();
				double yStart = currentObstacle.getCenterY() - 1.5*Constant.L;;
				double yEnd = currentObstacle.getCenterY() + 1.5*Constant.L + currentObstacle.getHeight();
				for(GameObject obs: obstacles) {
					if((obs.getY() + obs.getHeight() >= yStart && obs.getY() <= yEnd
							&& obs.getX() + obs.getWidth() >= xStart && obs.getX() <= xEnd 
							&& !obs.equals(null) && !obs.equals(currentObstacle))) {
						currentObstacle.getCircularList().add(obs);
					}
				}
			}
		}
	}
	
	public int getTotalObstacleNum() {
		// TODO Auto-generated method stub
		return totalObstacleNum;
	}

	public static void setObstacles(ArrayList<Obstacle> obstacleList) {
		// TODO Auto-generated method stub
		BMAction.obstacles = obstacleList;
	}

	public boolean checkMaxCriteria(int simple, int firm, int gift, int explosive) {
		int totalObstacleNum = simple + firm + gift + explosive;
		if(totalObstacleNum > Constant.MAX_OBSTACLES) {
			return false;
		}
		return true;
	}

	public boolean checkMinCriteria(int simple, int firm, int gift, int explosive) {
		if (simple >= Constant.MIN_SIMPLE && firm >= Constant.MIN_FIRM && 
				gift >= Constant.MIN_GIFT && explosive >= Constant.MIN_EXPLOSIVE) {
			obstacleNums.add(simple);
			obstacleNums.add(firm);
			obstacleNums.add(gift);
			obstacleNums.add(explosive);
			return true;
		}
		return false;
	}
	
	public boolean checkMinCriteriaFromList() {
		if (obstacleNums.size()!=0 &&(obstacleNums.get(0) >= Constant.MIN_SIMPLE && obstacleNums.get(1) >= Constant.MIN_FIRM && 
				obstacleNums.get(2) >= Constant.MIN_GIFT && obstacleNums.get(3) >= Constant.MIN_EXPLOSIVE)) {
			return true;
		}
		return false;
	}
	/**
	 * Locates the given number of obstacles
	 * @param ArrayList<Integer>
	 * @return
	 */
	public void locateObstacles() {
		Integer[][] locs = createGrids();
		Random rand = new Random();
		IntStream randNums = rand.ints(0,locs.length).distinct().limit(obstacleNums.stream().reduce(0, Integer::sum));
		int[] randArr = randNums.toArray();
		int counter = 0;
		Obstacle currentObs = null;
		for(int i = 0; i < obstacleNums.size(); i++) {
			int obstacleNum = obstacleNums.get(i);
			for(int currentObsNum = 0; currentObsNum < obstacleNum; currentObsNum++) {
				currentObs = selectObstacle(i);
				createObstacle(locs[randArr[counter]][0], locs[randArr[counter]][1], currentObs, true);
				counter++;
			}
		}
	}

	/**
	 * Create Grid
	 * @param 
	 * @return Point[]
	 */
	public Integer[][] createGrids(){
		Integer[][] grids = new Integer[Constant.pointSize][2];
		int startingX = Constant.menu_height/2;
		int startingY = Constant.menu_height / 2;
		int endingX = (int) (Constant.width - Constant.menu_height );
		int endingY = (int) (Constant.height - 6*Constant.menu_height);
		int counter = 0;
		for(int column = startingX; column < endingX; column+= (Constant.L/4) + 10) {
			for(int row = startingY; row < endingY; row+= 20 + 10) {
				grids[counter][0] = column;
				grids[counter][1] = row;
				counter++;
			}
		}
		return grids;
	}

	public Obstacle selectObstacle(int obstacleNum) {
		Obstacle currentObstacle = factory.getObstacle(obstacleNum);
		setCurrentObstacleToCreate(currentObstacle);
		return currentObstacle;
	}

	public void stop() {
		timerTask.cancel();
	}
	
	public ArrayList<Integer> getObstacleNums() {
		return obstacleNums;
	}

	public void setObstacleNums(ArrayList<Integer> obstacleNums) {
		this.obstacleNums = obstacleNums;
	}
	
	public Obstacle getCurrentObstacleToCreate() {
		return currentObstacleToCreate;
	}

	public void setCurrentObstacleToCreate(Obstacle currentObstacle) {
		this.currentObstacleToCreate = currentObstacle;
	}

	public Obstacle getCurrentObstacleToRelocate() {
		return currentObstacleToRelocate;
	}

	public void setCurrentObstacleToRelocate(Obstacle currentObstacleToRelocate) {
		this.currentObstacleToRelocate = currentObstacleToRelocate;
	}

	
	
}
