package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Constant;
import domain.GameHandler;
import domain.MagicHandler;
import domain.object.Hex;
import domain.object.Paddle;
import domain.object.ShootingMoveBehavior;
import domain.object.obstacle.FirmObstacle;
import domain.object.obstacle.Obstacle;
import domain.object.obstacle.SimpleObstacle;
import domain.welcomewindow.WelcomeListener;
import ui.GameUI;


/**
 * What to test
 * 1- Hex movement (black-box)
 * (black-box: since we know it should move to correct location without looking to code)
 * 2- Collison with Simple obstacle, obstacle and hex are removed (glass-box)
 * (glass-box: since we cannot know if hexes are removed from the list of the paddle without looking to code)
 * 3- Collison with Firm obstacle; obstacle still exist but its life is decreased, and hex is removed. (glass-box)
 * (glass-box beacuse of the same reason with second test)
 * 4- Collison with Frozen obstacle, obstacle still exist and its life does not change, and hex is removed (glass-box)
 * (glass-box beacuse of the same reason with second test)
 * 5- Collison of multiple hexes and a firm obstacle  (black-box)
 * (black-box: we know multiple hexes can hit an obstacle without looking to code)
 *	
 */

class HexShootingMoveTest {
	private static Paddle p;
	private static Hex rHex;
	private static Hex lHex;

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		WelcomeListener startWindow= new GameUI(new GameHandler(), new MagicHandler(), "test2");
		startWindow.onWelcomeEvent();
		Thread.sleep(1500);
		p = GameHandler.
				getGame().
				getPaddle();
	}


	@BeforeEach
	void setUp() throws Exception {
		p.setX((Constant.width - p.getWidth()) / 2);
		p.setY(Constant.height - (p.getHeight() + p.getWidth() / 2));
		p.setDy(0);
		p.setDx(0);
		
		p.setMagicalHexActive(true);
		if (p.isMagicalHexActive()) {
			Hex leftHex = new Hex(0, -5, p.getX(), p.getY());
			Hex rightHex = new Hex(0, -5, p.getX() + p.getWidth(), p.getY());
			p.getHexes().add(leftHex);
			p.getHexes().add(rightHex);
		}

		rHex = p.getHexes().get(0);
		lHex = p.getHexes().get(1);
		
		rHex.setX(100);
		rHex.setY(100);
		rHex.setMovementBehavior(new ShootingMoveBehavior());
		
		lHex.setX(90);
		lHex.setY(100);
		lHex.setMovementBehavior(new ShootingMoveBehavior());
		
	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	void testHexMovementNoObstacle() {

		rHex.getMovementBehavior().move(rHex);
		assertEquals(rHex.getX(), 100, 0.0001);
		assertEquals(rHex.getY(), 95, 0.0001);
		rHex.getMovementBehavior().move(rHex);
		assertEquals(rHex.getX(), 100, 0.0001);
		assertEquals(rHex.getY(), 90, 0.0001);
		rHex.getMovementBehavior().move(rHex);
		assertEquals(rHex.getX(), 100, 0.0001);
		assertEquals(rHex.getY(), 85, 0.0001);
		
		assertEquals(rHex.equals(GameHandler.getGame().getPaddle().getHexes().get(0)), true); //looks if hex still exist and reachable

	}

	@Test
	void testHexMovementSimpleObstacle() {

		SimpleObstacle simple = new SimpleObstacle();
		simple.setX(100);
		simple.setY(50);
		ArrayList<Obstacle> objList = new ArrayList<>();
		objList.add(simple);
		GameHandler.getGame().setObjList(objList);
		
		rHex.setY(55);
		rHex.getMovementBehavior().move(rHex);
		assertEquals(simple.isWillBeDeleted(), false);
		assertEquals(rHex.equals(GameHandler.getGame().getPaddle().getHexes().get(0)), true);

	}

	@Test
	void testHexMovementFirmObstacle() {
		
		FirmObstacle firm = new FirmObstacle();
		firm.setX(100);
		firm.setY(50);
		firm.setLife(3);
		ArrayList<Obstacle> objList = new ArrayList<>();
		objList.add(firm);
		GameHandler.getGame().setObjList(objList);


		rHex.setY(55);
		rHex.getMovementBehavior().move(rHex);
		assertEquals(firm.isWillBeDeleted(), false);
		assertEquals(rHex.equals(GameHandler.getGame().getPaddle().getHexes().get(0)), true);


	}
	
	@Test
	void testHexMovementFrozenObstacle() {
		
		SimpleObstacle simple = new SimpleObstacle();
		simple.setX(100);
		simple.setY(50);
		simple.setFrozen(true);
		ArrayList<Obstacle> objList = new ArrayList<>();
		objList.add(simple);
		GameHandler.getGame().setObjList(objList);

		rHex.setY(55);
		rHex.getMovementBehavior().move(rHex);
		assertEquals(simple.isWillBeDeleted(), false);
		assertEquals(rHex.equals(GameHandler.getGame().getPaddle().getHexes().get(0)), true);


	}
	
	 @Test
	  void testMultipleHexMovementFirmObstacle() {	  
		FirmObstacle firm = new FirmObstacle();
		firm.setX(100);
		firm.setY(50);
		firm.setLife(3);
		ArrayList<Obstacle> objList = new ArrayList<>();
		objList.add(firm);
		GameHandler.getGame().setObjList(objList);

		rHex.setY(55);
		lHex.setY(60);

		rHex.getMovementBehavior().move(rHex); 
		lHex.getMovementBehavior().move(lHex);
		lHex.getMovementBehavior().move(lHex);
		
		assertEquals(firm.isWillBeDeleted(), false);

	  
	  
	  }
	 


}
