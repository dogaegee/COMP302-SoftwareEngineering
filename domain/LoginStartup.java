package domain;

import javax.swing.SwingUtilities;
import ui.LoginUI;

/**
 * This class holds our main function.
 * @author Softwaring
 *
 */
public class LoginStartup {
	private static LoginUI loginGameUI;
	private static LoginHandler loginHandler;

	public static void main(String[] args) {
		loginHandler = new LoginHandler();

		//thread starts
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CreateAndShowGUI();
			}
		});
	}
	
	private static void CreateAndShowGUI() {
		loginGameUI = new LoginUI(loginHandler);
		loginGameUI.setVisible(true);
	}

}
