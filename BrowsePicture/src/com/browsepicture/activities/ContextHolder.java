package com.browsepicture.activities;

import android.content.Context;

public class ContextHolder
{
	private static ContextHolder instance;

	private final Context context;

	/**
	 * @param context
	 */
	private ContextHolder(final Context context)
	{
		this.context = context;
	}

	public static ContextHolder getInstance(final Context context)
	{
		if (instance == null)
			instance = new ContextHolder(context);
		return instance;
	}

	/**
	 * Can't be called before a call to getInstance(final Context context)!
	 * 
	 * @return ContextHolder
	 */
	public static ContextHolder getInstance()
	{
		return instance;
	}

	public Context getContext()
	{
		return this.context;
	}
}
