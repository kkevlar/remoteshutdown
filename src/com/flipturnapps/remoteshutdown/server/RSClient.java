package com.flipturnapps.remoteshutdown.server;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;

import com.flipturnapps.kevinLibrary.net.ClientData;
import com.flipturnapps.kevinLibrary.net.KServer;

public class RSClient extends ClientData 
{
	private static int idCount = 0;
	private String ip;
	private int id;
	public RSClient(Socket socket, KServer<?> server) throws IOException 
	{
		super(socket, server);
		ip = socket.getInetAddress().getHostAddress().toString();
		id = idCount++;
		
	}

}
