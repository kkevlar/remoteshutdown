package com.flipturnapps.remoteshutdown.common;
import java.util.Calendar;

import org.jasypt.util.password.BasicPasswordEncryptor;
public class PasswordCreator 
{
	private BasicPasswordEncryptor encryptor;
	private static final String SALT= "zSALTz";;
	public PasswordCreator() 
	{
		encryptor = new BasicPasswordEncryptor();
	}
	public String createPassword(String password , int dayOffset)
	{
		int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)+dayOffset;
		int yr = Calendar.getInstance().get(Calendar.YEAR);
		int daycode = day*yr;
		return encryptor.encryptPassword(SALT + daycode + SALT + password + SALT);
	}

}
