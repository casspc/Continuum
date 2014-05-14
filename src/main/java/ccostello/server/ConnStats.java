package ccostello.server;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnStats {
	
	private InetSocketAddress adrs;
	private AtomicInteger rcvd_from = new AtomicInteger(0);
	private AtomicInteger sent_to = new AtomicInteger(0);
	
	public ConnStats( InetSocketAddress adrs ) {
		this.adrs = adrs;
	}
	
	public InetSocketAddress getAdrs() {
		return adrs;
	}
	public void setAdrs(InetSocketAddress adrs) {
		this.adrs = adrs;
	}
	public AtomicInteger getRcvd_from() {
		return rcvd_from;
	}
	public void setRcvd_from(AtomicInteger rcvd_from) {
		this.rcvd_from = rcvd_from;
	}
	public AtomicInteger getSent_to() {
		return sent_to;
	}
	public void setSent_to(AtomicInteger sent_to) {
		this.sent_to = sent_to;
	}
	
	public void addRcvd( int i ) {
		rcvd_from.incrementAndGet();
	}
	
	public void addSent( int i ) {
		sent_to.incrementAndGet();
	}

}
