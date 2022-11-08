package domain;

import domain.magic.Activatable;
import domain.magic.ChanceGiving;
import domain.magic.DoubleAccel;
import domain.magic.HollowPurple;
import domain.magic.InfiniteVoid;
import domain.magic.MagicalHex;
import domain.magic.NoblePhantasmExpansion;
import domain.magic.UnstoppableEnchantedSphere;

/**
 * This method creates magical abilities according to the given magic code.
 * @author Softwaring
 *
 */
public class MagicFactory {
	private Activatable magicalAbility;
	private static MagicFactory factory;
	
	private MagicFactory() {
		
	}
	
	public static MagicFactory getInstance() {
		if (factory == null) 
			factory = new MagicFactory();
		return factory;
	}
	/*
	This is an example of the Factory pattern which creates 
	magical abilities according to the given magic code.
	*/
	Activatable getMagicalAbility(int magicChosen)  {
		if (magicChosen == Constant.CHANCE_GIVING) {
			magicalAbility = new  ChanceGiving();
		} else if(magicChosen == Constant.MAGICAL_HEX) {
			magicalAbility = new  MagicalHex();
		} else if(magicChosen == Constant.NOBLE_PHANTASM_EXPANSION) {
			magicalAbility = new  NoblePhantasmExpansion();
		} else if(magicChosen == Constant.UNSTOPPABLE_ENCHANTED_SPHERE) {
			magicalAbility = new  UnstoppableEnchantedSphere();
		} else if(magicChosen == Constant.INFINITE_VOID) {
			magicalAbility = new  InfiniteVoid();
		} else if(magicChosen == Constant.HOLLOW_PURPLE) {
			magicalAbility = new  HollowPurple();
		} else if(magicChosen == Constant.DOUBLE_ACCEL) {
			magicalAbility = new  DoubleAccel();
		} 
		return magicalAbility;
	}
}
