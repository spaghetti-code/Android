package com.browsepicture.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

import com.browsepicture.R;
import com.browsepicture.error.EError;
import com.browsepicture.error.ErrorUtil;
import com.browsepicture.shapes.EShape;

public class BrowsePicture extends Activity
{
	private static final int SELECT_PICTURE = 1;

	private static final int TAKE_PICTURE = 2;

	private static final int SHOW_PICTURE = 3;

	private String imagePath;

	private Uri photoUri;

	private RadioButton squareBtn;

	private RadioButton cubeBtn;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ContextHolder.getInstance(this);

		this.squareBtn = (RadioButton) findViewById(R.id.squareBtn);
		this.cubeBtn = (RadioButton) findViewById(R.id.cubeBtn);
		this.squareBtn.setChecked(true);

		((Button) findViewById(R.id.button1))
				.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						selectPicture();
					}

					private void selectPicture()
					{
						final Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(Intent.createChooser(intent,
								getString(R.string.select_picture)),
								SELECT_PICTURE);
					}
				});

		((Button) findViewById(R.id.button2))
				.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						takePicture();
					}

					private void takePicture()
					{
						//define the file-name to save photo taken by Camera activity
						final String fileName = "new-photo-name.jpg";
						//create parameters for Intent with filename
						final ContentValues values = new ContentValues();
						values.put(MediaStore.Images.Media.TITLE, fileName);
						values.put(MediaStore.Images.Media.DESCRIPTION,
								"Image capture by camera");
						//imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
						BrowsePicture.this.photoUri = getContentResolver()
								.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										values);
						//create new Intent
						final Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								BrowsePicture.this.photoUri);
						intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
						startActivityForResult(intent, TAKE_PICTURE);
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			if (requestCode == SELECT_PICTURE)
			{
				final Uri selectedImageUri = data.getData();
				showPicture(selectedImageUri);
			} else if (requestCode == TAKE_PICTURE)
			{
				showPicture(this.photoUri);
			}
		} else if (resultCode == RESULT_CANCELED)
		{
			// FIXME solta erro!! parece que o intent "data" perde o Map com o erro! 
			ErrorUtil.showError(data);
		}
	}

	/**
	 * @param selectedImageUri
	 */
	private void showPicture(final Uri selectedImageUri)
	{
		this.imagePath = getPath(selectedImageUri);

		if (this.imagePath == null)
			ErrorUtil.showError(EError.IMAGE_NOT_FOUND);
		else
		{
			final Intent intent = new Intent(BrowsePicture.this,
					ShowPicture.class);
			intent.putExtra(IExtraNames.IMAGE_PATH, this.imagePath);

			Parcelable shape = null;
			if (this.squareBtn.isChecked())
				shape = EShape.SQUARE;
			else if (this.cubeBtn.isChecked())
				shape = EShape.CUBE;
			intent.putExtra(IExtraNames.SHAPE, shape);

			BrowsePicture.this.startActivityForResult(intent, SHOW_PICTURE);
		}
	}

	/**
	 * @param uri
	 * @return path
	 */
	private String getPath(Uri uri)
	{
		final String[] projection = { MediaStore.Images.Media.DATA };
		final Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null)
		{
			final int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return null;
	}

}