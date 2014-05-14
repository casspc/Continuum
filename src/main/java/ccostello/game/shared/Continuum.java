package ccostello.game.shared;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

import ccostello.game.client.ContinuumClient;
import ccostello.game.server.ContinuumServer;
import ccostello.game.shared.messages.GameMessages.GameMessage;
import ccostello.game.shared.messages.GameMessages.GameMessage.MessageType;
import ccostello.game.shared.messages.GameMessages.ObjectStateMessage.Location;

public abstract class Continuum {
	
	// Number of simulation "ticks" per second. More = higher simulation precision, less = less CPU, bandwidth required
	private int simulationTickRate = 60;
	
	// number of times per sec user commands are broadcast to the server (if running as a client) or 
	//  deltas/world snapshots are broadcast to clients (if running as a server)
	//  1000/this number will be the period passed to the communicationTimer below
	private int broadcastRate = 30;
	
	// tick count, increments by 10 during every tick. Not sure we need this yet.
	protected static AtomicInteger tick = new AtomicInteger(10); //always start at 10? clients are told to start 3 ticks behind (45ms)?
	
	// all objects that exist in this conitnuum; by object id
	protected Map<String,ContinuumObject> myObjects = Collections.synchronizedMap( 
			new HashMap<String,ContinuumObject>() );
	
	// all player characters currently online in this continuum; by connection
	protected Map<InetSocketAddress,ContinuumObject> conns = Collections.synchronizedMap( 
			new HashMap<InetSocketAddress,ContinuumObject>() );
	
	protected int port;
	
	Timer continuumSimulationTimer;
	Timer communicationTimer;
	Timer messageCleanupTimer;
	
	
	public Continuum() {
	} 

	protected abstract void initialize();
	protected abstract void run();
	protected abstract void shutdown();
	
	
	//main entry
    public static void main( String[] args ) throws Exception {
    	
    	String mode = "", gameHost = "";
        int port = 0;
        
        if (args.length < 2) {
        	System.err.println( "Usage: java -jar ccostello.game.shared.Continuum server|client port [server host name]" );
        	System.exit(-1);
        } else {
        	mode = args[0];
        	port = Integer.parseInt( args[1] );
        }
        
        Continuum continuum;
        if ( mode.equals( "server" ) ) {
        	continuum = new ContinuumServer( port );
        } else {
        	gameHost = args[2];
        	continuum = new ContinuumClient( gameHost, port );
        }
        //load config from db, join cluster, init networking, etc
        continuum.initialize();
        
        //once we have everything in place, start network operations.
        continuum.run();
        
        //if we ever return from run(), make sure things are gracefully shutdown.
        continuum.shutdown();
    }
    
	public void handle( GameMessage gm, ChannelHandlerContext ctx, InetSocketAddress adr, NetworkHandler handler ) {
		
		switch (gm.getMsgType()) {
		
		    /* server-side cases */
			case REGISTER_CLIENT:
				GameMessage.Builder gmNew = GameMessage.newBuilder( gm );
				//gmNew.getUserStateBuilder().setServerTick( tickNum );
				Location.Builder loc = gmNew.getUserStateBuilder().getLocationBuilder();
				loc.setX( 0 );
				loc.setY( 0 );
				loc.setZ( 0 );
				gmNew.setMsgType( MessageType.REGISTER_CLIENT_RESP );
				
				handler.sendMessage(ctx, adr, gmNew);
				break;
			case USER_COMMAND:
				//IncomingMessagesManager.processUserCommand( gm, ctx, inetSocketAddress );
				break;
				
			/* client-side cases */
			case REGISTER_CLIENT_RESP:
				//System.out.println( "doing nothing in response" );
				break;
			case CLIENT_UPGRADE_COMMAND:
				break;
			case USER_STATE:
			case WORLD_STATE:
				//GameClient.updateEverything( gm );
				break;
				
			/* both cases */
			case END_CLIENT:
				//IncomingMessagesManager.unregisterClient( gm, ctx, inetSocketAddress );
				break;
			default:
				break;
		}
		
	}
	
	public int getSimulationTickRate() {
		return simulationTickRate;
	}

	public void setSimulationTickRate(int simulationTickRate) {
		this.simulationTickRate = simulationTickRate;
	}

	public int getBroadcastRate() {
		return broadcastRate;
	}

	public void setBroadcastRate(int broadcastRate) {
		this.broadcastRate = broadcastRate;
	}

	public int getPort() {
		return port;
	}

	public static Integer getTick() {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
