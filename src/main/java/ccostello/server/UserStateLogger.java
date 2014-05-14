package ccostello.server;

import java.util.TimerTask;

import ccostello.game.shared.ContinuumObject;

public class UserStateLogger extends TimerTask {
	
	private ContinuumObject obj;

	public UserStateLogger( ContinuumObject obj ) {
		this.obj = obj;
	}

	@Override
	public void run() {
//		System.out.println( "Current user state: " + obj.objectStates.get( obj.objectStates.lastKey() ) );
	}

}
