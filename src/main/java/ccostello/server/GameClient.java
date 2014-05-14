/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package ccostello.server;


/**
 * A UDP broadcast client that asks for a quote of the moment (QOTM) to
 * {@link GameServer}.
 *
 * Inspired by <a href="http://goo.gl/BsXVR">the official Java tutorial</a>.
 */
public class GameClient {

//    private final int port;
//    private static Continuum continuum;
//    private static ContinuumObject playerCharacter;
//
//    private final static int frametime = 20;
//    private static long tick;
//    private static AtomicInteger sequenceId = new AtomicInteger(1);
//    
//    private static Timer t, t1;
//    private static Channel ch;
//    private int secOfInactivyBeforeCloseApp = 20;
//    
//    static {
//    	playerCharacter = new ContinuumObject( UUID.randomUUID().toString() );
//    	continuum = new Continuum();
//    	continuum.myObjects.put( playerCharacter.objectId, playerCharacter );
//    	
//    	t = new Timer();
//    	TimerTask userStateLogger = new UserStateLogger( playerCharacter );
//    	t.schedule( userStateLogger, 2000, 2000 );
//    	
//    	// main timer for command processing
//    	t1 = new Timer();
//    	TimerTask commandProcessor = new PlayerCommandProcessor();
//    	t1.schedule( commandProcessor, frametime, frametime );
//    	
//    }
//
//    public GameClient(int port) {
//        this.port = port;
//    }
//
//    public void run() throws Exception {
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            Bootstrap b = new Bootstrap();
//            b.group(group)
//             .channel(NioDatagramChannel.class)
//             .option(ChannelOption.SO_BROADCAST, true)
//             .handler(new GameClientMessageHandler() );
//
//            ch = b.bind(0).sync().channel();
//
//            //on startup, we send a registration message
//            GameMessage.Builder gm = GameMessage.newBuilder();
//            gm.setMsgType( MessageType.REGISTER_CLIENT );
//            gm.setSessionToken( UUID.randomUUID().toString() );
//            gm.setSequenceId( sequenceId.getAndIncrement() );
//            ObjectStateMessage.Builder om = ObjectStateMessage.newBuilder();
//            om.setObjectId( playerCharacter.objectId );
//            gm.setUserState( om );
//            
//            ch.writeAndFlush(new DatagramPacket( 
//                    Unpooled.copiedBuffer( gm.build().toByteArray() ),
//                    new InetSocketAddress("centos1", port))).sync();
//           
//            if ( !ch.closeFuture().await( secOfInactivyBeforeCloseApp * 1000 )) {
//            	t.cancel();
//                System.err.println("No responses for " + secOfInactivyBeforeCloseApp + " sec ... quitting.");
//            }
//        } finally {
//            group.shutdownGracefully();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 9876;
//        }
//        new GameClient(port).run();
//    }
//
//	public static void setUp(GameMessage gm) {
//		playerCharacter.updateState( gm.getUserState() );
//		tick = gm.getUserState().getTick();
//		
//		//start timer for generating events
//	}
//
//	public static void shutdown() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public static void updateEverything(GameMessage gm) {
//		// TODO Auto-generated method stub
//		
//	}
}