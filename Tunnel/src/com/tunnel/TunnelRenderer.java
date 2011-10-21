package com.tunnel;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.BasicGLRenderer;

public class TunnelRenderer extends BasicGLRenderer
{
	private final Tunnel tunnel;
	private float z = 1.0f;

	// private final float[] lightAmbient = { 0.5f, 0.5f, 0.5f, 1.0f };
	// private final float[] lightDiffuse = { 1.0f, 0.0f, -10.0f, 1.0f };
	// private final float[] lightPosition = { 0.0f, 0.0f, 0.0f, 1.0f };

	public TunnelRenderer(final Context context)
	{
		super(context);
		this.tunnel = new Tunnel();
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		// go forward/back
		gl.glTranslatef(0.0f, 0.0f, this.z);

		// left walls
		final int leftWalls = this.tunnel.getLeftWalls(0);
		for (int w = 0; w < leftWalls; w++)
		{
			gl.glPushMatrix();
			gl.glTranslatef(-1.0f + 0.005f * w, 0.0f, -3 - ((1.9f * w) + 0.2f));
			gl.glRotatef(270, 0.0f, 1.0f, 0.0f);
			this.tunnel.drawWall(gl);
			gl.glPopMatrix();
		}

		// right walls
		final int rightWalls = this.tunnel.getRightWalls(0);
		for (int w = 0; w < rightWalls; w++)
		{
			gl.glPushMatrix();
			gl.glTranslatef(1.0f - 0.005f * w, 0.0f, -3 - ((1.9f * w) + 0.2f));
			gl.glRotatef(-270, 0.0f, 1.0f, 0.0f);
			this.tunnel.drawWall(gl);
			gl.glPopMatrix();
		}

		// floors
		final int floors = this.tunnel.getFloors(0);
		for (int f = 0; f < floors; f++)
		{
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, -1.0f + 0.005f * f, -3 - ((1.9f * f) + 0.2f));
			gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
			this.tunnel.drawFloor(gl);
			gl.glPopMatrix();
		}

		// ceilings
		final int ceilings = this.tunnel.getCeilings(0);
		for (int c = 0; c < ceilings; c++)
		{
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, 1.0f - 0.005f * c, -3 - ((1.9f * c) + 0.2f));
			gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
			this.tunnel.drawCeiling(gl);
			gl.glPopMatrix();
		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);
		
		this.tunnel.loadTextures(gl, this.context);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		// gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, this.lightAmbient, 0);
		// gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, this.lightDiffuse, 0);
		// gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, this.lightPosition,
		// 0);
		// gl.glEnable(GL10.GL_LIGHT1);
		// gl.glEnable(GL10.GL_LIGHT0);
		// gl.glEnable(GL10.GL_LIGHTING);
	}

	void forward()
	{
		this.z += 1f;
	}

	void back()
	{
		this.z -= 1f;
	}
}
