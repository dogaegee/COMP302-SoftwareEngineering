package ui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import domain.Constant;
import domain.GameHandler;
import domain.GameListener;
import domain.MagicAction;
import domain.MagicHandler;
import domain.WelcomeWindowHandler;
import domain.magic.MagicListener;
import domain.welcomewindow.WelcomeListener;


/**
* GameUI creates and handle to UI part of Game Mode in Need For Spear game 
* @author Softwaring 
*/
public class GameUI extends JFrame implements WelcomeListener, KeyListener, ActionListener, MouseListener, MagicListener, GameListener{

	private GameHandler gameHandler;
	private MagicHandler magicHandler;
	private BoardUI boardUI;
	private static JLabel timeLabel;
	private static JLabel scoreLabel;
	private static JLabel chancesLabel;
	private static JLabel magicalHexLabel;
	private static JLabel magicalHexRemainingTimeLabel;
	private static JLabel noblePhantasmExpansionLabel;
	private static JLabel noblePhantasmExpansionRemainingTimeLabel;
	private static JLabel unstoppableEnchantedSphereLabel;
	private static JLabel unstoppableEnchantedSphereRemainingTimeLabel;
	private static JLabel ymirLabel;
	private static JLabel ymirRemainingTimeLabel;
	private double width = Constant.width;
	private double height = Constant.height;
	private static Container pane;
	private JButton resumeButton;
	private JButton pauseButton;
	private JButton saveButton;
	private String username;
	private boolean isLoaded = Constant.isLoaded;
	
	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}
	/**
	 * Constructor for GameUI class
	 * @param gameHandler, magicHandler, username
	 */
	public GameUI(GameHandler gameHandler, MagicHandler magicHandler, String username) {
		this.username = username;
		this.gameHandler = gameHandler;
		this.magicHandler = magicHandler;
	}
	@Override
	public void onWelcomeEvent() {
		openGWindow();
	}


	/**
	 * Executes the opening GameUI
	 */
	private void openGWindow()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               createWindow();
               gameHandler.pauseTimer();
            }
        });

	} // end main
	
	
	/**
	 * Executes drawing panels and objects on GameUI
	 */
	private void createWindow() {
		magicHandler.addMagicListener(this);
		gameHandler.addGameListener(this);
		boardUI = new BoardUI();
		addKeyListener(this);
        setFocusable(true);
        this.setResizable(true);
        setFocusTraversalKeysEnabled(false);
        this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setSize((int)width, (int)height);
		this.setLocation(0, 0);
		this.setVisible(true);
		
		pane = this.getContentPane();
		pane.setSize((int)width, (int)height);
		boardUI.setPreferredSize(new Dimension((int)width, (int)height - Constant.menu_height));
		boardUI.setMinimumSize(new Dimension((int)width, (int)height - Constant.menu_height));
		pane.add(boardUI, BorderLayout.CENTER);

		Constant.changeHeight(Constant.menu_height);

		//Content panel
		JPanel options = new JPanel(new GridBagLayout());
		options.setPreferredSize(new Dimension((int)width, Constant.menu_height));
		options.setMaximumSize(new Dimension((int)width, Constant.menu_height));
		options.setBackground(new Color(230,255,255));
		pauseButton = new JButton(" PAUSE ");
		resumeButton = new JButton("RESUME");
		JButton helpButton = new JButton("HELP!");
		saveButton = new JButton("SAVE");
		JButton exitButton = new JButton("EXIT");
		chancesLabel = new JLabel("Chances: " + Constant.chances);
		scoreLabel = new JLabel("Score:" + 0);
		timeLabel = new JLabel("Time:" + 0);

		
		BufferedImage unstoppableImage = null;
		BufferedImage expansionImage = null;
		BufferedImage hexImage = null;
		BufferedImage ymirImage = null;

		try {
			unstoppableImage = ImageIO.read(new File("jpeg/uballLabel.png"));
			expansionImage = ImageIO.read(new File("jpeg/expansionLabel.png"));
			hexImage = ImageIO.read(new File("jpeg/fireLabel.png"));
			ymirImage = ImageIO.read(new File("jpeg/ymir.png"));

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		
		magicalHexLabel = new JLabel(new ImageIcon(hexImage));
		magicalHexLabel.setText("Magical Hex");
		magicalHexRemainingTimeLabel = new JLabel(Constant.magicalHexRemainingTimeLabel);
		
		noblePhantasmExpansionLabel = new JLabel(new ImageIcon(expansionImage));
		noblePhantasmExpansionLabel.setText("Noble Phantasm Expansion");
		noblePhantasmExpansionRemainingTimeLabel = new JLabel(Constant.noblePhantasmExpansionRemainingTimeLabel);
		
		unstoppableEnchantedSphereLabel = new JLabel(new ImageIcon(unstoppableImage));
		unstoppableEnchantedSphereLabel.setText("Unstoppable Enchanted Sphere");
		unstoppableEnchantedSphereRemainingTimeLabel = new JLabel(Constant.unstoppableEnchantedSphereRemainingTimeLabel);
		
		ymirLabel = new JLabel(new ImageIcon(ymirImage));
		ymirRemainingTimeLabel = new JLabel("Ymir is not reincarnated right now");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
	    c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.EAST;
		options.add(resumeButton, c);
		
		c.gridx = 0;
	    c.gridy = 1;
		options.add(exitButton, c);
		
		c.gridx = 2;
	    c.gridy = 0;
		options.add(pauseButton, c);
		
		c.gridx = 2;
	    c.gridy = 1;
		options.add(saveButton, c);
		
		c.gridx = 4;
	    c.gridy = 1;
		options.add(helpButton, c);
		
		c.gridx = 4;
	    c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		options.add(chancesLabel, c);
		
		c.gridx = 6;
	    c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		options.add(scoreLabel, c);
		
		c.gridx = 6;
	    c.gridy = 1;
		options.add(timeLabel, c);
	
		c.gridx = 7;
	    c.gridy = 0;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(magicalHexLabel, c);
		
		c.gridx = 7;
	    c.gridy = 1;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(magicalHexRemainingTimeLabel, c);
		
		c.gridx = 8;
	    c.gridy = 0;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(noblePhantasmExpansionLabel, c);
		
		c.gridx = 8;
	    c.gridy = 1;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(noblePhantasmExpansionRemainingTimeLabel, c);
		
		c.gridx = 9;
	    c.gridy = 0;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(unstoppableEnchantedSphereLabel, c);
		
		c.gridx = 9;
	    c.gridy = 1;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(unstoppableEnchantedSphereRemainingTimeLabel, c);
		
		c.gridx = 10;
	    c.gridy = 0;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(ymirLabel, c);
		
		c.gridx = 10;
	    c.gridy = 1;
	    c.gridwidth = 1;
		c.gridheight = 1;
		options.add(ymirRemainingTimeLabel, c);
		
		pane.add(options, BorderLayout.NORTH); 
		
		resumeButton.setFocusable(false);
		resumeButton.setEnabled(false);
		saveButton.setEnabled(false);

		pauseButton.setFocusable(false);
		pauseButton.setEnabled(false);
		

		helpButton.addActionListener(e -> {
			WelcomeListener helpWindow= new HelpUI();
			(new WelcomeWindowHandler()).createWindow(helpWindow);
			this.requestFocusInWindow();});
		
		saveButton.setEnabled(false);
		exitButton.addActionListener ( e -> {dispose(); gameHandler.pauseTimer(); gameHandler.endGame();});
		saveButton.addActionListener ( e -> {gameHandler.save(username);
											JOptionPane.showMessageDialog(null, Constant.SAVED);
											this.requestFocusInWindow();});
		
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameHandler.pauseTimer();
				pauseButton.setEnabled(false);
				resumeButton.setEnabled(true);
				saveButton.setEnabled(true);
			}
		});

		resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameHandler.resumeTimer();
				pauseButton.setEnabled(true);
				resumeButton.setEnabled(false);
				saveButton.setEnabled(false);
			}
		});
		this.pack();
		addMouseListener(this);
		gameHandler.createGame(boardUI);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_W) {
			gameHandler.resumeTimer();
			pauseButton.setEnabled(true);

		}
		if (pauseButton.isEnabled()) {
	
			if(e.getKeyCode() == KeyEvent.VK_H) {
				magicHandler.activateMagic(Constant.MAGICAL_HEX_PRESSED);
			} 
			if(e.getKeyCode() == KeyEvent.VK_T) {
				magicHandler.activateMagic(Constant.NOBLE_PHANTASM_EXPANSION_PRESSED);
			}
			if(e.getKeyCode() == KeyEvent.VK_U) {
				magicHandler.activateMagic(Constant.UNSTOPPABLE_ENCHANTED_SPHERE_PRESSED);
		    }
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				magicHandler.fireMagicalHex();
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				gameHandler.turnPaddle(Constant.D_PRESSED_ANGLE, Constant.D_PRESSED_SPEED);
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				gameHandler.turnPaddle(Constant.A_PRESSED_ANGLE, Constant.A_PRESSED_SPEED);
			}
	
			if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
				gameHandler.handlePaddleSpeed(Constant.RIGHT_PRESSED);
			 } else if(e.getKeyCode()== KeyEvent.VK_LEFT) {
				gameHandler.handlePaddleSpeed(Constant.LEFT_PRESSED);
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (pauseButton.isEnabled()) {
			gameHandler.handlePaddleSpeed(Constant.PAUSE_PRESSED);
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			gameHandler.returnPaddle();
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			gameHandler.returnPaddle();
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(isLoaded) {

			isLoaded = false;
			MagicAction.getCurrentMagicsAndActivations().forEach((magic, bool) -> {
				if(bool) {
					this.onMagicEvent(Constant.NOBLE_PHANTASM_EXPANSION, Constant.noblePhantasmExpansionRemainingTimeLabel);
					this.onMagicEvent(Constant.UNSTOPPABLE_ENCHANTED_SPHERE, Constant.unstoppableEnchantedSphereRemainingTimeLabel);
					this.onMagicEvent(Constant.MAGICAL_HEX, Constant.magicalHexRemainingTimeLabel);
					this.magicHandler.activateMagic(magic);

				}
		      });
		}

		gameHandler.resumeTimer();
		resumeButton.setEnabled(false);
		saveButton.setEnabled(false);
		pauseButton.setEnabled(true);
		Point clickedPoint = arg0.getPoint();
		if(isLabelClicked(magicalHexLabel, clickedPoint)) {
			magicHandler.activateMagic(Constant.MAGICAL_HEX_PRESSED);
		} else if(isLabelClicked(noblePhantasmExpansionLabel, clickedPoint)) {
			magicHandler.activateMagic(Constant.NOBLE_PHANTASM_EXPANSION_PRESSED);
		} else if(isLabelClicked(unstoppableEnchantedSphereLabel, clickedPoint)) {
			magicHandler.activateMagic(Constant.UNSTOPPABLE_ENCHANTED_SPHERE_PRESSED);
		}
		
	
		
	}
	public boolean isLabelClicked(JLabel label, Point point) {
		if(point.x >= label.getLocationOnScreen().x 
				&& point.y >= label.getLocationOnScreen().y
				&& point.x <= label.getLocationOnScreen().x + label.getWidth()
				&& point.y <= label.getLocationOnScreen().y + label.getHeight()){
				return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMagicEvent(int labelName, String information) {
		if(labelName == Constant.NOBLE_PHANTASM_EXPANSION) {
			Constant.noblePhantasmExpansionRemainingTimeLabel = information;
			noblePhantasmExpansionRemainingTimeLabel.setText(information);
		} else if(labelName == Constant.UNSTOPPABLE_ENCHANTED_SPHERE) {
			Constant.unstoppableEnchantedSphereRemainingTimeLabel = information;
			unstoppableEnchantedSphereRemainingTimeLabel.setText(information);
		} else if(labelName == Constant.MAGICAL_HEX) {
			Constant.magicalHexRemainingTimeLabel = information;
			magicalHexRemainingTimeLabel.setText(information);
		} else if(labelName == Constant.CHANCE_GIVING) {
			chancesLabel.setText(information);
		}
	}
	@Override
	public void onGameEvent(int labelName, String information) {
		// TODO Auto-generated method stub
		if(labelName == Constant.TIME_LABEL) {
			timeLabel.setText(information);
		} else if(labelName == Constant.SCORE_LABEL) {
			scoreLabel.setText(information);
		} else if(labelName == Constant.CHANCES_LABEL) {
			chancesLabel.setText(information);
			if(information.equals(Constant.LOSE_GAME)) {
				gameHandler.pauseTimer();
				resumeButton.setEnabled(false);
				pauseButton.setEnabled(false);
				saveButton.setEnabled(false);
				JOptionPane.showMessageDialog(null, Constant.LOSE_GAME_MESSAGE + Constant.score);
			} 
		} else if(labelName == Constant.YMIR_LABEL) {
			ymirLabel.setText(information);
		} else if(labelName == Constant.YMIR_TIME_LABEL) {
			ymirRemainingTimeLabel.setText(information);
		} else if(labelName == Constant.WIN) {
			gameHandler.pauseTimer();
			resumeButton.setEnabled(false);
			pauseButton.setEnabled(false);
			saveButton.setEnabled(false);
			this.dispose();
			JOptionPane.showMessageDialog(null, Constant.WIN_GAME_MESSAGE + Constant.score);
		}
	}
}


