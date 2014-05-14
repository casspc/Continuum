package ccostello.game.shared;

import java.util.Random;

import ccostello.game.shared.messages.GameMessages.ObjectCommandMessage;

public class ObjectCommand {
	
	public static enum COMMANDS {
		FORWARD(1), BACK(2), LEFT(4), RIGHT(8), ROTATE_LEFT(16), ROTATE_RIGHT(32);
		
		private int value;
		
		private COMMANDS( int i ) {
			value = i; 
		}
		
		public int value() {
			return value;
		}
	}
	
	private int commandSet = 0;
	private int createdTick = Continuum.getTick();
	
	
	public ObjectCommand() {}
	
	public ObjectCommand( ObjectCommandMessage message ) {
		this.commandSet = message.getCommands();
	}
	
	public ObjectCommand( boolean random ) {
		//generates random commands for testing
		
		Random r = new Random();
		if ( r.nextBoolean() ) {
			addButton( COMMANDS.FORWARD );
		}
		if ( r.nextBoolean() ) {
			addButton( COMMANDS.BACK );
		}
		if ( r.nextBoolean() ) {
			addButton( COMMANDS.RIGHT );
		}
		if ( r.nextBoolean() ) {
			addButton( COMMANDS.LEFT );
		}
	}
	
	public void addButton( COMMANDS command ) {
		commandSet += command.value();
	}
	
	public int getCommands() {
		return commandSet;
	}

	public int getTick() {
		return createdTick;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "{ commandSet: " + this.commandSet +"; tick: " + this.getTick() + "}" );
		return sb.toString();
	}
	
	public ObjectCommandMessage.Builder getMessageBdr() {
		ObjectCommandMessage.Builder cBdr = ObjectCommandMessage.newBuilder();
		cBdr.setTick( getTick() );
		cBdr.setCommands( getCommands() );
		return cBdr;
	}
	
}
