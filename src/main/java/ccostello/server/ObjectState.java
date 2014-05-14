package ccostello.server;

import ccostello.game.shared.Continuum;
import ccostello.game.shared.ObjectCommand.COMMANDS;
import ccostello.game.shared.messages.GameMessages.ObjectCommandMessage;
import ccostello.game.shared.messages.GameMessages.ObjectStateMessage;
import ccostello.game.shared.messages.GameMessages.ObjectStateMessage.Location;

public class ObjectState {
	
	private long tick;
	
	private short direction = 0; //0 = north, 90 = east, etc
	private float x = 0;
	private float y = 0;
	private float z = 0;
	
	public ObjectState( ObjectStateMessage message ) {
		Location loc = message.getLocation();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.tick = Continuum.getTick();
	}
	
	public ObjectState( ObjectState obj ) {
		this.x = obj.getX();
		this.y = obj.getY();
		this.z = obj.getZ();
	}
	
	public ObjectState getNewStateFromCommand( ObjectCommandMessage command ) {
		ObjectState newState = new ObjectState( this );
		
		if ( (command.getCommands() & COMMANDS.FORWARD.value()) == COMMANDS.FORWARD.value() ) {
			newState.z++;
		}
		if ( (command.getCommands() & COMMANDS.BACK.value()) == COMMANDS.BACK.value() ) {
			newState.z--;
		}
		if ( (command.getCommands() & COMMANDS.RIGHT.value()) == COMMANDS.RIGHT.value() ) {
			newState.x++;
		}
		if ( (command.getCommands() & COMMANDS.LEFT.value()) == COMMANDS.LEFT.value() ) {
			newState.x--;
		}
		
		newState.tick = Continuum.getTick();
		return newState;
	}
	
	public void printState() {
		System.out.println( "object is located at " + x + ", " + y + ", " + z + " at tick " + tick );
	}

	public short getDirection() {
		return direction;
	}

	public void setDirection(short direction) {
		this.direction = direction;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

//	public UserStateMessage getUserStateMessage() {
//		UserStateMessage.Builder usm = UserStateMessage.newBuilder();
//		usm.setUserId( this.user_id );
//		usm.setX( this.x );
//		usm.setY( this.y );
//		usm.setZ( this.z );
//		
//		return usm.build();
//	}
}
