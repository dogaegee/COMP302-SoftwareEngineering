package domain.saveload;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import domain.BMHandler;
import domain.Constant;
import domain.MagicAction;
import domain.bm.BMAction;
import domain.object.Ball;
import domain.object.GameObject;
import domain.object.Paddle;
import domain.object.obstacle.ExplosiveObstacle;
import domain.object.obstacle.ExplosiveRemains;
import domain.object.obstacle.FallingGiftObstacle;
import domain.object.obstacle.FirmObstacle;
import domain.object.obstacle.GiftObstacle;
import domain.object.obstacle.HollowPurpleObstacle;
import domain.object.obstacle.Obstacle;
import domain.object.obstacle.SimpleObstacle;

/**
 * Load is a class to load a game or building mode 
 * @author Softwaring 
 * @version 2.0
 */
public class Load {
	private static int chosenIndex = -1;
	private static JSONArray gameList; 
	private static JSONArray buildList;
	private static String type;
	private static JSONObject game;
	private static JSONObject build;
	private static ArrayList<Integer> obstacleNums = new ArrayList<Integer>();

	/**
	 * Checks the username and put dates of games and building modes
	 * into an array.
	 * @param username 
	 * @return the list that contains two elements first index stores number of games 
	 * second one stores number of building modes
	 */
	public static ArrayList<String[]> initializeLoad (String username) {
		// REQUIRES: 
		// MODIFIES: Checks the username and put dates of games and building modes
		// 			 into an array.
		// EFFECTS: the list that contains number of modes returned

		JSONParser jsonParser = new JSONParser();
		ArrayList<String[]> result = new ArrayList<String[]>();
		try (FileReader reader = new FileReader("users/"+username+".json"))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray tempList= (JSONArray) obj;
			gameList = new JSONArray();
			buildList = new JSONArray();
			for(int i=0; i<tempList.size();i++) {
				JSONObject saved = ((JSONObject)(tempList.get(i)));
				String mode = (String)(((JSONObject)(saved.get("GameInfo"))).get("Game Type"));

				if(mode.equals("Building Mode")) {
					buildList.add(saved);
				}else {
					gameList.add(saved);
				}
			}  
			//gameList = (JSONArray) obj;
			String[] arrGame = new String[gameList.size()];
			String[] arrBuild = new String[buildList.size()];
			for(int i=0; i<arrGame.length;i++) {
				JSONObject temp = (JSONObject) gameList.get(i);
				arrGame[i] = (String)(((JSONObject) temp.get("GameInfo")).get("date"));
			}
			for(int i=0; i<arrBuild.length;i++) {
				JSONObject temp = (JSONObject) buildList.get(i);
				arrBuild[i] = (String)(((JSONObject) temp.get("GameInfo")).get("date"));
			}

			result.add(arrGame);
			result.add(arrBuild);
			//Iterate over employee array

		} catch (FileNotFoundException e) {
			result.add(new String[0]);
			result.add(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result; 

	}


	/**
	 * Sets the chosenIndex with respect to chosen mode by user 
	 * and sets the type of mode.
	 * @param chosenIndex Chosen index for loaded games and build modes
	 * @param type Type of mode GameMode or BuildMode
	 */
	public static void setLoadType(int chosenIndex, String type) {
		// REQUIRES: 
		// MODIFIES: Sets the chosenIndex with respect to chosen mode by user 
		// 			 and sets the type of mode.
		// EFFECTS: chosenIndex and type are changed

		Load.chosenIndex = chosenIndex;
		Load.type = type;		
	}

	/**
	 * Loads the game. If there is no selected mode it automatically loads 
	 * default.json
	 */
	@SuppressWarnings("unchecked")
	public static void loadGame() 
	{
		// REQUIRES:
		// MODIFIES: To load the game, it checks chosenIndex
		//			 chosenIndex is -1 if there are no loaded games and this means default is going to be loaded
		//			 Otherwise it uses that index to load specific game
		// EFFECTS: game JSONObject is modified and other methods can use this object
		if(chosenIndex == -1) {
			initializeLoad("default");
			type = "Game";
			chosenIndex=0;
		}

		game = (JSONObject) gameList.get(chosenIndex);
		JSONObject gameDetails = (JSONObject) game.get("GameInfo");
		Constant.time = (double) gameDetails.get("time");
		Constant.chances = ((Long) gameDetails.get("chances")).intValue();
		Constant.score = (double) gameDetails.get("score");
		
		JSONObject magicDetails = (JSONObject) game.get("magicInfo");
		

		
		HashMap<Integer, Integer> magics = new HashMap<Integer, Integer>();
		HashMap<Integer, Boolean> currentMagicsAndActivations = new HashMap<Integer,Boolean>();
		ArrayList<Integer> remainingMagics = new ArrayList<Integer>();
		
		HashMap<String, String> loadedMagics = (HashMap<String, String>) magicDetails.get("magics");
		HashMap<String, String> loadedCurrentMagicsAndActivations = (HashMap<String, String>) magicDetails.get("activation");
		ArrayList<String> loadedRemainingMagics = (ArrayList<String>) magicDetails.get("remainingMagics");

		
		loadedMagics.forEach((magic, bool) -> {
			magics.put(Integer.parseInt(magic), Integer.parseInt(bool));
	      });
		
		loadedCurrentMagicsAndActivations.forEach((magic, bool) -> {
			currentMagicsAndActivations.put(Integer.parseInt(magic), Boolean.parseBoolean(bool));
	      });
		
		loadedRemainingMagics.forEach((magic) -> {
			remainingMagics.add(Integer.parseInt(magic));
	      });
		

		MagicAction.setMagics(magics);
		MagicAction.setCurrentMagicsAndActivations(currentMagicsAndActivations);
		Constant.remainingMagics = remainingMagics;

		Constant.noblePhantasmExpansionRemainingTimeLabel = (String) magicDetails.get("noblePhantasmExpansionRemainingTimeLabel");
		Constant.magicalHexRemainingTimeLabel = (String) magicDetails.get("magicalHexRemainingTimeLabel");
		Constant.unstoppableEnchantedSphereRemainingTimeLabel = (String) magicDetails.get("unstoppableEnchantedSphereRemainingTimeLabel");
		

		Constant.YMIR_SITUATION = (String) magicDetails.get("YMIR_SITUATION");
		Constant.ymirTimeCounter = (double) magicDetails.get("ymirTimeCounter");
		Constant.currentAdversaryMagic = (int)((long)magicDetails.get("currentMagic"));
		Constant.remainingExpansionMagicTime = (double) magicDetails.get("remainingExpansionMagicTime");
		Constant.remainingUnstoppableMagicTime = (double) magicDetails.get("remainingUnstoppableMagicTime");
		Constant.remainingHexMagicTime = (double) magicDetails.get("remainingHexMagicTime");
		
	}

	/**
	 * Returns ball from saved game using JSONObject from loadGame()
	 * @return ball object from the saved game
	 */
	public static Ball getBall() {
		// REQUIRES: loadGame is called
		// MODIFIES: Uses JSONObject to reach ball's values and creates a ball 
		// EFFECTS: ball object with given data is created and returned
		Ball ball = new Ball();
		JSONObject ballObj = (JSONObject) game.get("ball");
		double dx = (double) ballObj.get("dx");
		double dy = (double) ballObj.get("dy");
		double x = (double) ballObj.get("x");
		double y = (double) ballObj.get("y");
		ball.setDx(dx);
		ball.setDy(dy);
		ball.setX(x);
		ball.setY(y);
		return ball;
	}

	/**
	 * Returns paddle from saved game using JSONObject from loadGame()
	 * @return paddle object from the saved game
	 */
	public static Paddle getPaddle() {
		// REQUIRES: loadGame is called
		// MODIFIES: Uses JSONObject to reach paddle's values and creates a paddle 
		// EFFECTS: paddle object with given data is created and returned


		Paddle paddle = new Paddle();
		JSONObject paddleObj = (JSONObject) game.get("paddle");
		double dx = (double) paddleObj.get("dx");
		double dy = (double) paddleObj.get("dy");
		double x = (double) paddleObj.get("x");
		double y = (double) paddleObj.get("y");


		paddle.setDx(dx);
		paddle.setDy(dy);
		paddle.setX(x);
		paddle.setY(y);
		return paddle;
	}


	/**
	 * Returns list of falling obstacles from saved game using JSONObject from loadGame()
	 * @return the list of falling obstacles from saved game
	 */
	public static ArrayList<Obstacle> getFalling(){		
		// REQUIRES: loadGame is called
		// MODIFIES: Uses JSONObject to reach falling obstacles and their values and creates a list of them 
		// EFFECTS: the list of falling obstacles from saved game is created and returned

		JSONArray fallingObj =(JSONArray) ((JSONObject) game.get("ball")).get("Falling");
		ArrayList<Obstacle> fallingObjects = new ArrayList<Obstacle>();
		for(int i=0; i<fallingObj.size(); i++) {
			JSONObject fall = (JSONObject) fallingObj.get(i);
			String name = (String)fall.get("path");
			GameObject obs = null;
			double dx = (double) fall.get("dx");
			double dy = (double) fall.get("dy");
			double x = (double) fall.get("x");
			double y = (double) fall.get("y");
			if(name.compareTo(Constant.FALLING_GIFT_PATH)==0) {
				obs = new FallingGiftObstacle(x,y,dx,dy);
			}else if((name.compareTo(Constant.EXPLOSIVE_REMAINS_PATH)==0)) {
				obs = new ExplosiveRemains(x,y,dx,dy);

			}

			obs.setDx(dx);
			obs.setDy(dy);
			obs.setX(x);
			obs.setY(y);

			fallingObjects.add((Obstacle) obs);

		}
		return fallingObjects;
	}

	/**
	 * Returns list of obstacles from saved game using JSONObject from loadGame()
	 * @return the list of obstacles from saved game
	 */
	public static ArrayList<Obstacle> getObstacles() {
		// REQUIRES: loadGame is called
		// MODIFIES: Uses JSONObject to reach obstacles and their values and creates a list of them 
		// EFFECTS: the list of obstacles from saved game is created and returned

		ArrayList<Obstacle>obstacles = new ArrayList<Obstacle>();
		for(int i=0; i<Constant.NUM_OF_OBSTACLE_TYPE; i++) {
			obstacleNums.add(0);
		}

		JSONArray obstacleObj;
		if (type.equals("Build")) {
			build = (JSONObject) buildList.get(chosenIndex);
			obstacleObj = (JSONArray) build.get("obstacles");
		}else {
			obstacleObj = (JSONArray) game.get("obstacles");
		}
		for(int i=0; i<obstacleObj.size(); i++) {
			JSONObject obstacle = (JSONObject) obstacleObj.get(i);
			String name = (String)obstacle.get("path");
			Obstacle obs = null;
			
			int index = 100;
			if(name.compareTo(Constant.EXPLOSIVE_PATH)==0) {
				obs = new ExplosiveObstacle();
				obs.setProbability((int)((long) obstacle.get("probability")));
				obs.changeMoveBehavior();
				index = Constant.EXPLOSIVE_OBSTACLE;
				obstacleNums.set(index, obstacleNums.get(index) + 1);
			
			}else if((name.compareTo(Constant.FIRM1_PATH)==0)||(name.compareTo(Constant.FIRM2_PATH)==0)
					||(name.compareTo(Constant.FIRM3_PATH)==0)||(name.compareTo(Constant.FIRM4_PATH)==0)||
					name.compareTo(Constant.FIRM5_PATH)==0) {
				obs = new FirmObstacle();
				obs.setProbability((int)((long) obstacle.get("probability")));
				obs.changeMoveBehavior();
				index = Constant.FIRM_OBSTACLE;
				obstacleNums.set(index, obstacleNums.get(index) + 1);
			
			}else if(name.compareTo(Constant.GIFT_PATH)==0) {
				obs = new GiftObstacle();
				obs.setProbability((int)((long) obstacle.get("probability")));
				index = Constant.GIFT_OBSTACLE;
				obstacleNums.set(index, obstacleNums.get(index) + 1);
			
			}else if(name.compareTo(Constant.HOLLOW_PURPLE_PATH)==0) {
				obs = new HollowPurpleObstacle();
				obs.setProbability((int)((long) obstacle.get("probability")));
				obs.changeMoveBehavior();
				index = Constant.HOLLOW_PURPLE_OBSTACLE;
				obstacleNums.set(index, obstacleNums.get(index) + 1);
			}else {
				obs = new SimpleObstacle();
				obs.setProbability((int)((long) obstacle.get("probability")));
				obs.changeMoveBehavior();
				index = Constant.SIMPLE_OBSTACLE;
				obstacleNums.set(index, obstacleNums.get(index) + 1);
			}

			double dx = (double) obstacle.get("dx");
			double dy = (double) obstacle.get("dy");
			double x = (double) obstacle.get("x");
			double y = (double) obstacle.get("y");
			
			obs.setDx(dx);
			obs.setDy(dy);
			obs.setX(x);
			obs.setY(y);
			
			obs.setCanHitFromSide((boolean) obstacle.get("canHitFromSide"));
			obs.setLife((int)((long) obstacle.get("life")));
			obs.setFrozen((boolean) obstacle.get("isFrozen"));


			//Unnecessary
			obs.setExplosiveAngle((double) obstacle.get( "explosiveAngle"));
			obs.setCenterX((double) obstacle.get( "centerX"));
			obs.setCenterY((double) obstacle.get( "centerY"));
			obs.setRadius((double) obstacle.get( "radius"));
			obs.setClockwise((boolean) obstacle.get( "clockwise"));
			obs.setIsOutCount((int)((long)obstacle.get( "isOutCount")));
			obstacles.add(obs);

		}
		BMHandler bmHandler = new BMHandler();
		BMAction.setObstacles(obstacles);
		bmHandler.createCircularObstacleList();
		bmHandler.createHorizontalObstacleList();

		return obstacles;
	}
	
	public static ArrayList<Integer> getObstacleNums() {
		return obstacleNums;
	}

}