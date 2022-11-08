package domain;

import java.util.Random;
import domain.magic.Activatable;
/**
 * Ymir is a game feature which tries to add additional challenges to the player.
 * @author Softwaring
 *
 */
public class Ymir {
	private Random random;
	private MagicFactory magicFactory;
	private Activatable currentAdversaryMagic;
	private boolean active;
	
	/**
	 * Constructor
	 */
	public Ymir() {
		this.magicFactory = MagicFactory.getInstance();
		random = new Random();
		this.active = false;
		int temp = Constant.currentAdversaryMagic;
		currentAdversaryMagic = magicFactory.getMagicalAbility(temp);
	}
	/**
	 * This reincarnates Ymir. First flips coin. If the coin is a success, generates a random adversary magic and activates it.
	 */
	public void reincarnate() {
		int flip = flipCoin();
		if(flip == 1) {
			this.active = true;
			int selectedAdversaryMagicCode = random.nextInt(3)+4;
			Activatable selectedAdversaryMagic = magicFactory.getMagicalAbility(selectedAdversaryMagicCode);
			currentAdversaryMagic = selectedAdversaryMagic;
			Constant.currentAdversaryMagic = selectedAdversaryMagicCode;
			selectedAdversaryMagic.activate();
		} else {
			Constant.YMIR_SITUATION = "Ymir Is Sleeping";
			currentAdversaryMagic = null;
			this.active = false;
		}
	}
	/**
	 * This deactivates the adversary magic if there is one any active.
	 */
	public void sleep() {
		if(currentAdversaryMagic != null) {
			currentAdversaryMagic.deactivate();
			Constant.YMIR_SITUATION = "Ymir Is Sleeping";
			currentAdversaryMagic = null;
			this.active = false;
		}
	}
	
	public int flipCoin() {
		int flip = random.nextInt(2);
		return flip;
	}
	
	//Getters and setters
	public boolean getActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
