package com.browsepicture.activities;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.browsepicture.error.EError;
import com.browsepicture.error.ErrorUtil;
import com.browsepicture.renderers.CubeRenderer;
import com.browsepicture.renderers.IPictureRenderer;
import com.browsepicture.renderers.SquareRenderer;
import com.browsepicture.shapes.EShape;
import com.gigio.utils.TextureUtils;

public class ShowPicture extends Activity
{
	private GLSurfaceView glView;
	private IPictureRenderer glRenderer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.glView = new GLSurfaceView(this);

		final Bitmap bitmap = loadBitmap(
				getIntent().getStringExtra(IExtraNames.IMAGE_PATH), 256, 256);
		if (bitmap == null)
			ErrorUtil.returnError(this, EError.IMAGE_NOT_FOUND);

		if (getIntent().getParcelableExtra(IExtraNames.SHAPE).equals(
				EShape.SQUARE))
			this.glRenderer = new SquareRenderer(this, bitmap);
		else if (getIntent().getParcelableExtra(IExtraNames.SHAPE).equals(
				EShape.CUBE))
			this.glRenderer = new CubeRenderer(this, bitmap);
		else
			ErrorUtil.returnError(this, EError.INVALID_SHAPE);

		this.glView.setRenderer(this.glRenderer);
		this.setContentView(this.glView);
	}

	/**
	 * @param path
	 * @param imageW
	 * @param imageH
	 * @return Bitmap
	 */
	private Bitmap loadBitmap(final String path, final int imageW,
			final int imageH)
	{
		final File imgFile = new File(path);
		if (imgFile.exists())
			return TextureUtils.getResizedBitmap(
					BitmapFactory.decodeFile(imgFile.getAbsolutePath()),
					imageW, imageH);
		return null;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		this.glView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		this.glView.onResume();
	}
}