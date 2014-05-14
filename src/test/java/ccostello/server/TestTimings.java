package ccostello.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import ccostello.game.shared.ObjectCommand;

public class TestTimings {
	
	private static List<ObjectState> users = Collections.synchronizedList( new ArrayList<ObjectState>() );
	private static List<ObjectCommand> commands = new ArrayList<ObjectCommand>();
	private static List<List<ObjectCommand>> commandLists = new ArrayList<List<ObjectCommand>>();
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static Lock rl = lock.readLock();
	private static Lock wl = lock.writeLock();
	
	private static final int NUM_OF_USERS = 10;
	private static final int NUM_OF_COMMAND_CYCLES = 60;

	public static void main(String[] args) {
		
//		//create NUM_OF_USERS "users" all at the origin
//		for ( int i = 0; i< NUM_OF_USERS; i++ ) {
//			ObjectState u = new ObjectState( i );
//			users.add( u );
//		}
//		
//		//generate a list of NUM_OF_USERS random commands ( one for each UserState )
//		for ( int i = 0; i< NUM_OF_COMMAND_CYCLES; i++ ) {
//			for ( int y = 0; y < NUM_OF_USERS; y++ ) {
//				ObjectCommand c = new ObjectCommand( true );
//				commands.add( c );
//			}
//			commandLists.add( new ArrayList<ObjectCommand>( commands ) );
//			commands.clear();
//		}
//				
//		long allStart = System.currentTimeMillis();
//		for ( int i = 0; i < NUM_OF_COMMAND_CYCLES; i++ ) {		
//			long start = System.currentTimeMillis();
//			List<ObjectState> myCopy = new ArrayList<ObjectState>();
//			wl.lock();
//			myCopy.addAll(users);
//			wl.unlock();
//			List<ObjectCommand> newCommands = commandLists.get( i );
//			for ( int x = 0; x < myCopy.size(); x++ ) {
//				ObjectState u = myCopy.get( x );
//				ObjectCommand c = newCommands.get( x );
//				u.updateState(c );
//				//u.printState();
//			}
//			long stop = System.currentTimeMillis();
//			long total = (stop-start);
//			if ( total > 15 ) {
//				System.out.println( "too long: " + total );
//			}
//			
//			if ( total < 15 ) {
//				try { 
//					Thread.sleep( 15 - total );
//				} catch ( Exception e ) {}				
//			}
//
//		}
//		long allStop = System.currentTimeMillis();
//		System.out.println( "Total time: " + (allStop - allStart) );
		
		
	}

}
