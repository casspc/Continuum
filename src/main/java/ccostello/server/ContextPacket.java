package ccostello.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class ContextPacket {
	
	private ChannelHandlerContext ctx;
	private DatagramPacket packet;
	
	public ContextPacket(ChannelHandlerContext ctx2, DatagramPacket packet2) {
		this.ctx = ctx2;
		this.packet = packet2;
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public DatagramPacket getPacket() {
		return packet;
	}
	public void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}
	

}
