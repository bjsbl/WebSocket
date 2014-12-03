package com.socket;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {

	public static void info(String message) {
		System.out.println(getCurrentDate() + ":" + message);
	}

	private static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

}
