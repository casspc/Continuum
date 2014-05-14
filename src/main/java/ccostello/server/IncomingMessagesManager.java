package ccostello.server;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import ccostello.game.shared.ContinuumObject;
import ccostello.game.shared.messages.GameMessages.GameMessage;

public class IncomingMessagesManager {
	

	
	private static Map<InetSocketAddress,ContinuumObject> conns = Collections.synchronizedMap( new HashMap<InetSocketAddress,ContinuumObject>() );
	
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static Lock rl = lock.readLock();
	private static Lock wl = lock.writeLock();
	
	static {
//		Timer t = new Timer();
//		TimerTask responder = new PeriodicResponder( contexts, wl, conns );
//		t.schedule( responder, NetworkGameServer.frametime, NetworkGameServer.frametime );
//		
//		Timer t2 = new Timer();
//		TimerTask connectionManager = new ConnectionManager( conns );
//		t2.scheduleAtFixedRate( connectionManager, 3000, 3000 );
	}

//	public static void process(ChannelHandlerContext ctx, DatagramPacket packet) {
//		try {
//			rl.lock();
//			ContextPacket cp = new ContextPacket( ctx, packet );
//			contexts.add( cp );
//			//System.out.println( "received message from: " + packet.sender().getAddress() + " " + packet.sender().getPort() );
//		} catch ( Exception e ) {
//			e.printStackTrace( System.err );
//		} finally {
//			rl.unlock();
//		}	
//	}
	
	public static void initialize() {
		System.out.println( "IncomingMessagesManager initialized" );
		try {
			Thread.sleep(500);
		} catch ( Exception e ) {}
	}

	public static void registerNewClient( GameMessage gm, ChannelHandlerContext ctx, InetSocketAddress inetSocketAddress ) {
		//create object in continuum, send back correct ticks and object state
		//if object already exists, blow it away
//		System.out.println( "Creating new client connection" );
//		conns.remove( inetSocketAddress );
//		conns.put(inetSocketAddress, new ContinuumObject(gm) );
//		
//		GameMessage.Builder gmNew = GameMessage.newBuilder( gm );
//		gmNew.getUserStateBuilder().setTick( tick );
//		Location.Builder loc = gmNew.getUserStateBuilder().getLocationBuilder();
//		loc.setX( 0 );
//		loc.setY( 0 );
//		loc.setZ( 0 );
//		gmNew.setMsgType( MessageType.REGISTER_CLIENT_RESP );
//		
//		GameMessage returnedMessage = gmNew.build();
//		System.out.println( "Responding with: " + returnedMessage );
//		ctx.writeAndFlush(new DatagramPacket( 
//                Unpooled.copiedBuffer( returnedMessage.toByteArray() ),
//                inetSocketAddress));
	}

	public static void processUserCommand( GameMessage gm, ChannelHandlerContext ctx, InetSocketAddress inetSocketAddress ) {
		// TODO Auto-generated method stub
		
	}

	public static void unregisterClient( GameMessage gm, ChannelHandlerContext ctx, InetSocketAddress inetSocketAddress ) {
		// TODO Auto-generated method stub
		
	}

}
