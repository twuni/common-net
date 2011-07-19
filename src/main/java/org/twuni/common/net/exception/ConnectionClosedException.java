package org.twuni.common.net.exception;

import java.io.IOException;

public class ConnectionClosedException extends IOException {

	public ConnectionClosedException() {
		super( "Connection closed." );
	}

	public ConnectionClosedException( String message ) {
		super( message );
	}

}
