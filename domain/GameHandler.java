package domain;

import java.util.ArrayList;

/**
 * This class is a handler for the interactions of Game class.
 * @author Softwaring
 */
public class GameHandler {
	private static Game game;
	private static ArrayList<GameListener> gameListeners = new ArrayList<GameListener>();
	
	public void createGame(Drawable d) {
		game = new Game(d);
		game.initialize();
	}

	//Getters and setters
	public static Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		GameHandler.game = game;
	}
	
	public void pauseTimer() {
		game.pauseGame();
	}
	
	public void resumeTimer() {
		game.resumeGame();
	}
	
	public void save(String username) {
		game.save(username);
	}
	
	public void turnPaddle(int angle, int speed) {
		game.turnPaddle(angle, speed);
	}
	
	public void returnPaddle() {
		game.returnPaddle();
	}

	public void endGame() {
		Constant.isGameFinished = true;
	}

	public void handlePaddleSpeed(double direction) {
		game.acceleratePaddle(direction);
	}
	
	public void addGameListener(GameListener lis) {
		gameListeners.add(lis);
	}
	
	public static void publishGameEvent(){
		for(GameListener lis : gameListeners) {
			lis.onGameEvent(Constant.CHANCES_LABEL, "Chances:" + Constant.chances);
			lis.onGameEvent(Constant.TIME_LABEL, "Time:" + String.format("%.2f",Constant.time));
			lis.onGameEvent(Constant.SCORE_LABEL, "Score:" + String.format("%.2f",Constant.score));
			lis.onGameEvent(Constant.YMIR_LABEL, Constant.YMIR_SITUATION);
			lis.onGameEvent(Constant.YMIR_TIME_LABEL, String.format("%.2f", 30 - (Constant.time % 30)));
			if(game.getObstacles().size() == 0) {
				lis.onGameEvent(Constant.WIN, Constant.WIN_GAME_MESSAGE);
			}
		}
	}
}
