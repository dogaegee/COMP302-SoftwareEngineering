package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.Constant;
import domain.GameHandler;
import domain.MagicHandler;
import domain.object.Ball;
import domain.object.BallLinearMoveBehavior;
import domain.object.Paddle;
import domain.object.obstacle.Obstacle;
import domain.object.obstacle.SimpleObstacle;
import domain.welcomewindow.WelcomeListener;
import ui.GameUI;


/**
 * What to test
 * 1- Simple ball movement (black-box)
 * 2- Given the obstacle, ball reflects correctly from short edge (glass-box)
 * 3- Given the obstacle, ball reflects correctly from long edge (glass-box)
 * 4- Given the paddle with angle, ball reflects correctly (glass-box)	
 * 5- Speed test (black-box)
 * [Ball hits the corner and reflects correctly]		
 */

class BallLinearMoveTest {
	private static Ball b;
	//private static Game g;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		WelcomeListener startWindow= new GameUI(new GameHandler(), new MagicHandler(), "test");
		startWindow.onWelcomeEvent();
		//g = new Game(new BoardUI());
		Thread.sleep(1000);
		b = GameHandler.
				getGame().
				getBall();
		b.setMovementBehavior(new BallLinearMoveBehavior());
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		b.setX(100);
		b.setY(100);
		b.setDy(-3);
		b.setDx(0);
	}

	@AfterEach
	void tearDown() throws Exception {
		SimpleObstacle s = new SimpleObstacle();
		//reset the obstacle list
		s.setX(-1);
		s.setY(-1);
		ArrayList<Obstacle> temp = new ArrayList<>();
		temp.add(s);
		GameHandler.getGame().setObjList(temp);
		//reset the paddle
		Paddle p = GameHandler.getGame().getPaddle();
		p.setX((Constant.width - p.getWidth())/2);
		p.setY(Constant.height);
	}
	
	@Test
	void testBallMovementSimple() {
		SimpleObstacle s = new SimpleObstacle();
		s.setX(0);
		s.setY(0);
		ArrayList<Obstacle> temp = new ArrayList<>();
		temp.add(s);
		GameHandler.getGame().setObjList(temp);
		b.getMovementBehavior().move(b);
		assertEquals(b.getX(), 100, 0.0001);
		assertEquals(b.getY(), 97, 0.0001);
		b.getMovementBehavior().move(b);
		assertEquals(b.getX(), 100, 0.0001);
		assertEquals(b.getY(), 94, 0.0001);
		b.setY(0);
		b.getMovementBehavior().move(b);
		assertEquals(b.getX(), 100, 0.0001);
		assertEquals(b.getY(), 3, 0.0001);
	}

	@Test
	void testBallReflectionSimpleLongEdge() {
		SimpleObstacle s = new SimpleObstacle();
		s.setX(90);
		s.setY(100-s.getHeight());
		ArrayList<Obstacle> temp = new ArrayList<>();
		temp.add(s);
		GameHandler.getGame().setObjList(temp);
		assertEquals(b.getY(), 100, 0.0001);
		b.getMovementBehavior().move(b);
		assertEquals(b.getY(), 103, 0.0001);
	}
	
	@Test
	void testBallReflectionSimpleSpeed() {
		SimpleObstacle s = new SimpleObstacle();

		s.setX(90);
		s.setY(100-s.getHeight());
		s.setDx(3);
		ArrayList<Obstacle> temp = new ArrayList<>();
		temp.add(s);
		GameHandler.getGame().setObjList(temp);
		b.setDx(3);
		b.getMovementBehavior().move(b);
		assertEquals(b.getY(), 103-0.05, 0.0001);
		assertEquals(b.getX(), 103.05, 0.0001);
	}
	
	@Test
	void testBallReflectionSimpleShortEdge() {
		SimpleObstacle s = new SimpleObstacle();
		s.setX(119);
		s.setY(100-s.getHeight()/2);
		ArrayList<Obstacle> temp = new ArrayList<>();
		temp.add(s);
		GameHandler.getGame().setObjList(temp);		
		//ball moves only horizontally
		b.setDy(0);
		b.setDx(3); 
		//initially the isCanHitFromSide parameter should be false
		assertFalse(GameHandler.getGame().getObstacles().get(0).isCanHitFromSide());
		
		b.getMovementBehavior().move(b);
		//ball moves now ball can hit from the side to our obstacle s
		assertTrue(GameHandler.getGame().getObstacles().get(0).isCanHitFromSide());
		assertEquals(b.getY(), 100, 0.0001);
		assertEquals(b.getX(), 103, 0.0001);
		
		b.getMovementBehavior().move(b);
		//ball hits from the side it reflects and the isCanHitFromSide parameter is changed to false
		assertFalse(GameHandler.getGame().getObstacles().get(0).isCanHitFromSide());
		assertEquals(b.getY(), 100, 0.0001);
		assertEquals(b.getX(), 100, 0.0001);
		
		b.getMovementBehavior().move(b);
		//one more move to check if it goes to left
		assertEquals(b.getY(), 100, 0.0001);
		assertEquals(b.getX(), 97, 0.0001);
	}
	
	@Test
	void testBallReflectionPaddleWithRotation() {

		Paddle s = GameHandler.getGame().getPaddle();
		s.setX(100-s.getWidth()/2);
		s.setY(104);
		s.setAngle(45);
		b.setDy(3);
		b.getMovementBehavior().move(b);
		//reflect from the paddle
		assertEquals(b.getY(), 100, 0.0001);
		assertEquals(b.getX(), 103, 0.0001);


	}
	
	
}