package com.flipturnapps.remoteshutdown.server;

import java.io.File;
import java.io.IOException;

import com.flipturnapps.kevinLibrary.helper.FileHelper;
import com.flipturnapps.remoteshutdown.common.PasswordCreator;

public class ServerMain {

	private String[] args;
	public static final String PORT_PREFIX = "port=";
	public static final String IP_PREFIX = "ip=";

	public static File getSaveFile()
	{
		String saveFileLocation = FileHelper.getAppDataDir("flipturnapps", "RemoteShutdown");
		File dir = new File(saveFileLocation);
		dir.mkdirs();
		File saveFile = new File(FileHelper.fileInDir(saveFileLocation, "server.txt"));
		return saveFile;
	}
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
		int passLength = password.length();
		boolean allowShowPassword = Boolean.parseBoolean(args[2]);
		long timeout = Long.parseLong(args[3]);
		boolean shutDownAfterTimeout = Boolean.parseBoolean(args[4]);
		PasswordCreator creator = new PasswordCreator();
		String pass1 = creator.createPassword(password);
		
		if (!allowShowPassword)
			password = null;
		ServerFrame frame = new ServerFrame(password, shutDownAfterTimeout, timeout, port, passLength);
		RSServer server;
		try {
			server = new RSServer(port,pass1,frame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setVisible(true);
	}

}
