package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import domain.Constant;
import domain.object.Ball;
import domain.object.Paddle;
import domain.object.obstacle.ExplosiveObstacle;
import domain.object.obstacle.FirmObstacle;
import domain.object.obstacle.GiftObstacle;
import domain.object.obstacle.Obstacle;
import domain.object.obstacle.SimpleObstacle;
import domain.saveload.Load;
import domain.saveload.Save;

/* What to test
 * 1- Saving with null paramters (black-box testing)
 * 2- Testing non-exist user's save action (glass-box testing) 
 * 3- Saving of game mode (glass-box testing) 
 * 4- Saving of build mode (glass-box testing) 
 * 5- Loading of build mode (black-box testing) 
 * 6- Loading of game mode (black-box testing) 
 */


class LoadTest {
	static String date ;
	static String username;
	static double width = Constant.width;
	static double height = Constant.height;
	static ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	static ArrayList<Obstacle> fallingList = new ArrayList<Obstacle>();
	static SimpleObstacle simpleObstacle = new SimpleObstacle();
	static GiftObstacle giftObstacle = new GiftObstacle();
	static FirmObstacle firmObstacle = new FirmObstacle();
	static ExplosiveObstacle explosiveObstacle = new ExplosiveObstacle();

	static Ball ball = null;
	static Paddle paddle = null;
	
	@BeforeAll
	/**
	 * Initializer for test cases
	 * It creates objects to save and load them
	 */
	private static void init(){
		date = "00/00/0000 00:00:00";
		username = "SaveLoadTesting";
		
		ball = new Ball();
		paddle = new Paddle();
		
		ball.setX(Math.random()*width);
		ball.setY(Math.random()*height);
		ball.setDx(Math.random());
		ball.setDy(Math.random());
		
		paddle.setX(Math.random()*width);
		paddle.setY(Constant.height-20 -Constant.L/2);
		paddle.setDx(Math.random());
		paddle.setDy(0);
		
		double x = Math.random()*width;
		double y = Math.random()*height;
		
		simpleObstacle.setX(x);
		simpleObstacle.setY(y);
		firmObstacle.setX(x);
		firmObstacle.setY(y);
		giftObstacle.setX(x);
		giftObstacle.setY(y);
		explosiveObstacle.setX(x);
		explosiveObstacle.setY(y);
		
		obstacleList.add(simpleObstacle);
		obstacleList.add(firmObstacle);
		obstacleList.add(giftObstacle);
		obstacleList.add(explosiveObstacle);
		

	}
	

	
	@Test
	/**
	 * If object list is null it needs to throw an exception
	 * because you cannot save or load a game or build mode without obstacles
	 */
	void NullSaveTest() {
		assertThrows(NullPointerException.class,new Executable() {
			   @Override
	            public void execute() throws Throwable {
	               Save.save(null, null,null, null, username);
	            }
		});	
	}
	
	
	
	@Test
	/**
	 * Test for saving where user enters the system for the first time
	 */
	void CreatingNewUserTest() {		
		//First it needs to check there is no file for noSuchUser
		NullSaveTest();
		assertThrows(FileNotFoundException.class,new Executable() {
			   @Override
	            public void execute() throws Throwable {
					try (FileReader reader = new FileReader("users/"+"noSuchUser"+".json")){
					}
	            }
		});	
		
		// When save is called with that username it needs to create a file
		// in order to save game
		   Save.save(obstacleList, fallingList, ball, paddle, "noSuchUser");
		   
		   // After saving there should be a file with that name
		   File file = new File("users/noSuchUser.json");
		   assertTrue(file.exists());
		   
		   // To run this test multiple times
		   // it needs to delete this "noSuchUser" file
		   file.delete();
	}
	
	
	@Test
	/**
	 * Test for saving game
	 */
	void SaveGameTest() {
		
		Save.save(obstacleList, fallingList, ball, paddle, username);
		
		Load.initializeLoad(username);
		Load.setLoadType(0, username);
		Load.loadGame();
		
		Ball loadBall = Load.getBall();
		assertEquals(loadBall.getDx(),ball.getDx());
		assertEquals(loadBall.getDy(),ball.getDy());
		assertEquals(loadBall.getX(),ball.getX());
		assertEquals(loadBall.getY(),ball.getY());
		
		Paddle loadPaddle = Load.getPaddle();
		assertEquals(loadPaddle.getDx(),paddle.getDx());
		assertEquals(loadPaddle.getDy(),paddle.getDy());
		assertEquals(loadPaddle.getX(),paddle.getX());
		assertEquals(loadPaddle.getY(),paddle.getY());
		
		ArrayList<Obstacle> loadObst = Load.getObstacles();
		for(Obstacle o:loadObst) {
			assertEquals(o.getX(),simpleObstacle.getX());
			assertEquals(o.getY(),simpleObstacle.getY());
		}
		
		ArrayList<Obstacle> loadFalling = Load.getFalling();
		for(Obstacle f:loadObst) {
			assertEquals(f.getX(),simpleObstacle.getX());
			assertEquals(f.getY(),simpleObstacle.getY());
		}
	}
	
	@Test
	/**
	 * Test for saving building mode
	 */
	void SaveBuildTest() {
		
		Save.save(obstacleList, null, null, null, username);
		
		ArrayList<Obstacle> loadObst = Load.getObstacles();
		for(Obstacle o:loadObst) {
			assertEquals(o.getX(),simpleObstacle.getX());
			assertEquals(o.getY(),simpleObstacle.getY());
		}
		
	}
	
	@Test
	/**
	 * This test checks initializeLoad
	 * It uses LoadTest.json which is created for this specific case
	 * This tests helps to check LoginUI data (date)
	 */
	void BuildModeLoadTest() {
		assertEquals(Load.initializeLoad("LoadTest").get(1)[0],date);
	}
	
	@Test
	/**
	 * This test checks initializeLoad
	 * It uses LoadTest.json which is created for this specific case
	 * This tests helps to check LoginUI data (date)
	 */
	void GameModeLoadTest() {
		assertEquals(Load.initializeLoad("LoadTest").get(0)[0],date);
	}
	
	@AfterAll
	/**
	 * To run this test multiple times
	 * it needs to delete this test file
	 */
	private static void After() {
		   File file = new File("users/"+username+".json");
		   file.delete();
	}

}
