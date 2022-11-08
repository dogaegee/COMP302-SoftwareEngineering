package domain;

import domain.welcomewindow.WelcomeAction;
import domain.welcomewindow.WelcomeListener;

/**
 * This class is a handler for the interactions of welcome window
 * @author Softwaring
 *
 */
public class WelcomeWindowHandler {
	private WelcomeAction wAction;
	
	public WelcomeWindowHandler() {
		wAction = new WelcomeAction();	
	}
	
	/**
	 * Invokes the creation of the welcome window
	 * @param l
	 */
	public void createWindow(WelcomeListener l) {
		wAction.setAction(l);
	}
	


}
