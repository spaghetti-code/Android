package com.browsepicture.error;

import android.os.Parcel;
import android.os.Parcelable;

import com.browsepicture.R;
import com.browsepicture.activities.ContextHolder;
import com.browsepicture.shapes.EShape;

public enum EError implements Parcelable
{
	IMAGE_NOT_FOUND(R.string.error_image_not_found), // Image not Found!
	INVALID_SHAPE(R.string.error_invalid_shape); // Invalid Shape!

	private final String errorMessage;

	private EError(final int resourceId)
	{
		this.errorMessage = ContextHolder.getInstance().getContext()
				.getString(resourceId);
	}

	public String getErrorMessage()
	{
		return this.errorMessage;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags)
	{
		dest.writeInt(ordinal());
	}

	public static final Creator<EShape> CREATOR = new Creator<EShape>()
	{
		@Override
		public EShape createFromParcel(final Parcel source)
		{
			return EShape.values()[source.readInt()];
		}

		@Override
		public EShape[] newArray(final int size)
		{
			return new EShape[size];
		}
	};
}