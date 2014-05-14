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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.concurrent.atomic.AtomicInteger;

public class GameClientMessageHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	
	private AtomicInteger count = new AtomicInteger(0);

	@Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		
//		GameMessage gm;
//		ByteBufInputStream is = new ByteBufInputStream( packet.content() );
//		gm = GameMessage.parseFrom( is );
//		
//		System.out.println( "Received message: " + gm.toString() );
//		
//		switch (gm.getMsgType()) {
//			case REGISTER_CLIENT_RESP:
//				GameClient.setUp( gm );
//				break;
//			case CLIENT_UPGRADE_COMMAND:
//				break;
//			case END_CLIENT:
//				GameClient.shutdown();
//				break;
//			case USER_STATE:
//			case WORLD_STATE:
//				GameClient.updateEverything( gm );
//				break;
//			default:
//				break;
//		}

    	count.incrementAndGet();
        //System.out.println( count + ": " + usm.toString() );//+ ": Quote of the Moment: " + response.substring(6));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}