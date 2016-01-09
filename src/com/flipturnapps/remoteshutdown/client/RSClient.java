package com.flipturnapps.remoteshutdown.client;

import java.io.IOException;
import java.net.UnknownHostException;

import com.flipturnapps.kevinLibrary.net.KClient;

public class RSClient extends KClient
{

	public RSClient(String ip, int port) throws UnknownHostException, IOException 
	{
		super(ip, port);
	}

	@Override
	protected void disconnectedFromServer() 
	{
		
	}

	@Override
	protected void readMessage(String read) 
	{
		
	}
}
