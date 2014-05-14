package ccostello.server;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.TimerTask;

public class ConnectionManager extends TimerTask {
	
	private Map<InetSocketAddress, ObjectState> conns;

	public ConnectionManager(Map<InetSocketAddress, ObjectState> conns) {
		this.conns = conns;
	}

	@Override
	public void run() {
		
		System.out.println( "\nConnection\t\tRcvd\tSent" );
//		for ( InetSocketAddress adrs : conns.keySet() ) {
//			ObjectState us = conns.get( adrs );
//			System.out.println( adrs.getAddress() + ":" + adrs.getPort() + "\t" + 
//					us.getConnStats().getRcvd_from().intValue() + "\t" + 
//					us.getConnStats().getSent_to().intValue() + "\n" +
//					us.getUserStateMessage().toString() );
//		}

	}

}
