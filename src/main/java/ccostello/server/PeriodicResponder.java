package ccostello.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class PeriodicResponder extends TimerTask {
	
	private List<ContextPacket> myContexts = new ArrayList<ContextPacket>();
	private Lock wl;
	private List<ContextPacket> contexts;
	private static AtomicInteger msgCount = new AtomicInteger(1);
	private Map<InetSocketAddress, ObjectState> conns;
	
	private static AtomicInteger iterationCount = new AtomicInteger(0);

	public PeriodicResponder(List<ContextPacket> contexts, Lock wl, Map<InetSocketAddress, ObjectState> conns) {
		this.contexts = contexts;
		this.wl = wl;
		this.conns = conns;
	}

	@Override
	public void run() {
//		iterationCount.incrementAndGet();
//		long iterStart = System.currentTimeMillis();
//		
//		log( "contexts.size() = " + contexts.size() );
//		try {
//			wl.lock();
//			if ( contexts.size() > 0 ) {
//				myContexts.addAll( contexts );
//				contexts.clear();
//			}
//		} catch ( Exception e ) {
//			
//		} finally {
//			wl.unlock();
//		}
//		
//		log( "myContexts.size() = " + myContexts.size() );
//		if ( myContexts != null && myContexts.size() > 0 ) {
//				
//			//System.out.println( "Processing " + myContexts.size() + " received messages" );
//			//for each command message, compress by ip/port, then send single response to each
//			Map<InetSocketAddress,ChannelHandlerContext> cMap = new HashMap<InetSocketAddress, ChannelHandlerContext>();
//			for ( ContextPacket ctx : myContexts ) {
//				InetSocketAddress adrs = ctx.getPacket().sender();
//				cMap.put( adrs, ctx.getCtx() );
//				ObjectState us = conns.get( adrs );
//				if ( us == null ) {
//					log( "No UserState found for adrs " + adrs );
//					Random r = new Random();
//					int fakeUserId = r.nextInt( 1000 );
//					us = new ObjectState( fakeUserId, adrs );
//					ConnStats cs = new ConnStats( adrs );
//					us.setConnStats( cs );
//					conns.put( adrs, us );
//				} else {
//					log( "UserState found for adrs " + adrs );
//				}
//				us.getConnStats().addRcvd(1);
//				
//				//parse user command from datagram, update userstate
//				UserCommandMessage uc;
//
//				ByteBufInputStream is = new ByteBufInputStream( ctx.getPacket().content() );
//				try {
//					uc = UserCommandMessage.parseFrom( is );
//					ctx.getPacket().release();
//					ObjectCommand fromMsg = new ObjectCommand( uc );
//					log( "Updating with uc: " + fromMsg.toString() );
//					us.updateState( new ObjectCommand( uc ) );
//				} catch ( Exception e ) {
//					e.printStackTrace( System.err );
//				}	
//			}
//			
//			for ( InetSocketAddress address : cMap.keySet() ) {
//				ObjectState us = conns.get( address );
//				conns.get( address ).getConnStats().addSent(1);
//				ChannelHandlerContext ctx = cMap.get( address );
//				UserStateMessage usm = us.getUserStateMessage();
//				log( "sending usm: " + usm.toString() );
//				ctx.writeAndFlush( new DatagramPacket( Unpooled.copiedBuffer( usm.toByteArray() ), address ) );
//
//			}
//			msgCount.addAndGet( myContexts.size() );
//			//System.out.println( "Have processed " + msgCount + " total messages." );
//			myContexts.clear();
//		
//		} else {
//			//System.out.println( "Processing " + myContexts + " received messages" );
//		}
//		
//		long iterStop = System.currentTimeMillis();
//		//System.out.println( "Iteration " + iterationCount.intValue() + " took " + (iterStop - iterStart) + "ms" );

	}

	private void log(String string) {
		//System.out.println( iterationCount.intValue() + ": " + string );
	}

}
