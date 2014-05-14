package ccostello.game.shared;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import ccostello.game.client.ContinuumClient;
import ccostello.game.shared.messages.GameMessages.GameMessage;

import com.google.protobuf.ByteString;

public class NetworkHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	
	Continuum continuum;
	
	// for reliable messaging control
	// each incoming message's sequenceId is stored here so we can a) check for and ignore duplicates and b) calculate returned 
	//  "ack_bits" bitsets
	protected Map<InetSocketAddress,TreeSet<Integer>> receivedMessages = Collections.synchronizedMap( 
			new HashMap<InetSocketAddress,TreeSet<Integer>>() );
	
	// receiving and sending threads will compete for receivedMessages list for reading/building ack_bits bitset...need to synchronize.
	protected ReentrantReadWriteLock recMsgsLock = new ReentrantReadWriteLock();
	protected Lock rrl = recMsgsLock.readLock();
	protected Lock rwl = recMsgsLock.writeLock();
	
	// each outgoing message is stored by client InetAddress and sequenceId so that we can replay if no ack is received w/i 
	//  specified "replayDelay" time
	protected Map<InetSocketAddress,TreeMap<Integer,GameMessage>> sentMessages = Collections.synchronizedMap( 
			new HashMap<InetSocketAddress,TreeMap<Integer,GameMessage>>() );
	// receiving and sending threads will compete for receivedMessages list for reading/building ack_bits bitset...need to synchronize.
	protected ReentrantReadWriteLock sentMsgsLock = new ReentrantReadWriteLock();
	protected Lock srl = recMsgsLock.readLock();
	protected Lock swl = recMsgsLock.writeLock();
	
	// maintain a sequenceId per client
	protected Map<InetSocketAddress,Integer> sequenceIds = new HashMap<InetSocketAddress,Integer>();
	
	// amount of time in msec to wait for a message ack before resending
	private int replayDelay = 1000;

	private Channel channel;
	
	public NetworkHandler( Continuum continuum ) {
		this.continuum = continuum;
	}
	
	public void sendMessage( ChannelHandlerContext ctx, InetSocketAddress adr, GameMessage.Builder mBdr ) {
		sendMessage( ctx.channel(), adr, mBdr );
	}
	
	public void sendMessage( GameMessage.Builder mBdr ) {
		sendMessage( channel, ((ContinuumClient)continuum).getRemoteServer(), mBdr );
	}
	
	private void sendMessage( Channel ch, InetSocketAddress adr, GameMessage.Builder mBdr ) {
		
		long start = System.currentTimeMillis();
		Integer sequenceId = sequenceIds.get( adr );
		if ( sequenceId == null ) {
			sequenceId = new Integer(1);
		}
		sequenceIds.put( adr, new Integer( sequenceId.intValue() + 1 ) );
		
		mBdr.setSequenceId( sequenceId );
		addAckBits( adr, mBdr );
		
		GameMessage gm = mBdr.build();
		
		debug( "Sending to " + adr + ": " + gm.toString() );
		debug( "Total bytes: " + gm.toByteArray().length );

		try {
	        ch.writeAndFlush(new DatagramPacket( 
	                Unpooled.copiedBuffer( gm.toByteArray() ),
	                adr )).sync();
	        
	        TreeMap<Integer,GameMessage> cliSent = sentMessages.get( adr );
	        if ( cliSent == null ) {
	        	cliSent = new TreeMap<Integer,GameMessage>();
	        	sentMessages.put( adr, cliSent );
	        }
	        cliSent.put( gm.getSequenceId(), gm );
	        
			long stop = System.currentTimeMillis();
			long duration = (stop-start);
            if ( duration > 2 ) {
            	System.out.println( duration + "ms for send" );
            }
	        
		} catch ( Exception e ) {
			e.printStackTrace( System.err );
		}
	}
	
	private void addAckBits( InetSocketAddress adr, GameMessage.Builder mBdr ) {
		TreeSet<Integer> sequenceIds = receivedMessages.get( adr );
		if ( sequenceIds != null ) {
			rwl.lock();
			Integer ackId = sequenceIds.first();
			mBdr.setAckId( ackId );
			
			if ( sequenceIds.size() > 1 ) {
				BitSet bs = new BitSet();
				StringBuffer sb = new StringBuffer();
				for ( int i = 1; i <= continuum.getBroadcastRate(); i++ ) {
					if ( sequenceIds.contains( new Integer( ackId.intValue() - i ) ) ) {
						bs.set( i );
						sb.append( String.valueOf( ackId.intValue() - i ) + ";" );
					}
				}
				debug( "Ack bits: " + sb.toString() );
				mBdr.setAckBits( ByteString.copyFrom( bs.toByteArray() ) );
			}
			rwl.unlock();
		}
	}

	@Override
	protected void channelRead0( ChannelHandlerContext context, DatagramPacket packet )
			throws Exception {
		
		long start = System.currentTimeMillis();
		InetSocketAddress adr = packet.sender();
		Set<Integer> sendersMessages;
		
		if ( receivedMessages.get( adr ) == null ) {
			receivedMessages.put( adr, new TreeSet<Integer>( new ReverseIntegerComparator() ) );
		}
		
		sendersMessages = receivedMessages.get( adr );
		
		GameMessage gm;
		ByteBufInputStream is = new ByteBufInputStream( packet.content() );
		gm = GameMessage.parseFrom( is );
		
		debug( "Received from " + adr + ": " + gm.toString() );
		
		int sequenceId = gm.getSequenceId();
		
		//if we've already received this message, ignore it
		if ( receivedMessages.get( adr ).contains( sequenceId ) ) {
			return;
		} else {
			sendersMessages.add( sequenceId );
		}
	
		if ( gm.hasAckId() ) {	
			int ackId = gm.getAckId();
			sentMessages.get( adr ).remove( ackId );
			
			if ( gm.hasAckBits() ) {
				BitSet bs = null;
				bs = BitSet.valueOf( gm.getAckBits().toByteArray() );
				int numberOfAcks = continuum.getBroadcastRate();
				for ( int bitIndex = 1; bitIndex <= numberOfAcks; bitIndex++ ) {
					if ( bs.get( bitIndex ) ) {
						sentMessages.get( adr ).remove( ackId - bitIndex );
					}
				}
			}
		}
	
		long stop = System.currentTimeMillis();
		long duration = (stop-start);
        if ( duration > 2 ) {
        	System.out.println( duration + "ms for receipt" );
        }
		continuum.handle( gm, context, packet.sender(), this );

	}
	
    private void debug(String string) {
	 //System.out.println( string );
	}

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace( System.err );
        // We don't close the channel because we can keep serving requests.
    }

	public void setChannel(Channel ch) {
		this.channel = ch;
	}

}
