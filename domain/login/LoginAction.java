package domain.login;

public class LoginAction {
	
	private LoginListener l;
	
	public void setAction() {
		publishLoginEvent();
	}
	
	public void addLoginListener(LoginListener lis) {
		l = lis;
	}
	
	public void publishLoginEvent() {
		l.onLoginEvent();
	}

}
