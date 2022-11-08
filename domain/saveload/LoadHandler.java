package domain.saveload;

import java.util.ArrayList;
import domain.BMHandler;
import domain.object.obstacle.Obstacle;

public class LoadHandler {
	private String[] arrGame;
	private String[] arrBuild;
	
	public String[] getGames(){
		return arrGame;	
	}

	public void createBuildWindow(BMHandler bmHandler, int index) {
		Load.setLoadType(index,"Build");
		ArrayList<Obstacle> temp = Load.getObstacles();
		bmHandler.setIsLoaded(true);
		bmHandler.setObstacleNums(Load.getObstacleNums());
	}
	public void initializeLoad(String username) {
		ArrayList<String[]> list = Load.initializeLoad(username);
		arrGame = list.get(0);
		arrBuild = list.get(1);
	}
	public String[] getBuilds() {
		return arrBuild;
	}
	public void createGameWindow(int index) {
		Load.setLoadType(index,"Game");		
	}
}