package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.BMHandler;
import domain.ObstacleFactory;
import domain.bm.BMAction;
import domain.object.obstacle.ExplosiveObstacle;
import domain.object.obstacle.FirmObstacle;
import domain.object.obstacle.GiftObstacle;
import domain.object.obstacle.Obstacle;
import domain.object.obstacle.SimpleObstacle;
import ui.BMUI;

/**
 * What to test:
 * - Returning the correct type of obstacle in the given location if there exists one.
 * - Returning null if there is no obstacle in the given location.
 */

class GetObstacleTest {
	ArrayList<Obstacle> obstacleList = BMAction.getObstacles();
	ObstacleFactory obstacleFactory =  ObstacleFactory.getInstance();
	SimpleObstacle simpleObstacle = new SimpleObstacle();
	GiftObstacle giftObstacle = new GiftObstacle();
	FirmObstacle firmObstacle = new FirmObstacle();
	ExplosiveObstacle explosiveObstacle = new ExplosiveObstacle();
	BMHandler bmHandler = new BMHandler();
	BMUI bmUI = new BMUI(bmHandler, "betul");
	BMAction bmAction = new BMAction();
	final static double Y_CONSTANT = 110;
	
	@BeforeEach
	void setUp() {
		simpleObstacle.setX(10);
		simpleObstacle.setY(10);
		firmObstacle.setX(-10);
		firmObstacle.setY(-10);
		giftObstacle.setX(75);
		giftObstacle.setY(75);
		explosiveObstacle.setX(150);
		explosiveObstacle.setY(150);
		obstacleList.add(simpleObstacle);
		obstacleList.add(firmObstacle);
		obstacleList.add(giftObstacle);
		obstacleList.add(explosiveObstacle);
		bmAction.setObstacles(obstacleList);
	}
	
	//This is a black-box test.
	/*
	 * This test checks whether the function returns null correctly when there is no obstacle in the given point
	 */
	@Test
	void nullTest() {
		assertEquals(null, bmAction.getObstacle(0,0));
	}
	
	//The remaining tests are glass-box tests.
	/*
	 * This test checks whether the function correctly returns the simple obstacle in the given location.
	 */
	@Test
	void simpleTest() {
		String obsName = simpleObstacle.getClass().toString();
		double yLoc = simpleObstacle.getY() + Y_CONSTANT ;
		assertEquals(obsName, bmAction.getObstacle((int)simpleObstacle.getX(),(int)yLoc).getClass().toString());
	}
	
	/*
	 * This test checks whether the function correctly returns the firm obstacle in the given location.
	 */
	@Test
	void firmTest() {
		String obsName = firmObstacle.getClass().toString();
		double yLoc = firmObstacle.getY() + Y_CONSTANT ;
		assertEquals(obsName, bmAction.getObstacle((int)firmObstacle.getX(),(int)yLoc).getClass().toString());
	}
	
	/*
	 * This test checks whether the function correctly returns the gift obstacle in the given location.
	 */
	@Test
	void giftTest() {
		String obsName = giftObstacle.getClass().toString();
		double yLoc = giftObstacle.getY() + Y_CONSTANT;
		assertEquals(obsName, bmAction.getObstacle((int)giftObstacle.getX(),(int)yLoc).getClass().toString());
	}
	
	/*
	 * This test checks whether the function correctly returns the explosive obstacle in the given location.
	 */
	@Test
	void explosiveTest() {
		String obsName = explosiveObstacle.getClass().toString();
		double yLoc = explosiveObstacle.getY() + Y_CONSTANT ;
		assertEquals(obsName, bmAction.getObstacle((int)explosiveObstacle.getX(),(int)yLoc).getClass().toString());
	}
	
	

}
