package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import domain.welcomewindow.WelcomeListener;
import domain.BMHandler;
import domain.Constant;
import domain.GameHandler;
import domain.MagicHandler;
import domain.WelcomeWindowHandler;
import domain.login.LoginListener;
import domain.saveload.LoadHandler;

/**
 * WelcomeWindow creates and handle to UI part of Welcome Window in Need For Spear game 
 * @author Softwaring 
 */
public class WelcomeWindow extends JFrame  implements LoginListener{
	
	private String username;
	private JButton start = new JButton("Start New Game");
	private JButton bm =    new JButton("Build Mode");
	private JButton load =  new JButton("Load Game");
	private JButton quit =  new JButton("Quit");
	private JButton help =  new JButton("HELP!");
    private JLabel loginLabel = new JLabel();
	private double width = Constant.width;
	private double height = Constant.height;
	private WelcomeWindowHandler welcomeWindowHandler;

	/**
	 * Constructor for WelcomeWindow class
	 * @param username, welcomeWindowHandler
	 */
	public  WelcomeWindow(String username, WelcomeWindowHandler welcomeWindowHandler){
		this.username = username;
		this.welcomeWindowHandler = welcomeWindowHandler;
	}
	private void openWelcomeWindow() {
		this.setSize((int)width, (int)height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//game opening
		start.addActionListener( e -> {dispose(); 
		GameHandler gameHandler = new GameHandler();
		MagicHandler magicHandler = MagicHandler.getInstance();
		WelcomeListener startWindow= new GameUI(gameHandler, magicHandler, username);
		welcomeWindowHandler.createWindow(startWindow);});
		
		//building mode opening
		bm.addActionListener( e -> {dispose(); 
		BMHandler bmHandler = new BMHandler();
		WelcomeListener bmWindow= new BMUI(bmHandler,username);
		welcomeWindowHandler.createWindow(bmWindow);});
		
		loginLabel.setText("Welcome " + username + "!");
		loginLabel.setFont(new Font("Serif", Font.PLAIN, 14));
		

		load.addActionListener( e -> {dispose(); 
		LoadHandler loadHandler = new LoadHandler();
		WelcomeListener loadWindow= new LoadUI(loadHandler,username);
		welcomeWindowHandler.createWindow(loadWindow);});
		

		help.addActionListener(e -> {
			WelcomeListener helpWindow= new HelpUI();
			welcomeWindowHandler.createWindow(helpWindow);});
		
		quit.addActionListener ( e -> {dispose();});
		
		this.setVisible(true);
        add(new Panel(new GridBagLayout()));
	}
	
	@Override
	public void onLoginEvent() {
		openWelcomeWindow();
		this.setVisible(true);
	}
	
	/**
	 * Puts selected image as background
	 */
	private class Panel extends JPanel{
		
		public Panel(GridBagLayout gridBagLayout) {
			this.setLayout(gridBagLayout);

		}

		public void paintComponent(Graphics g){	
		    super.paintComponent(g);
	        this.setSize((int)width, (int)height);
	        this.setBackground(Color.white);
	        Image bg = Toolkit.getDefaultToolkit().getImage("jpeg/bg.png");
			g.drawImage(bg, 0, 0, (int)Constant.width, (int)Constant.height, this);
	        GridBagConstraints c = new GridBagConstraints();

	        c.insets = new Insets(10, 10, 10, 10);
	        c.fill = GridBagConstraints.CENTER;
	        c.gridx = 0;
	        c.gridy = 0; 
	        c.gridwidth = 2;
	        this.add(loginLabel, c);
	        c.fill = GridBagConstraints.BOTH;

	        c.gridy = 1;
	        this.add(start, c);
			
	        c.gridy = 2;
	        this.add(bm, c);

	        c.gridy = 3;
	        this.add(load, c);
	        
	        c.gridy = 4;
	        this.add(quit, c);
	        
	        c.gridy = 5;
	        this.add(help, c);
	        
	 }
	}
}