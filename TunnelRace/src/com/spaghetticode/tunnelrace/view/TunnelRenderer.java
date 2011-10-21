package com.spaghetticode.tunnelrace.view;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.BasicGLRenderer;
import com.spaghetticode.tunnelrace.R;
import com.spaghetticode.tunnelrace.command.SpeedTouchListener;
import com.spaghetticode.tunnelrace.tunnel.Passage;
import com.spaghetticode.tunnelrace.tunnel.Ship;

public class TunnelRenderer extends BasicGLRenderer
{
	private final SpeedTouchListener listener;

	private final Passage passage;

	private final Ship ship;

	private float z = 0.0f;

	public TunnelRenderer(final Context context,
			final SpeedTouchListener listener)
	{
		super(context);
		this.passage = new Passage(40, R.drawable.arrows_and_bricks,
				R.drawable.steel);
		this.ship = new Ship();
		this.listener = listener;
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		gl.glTranslatef(0.0f, -2.5f, this.z);

		this.z += this.listener.getSpeed();
		if (this.z < -4.0f)
		{
			this.z = -4.0f;
			this.listener.breakSpeed();
		} else if (this.z > 66.0f)
		{
			this.z = 66.0f;
			this.listener.breakSpeed();
		}

		this.passage.draw(gl);

		final float s = -6.0f - this.z;
		gl.glTranslatef(0.0f, 1.0f, s);
		gl.glRotatef(-45, 1.0f, 0.0f, 0.0f);
		this.ship.draw(gl);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);
		this.passage.loadTexture(gl, this.context);
		gl.glEnable(GL10.GL_TEXTURE_2D);
	}
}
