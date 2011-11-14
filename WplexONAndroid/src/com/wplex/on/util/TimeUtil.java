package com.wplex.on.util;

public class TimeUtil
{
	public static String convertTimeToString(final Short time)
	{
		final int hours = time / 60;
		final int minutes = time % 60;
		final String stringHours = (hours > 9 ? String.valueOf(hours) : "0"
				+ String.valueOf(hours));
		final String stringMinutes = (minutes > 9 ? String.valueOf(minutes)
				: "0" + String.valueOf(minutes));
		return stringHours + ":" + stringMinutes;
	}
}
