package domain.object;

import java.util.ArrayList;
import domain.object.obstacle.Obstacle;
/**
* HorizontalObstacleMoveBehaviour implements MoveBehviour
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class HorizontalObstacleMoveBehavior implements MoveBehavior<Obstacle>{
	
	/**
	* Override method of MoveBehaviour
	* @param GameObject
	* This movement only handles horizontal movements i.e only x coordinate changes
	* Handles move for obstacles
	*/
	@Override
	public void move(Obstacle obj) {
		ArrayList<GameObject> horizontalList = obj.getHorizontalList();
		double currentLoc = obj.getX();
		double nextLoc = obj.getX() + obj.getDx();
		if(nextLoc <= 0) {
			obj.setDx(-obj.getDx());
			obj.setX(0);
			return;
		} else if(nextLoc >= width - obj.getWidth()) {
			obj.setDx(-obj.getDx());
			obj.setX(width - obj.getWidth());
			return;
		} 
		ArrayList<GameObject> deletedObs = new ArrayList<GameObject>();
		for(GameObject hittingCandidateObs: horizontalList) {
			if(hittingCandidateObs.isWillBeDeleted()) {
				deletedObs.add(hittingCandidateObs);
			} else {
				double currentCandidateLoc = hittingCandidateObs.getX();
				double currentCandidateLocY = hittingCandidateObs.getY();
				if(currentCandidateLocY + hittingCandidateObs.getHeight() >= obj.getY() && currentCandidateLocY <= obj.getY() + obj.getHeight()) {
					if(currentLoc >= currentCandidateLoc && currentLoc <= currentCandidateLoc + hittingCandidateObs.getWidth()) {
						obj.setDx(-obj.getDx());
						break;
					} 
					else if (currentLoc + obj.getWidth() >= currentCandidateLoc && 
							currentLoc + obj.getWidth()  <= currentCandidateLoc + hittingCandidateObs.getWidth()) {
						obj.setDx(-obj.getDx());
						break;
					}
				}
				
			}
		}
		obj.getHorizontalList().removeAll(deletedObs);
		nextLoc = obj.getX() + obj.getDx();
		obj.setX(nextLoc);
	}

}
