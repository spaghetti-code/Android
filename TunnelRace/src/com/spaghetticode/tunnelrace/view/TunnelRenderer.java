package com.spaghetticode.tunnelrace.view;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.BasicGLRenderer;
import com.spaghetticode.tunnelrace.R;
import com.spaghetticode.tunnelrace.command.SpeedTouchListener;
import com.spaghetticode.tunnelrace.tunnel.Ship;
import com.spaghetticode.tunnelrace.tunnel.Tunnel;

public class TunnelRenderer extends BasicGLRenderer
{
	private final SpeedTouchListener listener;

	private final Tunnel tunnel;

	private final Ship ship;

	private float z = 0.0f;

	private float r = 0.0f;

	public TunnelRenderer(final Context context,
			final SpeedTouchListener listener)
	{
		super(context);
		this.tunnel = new Tunnel(R.drawable.arrows_and_bricks, R.drawable.steel);
		this.ship = new Ship();
		this.listener = listener;
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		if (this.z > 70)
		{
			if (this.listener.getSpeed() > 0)
				this.r += 0.3f;
			else if (this.listener.getSpeed() < 0)
				this.r -= 0.3f;
		} else
			this.r = 0.0f;

		gl.glRotatef(this.r, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, -2.5f, this.z);

		this.z += this.listener.getSpeed();
		if (this.z < -4.0f)
		{
			this.z = -4.0f;
			this.listener.breakSpeed();
		} else if (this.z > 100.0f)
		{
			this.z = 150.0f;
			this.listener.breakSpeed();
		}

		this.tunnel.draw(gl);

		//		final float s = -6.0f - this.z;
		//		gl.glTranslatef(0.0f, 1.0f, s);
		//		gl.glRotatef(-45, 1.0f, 0.0f, 0.0f);
		//		this.ship.draw(gl);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);
		this.tunnel.loadTexture(gl, this.context);
		gl.glEnable(GL10.GL_TEXTURE_2D);
	}
}
