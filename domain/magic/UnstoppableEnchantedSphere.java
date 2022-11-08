package domain.magic;

import java.util.Timer;
import java.util.TimerTask;
import domain.Constant;
import domain.GameHandler;
import domain.MagicAction;
import domain.MagicHandler;
import domain.object.Ball;

/**
 * UnstoppableEnchantedSphere is the class of a magical ability which
 * provides the sphere the power of destructing the obstacles without
 * reflecting back for 30 seconds when it is activated.
 * @author Softwaring
 */

public class UnstoppableEnchantedSphere implements Activatable{
	private int keyCode = 3;
	
	@Override
	/**
	 * To change moving behavior of the sphere, a boolean named unstoppable is changed.
	 * The looking of the sphere is changed.
	 * It is active for 30 seconds.
	 * @param
	 * @return
	 */
	public void activate() {
		// TODO Auto-generated method stub
		Ball ball = GameHandler.getGame().getBall();
	    ball.setUnstoppable(true); //unstoppable property is activated.
	    ball.setPath(Constant.UNS_BALL_PATH); //sphere looking is changed.
		//timeCounter = 0;
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	if(GameHandler.getGame().isStarted()) {
            		//timeCounter++;
            		Constant.remainingUnstoppableMagicTime -= 1;
            	}
            	MagicHandler.getInstance().publishMagicEvent(keyCode, "Remaining Time:" + String.format("%.2f",Constant.remainingUnstoppableMagicTime));
            	if(Constant.isGameFinished == true) {
            		timer.cancel();
            	}
                if (Constant.remainingUnstoppableMagicTime == 0) {
                    timer.cancel();
                    deactivate();
                    Constant.remainingUnstoppableMagicTime = 30;
                    MagicAction.getCurrentMagicsAndActivations().put(3,false);
                }
            }
        }, 0, 1000);
		
	}
	
	@Override
	/**
	 * The boolean is changed to change the move behavior.
	 * The looking is changed.
	 */
	public void deactivate() {
		// TODO Auto-generated method stub
		MagicHandler.getInstance().publishMagicEvent(keyCode, "Not active. You have " + Constant.remainingMagics.get(keyCode) + " left");
		Ball ball = GameHandler.getGame().getBall();
		ball.setUnstoppable(false); //unstoppable property is deactivated.
        ball.setPath(Constant.BALL_PATH); //sphere looking is changed.
	}
	
	//Getters and setters
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
}
