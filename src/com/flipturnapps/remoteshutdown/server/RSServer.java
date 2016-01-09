package com.flipturnapps.remoteshutdown.server;

import java.io.IOException;
import java.net.Socket;

import com.flipturnapps.kevinLibrary.net.KServer;

public class RSServer extends KServer<RSClient> 
{

	private String pass1;
	private String pass2;
	private ServerFrame frame;

	public RSServer(int port, String pass1, ServerFrame frame) throws IOException 
	{
		super(port);
		this.pass1 = pass1;
		this.pass2 = pass2;
		this.frame = frame;
	}

	@Override
	protected RSClient getNewClientData(Socket socket, KServer<RSClient> kServer) 
	{
		try {
			return new RSClient(socket, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void newMessage(String message, RSClient client) 
	{
		frame.output("\"" + message + "\"");
		if(message==null || message.equals(""))
			return;
		if(message.equals(pass1) || message.equals(pass2))
			try {
				shutDown();
			} catch (RuntimeException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

	@Override
	protected void newClient(RSClient data) 
	{
		frame.output("new Client");
		
	}
	public static void shutDown() throws RuntimeException, IOException {
	    String shutdownCommand;
	    String operatingSystem = System.getProperty("os.name").toLowerCase();

	    if (operatingSystem.contains("nux") || operatingSystem.contains("mac")) {
	        shutdownCommand = "shutdown -h now";
	    }
	    else if (operatingSystem.contains("win")) {
	        shutdownCommand = "shutdown.exe -s -t 0";
	    }
	    else {
	        throw new RuntimeException("Unsupported operating system.");
	    }

	    Runtime.getRuntime().exec(shutdownCommand);
	    System.exit(0);
	}

}
