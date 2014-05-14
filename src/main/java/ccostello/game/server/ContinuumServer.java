package ccostello.game.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import ccostello.game.shared.Continuum;
import ccostello.game.shared.NetworkHandler;

public class ContinuumServer extends Continuum {

	public ContinuumServer(int port) {
		this.port = port;
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
        	
        	NetworkHandler nHdlr = new NetworkHandler( this );
            Bootstrap b = new Bootstrap();
            
            b.group(group)
             .channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler( nHdlr );

            System.out.println( "Listening on port( " + port + " )..." );
            b.bind(port).sync().channel().closeFuture().await();
            
        } catch ( Exception e ) {
        	e.printStackTrace( System.err );
        } finally {
            group.shutdownGracefully();
        }

	}

	@Override
	protected void shutdown() {
		// TODO Auto-generated method stub

	}

}
