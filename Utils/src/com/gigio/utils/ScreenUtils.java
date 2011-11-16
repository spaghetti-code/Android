package com.gigio.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ScreenUtils
{
	/**
	 * @param context
	 * @return True se a tela estiver em modalidade Portrait
	 */
	public static boolean isPortrait(final Context context)
	{
		final DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		return displayMetrics.heightPixels > displayMetrics.widthPixels;
	}

	/**
	 * @param context
	 * @return True se a tela estiver em modalidade Landscape
	 */
	public static boolean isLandscape(final Context context)
	{
		final DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		return displayMetrics.heightPixels < displayMetrics.widthPixels;
	}

	/**
	 * @param context
	 * @param dipValue
	 * @return Conversão em Pixels do valor DIP (Device Independent Pixels) informado
	 */
	public static float convertFromDPIToPixels(final Context context,
			final float dipValue)
	{
		final DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
				displayMetrics);
	}
}
