package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import domain.Constant;
import domain.LoginHandler;
import domain.login.LoginListener;
import domain.WelcomeWindowHandler;


/**
 * LoginUI creates and handle to UI part of Login Window in Need For Spear game 
 * @author Softwaring 
 */
public class LoginUI  extends JFrame {
	private double width = Constant.width;
	private double height = Constant.height;
	private JLabel userNameLabel = new JLabel("User Name");
	private JTextField logt = new JTextField("Enter your username:");
	private JButton loginButton = new JButton("Login");

	/**
	 * Constructor for LoginUI class
	 * @param loginHandler
	 */
	public LoginUI(LoginHandler loginHandler) {

		this.setSize((int)width, (int)height);
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		logt.setForeground(Color.GRAY);
		logt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logt.setText("");
				logt.setForeground(Color.BLACK);
			}
		});
		//add listeners and create the window
		loginButton.addActionListener( e -> {dispose(); 
		WelcomeWindowHandler welcomeWindowHandler = new WelcomeWindowHandler();
		LoginListener window = new WelcomeWindow(logt.getText(), welcomeWindowHandler);
		loginHandler.addListener(window);
		loginHandler.createWindow();});		
		userNameLabel.setForeground (Color.black);
		this.setVisible(true);
		add(new Panel(new GridBagLayout()));
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
			this.setBackground(Color.DARK_GRAY);
			Image bg = Toolkit.getDefaultToolkit().getImage("jpeg/bg.png");
			g.drawImage(bg, 0, 0, (int)Constant.width, (int)Constant.height, this);
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10, 10, 10, 10);
			c.gridx = 0;
			c.gridy = 0;     
			this.add(userNameLabel, c);

			c.gridx = 1;
			this.add(logt, c);

			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
			this.add(loginButton, c);

		}
	}
}
