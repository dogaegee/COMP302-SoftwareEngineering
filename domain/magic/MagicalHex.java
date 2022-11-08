package domain.magic;

import java.util.Timer;
import java.util.TimerTask;
import domain.Constant;
import domain.GameHandler;
import domain.MagicAction;
import domain.MagicHandler;
import domain.object.Paddle;

/**
 * MegicalHex is the class of a magical ability which
 * provides the noble phantasm to fire magical hexes and
 * shoots the obstacles.
 * @author Softwaring
 */
public class MagicalHex implements Activatable{
	private int keyCode = 1;
	
	@Override
	/**
	 * The looking of the noble phantasm is changed.
	 * To allow hex firing, isMagicalHexActive boolean of the Paddle object is set true.
	 * It is active for 30 seconds.
	 * @param
	 * @return
	 */
	public void activate() {
		// TODO Auto-generated method stub
		Paddle paddle = GameHandler.getGame().getPaddle();
		paddle.setPath(Constant.HEX_PADDLE_PATH); //Noble phantasm looking is changed.
		paddle.setMagicalHexActive(true); //Noble phantasm gained the hexes.
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	if(GameHandler.getGame().isStarted()) {
                	Constant.remainingHexMagicTime -= 1;
            	}
            	MagicHandler.getInstance().publishMagicEvent(keyCode, "Remaining Time:" + String.format("%.2f",Constant.remainingHexMagicTime));
            	if(Constant.isGameFinished == true) {
            		timer.cancel();
            	}
                if (Constant.remainingHexMagicTime == 0) {
                	Constant.remainingHexMagicTime = 30;
                	deactivate();
                	MagicAction.getCurrentMagicsAndActivations().put(1,false);
                	timer.cancel();
                }
            }
        }, 0, 1000);
	}
	
	
	@Override
	/**
	 * To prevent the hex firing, the boolean is set false.
	 * The looking is changed.
	 * @param
	 * @return
	 */
	public void deactivate() {
		// TODO Auto-generated method stub
		 MagicHandler.getInstance().publishMagicEvent(keyCode, "Not active. You have " + Constant.remainingMagics.get(keyCode) + " left");
		 Paddle paddle = GameHandler.getGame().getPaddle();
		 paddle.setMagicalHexActive(false);
         paddle.setPath(Constant.PADDLE_PATH);
    }
	
	//Getters and setters
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

}
