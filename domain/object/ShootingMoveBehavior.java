package domain.object;

import java.util.ArrayList;
import java.util.List;
import domain.Constant;
import domain.GameHandler;
import domain.object.obstacle.Obstacle;


/**
* ShootingMoveBehavior implements MoveBehviour
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class ShootingMoveBehavior implements MoveBehavior<Obstacle>{
	private List<GameObject> list = null;
	/**
	* Override method of MoveBehaviour
	* This movement handles the movement of magical hexes
	* @param o Hex object to make changes on it
	*/
	

	@Override
	public void move(Obstacle o) {
		// REQUIRES: MagicalHex is activated.
		// MODIFIES: modifies the hex dx, dy and x and y location.
		// EFFECTS: location of hex changes till collides with an obstacle
		//			obstacle is affected based on its specs such that 
		//			simple, explosive, gift: disappear; firm: life decrease; 
		//			frozen: no change
		o.setX(o.getX()+o.getDx()); 
		o.setY(o.getY()+o.getDy());
		
		if (o.getHorizontalList()!=null) {
			if (o.getX() <= 0)						GameHandler.getGame().getPaddle().getHexes().remove(o);
			if (o.getX() >= width - o.getWidth()) 	GameHandler.getGame().getPaddle().getHexes().remove(o);
			if (o.getY() <= 0)						GameHandler.getGame().getPaddle().getHexes().remove(o);
			if (o.getY() >= height - o.getHeight()) GameHandler.getGame().getPaddle().getHexes().remove(o);
			
			list = o.getHorizontalList(); 
			ArrayList<Obstacle> temp = new ArrayList<Obstacle>();
			for(GameObject b : list)
				temp.add((Obstacle)b);
			double startx = o.getX();
			double endx = o.getX() + o.getWidth();
			double starty = o.getY();
			double endy = o.getY()+o.getHeight();
			
			Obstacle tempc =  null;

			for(Obstacle c : temp) {

				if(!c.getPath().equals(Constant.BALL_PATH) && !c.getPath().equals(Constant.PADDLE_PATH)) {
					boolean hit = false;
					double startW = c.getX();
					double endW = c.getWidth() + c.getX();
					double startH = c.getY();
					double endH = c.getHeight() + c.getY();

						//Hit from below
					if(starty <= endH && starty >=startH) {
						if(endx>=startW && endx<=endW) {
							//Hit from left
								hit = true;
						}else if(startx>=startW && startx<=endW) {
								hit = true;
						}
					}
					if(hit) {

						tempc = c;
						if(!c.isFrozen())
						{
							tempc.setLife(tempc.getLife() - 1);
							if(tempc.getLife() == 0) {
								tempc.setWillBeDeleted(true);							
							}
							
						}
						list.remove(o);
						GameHandler.getGame().getPaddle().getHexes().remove(o);
						break;
					}
				}
			}	
		}
	}
}
