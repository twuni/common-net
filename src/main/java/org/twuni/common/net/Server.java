package org.twuni.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.LoggerFactory;
import org.twuni.common.Factory;
import org.twuni.common.log.NamedLogger;

public class Server extends Thread {

	private final NamedLogger log;

	private final int port;
	private final Factory<? extends Thread> workerThreadFactory;

	public Server( int port, Factory<? extends Thread> workerThreadFactory ) {
		super( String.format( "[%s] [%s]", workerThreadFactory.getClass().getName(), Integer.valueOf( port ) ) );
		this.log = new NamedLogger( LoggerFactory.getLogger( getClass() ), getName() );
		this.port = port;
		this.workerThreadFactory = workerThreadFactory;
	}

	@Override
	public void run() {

		try {

			ServerSocket server = new ServerSocket( port );
			log.info( "Server started." );

			while( server.isBound() ) {

				try {

					Socket socket = server.accept();
					Thread worker = workerThreadFactory.createInstance( socket );
					worker.start();

				} catch( IOException exception ) {
					log.warn( "An error occurred while attempting to accept a connection.", exception );
				}

			}

		} catch( IOException exception ) {
			log.error( String.format( "Unable to bind to port %s", Integer.valueOf( port ) ), exception );
		}

	}

}
