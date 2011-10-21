package com.tunnel2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gigio.utils.MovementRenderer;

public class TunnelRenderer extends MovementRenderer
{
	private final Tunnel tunnel;

	public TunnelRenderer(final Context context)
	{
		super(context);
		this.tunnel = new Tunnel();
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		// ajuste para começar "dentro do tunel"
		gl.glTranslatef(0.0f, 0.0f, 3.0f);

		// parede de trás
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, -2.2f);
		this.tunnel.drawStoneWall(gl);
		gl.glPopMatrix();

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
		float z = 0.0f;
		final int ceilings = this.tunnel.getCeilings(0);
		for (int c = 0; c < ceilings; c++)
		{
			gl.glPushMatrix();
			z = -3 - ((1.9f * c) + 0.2f);
			gl.glTranslatef(0.0f, 1.0f - 0.005f * c, z);
			gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
			this.tunnel.drawCeiling(gl);
			gl.glPopMatrix();
		}

		// parede da frente
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, z);
		this.tunnel.drawStoneWall(gl);
		gl.glPopMatrix();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);

		this.tunnel.loadTextures(gl, this.context);
		gl.glEnable(GL10.GL_TEXTURE_2D);
	}
}
