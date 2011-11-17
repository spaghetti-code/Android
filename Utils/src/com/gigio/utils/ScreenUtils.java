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
		return getScreenHeight(context) > getScreenWidth(context);
	}

	/**
	 * @param context
	 * @return True se a tela estiver em modalidade Landscape
	 */
	public static boolean isLandscape(final Context context)
	{
		return getScreenHeight(context) < getScreenWidth(context);
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

	/**
	 * @param context
	 * @return Largura da tela
	 */
	public static int getScreenWidth(final Context context)
	{
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * @param context
	 * @return Altura da tela
	 */
	public static int getScreenHeight(final Context context)
	{
		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
