package ui;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import domain.Constant;
import domain.welcomewindow.WelcomeListener;


/**
 * HelpUI creates and handle to UI part of Help Window in Need For Spear game 
 * @author Softwaring 
 */
public class HelpUI extends JFrame implements WelcomeListener{

	private static Container pane;
	

	@Override
	public void onWelcomeEvent() {
		// TODO Auto-generated method stub
		openHelpWindow();
	}

	/**
	 * Executes the opening HelpUI class
	 */
	private void openHelpWindow() {
		//HelpWindow
		JFrame helpWindow = new JFrame();
		helpWindow.setSize((int)(0.75*Constant.width), (int)Constant.height/2);
		helpWindow.setLocation((int)(Constant.width/8), (int)Constant.height/4); 
		pane = helpWindow.getContentPane();
		pane.setSize((int)Constant.width/2, (int)Constant.height/2);
		GridLayout layout = new GridLayout(1,3);
		helpWindow.setLayout(layout);

		//Panel Constructor Left/Right
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel centerPanel =  new JPanel();
		helpWindow.add(leftPanel);
		helpWindow.add(centerPanel);
		helpWindow.add(rightPanel);
		helpWindow.setVisible(true);

		//LeftPanel inside
		String options[] = {"General Information", "Building Mode", "Save/Load", "Pause/Resume" , "Noble Phantasm", "Enchanted Sphere", "Obstacles", "Magics and Hexes"};
		JList optionList = new JList(options);
		optionList.setPreferredSize(new Dimension(leftPanel.getWidth(), (int)Constant.height/2));
		optionList.setSelectedIndex(0);
		leftPanel.add(optionList);

		//CenterPanel inside
		ImageIcon icon;
		JLabel picLabel = new JLabel();
		picLabel.setPreferredSize(new Dimension(centerPanel.getWidth(), (int)Constant.height/2));
		picLabel.setHorizontalAlignment(JLabel.CENTER);
		picLabel.setVerticalAlignment(JLabel.CENTER);
		picLabel.setBackground(new Color(230,255,255));

		centerPanel.add(picLabel);

		//RightPanel inside
		JTextPane infoScreen = new JTextPane();
		JScrollPane infoScreenScroll = new JScrollPane(infoScreen);
		infoScreen.setPreferredSize(new Dimension(rightPanel.getWidth(), (int)Constant.height/2));
		infoScreen.setEditable(false);
		rightPanel.add(infoScreenScroll);
		optionList.clearSelection() ;
		SimpleAttributeSet aSet = new SimpleAttributeSet(); 
        StyleConstants.setFontFamily(aSet, "lucida bright italic");
        StyleConstants.setFontSize(aSet, 15);
  
        SimpleAttributeSet bSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontFamily(bSet, "lucida typewriter bold");
        StyleConstants.setFontSize(bSet, 15);
  
        StyledDocument doc = infoScreen.getStyledDocument();
        doc.setCharacterAttributes(105, doc.getLength()-105, aSet, false);
        doc.setParagraphAttributes(0, 104, bSet, false);
        ImageIcon bmIcon = new ImageIcon("jpeg/generalGame.png");
		picLabel.setIcon(bmIcon);
		infoScreen.setText("Need for Spear is an easy and fun game to play. You are a brave soldier who is trying to obtain"
				+ " the Spear of Power that will enable its owner to rule the world given its powerful capabilities. "
				+ "Its creator, Shazam, the legendary wise magician, has created several obstacles to protect the spear in a "
				+ "way that only a worthy warrior shall be able to reach. In this game, you will compete against Ymir. \n"
				+ "Ymir represents the reincarnation of the great sorcerer. Ymir is the spirit of an old sorcerer that was "
				+ "brought to life again in order to prevent any warrior from obtaining the spear of power. Try to reach the "
				+ "Spear of Power while figthing agains Ymir's hexes!\n There are several types of obstacles that you shall face. "
				+ "You can obtain magical abilities during your quest. Some of the magical abilities can be used to enhance "
				+ "attacking power and status (Useful Magical Abilities), If you finishe all the obstacles, you are deemed the "
				+ "winner and the one worthy of this treasure. \n" + 
				"You can control noble phantasm and enchanted sphere. A noble phantasm is a paddle-like object that is used to "
				+ "deflect the enchanted sphere from falling to the ground. The enchanted sphere is the object that is sent "
				+ "around to destroy obstacles, but it is affected by gravity, therefore the noble phantasm is used to set its "
				+ "track towards the target obstacles to destroy them. If the enchanted sphere falls below the noble phantasm, "
				+ "you lose a chance. You have 3 chances only! Once you runs out of chances, you are considered unworthy and"
				+ " therefore lose the game :( .\n"
				+ "But do not worry, you can resart the game as much as you want!\n\n");
		optionList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int index = optionList.getSelectedIndex() - 1;
				if(index == -1) {
					ImageIcon bmIcon = new ImageIcon("jpeg/generalGame.png");
					picLabel.setIcon(bmIcon);
					infoScreen.setText("Need for Spear is an easy and fun game to play. You are a brave soldier who is trying to obtain"
							+ " the Spear of Power that will enable its owner to rule the world given its powerful capabilities. "
							+ "Its creator, Shazam, the legendary wise magician, has created several obstacles to protect the spear in a "
							+ "way that only a worthy warrior shall be able to reach. In this game, you will compete against Ymir. \n"
							+ "Ymir represents the reincarnation of the great sorcerer. Ymir is the spirit of an old sorcerer that was "
							+ "brought to life again in order to prevent any warrior from obtaining the spear of power. Try to reach the "
							+ "Spear of Power while figthing agains Ymir's hexes!\n There are several types of obstacles that you shall face. "
							+ "You can obtain magical abilities during your quest. Some of the magical abilities can be used to enhance "
							+ "attacking power and status (Useful Magical Abilities), If you finishe all the obstacles, you are deemed the "
							+ "winner and the one worthy of this treasure. \n" + 
							"You can control noble phantasm and enchanted sphere. A noble phantasm is a paddle-like object that is used to "
							+ "deflect the enchanted sphere from falling to the ground. The enchanted sphere is the object that is sent "
							+ "around to destroy obstacles, but it is affected by gravity, therefore the noble phantasm is used to set its "
							+ "track towards the target obstacles to destroy them. If the enchanted sphere falls below the noble phantasm, "
							+ "you lose a chance. You have 3 chances only! Once you runs out of chances, you are considered unworthy and"
							+ " therefore lose the game :( .\n"
							+ "But do not worry, you can resart the game as much as you want!\n\n");
				}
				else if(index == 0) {
					ImageIcon bmIcon = new ImageIcon("jpeg/bmUIimage.png");
					picLabel.setIcon(bmIcon);
					infoScreen.setText("Before starting the game, you can build the map" +" in the Build Mode! First, you need to input numbers for "
							+ "each obstacle type to the table above. Then, by pressing " 
							+ "Add Obstacles your obstacles wil be placed in the map as "
							+ "shown. After using the table, you can drag obstacles"  
							+ " however you wish or add new ones by first clicking the " 
							+ "image in the table then clicking the desired space. "
							+ "You can also delete an obstacle if you RIGHT CLICK on it once."
							+ "Be aware that there are limitations for the " 
							+ "obstacle counts." + "\n"
							+ "HAVE FUN!");
					
				}else if(index==1){
					ImageIcon slIcon = new ImageIcon("jpeg/saveload.png");
					picLabel.setIcon(slIcon);
					infoScreen.setText("If you login previously with the same username, you can select a previously loaded"
							+ "game or map to start. Your saved games and maps are stored seperatly and you can"
							+ "choose different saved files."
							+ "Just a quick heads up: To save the game you need to PAUSE FIRST!");
				}else if(index==2){
					ImageIcon prIcon = new ImageIcon("jpeg/resume.png");
					picLabel.setIcon(prIcon);
					infoScreen.setText("While playing the game, you can pause it whenever you want and resume playing."
							+ "Just a quick heads up: To save the game you need to PAUSE FIRST!");
				}else if(index==3){
					ImageIcon paddleIcon = new ImageIcon("jpeg/paddles.png");
					picLabel.setIcon(paddleIcon);
					infoScreen.setText("You can control the Noble Phantasm with right and left arrow keys. \nTo turn the paddle"
							+ " left, press A. \nTo turn the paddle right, press D."
							+ " The upper one is the normal paddle. When you activate the NoblePhantasmHex magic, paddle will turn into the second one.");
				}else if(index==4){
					ImageIcon ballIcon = new ImageIcon("jpeg/balls.png");
					picLabel.setIcon(ballIcon);

					infoScreen.setText("This is the Enchanted Sphere. Protect it at all costs!"
							+ "If you drop it you will lose one chance. You can bounce it using the Noble Phantasm."
							+ "\nThe ball speeds up if it hits an object moving in the same horizontal direction."
							+ "The red one is the normal ball. When you activate the Unstoppable Enchanted Sphere, the ball will turn purple.");
				}else if(index==5){
					ImageIcon obsIcon = new ImageIcon("jpeg/obstacles.png");
					picLabel.setIcon(obsIcon);
					infoScreen.setText("There are four types of obstacles."
							+ "\n1- Simple Obstacle: They break down if Enchanted Sphere hits. They sometimes move horizontally."
							+ "\n2- Firm Obstacle: They break down when the number on them reaches 0. They sometimes move horizontally."
							+ "\n3- Gift Obstacle: They break down if Enchanted Sphere hits and they drop a magical ability. To collect it, you should "
							+ "touch them with Noble Phantasm."
							+ "\n4- Explosive Obstacle: ATTENTION! They break down if Enchanted Sphere hits but if the Noble Phantasm touches any of "
							+ "the remainings, you loose one chance.");
				}else if(index==6){
					ImageIcon magicIcon = new ImageIcon("jpeg/magics.png");
					picLabel.setIcon(magicIcon);
					infoScreen.setText("There are 4 types of magical abilities."
							+ "\n1- Chance giving ability: It gives you one additional chance. You do not need to activate it."
							+ "\n2- Noble Phantasm Expansion: It makes the Noble Phantasm twice as big. You can activate it by either pressing the button T, or pressing its icon on the screen. Once activated, it lasts for only 30 seconds, after which the noble phantasm returns to its original state."
							+ "\n3- Magical Hex: This ability equips the noble phantasm with two magical canons on both of its ends. They can fire magical hexes that can hit the obstacles. You can activate it by pressing H or pressing its icon on the screen. Once activated press space bar to fire, it remains active for only 30 seconds and then disappears afterwards."
							+ "\n4- Unstoppable Enchanted Sphere: This ability upgrades the enchanted sphere and makes it much more powerful, such that if it hits any obstacles, it destroys it and passes through it regardless of its type (even for the firm obstacles). You can activate it by pressing U or pressing its icon on the screen. This upgrade only lasts 30 seconds after it is activated."
							+ "\nBEWARE THE WITCH YMIR!"
							+ "\nEvery 30 seconds, with probabilty 0.5 Ymir activates one of the hexes."
							+ "\nIn the middle you can see Ymir! She tries to slow you down with 3 hexes."
							+ "\n1- Double Accel: The speed of the enchanted sphere of the player is reduced by half. Its effect lasts for 30 seconds."
							+ "\n2-Hollow Purple: 8 additional purple obstacles are added. You need to break them to finish the game but they do not give you any points."
							+ "\n3- Infinite Void: 8 selected obstacles are frozen for 30 seconds. You cannot break them with the normal ball but Unstoppable Enchanted Sphere can break them!\n\n");
				}

			}
		});

	} // end main

}
