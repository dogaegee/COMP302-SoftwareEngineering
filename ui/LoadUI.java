package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import domain.BMHandler;
import domain.Constant;
import domain.GameHandler;
import domain.LoginHandler;
import domain.MagicHandler;
import domain.WelcomeWindowHandler;
import domain.login.LoginListener;
import domain.saveload.LoadHandler;
import domain.welcomewindow.WelcomeListener;


/**
* LoadUI creates and handle to UI part of Load Window in Need For Spear game 
* @author Softwaring 
*/
public class LoadUI extends JFrame implements WelcomeListener {
	private double width = Constant.width;
	private double height = Constant.height;
	private LoadHandler loadHandler;
	private JButton buttonGame;
	private JButton buttonBuild;
	private String username; 

	public LoadUI(LoadHandler loadHandler, String text) {
		this.loadHandler = loadHandler;
		this.username = text;
	}

	@Override
	public void onWelcomeEvent() {
		openLoadWindow();
	}

	@SuppressWarnings("unused")
	/**
	 * Executes the opening LoadUI class
	 */
	private void openLoadWindow() {

		WelcomeWindowHandler welcomeWindowHandler = new WelcomeWindowHandler();
		LoginListener welcomeWindow= new WelcomeWindow(username,welcomeWindowHandler);
		LoginHandler loginHandler = new LoginHandler();
		loginHandler.addListener(welcomeWindow);
		this.setSize((int)width/3, (int)height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		this.setLocationRelativeTo(null);

		buttonGame = new JButton("START");
		buttonBuild = new JButton("BUILD");
		loadHandler.initializeLoad(username);
		JComboBox cbBuild = new JComboBox();
		JComboBox cbGame = new JComboBox();
		String[] arrGame = loadHandler.getGames();
		String[] arrBuild = loadHandler.getBuilds();
		if(arrGame.length == 0 ) {
			if(arrBuild.length == 0)
			{
				JOptionPane.showMessageDialog(null, Constant.NOSAVEDGAMES);
				dispose(); 
				loginHandler.createWindow();
			}else {
				buttonGame.setEnabled(false);
				cbBuild =new JComboBox(arrBuild);
				cbGame =new JComboBox();
			}
		}else if(arrBuild.length == 0){
			buttonBuild.setEnabled(false);
			cbBuild =new JComboBox();
			cbGame =new JComboBox(arrGame);
		}else {
			cbGame =new JComboBox(arrGame);
			cbBuild =new JComboBox(arrBuild);
		}

		cbGame.setBounds(50, 50,90,20);    
		cbBuild.setBounds(50, 50,90,20);  


		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10, 10, 10, 10);
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0; 
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		this.add(cbGame, c);
		c.gridy = 1; 
		this.add(buttonGame, c);
		c.gridy = 2; 
		this.add(cbBuild, c);
		c.gridy = 3; 
		this.add(buttonBuild, c);

		JComboBox cbGameFinal = cbGame;
		JComboBox cbBuildFinal = cbBuild;
		
		buttonBuild.addActionListener( e -> {dispose(); 
		Constant.isLoaded = true;
		BMHandler bmHandler = new BMHandler();
		WelcomeListener bmWindow= new BMUI(bmHandler,username);
		loadHandler.createBuildWindow(bmHandler,cbBuildFinal.getSelectedIndex());
		welcomeWindowHandler.createWindow(bmWindow);
});
		
		buttonGame.addActionListener( e -> {dispose();
		Constant.isLoaded = true;
		GameHandler gameHandler = new GameHandler();
		MagicHandler magicHandler = MagicHandler.getInstance();
		WelcomeListener startWindow= new GameUI(gameHandler, magicHandler, username);
		loadHandler.createGameWindow(cbGameFinal.getSelectedIndex());
		welcomeWindowHandler.createWindow(startWindow);
		});

	}

}