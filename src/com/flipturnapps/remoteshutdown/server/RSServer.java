package com.flipturnapps.remoteshutdown.server;

import java.io.IOException;
import java.net.Socket;

import com.flipturnapps.kevinLibrary.net.KServer;

public class RSServer extends KServer<RSClient> 
{

	public RSServer(int port) throws IOException 
	{
		super(port);
	}

	@Override
	protected RSClient getNewClientData(Socket socket, KServer<RSClient> kServer) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void newMessage(String message, RSClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void newClient(RSClient data) {
		// TODO Auto-generated method stub
		
	}

}
