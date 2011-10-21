package com.spaghetticode.tunnelrace.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.spaghetticode.tunnelrace.command.SpeedTouchListener;

public class TunnelView extends GLSurfaceView
{
	private TunnelRenderer tunnelRenderer;

	private SpeedTouchListener listener;

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
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.listener = new SpeedTouchListener();
		this.setOnTouchListener(this.listener);

		this.tunnelRenderer = new TunnelRenderer(context, this.listener);
		setRenderer(this.tunnelRenderer);
	}

}
