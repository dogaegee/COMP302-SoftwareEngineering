package domain.saveload;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import domain.Constant;
import domain.MagicAction;
import domain.object.Ball;
import domain.object.GameObject;
import domain.object.obstacle.Obstacle;


/**
 * Save is a class to save a game or building mode 
 * @author Softwaring 
 * @version 2.0
 */
public class Save {

	@SuppressWarnings("unchecked")
	/**
	 * Saves the game with current date and time 
	 * uses given parameters to save game.
	 * @param obsList list of obstacles
	 * @param falling list of falling obstacles
	 * @param ball ball object
	 * @param paddle paddle object
	 * @param username
	 */
	public static void save (List<Obstacle> obsList,ArrayList<Obstacle>falling, Ball ball,GameObject paddle,String username) {
		// REQUIRES: 
		// MODIFIES: Takes date and time. Puts game related things into one JSONObject
		//			 puts ball and its details, paddle and its details, 
		//			 all obstacles and their details into a json file.
		//			 Throws an exception when obstacle list is null beacuse game or build mode 
		//			 cannot be saved unless they have obstacles.
		// EFFECTS: save file is updated or created.

		JSONObject game = new JSONObject();
		JSONObject gameDetails = new JSONObject();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();  

		gameDetails.put("date", formatter.format(date));


		JSONObject ballDetails = new JSONObject();
		JSONObject paddleDetails = new JSONObject();
		JSONObject magicDetails = new JSONObject();

		if(ball == null) {
			magicDetails.put("null", "null");
			ballDetails.put("null","null");
			gameDetails.put("Game Type", "Building Mode");
		}else {
			HashMap<Integer, Integer> magics = MagicAction.getMagics();
			HashMap<Integer, Boolean> currentMagicsAndActivations = MagicAction.getCurrentMagicsAndActivations();
			ArrayList<Integer> remainingMagics = Constant.remainingMagics;

			
			HashMap<String,String> savedCurrentMagicsAndActivations = new HashMap<String,String>();
			HashMap<String,String> savedMagics = new HashMap<String,String>();
			ArrayList<String> savedRemainingMagics = new ArrayList<String> ();
			magics.forEach((magic, bool) -> {
				savedMagics.put(magic.toString(), bool.toString());
		      });
			currentMagicsAndActivations.forEach((magic, bool) -> {
				savedCurrentMagicsAndActivations.put(magic.toString(), bool.toString());
		      });
			remainingMagics.forEach((magic) -> {
				savedRemainingMagics.add(magic.toString());
		      });
			
			
			
			magicDetails.put("magics", savedMagics);
			magicDetails.put("activation", savedCurrentMagicsAndActivations);
			magicDetails.put("remainingMagics",savedRemainingMagics);

			magicDetails.put("noblePhantasmExpansionRemainingTimeLabel",Constant.noblePhantasmExpansionRemainingTimeLabel);
			magicDetails.put("magicalHexRemainingTimeLabel",Constant.magicalHexRemainingTimeLabel);
			magicDetails.put("unstoppableEnchantedSphereRemainingTimeLabel",Constant.unstoppableEnchantedSphereRemainingTimeLabel);

		
			magicDetails.put("YMIR_SITUATION", Constant.YMIR_SITUATION);
			magicDetails.put("currentMagic", Constant.currentAdversaryMagic);
			magicDetails.put("ymirTimeCounter", Constant.ymirTimeCounter);
			magicDetails.put("remainingExpansionMagicTime", Constant.remainingExpansionMagicTime);
			magicDetails.put("remainingUnstoppableMagicTime", Constant.remainingUnstoppableMagicTime);
			magicDetails.put("remainingHexMagicTime", Constant.remainingHexMagicTime);

			
			JSONArray fallingList = new JSONArray();
			gameDetails.put("time", Constant.time);
			gameDetails.put("chances", Constant.chances);
			gameDetails.put("score", Constant.score);
			gameDetails.put("Game Type", "Game Mode");

			ballDetails.put("x",ball.getX());
			for(Obstacle o:falling) {
				JSONObject fallingObj = new JSONObject();
				fallingObj.put("x", o.getX());
				fallingObj.put("y", o.getY());
				fallingObj.put("dx", o.getDx());
				fallingObj.put("dy", o.getDy());
				fallingObj.put("path", o.getPath());
				fallingList.add(fallingObj);
			}
			ballDetails.put("Falling", fallingList);
			ballDetails.put("y",ball.getY());
			ballDetails.put("dx",ball.getDx());
			ballDetails.put("dy",ball.getDy());
			ballDetails.put("width",ball.getWidth());
			ballDetails.put("height",ball.getHeight());
		}

		
		
		if(paddle == null) {
			paddleDetails.put("null","null");		
		}else {
			paddleDetails.put("x",paddle.getX());
			paddleDetails.put("y",paddle.getY());
			paddleDetails.put("dx",paddle.getDx());
			paddleDetails.put("dy",paddle.getDy());
			paddleDetails.put("width",paddle.getWidth());
			paddleDetails.put("height",paddle.getHeight());
		}

		JSONArray obstacles = new JSONArray();

		for(Obstacle o:obsList) {
			JSONObject obstacle = new JSONObject();
			obstacle.put("x",o.getX());
			obstacle.put("y",o.getY());
			obstacle.put("dx",o.getDx());
			obstacle.put("dy",o.getDy());
			obstacle.put("width",o.getWidth());
			obstacle.put("height",o.getHeight());
			obstacle.put("path", o.getPath());
			obstacle.put("probability", o.getProbability());
			obstacle.put( "isFrozen", o.isFrozen());

			
			obstacle.put( "canHitFromSide", o.isCanHitFromSide());
			obstacle.put( "life", o.getLife());
			obstacle.put( "willBeDeleted", o.isWillBeDeleted());


			//Unnecessary
			obstacle.put( "explosiveAngle",o.getExplosiveAngle());
			obstacle.put( "centerX", o.getCenterX());
			obstacle.put( "centerY", o.getCenterY());
			obstacle.put( "radius", o.getRadius());
			obstacle.put( "clockwise", o.isClockwise());
			obstacle.put( "isOutCount", o.getIsOutCount());
			obstacles.add(obstacle);
		}


		game.put("GameInfo",gameDetails);
		game.put("ball",ballDetails);
		game.put("paddle",paddleDetails);
		game.put("obstacles",obstacles);
		game.put("magicInfo", magicDetails);
		JSONArray savedGameList = new JSONArray();
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("users/"+username+".json")){
			Object obj = jsonParser.parse(reader);
			savedGameList = (JSONArray) obj;
		} catch (FileNotFoundException e1) {
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		savedGameList.add(game);
		try (FileWriter file = new FileWriter("users/"+username+".json")) {
			file.write(savedGameList.toString()); 
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
