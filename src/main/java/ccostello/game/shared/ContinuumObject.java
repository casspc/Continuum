package ccostello.game.shared;

import java.util.TreeMap;

import ccostello.game.shared.messages.GameMessages.GameMessage;
import ccostello.game.shared.messages.GameMessages.ObjectCommandMessage;
import ccostello.server.ObjectState;

public class ContinuumObject {
	
	protected TreeMap<Integer,ObjectState> objectStates = new TreeMap<Integer,ObjectState>(); //key is tick
	protected TreeMap<Integer,ObjectCommand> objectCommands = new TreeMap<Integer,ObjectCommand>(); //key is message sequence number
	
	protected String objectId;
	
	public ContinuumObject( boolean random ) {
//		this.objectId = gm.getUserState().getObjectId();
//		updateState( gm.getUserState() );
	}
	
	public ContinuumObject( GameMessage gm ) {
		this.objectId = gm.getUserState().getObjectId();
		updateState( gm.getUserState() );
	}
	
	public ContinuumObject( String objectId ) {
		this.objectId = objectId;
	}

	public void updateState( ObjectCommandMessage obj ) {
		int commandTick = obj.getTick();
		ObjectState tickState = objectStates.get( commandTick );
		if ( tickState == null ) {
			tickState = new ObjectState( this );
		}
		objectStates.put( Continuum.getTick(), (new ObjectState( obj ) ) );
	}

}
