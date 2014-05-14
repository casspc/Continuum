package ccostello.game.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ccostello.game.shared.Continuum;
import ccostello.game.shared.ContinuumObject;
import ccostello.game.shared.NetworkHandler;
import ccostello.game.shared.messages.GameMessages.GameMessage;
import ccostello.game.shared.messages.GameMessages.GameMessage.MessageType;
import ccostello.game.shared.messages.GameMessages.ObjectStateMessage;

public class ContinuumClient extends Continuum {
	
	protected String gameHost;
	private Channel ch;

	private InetSocketAddress remoteServer;
	private int secOfInactivyBeforeCloseApp = 20;
	private ContinuumObject playerCharacter;
	
	public ContinuumClient( String gameHost, int port ) {
		this.gameHost = gameHost;
		this.port = port;
		this.remoteServer = new InetSocketAddress( gameHost, port );
	}

	@Override
	protected void initialize() {
		playerCharacter = new ContinuumObject( true );
		
        GameMessage.Builder gm = GameMessage.newBuilder();
        gm.setMsgType( MessageType.REGISTER_CLIENT );
        gm.setSessionToken( String.valueOf( (new Random()).nextInt(10000000) ) );
        //gm.setSequenceId( sequenceId.getAndIncrement() );
        ObjectStateMessage.Builder om = ObjectStateMessage.newBuilder();
        om.setObjectId( String.valueOf( (new Random()).nextInt(10000000) )  );
        gm.setUserState( om );
	}

	@Override
	protected void run() {
		startProcessingThreads();
        startNetwork();
	}
	
	protected void startProcessingThreads() {
		// simulationTickRate times per second, sample input devices, update playerCharacter state
		
		int timerPeriod = 1000 / this.getSimulationTickRate();
		Timer t = new Timer();
		TimerTask commandProcessor = new ClientCommandProcessor( playerCharacter );
		t.scheduleAtFixedRate( commandProcessor, timerPeriod, timerPeriod );
	}

	private void startNetwork() {
		EventLoopGroup group = new NioEventLoopGroup();
        try {
        	
        	NetworkHandler nHdlr = new NetworkHandler( this );
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler( nHdlr );

            ch = b.bind(0).sync().channel();
            nHdlr.setChannel( ch );

            //on startup, we send a registration message

            
            long start = System.currentTimeMillis();
            for ( int i = 0; i < 90; i++ ) {
            	nHdlr.sendMessage( gm );
            	Thread.sleep( 31 );
            }
            long stop = System.currentTimeMillis();
            long duration = (stop-start);
            if ( duration > 2 ) {
            	System.out.println( duration + "ms" );
            }
            
//            ch.writeAndFlush(new DatagramPacket( 
//                    Unpooled.copiedBuffer( gm.build().toByteArray() ),
//                    new InetSocketAddress("localhost", port))).sync();
           
            if ( !ch.closeFuture().await( secOfInactivyBeforeCloseApp * 1000 )) {
//            	t.cancel();
                System.err.println("No responses for " + secOfInactivyBeforeCloseApp + " sec ... quitting.");
            }
        } catch ( Exception e ) {
        	e.printStackTrace( System.err );
        } finally {
            group.shutdownGracefully();
        }
	}

	@Override
	protected void shutdown() {
		// shutdown networking, clean up save prefs, etc..
	}

	public InetSocketAddress getRemoteServer() {
		return remoteServer;
	}

}
