package domain;

import java.util.HashMap;
import java.util.Map;
import domain.magic.Activatable;
import domain.object.Hex;
import domain.object.Paddle;

public class MagicAction {
	//Overview: This function keeps the necessary information about the magics
	//such as the remaining magic numbers, whether a magic is active or not. 
	//Additionally, it controls the creation and activation of the magics.
	
	//The representation
	private static HashMap<Integer, Integer> magics = new HashMap<Integer, Integer>();
	private static MagicFactory magicFactory = MagicFactory.getInstance();
	private static HashMap<Integer, Boolean> currentMagicsAndActivations = new HashMap<Integer, Boolean>();
	
	//The abstraction function is
	//Represents the activation and remaining number information about the magics in the game.
	//magics HashMap represents the remaining corresponding magic count.
	//currentMagicsAndActivations HashMap represents the activation situation of the corresponding magic.
	
	//The representation invariant is
	// Representation Invariant: 
	// magics.get(0) is always 0.
	// magics HashMap has only 4 key values which are 0,1,2,3.
	// currentMagicsAndActivations.get(0) is always false.
	// currentMagicsAndActivations HashMap has only 4 key values which are 0,1,2,3.

	/**
	 * Calls the magic factory to initialize the magics as 0 for the constructor
	 * and initializes all the magics as false which means that all of them are deactive.
	 */
	public void initializeMagics() {
		for(int i = 0; i < 4; i++) {
			magics.put(i, 0);
		}
		for(int i = 0; i < 4; i++) {
			currentMagicsAndActivations.put(i, false);
		}
	}

	/**
	 * Activates an Activatable object corresponding to the magicKeyCode if it is legal.
	 * @param magicKeyCode a number which represents an Activatable object
	 */
	public void activate(int magicKeyCode) {
		
		if(magicKeyCode == 0) {
			magicFactory.getMagicalAbility(magicKeyCode).activate();
		} else {
			Activatable chosenMagic = getMagic(magicKeyCode);
			if(chosenMagic != null) {
				chosenMagic.activate();
			} 
		}
		
	}

	/**
	 * Finds the corresponding magic from the hashmap, checks whether the player has remaining,
	 * checks whether the selected magic is already active or not, and checks the magicKeyCode is legal. 
	 * If the player has the selected magic and it is not active, it returns the selected Activatable object.
	 * @param magicKeyCode a number which represents an Activatable object
	 * @return selected Activatable object
	 */
	public Activatable getMagic(int magicKeyCode) {
		Activatable chosenMagic = null;
		if(magicKeyCode >= 0 && magicKeyCode <= 3 && (!currentMagicsAndActivations.get(magicKeyCode)||Constant.isLoaded)) {
			int remainingMagicNum = magics.get(magicKeyCode);
			if(remainingMagicNum > 0||Constant.isLoaded) {
				if(magicKeyCode != 0) {
					if(Constant.isLoaded && remainingMagicNum == 0) {
						magics.put(magicKeyCode, 0);
					}else {
						magics.put(magicKeyCode, remainingMagicNum-1);
						Constant.remainingMagics.set(magicKeyCode, Constant.remainingMagics.get(magicKeyCode) - 1);
					}
					currentMagicsAndActivations.put(magicKeyCode, true);
				}
				chosenMagic = magicFactory.getMagicalAbility(magicKeyCode);
			} 
		}
		return chosenMagic;
	}
	
	/**
	 * Updates the hashmap that holds the current magic counts and
	 * the constant value correspondingly. If the selected magic is a ChanceGivingAbility,
	 * whose key code is 0, it does not update the hashmap and constant value. 
	 * @param i a number which represents an Activatable object
	 * @throws Exception 
	 */
	public void addMagic(int i) throws Exception {
		if(i < 0 || i > 3) {
			throw new Exception("Magic Code cannot be nagative.");
		} else if(i == 0) {
			magics.put(i, 0);
			Constant.remainingMagics.set(i, 0);
		} else {
			magics.put(i, magics.get(i) + 1);
			Constant.remainingMagics.set(i, Constant.remainingMagics.get(i) + 1);
		}
	}

	/**
	 * Adds two Hex objects to the hex list of the Paddle object.
	 */
	public void fire() {
		Paddle paddle = GameHandler.getGame().getPaddle();
		double hexDX = 0;
		double hexDY = -5;
		
		if(paddle.isMagicalHexActive()) {
			if(paddle.getAngle() == 0) {
				Hex leftHex = new Hex(hexDX,hexDY,paddle.getX(), paddle.getY());
				Hex rightHex = new Hex(hexDX,hexDY,paddle.getX() + paddle.getWidth(), paddle.getY());
				paddle.getHexes().add(leftHex);
				paddle.getHexes().add(rightHex);
			}
			else {
				double coordinateAngle = -Math.toRadians(paddle.getAngle());
				
				hexDX = hexDY * Math.sin(coordinateAngle);
				hexDY = hexDY * Math.cos(coordinateAngle);
				
				Hex leftHex = new Hex(hexDX,hexDY,paddle.getT_StartX(), paddle.getT_StartY());
				Hex rightHex = new Hex(hexDX,hexDY,paddle.getT_EndX(), paddle.getT_StartY());
				paddle.getHexes().add(leftHex);
				paddle.getHexes().add(rightHex);
				
			}
		}
	}

	/**
	 * Checks whether the MagicAction rep is OK or not:
	 * - Checks the ChanceGiving remaining is 0 or not.
	 * - Checks the magics HashMap keys are valid or not.
	 * - Checks the ChanceGiving activation situation is false or not.
	 * - Checks the currentMagicsAndActivations HashMap keys are valid or not.
	 */
	public boolean repOk() {
		// Chance giving ability remaining is always 0 because it is used immediately.
		if(magics.get(0) > 0) {
			return false;
		} 
		// Magic codes are in range [0,3] and remaining magic numbers cannot be negative.
		for(Map.Entry<Integer, Integer> mapEntry : magics.entrySet()) {
			int magicCode = mapEntry.getKey();
			int remainingMagicCount = mapEntry.getValue();
			if(magicCode < 0 || magicCode > 3) {
				return false;
			}
			if(remainingMagicCount < 0) {
				return false;
			}
		}
		// Chance giving ability is never active because it is used immadiately.
		if(currentMagicsAndActivations.get(0)) {
			return false;
		}
		// Magic codes are in range [0,3]
		for(Map.Entry<Integer, Boolean> mapEntry : currentMagicsAndActivations.entrySet()) {
			int magicCode = mapEntry.getKey();
			if(magicCode < 0 || magicCode > 3) {
				return false;
			}
		}
		return true;
	}
	
	//Getters and Setters
	public static HashMap<Integer, Boolean> getCurrentMagicsAndActivations() {
		return currentMagicsAndActivations;
	}
	public static void setCurrentMagicsAndActivations(HashMap<Integer, Boolean> currentMagicsAndActivations) {
		MagicAction.currentMagicsAndActivations = currentMagicsAndActivations;
	}
	public static HashMap<Integer, Integer> getMagics() {
		return magics;
	}
	public static void setMagics(HashMap<Integer, Integer> magics) {
		MagicAction.magics = magics;
	}
}
