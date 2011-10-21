package com.coordsconverter;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.gigio.utils.BasicGLRenderer;
import com.gigio.utils.GeometryUtils;
import com.gigio.utils.MatrixTrackingGL;

public class CoordsConverterActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		final CoordsView view = new CoordsView(this);
		view.setGLWrapper(new GLSurfaceView.GLWrapper()
		{
			@Override
			public GL wrap(GL gl)
			{
				return new MatrixTrackingGL(gl);
			}
		});

		setContentView(view);
	}

	private class CoordsView extends GLSurfaceView
	{
		private final CoordsRenderer renderer;

		public CoordsView(Context context)
		{
			super(context);
			this.renderer = new CoordsRenderer(context);
			setRenderer(this.renderer);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event)
		{
			final float touchX = event.getX();
			final float touchY = event.getY();

			switch (event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					this.renderer.setTouchX(touchX);
					this.renderer.setTouchY(touchY);
					break;
				case MotionEvent.ACTION_UP:
					this.renderer.setTouchX(-1);
					this.renderer.setTouchY(-1);
					break;
			}
			return true;
		}

		private class CoordsRenderer extends BasicGLRenderer
		{
			private final Point point;
			private boolean landscape;

			private float touchX;
			private float touchY;

			public CoordsRenderer(Context context)
			{
				super(context);
				this.point = new Point();
			}

			@Override
			public void onDrawFrame(GL10 gl)
			{
				super.onDrawFrame(gl);

				if (this.landscape)
					gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
				else
					gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

				float[] coords = convertScreenCoordsToWorldCoords(gl, 0, 0);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, 0, this.h / 2);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, 0, this.h);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, this.w / 2, 0);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, this.w / 2,
						this.h / 2);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, this.w / 2,
						this.h);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, this.w, 0);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, this.w,
						this.h / 2);
				this.point.draw(gl, coords[0], coords[1]);

				coords = convertScreenCoordsToWorldCoords(gl, this.w, this.h);
				this.point.draw(gl, coords[0], coords[1]);

				// touch
				if (this.touchX != -1)
				{
					gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
					coords = convertScreenCoordsToWorldCoords(gl, this.touchX,
							this.touchY);
					this.point.draw(gl, coords[0], -coords[1]);
				}

			}

			private float[] convertScreenCoordsToWorldCoords(GL10 gl,
					float screenX, float screenY)
			{
				return GeometryUtils.convertScreenCoordsToWorldCoords(gl,
						screenX, screenY, this.w, this.h);
			}

			@Override
			public void onSurfaceChanged(GL10 gl, int width, int height)
			{
				super.onSurfaceChanged(gl, width, height);
				this.landscape = (width > height);
			}

			public void setTouchX(float touchX)
			{
				this.touchX = touchX;
			}

			public void setTouchY(float touchY)
			{
				this.touchY = touchY;
			}
		}

	}
}