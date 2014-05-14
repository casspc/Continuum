package ccostello.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.BitSet;
import java.util.TreeSet;

import org.commoncrawl.util.OpenBitSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ccostello.game.shared.ObjectCommand;
import ccostello.game.shared.ReverseIntegerComparator;
import ccostello.game.shared.ObjectCommand.COMMANDS;

public class SimpleTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBitwiseStuff() {
		final int FLAG1 = 1;
		final int FLAG2 = 2;
		final int FLAG3 = 4;
		final int FLAG4 = 8;
		
		int values = 5;
		
		assertTrue( (values & FLAG3) == FLAG3 );
		assertTrue( (values & FLAG1) == FLAG1 );
		assertFalse( (values & FLAG2) == FLAG2 );
		assertFalse( (values & FLAG4) == FLAG4 );
		assertFalse( (values & FLAG2) == FLAG3 );
	}
	
	@Test 
	public void testCommand() {
		ObjectCommand u = new ObjectCommand();
		u.addButton( COMMANDS.FORWARD );
		u.addButton( COMMANDS.RIGHT );
		
		assertTrue( (u.getCommands() & COMMANDS.FORWARD.value()) == COMMANDS.FORWARD.value() );
		assertTrue( (u.getCommands() & COMMANDS.RIGHT.value()) == COMMANDS.RIGHT.value() );
		assertTrue( (u.getCommands() & COMMANDS.BACK.value()) != COMMANDS.BACK.value() );
		assertTrue( (u.getCommands() & COMMANDS.LEFT.value()) != COMMANDS.LEFT.value() );
	}
	
//	@Test 
//	public void testUser() {
//		ObjectState us = new ObjectState( 1 );
//		ObjectCommand u = new ObjectCommand();
//		u.addButton( COMMANDS.FORWARD );
//		u.addButton( COMMANDS.RIGHT );
//		
//		assertTrue( us.getX() == 0 );
//		assertTrue( us.getY() == 0 );
//		assertTrue( us.getZ() == 0 );
//		
//		us.updateState( u );
//		assertTrue( us.getX() == 1 );
//		assertTrue( us.getY() == 0 );
//		assertTrue( us.getZ() == 1 );
//		
//		u = new ObjectCommand();
//		u.addButton( COMMANDS.BACK );
//		u.addButton( COMMANDS.LEFT );
//		
//		us.updateState( u );
//		assertTrue( us.getX() == 0 );
//		assertTrue( us.getY() == 0 );
//		assertTrue( us.getZ() == 0 );
//		
//		u = new ObjectCommand();
//		u.addButton( COMMANDS.BACK );
//		
//		us.updateState( u );
//		assertTrue( us.getX() == 0 );
//		assertTrue( us.getY() == 0 );
//		assertTrue( us.getZ() == -1 );
//		
//		u.addButton( COMMANDS.LEFT );
//		us.updateState( u );
//		assertTrue( us.getX() == -1 );
//		assertTrue( us.getY() == 0 );
//		assertTrue( us.getZ() == -2 );
//	}
	
	@Test
	public void testOpenBitSet() {
		
		long start = System.currentTimeMillis();
		for ( int x = 0; x < 10000; x ++ ) {
			OpenBitSet obs = new OpenBitSet(33);
			for ( long i = 0; i < 33; i ++ ) {
				obs.set(i);
			}
		}
		long stop = System.currentTimeMillis();
		System.out.println( "Took " + (stop-start) + "ms to populate obs " );
		
		BitSet bs = new BitSet(33);
		start = System.currentTimeMillis();
		for ( int x = 0; x < 10000; x ++ ) {
			bs = new BitSet(33);
			for ( int i = 0; i < 33; i ++ ) {
				bs.set(i);
			}
			stop = System.currentTimeMillis();
		}
		System.out.println( "Took " + (stop-start) + "ms to populate bs " );
		System.out.println( "bs byte[] size: " + bs.toByteArray().length );
	}
	
	@Test
	public void testSetOps() {
		TreeSet<Integer> s = new TreeSet<Integer>( new ReverseIntegerComparator() );
		
		s.add( new Integer( 1 ) );
		s.add( new Integer( 2 ) );
		s.add( new Integer( 3 ) );
		s.add( new Integer( 4 ) );
		
		assertTrue ( s.contains( new Integer( 1 ) ) );
		assertFalse ( s.contains( new Integer( 5 ) ) );
		
		Integer first = s.first();
		Integer last = s.last();
		assertTrue( first.equals( new Integer( 4 ) ) );
		assertTrue( last.equals( new Integer( 1 ) ) );
		
		assertTrue( s.contains( new Integer(3) ) ); 
		
	}
	

}
