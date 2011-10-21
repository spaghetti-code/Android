package com.spaghetticode.tunnelrace.command;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SpeedTouchListener implements OnTouchListener
{
	private boolean accelerating = false;

	private boolean decelerating = false;

	private boolean breaking = false;

	private float speed = 0.0f;

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		final float x = event.getX();
		final float y = event.getY();

		final int upperArea = view.getHeight() / 10;
		final int lowerArea = view.getHeight() - upperArea;

		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (y > lowerArea)
			{
				if (x < (view.getWidth() / 4))
				{
					this.accelerating = true;
					this.decelerating = false;
					this.breaking = false;
				} else if (x > (view.getWidth() * 3 / 4))
				{
					this.accelerating = false;
					this.decelerating = true;
					this.breaking = false;
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (y > lowerArea)
			{
				if (x < (view.getWidth() / 4))
				{
					this.accelerating = false;
					this.breaking = true;
				} else if (x > (view.getWidth() * 3 / 4))
				{
					this.decelerating = false;
					this.breaking = true;
				}
			}
		}
		return true;
	}

	public float getSpeed()
	{
		if (this.accelerating && this.speed <= 1.0f)
			this.speed += 0.1f;
		else if (this.decelerating && this.speed >= -1.0f)
			this.speed -= 0.1f;
		else if (this.breaking)
			if (this.speed > 0.0f)
			{
				this.speed -= 0.05f;
				if (this.speed < 0.0f)
					this.speed = 0.0f;
			} else if (this.speed < 0.0f)
			{
				this.speed += 0.05f;
				if (this.speed > 0.0f)
					this.speed = 0.0f;
			}
		return this.speed;
	}

	public void breakSpeed()
	{
		this.accelerating = false;
		this.decelerating = false;
		this.breaking = true;
	}
}
