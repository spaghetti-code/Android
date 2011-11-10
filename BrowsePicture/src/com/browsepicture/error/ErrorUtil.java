package com.browsepicture.error;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.Gravity;
import android.widget.Toast;

import com.browsepicture.activities.ContextHolder;
import com.browsepicture.activities.IExtraNames;

public class ErrorUtil
{
	/**
	 * @param error
	 */
	public static void showError(final EError error)
	{
		final Toast msg = Toast.makeText(ContextHolder.getInstance()
				.getContext(), error.getErrorMessage(), Toast.LENGTH_LONG);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
				msg.getYOffset() / 2);
		msg.show();
	}

	/**
	 * @param intent
	 */
	public static void showError(final Intent intent)
	{
		if (intent != null)
		{
			final EError error = intent.getParcelableExtra(IExtraNames.ERROR);
			if (error != null)
				ErrorUtil.showError(error);
		}
	}

	/**
	 * @param activity
	 * @param error
	 */
	public static void returnError(final Activity activity, final EError error)
	{
		final Intent resultIntent = new Intent();
		resultIntent.putExtra(IExtraNames.ERROR, (Parcelable) error);
		activity.setResult(Activity.RESULT_CANCELED, resultIntent);
		activity.finish();
	}
}
