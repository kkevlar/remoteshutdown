package com.flipturnapps.remoteshutdown.server;

import java.io.IOException;

import com.flipturnapps.remoteshutdown.common.PasswordCreator;

public class ServerMain {

	private String[] args;

	public static void main(String args[])
	{
		new ServerMain(args).createServerFromArgs();
	}
	public ServerMain(String[] args) 
	{
		this.args = args;
	}
	public void createServerFromArgs()
	{
		int port = Integer.parseInt(args[0]);
		String password = args[1];
		int dateAllowance = Integer.parseInt(args[2]);
		boolean allowShowPassword = Boolean.parseBoolean(args[3]);
		long timeout = Long.parseLong(args[4]);
		boolean shutDownAfterTimeout = Boolean.parseBoolean(args[5]);
		PasswordCreator creator = new PasswordCreator();
		String pass1;
		String pass2 = null;
		if (dateAllowance < 2)
			pass1 = creator.createPassword(password, dateAllowance);
		else
		{
			pass1 = creator.createPassword(password, 0);
			pass2 = creator.createPassword(password, 1);
		}
		try {
			RSServer server = new RSServer(port,pass1,pass2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!allowShowPassword)
			password = null;
		ServerFrame frame = new ServerFrame(password, shutDownAfterTimeout, timeout);
		frame.setVisible(true);
	}

}
