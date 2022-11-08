package domain;

import java.util.ArrayList;
import domain.object.GameObject;
/**
 * This interface is implemented by every class that needs to call the draw method. 
 * @author Softwaring
 *
 */

public interface Drawable {
	void triggerDraw(ArrayList<GameObject> o);
}
