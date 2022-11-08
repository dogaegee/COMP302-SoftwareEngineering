package domain;

import domain.login.LoginAction;
import domain.login.LoginListener;

/**
 * This class is a handler for the login action
 * @author Softwaring
 *
 */
public class LoginHandler {
	private LoginAction loginAction;
	
	public LoginHandler() {
		loginAction = new LoginAction();
	}
	
	public void createWindow() {
		loginAction.setAction();
	}

	public void addListener(LoginListener lis) {
		loginAction.addLoginListener(lis);
	}
	

}
