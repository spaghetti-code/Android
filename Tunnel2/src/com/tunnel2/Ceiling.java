package com.tunnel2;

public class Ceiling extends TunnelElement
{
	public Ceiling()
	{
		super(R.drawable.bricks, new float[] { 1.0f, 1.0f, // B. right-bottom
				1.0f, 0.0f, // D. right-top
				0.0f, 1.0f, // A. left-bottom
				0.0f, 0.0f // C. left-top
				});
	}

}
