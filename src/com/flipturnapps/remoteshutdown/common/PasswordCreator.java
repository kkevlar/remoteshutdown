package com.flipturnapps.remoteshutdown.common;
import java.util.Calendar;

import org.jasypt.util.password.BasicPasswordEncryptor;
public class PasswordCreator 
{
	public PasswordCreator() 
	{
		int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		int yr = Calendar.getInstance().get(Calendar.YEAR);
		int daycode = day*yr;
		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
		encryptor.encryptPassword();
	}

}
