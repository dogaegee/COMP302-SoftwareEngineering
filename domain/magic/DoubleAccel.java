package domain.magic;
import domain.Constant;
import domain.GameHandler;
import domain.object.Ball;
/**
 * DoubleAccel is the class of an adversary magical ability
 * which halves the speed of the enchanted sphere for 15 seconds.
 * It is activated with the activate() method and deactivated
 * with the deactivate() method.
 * @author Softwaring
 */
public class DoubleAccel implements Activatable{
	private int keyCode = 6;
	
	@Override
	/**
	 * activate() halves the speed of the enchanted sphere. (Activation)
	 * @param
	 * @return
	 */
	public void activate() {
		// TODO Auto-generated method stub
		Constant.YMIR_SITUATION = "Double Accel Is Active.";
		Ball ball = GameHandler.getGame().getBall();
		ball.setDx(ball.getDx() / 2);
		ball.setDy(ball.getDy() / 2);
	}
	@Override
	/**
	 * deactivate() doubles the speed of the enchanted sphere. (Deactivation)
	 * @param
	 * @return
	 */
	public void deactivate() {
		Ball ball = GameHandler.getGame().getBall();
		ball.setDx(ball.getDx() * 2);
		ball.setDy(ball.getDy() * 2);
	}
	
	//Getters and setters
	
	@Override
	public int getKeyCode() {
		// TODO Auto-generated method stub
		return keyCode;
	}

	@Override
	public void setKeyCode(int keyCode) {
		// TODO Auto-generated method stub
		this.keyCode = keyCode;
	}

}
