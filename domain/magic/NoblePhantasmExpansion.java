package domain.magic;

import java.util.Timer;
import java.util.TimerTask;
import domain.Constant;
import domain.GameHandler;
import domain.MagicAction;
import domain.MagicHandler;
import domain.object.Paddle;

/**
 * NoblePhantasmExpansion is the class of a magical ability
 * which doubles the length of the noble phantasm for 30 seconds
 * when it is activated.
 * @author Softwaring
 */
public class NoblePhantasmExpansion implements Activatable{
	private int keyCode = 2;
	
	@Override
	/**
	 * First, it checks the exceptional cases for the location of the noble phantasm.
	 * When expansion is activated, if the noble phantasm overflows the limits of the screen, it must be relocated at this limits.
	 * Then, the width of the noble phantasm is set.
	 * It is active for 30 seconds with this thread.
	 * @param
	 * @return
	 */
	public void activate() {
		// TODO Auto-generated method stub
		Paddle paddle = GameHandler.getGame().getPaddle();
		int newPaddleWidth = paddle.getWidth() * 2;
		double possibleLeftX = paddle.getX() - newPaddleWidth/4;
		double possibleRightX = possibleLeftX + newPaddleWidth;
		if(possibleLeftX < 0) { //checking for exceptional cases.
			paddle.setX(0);
		} else if(possibleRightX > Constant.width) {
			paddle.setX(Constant.width - newPaddleWidth);
		} else {
			paddle.setX(possibleLeftX);
		}
		paddle.setWidth(newPaddleWidth); //width update of the noble phantasm
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	if(GameHandler.getGame().isStarted()) {
                	Constant.remainingExpansionMagicTime -= 1;
            	}
            	MagicHandler.getInstance().publishMagicEvent(keyCode, "Remaining Time:" + String.format("%.2f",Constant.remainingExpansionMagicTime));
            	if(Constant.isGameFinished == true) {
            		timer.cancel();
            	}
                if (Constant.remainingExpansionMagicTime == 0) {
                    timer.cancel();
                    deactivate();
                    Constant.remainingExpansionMagicTime = 30;
                    MagicAction.getCurrentMagicsAndActivations().put(2,false);
                }
            }
        }, 0, 1000);
	}
	
	@Override
	/**
	 * The width of the noble phantasm is halved.
	 * It is located by considering its center point.
	 * @param
	 * @return
	 */
	public void deactivate() {
		MagicHandler.getInstance().publishMagicEvent(keyCode, "Not active. You have " + Constant.remainingMagics.get(keyCode) + " left");
		Paddle paddle = GameHandler.getGame().getPaddle();
		int newPaddleWidth = paddle.getWidth() / 2;
		double possibleLeftX = paddle.getX() + paddle.getWidth()/4; //location update considering the center.
		paddle.setX(possibleLeftX);
		paddle.setWidth(newPaddleWidth); //width update of the noble phantasm
	}
	
	//Getters and setters
	
	public int getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
}
