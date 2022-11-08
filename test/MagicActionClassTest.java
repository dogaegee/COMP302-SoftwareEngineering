package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.MagicAction;
import domain.MagicFactory;

/**
 * What to test:
 * - Checking rep is OK or not when addMagic method takes illegal magicKeyCode.
 * - Checking rep is OK or not when getMagic method takes illegal magicKeyCode.
 * - Checking rep is OK or not when an already activated magic is tried to be activated.
 * - Checking rep is OK or not with an illegal initial HashMaps.
 * - Checking rep is OK or not when the selected magic is activated successfully.
 * - Checking rep is OK or not when a magic is activated successively until it runs out of.
 */

class MagicActionClassTest {
	MagicAction mH = new MagicAction();
	private static HashMap<Integer, Integer> magics;
	private static MagicFactory magicFactory;
	private static HashMap<Integer, Boolean> currentMagicsAndActivations;

	@BeforeEach
	void setUp() {
		magics = new HashMap<Integer, Integer>();
		magicFactory = MagicFactory.getInstance();
		currentMagicsAndActivations = new HashMap<Integer, Boolean>();
		mH.initializeMagics();
		magics.put(0, 0);
		magics.put(1, 4);
		magics.put(2, 0);
		magics.put(3, 1);
		currentMagicsAndActivations.put(0, false);
		currentMagicsAndActivations.put(1, false);
		currentMagicsAndActivations.put(2, false);
		currentMagicsAndActivations.put(3, false);
		MagicAction.setMagics(magics);
	}

	@Test
	void incorrectMagicCodeTestForAddMagic() throws Exception {	
		assertTrue(mH.repOk());
		assertThrows(Exception.class, () -> {mH.addMagic(-1);});
		assertTrue(mH.repOk());
		assertThrows(Exception.class, () -> {mH.addMagic(7);});
		assertTrue(mH.repOk());
	}

	@Test 
	void incorrectMagicCodeTestForGetMagic(){	
		assertTrue(mH.repOk());
		assertEquals(null, mH.getMagic(-3));
		assertTrue(mH.repOk());
		assertEquals(null, mH.getMagic(4));
		assertTrue(mH.repOk());
	}

	@Test
	void magicAlreadyActiveTest() {
		assertEquals(true, mH.repOk());
		HashMap<Integer,Boolean> mapFirstMagicActive = new HashMap<Integer,Boolean>();
		mapFirstMagicActive.put(0, false);
		mapFirstMagicActive.put(1, true);
		mapFirstMagicActive.put(2, false);
		mapFirstMagicActive.put(3, false);
		MagicAction.setCurrentMagicsAndActivations(mapFirstMagicActive);
		assertEquals(null, mH.getMagic(2));
		assertTrue(mH.repOk());
	}

	@Test 
	void illegalInitialHashMapTest() {
		assertEquals(true, mH.repOk());
		HashMap<Integer,Integer> illegalMagicMap1 = new HashMap<Integer,Integer>();
		illegalMagicMap1.put(0, 10); //Value of 0 key cannot be 10.
		illegalMagicMap1.put(1, 5);
		illegalMagicMap1.put(2, 6);
		illegalMagicMap1.put(3, 7);
		MagicAction.setMagics(illegalMagicMap1);
		assertFalse(mH.repOk());

		HashMap<Integer,Integer> illegalMagicMap2 = new HashMap<Integer,Integer>();
		illegalMagicMap2.put(0, 0);
		illegalMagicMap2.put(1, 5);
		illegalMagicMap2.put(2, 6);
		illegalMagicMap2.put(3, 7);
		illegalMagicMap2.put(4, 5); //Key  cannot be 4.
		MagicAction.setMagics(illegalMagicMap2);
		assertFalse(mH.repOk());

		HashMap<Integer,Integer> illegalMagicMap3 = new HashMap<Integer,Integer>();
		illegalMagicMap3.put(0, 0);
		illegalMagicMap3.put(1, 5);
		illegalMagicMap3.put(2, -6); //Any value cannot be negative.
		illegalMagicMap3.put(3, 7);
		MagicAction.setMagics(illegalMagicMap3);
		assertFalse(mH.repOk());

		HashMap<Integer,Boolean> illegalMagicMap4 = new HashMap<Integer,Boolean>();
		illegalMagicMap4.put(0, true); //Value of key 0 cannot be true.
		illegalMagicMap4.put(1, false);
		illegalMagicMap4.put(2, false);
		illegalMagicMap4.put(3, false);
		MagicAction.setCurrentMagicsAndActivations(illegalMagicMap4);
		assertFalse(mH.repOk());
	}

	@Test
	void testReturnsCorrectMagic() throws Exception { //Correct-returning cases when the selected magic exists and the player has it.
		HashMap<Integer,Integer> magics4 = new HashMap<Integer,Integer>();
		magics4.put(0, 1);
		magics4.put(1, 1);
		magics4.put(2, 1);
		magics4.put(3, 1);
		MagicAction.setMagics(magics4);

		//repOK should be false since addChances magic should activate automatically
		assertFalse(mH.repOk());


		magics4 = new HashMap<Integer,Integer>();
		magics4.put(0, 0);
		magics4.put(1, 1);
		magics4.put(2, 1);
		magics4.put(3, 1);
		MagicAction.setMagics(magics4);
		assertTrue(mH.repOk());
		assertEquals("class domain.magic.MagicalHex", mH.getMagic(1).getClass().toString());
		assertTrue(mH.repOk());
		assertEquals("class domain.magic.NoblePhantasmExpansion", mH.getMagic(2).getClass().toString());
		assertTrue(mH.repOk());
		assertEquals("class domain.magic.UnstoppableEnchantedSphere", mH.getMagic(3).getClass().toString());
		assertTrue(mH.repOk());

		//if the magic is active, do not activate the same instance again
		assertEquals(null, mH.getMagic(1));
		assertTrue(mH.repOk());
		assertEquals(null, mH.getMagic(2));
		assertTrue(mH.repOk());
		assertEquals(null, mH.getMagic(3));
		assertTrue(mH.repOk());
	}

	@Test
	void testReturnsNullAfterSeveralTimes() { //Null-returning cases after it is run short.
		HashMap<Integer,Integer> magics2 = new HashMap<Integer,Integer>();
		magics2.put(0, 0);
		magics2.put(1, 2);
		magics2.put(2, 0);
		magics2.put(3, 1);
		MagicAction.setMagics(magics2);
		assertTrue(mH.repOk());
		mH.getMagic(1);
		mH.getMagic(1);
		assertEquals(null, mH.getMagic(1));
		assertEquals(null, mH.getMagic(1));
		mH.getMagic(3);
		assertEquals(null, mH.getMagic(3));
		assertTrue(mH.repOk());
	}



}
