package domain.bm;

import java.util.ArrayList;
import java.util.TimerTask;
import domain.Drawable;
import domain.object.GameObject;
import domain.object.obstacle.Obstacle;

/**
* BMActionTask is a class to create and handle to Domain part of Building Mode in Need For Spear Game 
* @author Softwaring 
* @since 02-12-2021
* @version 2.0
*/

public class BMActionTask extends TimerTask{
	
	private Drawable drawable;
	private ArrayList <Obstacle> objList = new ArrayList<>(); //Arraylist to store objects
	
	/**
	* Constructor for BMActionTask class
	* @param ArrayList<GameObject>, Drawable
	*/
	public BMActionTask(ArrayList<Obstacle> list, Drawable drawable) {
		this.drawable = drawable;
		this.objList = list;
	}

	
	/**
	* After timertaskStart is called, run method begins its loops till it is stopped with user input
	*/
	@Override
	public void run() {
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.addAll(this.objList);
		drawable.triggerDraw(objects);
	}

}
