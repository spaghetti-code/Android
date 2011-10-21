package com.tunnel2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class TunnelView extends GLSurfaceView
{
	private TunnelRenderer tunnelRenderer;

	public TunnelView(Context context)
	{
		super(context);
		initView(context);
	}

	public TunnelView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context)
	{
		this.tunnelRenderer = new TunnelRenderer(context);
		setRenderer(this.tunnelRenderer);

		this.requestFocus();
		this.setFocusableInTouchMode(true);

		this.setOnTouchListener(this.tunnelRenderer);
	}

}
