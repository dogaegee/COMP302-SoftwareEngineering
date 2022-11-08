package domain;

import java.util.ArrayList;
import domain.magic.MagicListener;

/**
 * This class hold the methods for the magical ability usages.
 * @author Softwaring
 *
 */
public class MagicHandler {
	private MagicAction magicAction;
	private static MagicHandler magicHandler;
	private ArrayList<MagicListener> magicListeners = new ArrayList<MagicListener>();

	/*
	 * The constructor
	 */
	public MagicHandler() {
		magicAction = new MagicAction();
		magicAction.initializeMagics();
	}
	
	public static MagicHandler getInstance() {
		if (magicHandler == null) {
			magicHandler = new MagicHandler();
		}
		return magicHandler;
	}
	/**
	 * Activates the selected magic in the domain
	 * @param magicKeyCode
	 */
	
	public void activateMagic(int magicKeyCode) {
		magicAction.activate(magicKeyCode);
	}
	
	/**
	 * 
	 */
	public void fireMagicalHex() {
		magicAction.fire();
	}
	
	public void addMagic(int i) {
		try {
			magicAction.addMagic(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void addMagicListener(MagicListener lis) {
		magicListeners.add(lis);
	}
	
	public void publishMagicEvent(int labelName, String information){
		for(MagicListener lis : magicListeners) {
			lis.onMagicEvent(labelName, information);
		}
	}
}