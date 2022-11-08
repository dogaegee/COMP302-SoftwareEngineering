package domain;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import domain.magic.Activatable;

/**
 * This class holds the constant numbers that we use to specify certain functionalities in the game
 * @author Softwaring
 *
 */
public class Constant {
	//Size related constants
	public final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final static double width = screenSize.getWidth();
	public static double height = screenSize.getHeight();
	public final static double L = width/10.0;
	public final static int menu_height = 80;
	public final static double barHeight = 30;
	public static int pointSize = ((int)(((int) (Constant.width - Constant.menu_height )) - (Constant.menu_height/2))/(int)(Constant.L/4 + 10) + 1)*((int)(((int) (Constant.height - 6*Constant.menu_height)) - (Constant.menu_height / 2)+1)/30 + 1);
	
	
	//Location related constants
	public final static int SHIFTING_CONSTANT = 110;
	public final static int MAX_OBSTACLE_Y_LOCATION = 400;
	public final static int MAX_OBSTACLE_X_LOCATION = 1400;
	public final static int INTENDED_OBSTACLE_DISTANCE = 20;
	//Time related constants
	public static double time = 0;
	public static double remainingExpansionMagicTime = 30;
	public static double remainingUnstoppableMagicTime = 30;
	public static double remainingHexMagicTime = 30;
	public final static int YMIR_REINCARNATION_TIME = 30;
	public final static int YMIR_SLEEP_TIME = 15;
	public static double ymirTimeCounter = 0;

	//Game logic related constants
	public static double score = 0;
	public static int chances = 3;
	public final static int MAX_OBSTACLES = 360;
	public final static int MIN_SIMPLE = 75;
	public final static int MIN_FIRM = 10;
	public final static int MIN_EXPLOSIVE = 5;
	public final static int MIN_GIFT = 10;
	public static boolean isGameFinished = false;
	public static int remainingExpansionMagic = 0;
	public static int remainingUnstoppableMagic = 0;
	public static int remainingHexMagic = 0;
	public static int currentAdversaryMagic;
	public static ArrayList<Integer> remainingMagics = new ArrayList<Integer>(Arrays.asList(0,0,0,0));
	public final static String LOSE_GAME = "Chances:0";
	//Message constants
	public final static String SAVED = "Your Game Is Saved!";
	public final static String MAX_CRITERIA_ERROR = "You have exceeded the upper limit of obstacle numbers.\nYou could have at most 360 obstacles!";
	public final static String LOAD_ERROR = "You have already load a building mode.\nYou cannot use table again\nYou can use drag and delete";
	public final static String TABLE_EMPTY_ERROR = "You have to enter numbers for all of the obstacles in the table!";
	public final static String MIN_CRITERIA_ERROR = "You have entered less obstacles than required. You should have at least :\nSimple Obstacle: 75\nFirm Obstacle: 10\nGift Obstacle: 10\nExplosive Obstacle: 5";
	public static final String NOSAVEDGAMES = "There are no saved games ";
	public static String YMIR_SITUATION = "YMIR IS SLEEPING.";
	public static boolean isLoaded = false;
	public final static String LOSE_GAME_MESSAGE = "GAME OVER\nYOU LOST!\nYour score: ";
	public final static String WIN_GAME_MESSAGE = "CONGRATULATIONS!\nYOU WIN!\nYour score: ";
	//Keyboard and mouse event constans
	public static final int PAUSE_PRESSED = 0;
	public static final int MAGICAL_HEX_PRESSED = 1;
	public static final int NOBLE_PHANTASM_EXPANSION_PRESSED = 2;
	public static final int UNSTOPPABLE_ENCHANTED_SPHERE_PRESSED = 3;
	public static final int D_PRESSED_ANGLE = 45;
	public static final int D_PRESSED_SPEED = 20;
	public static final int A_PRESSED_ANGLE = -45;
	public static final int A_PRESSED_SPEED = -20;
	public static final double RIGHT_PRESSED = 1.0;
	public static final double LEFT_PRESSED = -1.0;
	public static final int SIMPLE_LABEL_CLICKED = 0;
	public static final int FIRM_LABEL_CLICKED = 1;
	public static final int GIFT_LABEL_CLICKED = 2;
	public static final int EXPLOSIVE_LABEL_CLICKED = 3;
	
	//Factory related constants
	public static final int SIMPLE_OBSTACLE = 0;
	public static final int FIRM_OBSTACLE = 1;
	public static final int GIFT_OBSTACLE = 2;
	public static final int EXPLOSIVE_OBSTACLE = 3;
	public static final int HOLLOW_PURPLE_OBSTACLE = 4;
	public static final int NUM_OF_OBSTACLE_TYPE = 5;

	
	public static final int CHANCE_GIVING = 0;
	public static final int MAGICAL_HEX = 1;
	public static final int NOBLE_PHANTASM_EXPANSION = 2;
	public static final int UNSTOPPABLE_ENCHANTED_SPHERE = 3;
	public static final int INFINITE_VOID = 4;
	public static final int HOLLOW_PURPLE = 5;
	public static final int DOUBLE_ACCEL = 6;
	
	//Image paths
	public static final String PADDLE_PATH = "jpeg/paddle.png";
	public static final String HEX_PADDLE_PATH = "jpeg/paddleHex.png";
	public static final String BALL_PATH = "jpeg/ball.png";
	public static final String UNS_BALL_PATH = "jpeg/uball.png";
	public static final String SIMPLE_PATH = "jpeg/wood.png";
	public static final String FIRM_PATH = "jpeg/brick.png";
	public static final String FIRM1_PATH = "jpeg/brick1.png";
	public static final String FIRM2_PATH = "jpeg/brick2.png";
	public static final String FIRM3_PATH = "jpeg/brick3.png";
	public static final String FIRM4_PATH = "jpeg/brick4.png";
	public static final String FIRM5_PATH = "jpeg/brick5.png";
	public static final String GIFT_PATH = "jpeg/chest.png";
	public static final String EXPLOSIVE_PATH = "jpeg/bomb.png";
	public static final String FROZEN_SIMPLE_PATH = "jpeg/frozenWood.png";
	public static final String FROZEN_FIRM1_PATH = "jpeg/frozenBrick1.png";
	public static final String FROZEN_FIRM2_PATH = "jpeg/frozenBrick2.png";
	public static final String FROZEN_FIRM3_PATH = "jpeg/frozenBrick3.png";
	public static final String FROZEN_FIRM4_PATH = "jpeg/frozenBrick4.png";
	public static final String FROZEN_FIRM5_PATH = "jpeg/frozenBrick5.png";
	public static final String FROZEN_GIFT_PATH = "jpeg/frozenChest.png";
	public static final String FROZEN_EXPLOSIVE_PATH = "jpeg/frozenBomb.png";
	public static final String HOLLOW_PURPLE_PATH = "jpeg/hollowPurple.png";
	public static final String FIREBALL_PATH = "jpeg/fireball.png";
	public static final String EXPLOSIVE_REMAINS_PATH = "jpeg/remains.png";
	public static final String FALLING_GIFT_PATH = "jpeg/magic.png";

	//UI Related Constants
	public static final int CHANCES_LABEL = 0;
	public static final int SCORE_LABEL = 1;
	public static final int TIME_LABEL = 2;
	public static final int YMIR_LABEL = 3;
	public static final int YMIR_TIME_LABEL = 4;
	public static int WIN = 5;
	public static String noblePhantasmExpansionRemainingTimeLabel = "Not active. You have 0 left";
	public static String magicalHexRemainingTimeLabel = "Not active. You have 0 left";
	public static String unstoppableEnchantedSphereRemainingTimeLabel = "Not active. You have 0 left";

	
	/**
	 * This method handles the changes in the screen height
	 * @param h
	 */
	public static void changeHeight(double h) {
		height = height - h;
	}
	/**
	 * This method handles the time incrementation
	 */
	public static void incrementTimeByLoop() {
		time = time + 0.01;
	}
	
}
