package ui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import domain.BMHandler;
import domain.Constant;
import domain.GameHandler;
import domain.MagicHandler;
import domain.WelcomeWindowHandler;
import domain.welcomewindow.WelcomeListener;

/**
 * BMUI creates and handle to UI part of Building Mode in Need For Spear Game 
 * @author Softwaring 
 */
public class BMUI extends JFrame implements WelcomeListener, MouseListener, ActionListener{

	private BMHandler bmHandler;
	private BMObjectsUI objectsUI;
	private String username;
	private JLabel simpleLabel;
	private JLabel firmLabel;
	private JLabel giftLabel;
	private JLabel explosiveLabel;
	private double width = Constant.width;
	private double height = Constant.height;
	private static Container pane;
	private boolean tableActivityFinished = false;
	private boolean isObstacleSelected = false;

	/**
	 * Constructor for BMUI class
	 * @param BMHandler, String
	 */
	public BMUI(BMHandler bmHandler,String username) {
		this.bmHandler = bmHandler;
		this.username = username;
	}

	
	@Override
	public void onWelcomeEvent() {
		openBMUI();
	}

	/**
	 * Executes the opening BMUI
	 */
	private void openBMUI()
	{
		createWindow();
	} 

	private void createWindow() {
		objectsUI = new BMObjectsUI();
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setSize((int)width, (int)height);
		this.setLocation(0, 0);
		this.setVisible(true);

		pane = this.getContentPane();
		pane.setSize((int)width, (int)height);
		objectsUI.setPreferredSize(new Dimension((int)width, (int)height - Constant.menu_height));
		objectsUI.setMinimumSize(new Dimension((int)width, (int)height - Constant.menu_height));
		pane.add(objectsUI, BorderLayout.CENTER);

		//Content panel
		JPanel options = new JPanel(new GridBagLayout());
		options.setPreferredSize(new Dimension((int)width, Constant.menu_height));
		options.setMaximumSize(new Dimension((int)width, Constant.menu_height));
		options.setBackground(new Color(230,255,255));

		//start
		JButton startButton = new JButton("Start");
		JButton exitButton = new JButton("EXIT");
		JButton saveButton = new JButton("Save Map");
		JButton helpButton = new JButton("HELP!");

		BufferedImage simplePicture = null;
		BufferedImage firmPicture = null;
		BufferedImage giftPicture = null;
		BufferedImage explosivePicture = null;

		try {
			simplePicture = ImageIO.read(new File(Constant.SIMPLE_PATH));
			firmPicture = ImageIO.read(new File(Constant.FIRM_PATH));
			giftPicture = ImageIO.read(new File(Constant.GIFT_PATH));
			explosivePicture = ImageIO.read(new File(Constant.EXPLOSIVE_PATH));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<JTextField> textFields = new ArrayList<JTextField>();

		simpleLabel = new JLabel(new ImageIcon(simplePicture));
		JTextField numSimple = new JTextField("Simple Obstacles:");
		textFields.add(numSimple);
		numSimple.setForeground(Color.GRAY);

		firmLabel = new JLabel(new ImageIcon(firmPicture));
		JTextField numFirm = new JTextField("Firm Obstacles:");
		textFields.add(numFirm);
		numFirm.setForeground(Color.GRAY);

		giftLabel = new JLabel(new ImageIcon(giftPicture));
		JTextField numGift = new JTextField("Gift Obstacles:");
		textFields.add(numGift);
		numGift.setForeground(Color.GRAY);

		explosiveLabel = new JLabel(new ImageIcon(explosivePicture));
		JTextField  numExp = new JTextField("Explosive Obstacles:");
		textFields.add(numExp);
		numExp.setForeground(Color.GRAY);

		for(JTextField textField: textFields) {
			textField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			});
		}

		JButton addObstaclesButton = new JButton("Add Obstacles");
		addObstaclesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(bmHandler.getIsLoaded()) {
					JOptionPane.showMessageDialog(null, Constant.LOAD_ERROR);
					addObstaclesButton.setEnabled(false);
					numSimple.setEnabled(false);
					numFirm.setEnabled(false);
					numGift.setEnabled(false);
					numExp.setEnabled(false);
					tableActivityFinished = true;
				}else { 
					try {
						int simpleNumber = Integer.parseInt(numSimple.getText());
						int firmNumber = Integer.parseInt(numFirm.getText());
						int giftNumber = Integer.parseInt(numGift.getText());
						int explosiveNumber = Integer.parseInt(numExp.getText());

						if (!bmHandler.checkMaxObstacleCriteria(simpleNumber, firmNumber, giftNumber,
								explosiveNumber)) {
							JOptionPane.showMessageDialog(null, Constant.MAX_CRITERIA_ERROR);
						} else if (bmHandler.checkMinObstacleCriteria(simpleNumber, firmNumber, giftNumber,
								explosiveNumber)) {
							bmHandler.setObstacles();
							bmHandler.createCircularObstacleList();
							addObstaclesButton.setEnabled(false);
							numSimple.setEnabled(false);
							numFirm.setEnabled(false);
							numGift.setEnabled(false);
							numExp.setEnabled(false);
							tableActivityFinished = true;
						} else {
							JOptionPane.showMessageDialog(null, Constant.MIN_CRITERIA_ERROR);
						}
					}catch (Exception ex) {
						JOptionPane.showMessageDialog(null, Constant.TABLE_EMPTY_ERROR);
					}
				}
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.CENTER;
		options.add(startButton, c);

		c.gridx = 0;
		c.gridy = 1;
		options.add(exitButton, c);

		c.gridx = 2;
		c.gridy = 0;
		options.add(saveButton, c);
		
		c.gridx = 2;
		c.gridy = 1;
		options.add(helpButton, c);

		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		options.add(simpleLabel, c);

		c.gridx = 4;
		c.gridy = 1;
		options.add(numSimple, c);

		c.gridx = 5;
		c.gridy = 0;

		options.add(firmLabel, c);

		c.gridx = 5;
		c.gridy = 1;
		options.add(numFirm, c);

		c.gridx = 6;
		c.gridy = 0;

		options.add(giftLabel, c);

		c.gridx = 6;
		c.gridy = 1;
		options.add(numGift, c);

		c.gridx = 7;
		c.gridy = 0;

		options.add(explosiveLabel, c);

		c.gridx = 7;
		c.gridy = 1;
		options.add(numExp, c);

		c.gridx = 8;
		c.gridy = 0;
		options.add(addObstaclesButton, c);
		pane.add(options, BorderLayout.NORTH);

		exitButton.addActionListener ( e -> {dispose();});
		saveButton.addActionListener ( e -> {
			if (bmHandler.checkTotalObstacleNum()) {
				JOptionPane.showMessageDialog(null, Constant.MAX_CRITERIA_ERROR);
			}else if (bmHandler.getIsLoaded()) {
				bmHandler.save(username);
				JOptionPane.showMessageDialog(null, Constant.SAVED);
			} else if (bmHandler.checkMinObstacleCriteriaFromList()) {
				bmHandler.save(username);
				JOptionPane.showMessageDialog(null, Constant.SAVED);
			} else {
				JOptionPane.showMessageDialog(null, Constant.MIN_CRITERIA_ERROR);
			}
		});
		GameHandler gameHandler = new GameHandler();
		MagicHandler magicHandler = MagicHandler.getInstance();
		WelcomeListener startWindow= new GameUI(gameHandler, magicHandler, username);

		startButton.addActionListener(new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e){ 
				if (bmHandler.checkTotalObstacleNum()) {
					JOptionPane.showMessageDialog(null, Constant.MAX_CRITERIA_ERROR);
				} else if (bmHandler.checkMinObstacleCriteriaFromList()) {
					dispose();
					bmHandler.createHorizontalObstacleList();
					bmHandler.createWindow();
					bmHandler.stopTimer();
				} else {
					JOptionPane.showMessageDialog(null, Constant.MIN_CRITERIA_ERROR);
				}
			}
		});
		

		helpButton.addActionListener(e -> {
			WelcomeListener helpWindow= new HelpUI();
			(new WelcomeWindowHandler()).createWindow(helpWindow);});
		
		bmHandler.actOnButtons(startWindow);
		this.pack();
		bmHandler.addObjects(objectsUI);
	}
	/**
	 * Returns a boolean
	 * @param JLabel
	 * @return boolean
	 */
	public boolean isLabelSelected(JLabel label, Point point) {
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
	public void mouseClicked(MouseEvent e) {
		Point eP = e.getPoint();
		int xLoc = e.getX();
		int yLoc = e.getY();
		if (SwingUtilities.isRightMouseButton(e)) {
			bmHandler.handleObstacleRemoval(xLoc, yLoc);
		} else {
			if(isObstacleSelected == false) {
				if(tableActivityFinished||bmHandler.getIsLoaded()) {
					isObstacleSelected = true;
					if(isLabelSelected(simpleLabel, eP)) {
						bmHandler.createObstacle(Constant.SIMPLE_LABEL_CLICKED);
					}else if(isLabelSelected(firmLabel, eP)) {
						bmHandler.createObstacle(Constant.FIRM_LABEL_CLICKED);
					}else if(isLabelSelected(giftLabel, eP)) {
						bmHandler.createObstacle(Constant.GIFT_LABEL_CLICKED);
					}else if(isLabelSelected(explosiveLabel, eP)){
						bmHandler.createObstacle(Constant.EXPLOSIVE_LABEL_CLICKED);
					} else {
						isObstacleSelected = false;
					}
				}
			} else {
				bmHandler.handleObstacleCreation(xLoc-Constant.INTENDED_OBSTACLE_DISTANCE, yLoc-Constant.menu_height-Constant.INTENDED_OBSTACLE_DISTANCE, bmHandler.getSelectedObstacleToAdd(), false);
				isObstacleSelected = false;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) {
			Point obstacleLoc = e.getPoint();
			int xLoc = (int) obstacleLoc.getX();
			int yLoc = (int) obstacleLoc.getY();
			bmHandler.getSelectedObstacleRelocate(xLoc, yLoc);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) {
			bmHandler.dragObstacle(e.getX(), e.getY());
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}


