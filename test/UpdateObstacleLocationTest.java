package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.Constant;
import domain.ObstacleFactory;
import domain.bm.BMAction;
import domain.object.obstacle.Obstacle;

/**
 * What to test:
 * - Relocating the given obstacle to the given locations correctly.
 * - Not relocating the given obstacle to the given locations because there is another obstacle on this location.
 * - Not relocating when the given locations is out of the obstacle locating area.
 * - Relocating the Explosive Obstacle object differently.
 * - Relocating by considering the latest locations of the other obstacles.
 */

class UpdateObstacleLocationTest {
	
	private BMAction bmAction = new BMAction();
	private ObstacleFactory factory = ObstacleFactory.getInstance();
	private static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private Obstacle simpleObstacle;
	private Obstacle firmObstacle;
	private Obstacle giftObstacle;
	private Obstacle explosiveObstacle;
	
	@BeforeEach
	void setUp() {
		simpleObstacle = factory.getObstacle(Constant.SIMPLE_OBSTACLE);
		simpleObstacle.setX(50);
		simpleObstacle.setY(50);
		obstacles.add(simpleObstacle);
		firmObstacle = factory.getObstacle(Constant.FIRM_OBSTACLE);
		firmObstacle.setX(150);
		firmObstacle.setY(70);
		obstacles.add(firmObstacle);
		giftObstacle = factory.getObstacle(Constant.GIFT_OBSTACLE);
		giftObstacle.setX(1200);
		giftObstacle.setY(278);
		obstacles.add(giftObstacle);
		explosiveObstacle = factory.getObstacle(Constant.EXPLOSIVE_OBSTACLE);
		explosiveObstacle.setX(770);
		explosiveObstacle.setY(300);
		obstacles.add(explosiveObstacle);
		BMAction.setObstacles(obstacles);
	}
	
	@Test
	/**
	 * Black-Box Test
	 * This is the situation where the given obstacle is directly
	 * moved to the given x and y coordinates without a problem.
	 */
	void correctlyUpdateLocationWithoutCollision() {
		bmAction.updateObstacleLocation(50, 200, giftObstacle);
		assertEquals(50, giftObstacle.getX() + giftObstacle.getWidth()/2);
		assertEquals(200, giftObstacle.getY() + giftObstacle.getHeight());
		
		bmAction.updateObstacleLocation(100, 180, firmObstacle);
		assertEquals(100, firmObstacle.getX() + firmObstacle.getWidth()/2);
		assertEquals(180, firmObstacle.getY() + firmObstacle.getHeight());
	}
	
	@Test
	/**
	 * Black-Box Test
	 * This is the situation where the given obstacle cannot be
	 * moved to the given x and y coordinates because that locations
	 * is occupied by another obstacle already.
	 */
	void belongsToAnotherObstacleTest() {
		bmAction.updateObstacleLocation(770, 300, giftObstacle);
		assertEquals(1200, giftObstacle.getX());
		assertEquals(278, giftObstacle.getY());
		
		bmAction.updateObstacleLocation(790, 320, giftObstacle);
		assertEquals(1200, giftObstacle.getX());
		assertEquals(278, giftObstacle.getY());
	}
	
	@Test
	/**
	 * Glass-Box Test
	 * This is the situation where the given location is out of
	 * the area which an obstacle can be. This area may be the area
	 * of the upward GUI or an area which close to the paddle or screen borders.
	 * To locate an obstacle: 
	 * Legal x range is [40.0, 1456.0]
	 * Legal y range is [40.0, 384.0]
	 */
	void locationIsOutOfBounds() {
		bmAction.updateObstacleLocation(30, 300, simpleObstacle);
		assertEquals(50, simpleObstacle.getX());
		assertEquals(50, simpleObstacle.getY());
		
		bmAction.updateObstacleLocation(10000, 300, simpleObstacle);
		assertEquals(50, simpleObstacle.getX());
		assertEquals(50, simpleObstacle.getY());
		
	}
	
	@Test
	/**
	 * Glass-Box Test
	 * This is the situation where the given obstacle is an explosive obstacle.
	 * Since its shape is circular, an explosive obstacle is located 5 pixels downwards
	 * and 5 pixels right to look better.
	 */
	void obstacleIsExplosiveTest() {
		bmAction.updateObstacleLocation(100, 300, explosiveObstacle);
		assertEquals(105, explosiveObstacle.getX() + explosiveObstacle.getWidth()/2);
		assertEquals(295, explosiveObstacle.getY() + explosiveObstacle.getHeight());
	}
	
	@Test
	/**
	 * Glass-Box Test
	 * This is the situation which includes succesive relocations. Here, we are testing whether
	 * a relocation of an obstacle considers the current locations of the other obstacles or
	 * initial locations of them. If these tests are passed, our method is dynamic.
	 */
	void successiveTest() {
		obstacles = new ArrayList<Obstacle>();
		Obstacle giftObstacle2 = factory.getObstacle(Constant.GIFT_OBSTACLE);
		giftObstacle2.setX(1200);
		giftObstacle2.setY(278);
		obstacles.add(giftObstacle2);
		Obstacle firmObstacle2 = factory.getObstacle(Constant.FIRM_OBSTACLE);
		firmObstacle2.setX(500);
		firmObstacle2.setY(100);
		obstacles.add(firmObstacle2);
		Obstacle simpleObstacle2 = factory.getObstacle(Constant.SIMPLE_OBSTACLE);
		simpleObstacle2.setX(700);
		simpleObstacle2.setY(50);
		obstacles.add(simpleObstacle2);
		BMAction.setObstacles(obstacles);
		
		bmAction.updateObstacleLocation(100, 200, giftObstacle2);
		assertEquals(100, giftObstacle2.getX() + giftObstacle2.getWidth()/2);
		assertEquals(200, giftObstacle2.getY() + giftObstacle2.getHeight());
		
		bmAction.updateObstacleLocation(100, 200, firmObstacle2); 
		//It doesn't move because this location is occupied by giftObstacle2 right now.
		assertEquals(500, firmObstacle2.getX());
		assertEquals(100, firmObstacle2.getY());
		
		bmAction.updateObstacleLocation(1200, 278, simpleObstacle2);
		//It moves because this location is not occupied by giftObstacle2 anymore.
		assertEquals(1200, simpleObstacle2.getX() + simpleObstacle2.getWidth()/2);
		assertEquals(278, simpleObstacle2.getY() + simpleObstacle2.getHeight());
		
		
	}
	

}
