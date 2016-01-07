package com.flipturnapps.remoteshutdown.server;

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
		setIp(socket.getInetAddress().getHostAddress().toString());
		setId(idCount++);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

}
