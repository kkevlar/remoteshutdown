package com.flipturnapps.remoteshutdown.client;

import java.io.File;
import java.io.IOException;

import com.flipturnapps.kevinLibrary.helper.FileHelper;
import com.flipturnapps.kevinLibrary.helper.TextFileHelper;

public class ClientMain
{

	public static final String PORT_PREFIX = "port=";
	public static final String IP_PREFIX = "ip=";

	public static File getSaveFile()
	{
		String saveFileLocation = FileHelper.getAppDataDir("flipturnapps", "RemoteShutdown");
		File dir = new File(saveFileLocation);
		dir.mkdirs();
		File saveFile = new File(FileHelper.fileInDir(saveFileLocation, "client.txt"));
		return saveFile;
	}

	public static void main(String[] args)
	{
		File saveFile = getSaveFile();
		String[] lines = null;
		String ip = null;
		String port = null;
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
				if(line.startsWith(IP_PREFIX))
					ip = line.substring(IP_PREFIX.length());
				if(line.startsWith(PORT_PREFIX))
					port = line.substring(PORT_PREFIX.length());					
			}
		}
		new ClientFrame(ip,port).setVisible(true);

	}

}
