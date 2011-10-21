package com.gigio.utils;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MovementRenderer extends BasicGLRenderer implements
		OnTouchListener
{
	private final float piover180 = 0.0174532925f;
	private float heading;
	private float xpos;
	private float zpos;
	private float yrot; //Y Rotation
	private float walkbias = 0;
	private float walkbiasangle = 0;
	private float lookupdown = 0.0f;

	/* Variables and factor for the input handler */
	private float oldX;
	private float oldY;
	private final float TOUCH_SCALE = 0.2f;

	boolean moveForward = false;
	boolean moveBackward = false;

	public MovementRenderer(Context context)
	{
		super(context);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		super.onDrawFrame(gl);

		if (this.moveForward)
			walkForward();
		else if (this.moveBackward)
			walkBackward();

		final float xtrans = -this.xpos; //Used For Player Translation On The X Axis
		final float ztrans = -this.zpos; //Used For Player Translation On The Z Axis
		final float ytrans = -this.walkbias - 0.25f; //Used For Bouncing Motion Up And Down
		final float sceneroty = 360.0f - this.yrot; //360 Degree Angle For Player Direction

		//View
		gl.glRotatef(this.lookupdown, 1.0f, 0, 0); //Rotate Up And Down To Look Up And Down
		gl.glRotatef(sceneroty, 0, 1.0f, 0); //Rotate Depending On Direction Player Is Facing
		gl.glTranslatef(xtrans, ytrans, ztrans); //Translate The Scene Based On Player Position
	}

	void walkForward()
	{
		this.xpos -= (float) Math.sin(this.heading * this.piover180) * 0.05f; //Move On The X-Plane Based On Player Direction
		this.zpos -= (float) Math.cos(this.heading * this.piover180) * 0.05f; //Move On The Z-Plane Based On Player Direction

		if (this.walkbiasangle >= 359.0f)
		{ //Is walkbiasangle>=359?
			this.walkbiasangle = 0.0f; //Make walkbiasangle Equal 0
		} else
		{
			this.walkbiasangle += 10; //If walkbiasangle < 359 Increase It By 10
		}
		this.walkbias = (float) Math.sin(this.walkbiasangle * this.piover180) / 20.0f; //Causes The Player To Bounce
	}

	void walkBackward()
	{
		this.xpos += (float) Math.sin(this.heading * this.piover180) * 0.05f; //Move On The X-Plane Based On Player Direction
		this.zpos += (float) Math.cos(this.heading * this.piover180) * 0.05f; //Move On The Z-Plane Based On Player Direction

		if (this.walkbiasangle <= 1.0f)
		{ //Is walkbiasangle<=1?
			this.walkbiasangle = 359.0f; //Make walkbiasangle Equal 359
		} else
		{
			this.walkbiasangle -= 10; //If walkbiasangle > 1 Decrease It By 10
		}
		this.walkbias = (float) Math.sin(this.walkbiasangle * this.piover180) / 20.0f; //Causes The Player To Bounce
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		final float x = event.getX();
		final float y = event.getY();

		//Define an upper area of 10% to define a lower area
		final int upperArea = view.getHeight() / 10;
		final int lowerArea = view.getHeight() - upperArea;

		//A press on the screen
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (y > lowerArea)
			{
				if (x < (view.getWidth() / 4))
				{
					this.moveBackward = false;
					this.moveForward = false;
				} else if (x > (view.getWidth() * 3 / 4))
				{
					this.moveBackward = false;
					this.moveForward = false;
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (y > lowerArea)
			{
				if (x < (view.getWidth() / 4))
				{
					this.moveBackward = false;
					this.moveForward = true;
				} else if (x > (view.getWidth() * 3 / 4))
				{
					this.moveBackward = true;
					this.moveForward = false;
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			//Calculate the change
			final float dx = x - this.oldX;
			final float dy = y - this.oldY;

			//Up and down looking through touch
			this.lookupdown -= dy * this.TOUCH_SCALE;
			//Look left and right through moving on screen
			this.heading += dx * this.TOUCH_SCALE;
			this.yrot = this.heading;
		}

		//Remember the values
		this.oldX = x;
		this.oldY = y;

		return true;
	}

}
