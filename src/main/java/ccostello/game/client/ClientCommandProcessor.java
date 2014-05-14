package ccostello.game.client;

import java.util.TimerTask;

import ccostello.game.shared.ContinuumObject;
import ccostello.game.shared.ObjectCommand;

public class ClientCommandProcessor extends TimerTask {
	
	ContinuumObject playerCharacter;

	public ClientCommandProcessor(ContinuumObject playerCharacter) {
		this.playerCharacter = playerCharacter;
	}

	@Override
	public void run() {
		
		// generate a fake command set, update playChar, send to server
		ObjectCommand command = new ObjectCommand( true );
		playerCharacter.updateState( command.getMessageBdr().build() );

	}

}
