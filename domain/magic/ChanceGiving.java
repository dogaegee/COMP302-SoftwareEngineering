package domain.magic;

import domain.Constant;
/**
 * ChanceGiving is the class of a magical ability which ,
 * increases the chances of the player immediately. 
 * @author Softwaring
 */
public class ChanceGiving implements Activatable{
	private int keyCode = 0;

	@Override
	/**
	 * Increases the chances of the player.
	 * @param
	 * @return
	 */
	public void activate() {
		// TODO Auto-generated method stub
		Constant.chances += 1;
	}
	
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
	}
	
	//Getters and setters
	
	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	

}
