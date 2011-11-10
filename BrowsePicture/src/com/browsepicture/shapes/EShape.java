package com.browsepicture.shapes;

import android.os.Parcel;
import android.os.Parcelable;

public enum EShape implements Parcelable
{
	SQUARE, CUBE;

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
