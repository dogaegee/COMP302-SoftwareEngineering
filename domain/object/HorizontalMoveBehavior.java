package domain.object;
/**
* HorizontalMoveBehaviour implements MoveBehviour
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/
public class HorizontalMoveBehavior implements MoveBehavior<GameObject> {

	/**
	* Override method of MoveBehaviour
	* @param GameObject
	* This movement only handles horizontal movements i.e only x coordinate changes
	*/
	@Override
	public void move(GameObject obj) {
		double nextLoc = obj.getX() + obj.getDx();
		if(nextLoc <= 0) {
			obj.setX(0);
		} else if(nextLoc >= width - obj.getWidth()) {
			obj.setX(width - obj.getWidth());
		} else {
			obj.setX(nextLoc);
		}

	}

}
