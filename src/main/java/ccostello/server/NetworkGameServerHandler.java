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

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import ccostello.game.shared.messages.GameMessages.GameMessage;

public class NetworkGameServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        //System.err.println(packet);
    	
		GameMessage gm;
		ByteBufInputStream is = new ByteBufInputStream( packet.content() );
		gm = GameMessage.parseFrom( is );
		
		System.out.println( "Received message: " + gm.toString() );
		
		switch (gm.getMsgType()) {
			case REGISTER_CLIENT:
				IncomingMessagesManager.registerNewClient( gm, ctx, packet.sender() );
				break;
			case USER_COMMAND:
				IncomingMessagesManager.processUserCommand( gm, ctx, packet.sender() );
				break;
			case END_CLIENT:
				IncomingMessagesManager.unregisterClient( gm, ctx, packet.sender() );
				break;
			default:
				break;
		}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }
}