package com.flipturnapps.remoteshutdown.server;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.flipturnapps.kevinLibrary.helper.FileHelper;
import com.flipturnapps.kevinLibrary.helper.TextFileHelper;
import com.flipturnapps.remoteshutdown.client.ClientFrame;
import com.flipturnapps.remoteshutdown.common.PasswordCreator;

public class ServerMain 
{

	public static final String PORT_PREFIX = "port=";
	public static final String HIDEPASS_PREFIX = "hide_pass=";
	public static final String TIMEOUTENABLED_PREFIX = "timeout_enabled=";
	public static final String TIMEOUT_PREFIX = "timeout_millis=";
	public static final String TIMEOUTACTION_PREFIX = "timeout_action=";

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
		File saveFile = getSaveFile();
		String[] lines = null;
		String port = null;
		boolean hidePass = true;
		boolean tmEnabled = true;
		long tmMillis = 120000;
		int tmAction = 0;
		if(saveFile.exists())
		{
			try {
				lines = TextFileHelper.getTextByLine(saveFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < lines.length; i++)
			{
				String line = lines[i];
				if(line.startsWith(PORT_PREFIX))
					port = line.substring(PORT_PREFIX.length());	
				if(line.startsWith(HIDEPASS_PREFIX))
					hidePass = Boolean.parseBoolean(line.substring(HIDEPASS_PREFIX.length()));	
				if(line.startsWith(TIMEOUTENABLED_PREFIX))
					tmEnabled = Boolean.parseBoolean(line.substring(TIMEOUTENABLED_PREFIX.length()));	
				if(line.startsWith(TIMEOUT_PREFIX))
					tmMillis = Long.parseLong(line.substring(TIMEOUT_PREFIX.length()));	
				if(line.startsWith(TIMEOUTACTION_PREFIX))
					tmAction = Integer.parseInt(line.substring(TIMEOUTACTION_PREFIX.length()));	
			}
		}
		new SetupFrame(port,hidePass,tmEnabled,tmMillis,tmAction).setVisible(true);
	}
	
	
	public static String millisToHMS(long millis) {
		String textUpdate = String.format("%02dh %02dm %02ds", 
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis) -  
				TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
				TimeUnit.MILLISECONDS.toSeconds(millis) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		return textUpdate;
	}

}
