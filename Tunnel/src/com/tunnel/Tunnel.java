package com.tunnel;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Tunnel
{
	private final int[] leftWalls = new int[1];
	private final int[] rightWalls = new int[1];
	private final int[] floors = new int[1];
	private final int[] ceilings = new int[1];
	private final Wall wall;
	private final Floor floor;
	private final Ceiling ceiling;

	public Tunnel()
	{
		this.leftWalls[0] = 6;
		this.rightWalls[0] = 6;
		this.floors[0] = 6;
		this.ceilings[0] = 6;
		this.wall = new Wall();
		this.floor = new Floor();
		this.ceiling = new Ceiling();
	}

	public void drawWall(GL10 gl)
	{
		this.wall.draw(gl);
	}

	public void drawFloor(GL10 gl)
	{
		this.floor.draw(gl);
	}

	public void drawCeiling(GL10 gl)
	{
		this.ceiling.draw(gl);
	}

	public void loadTextures(GL10 gl, Context context)
	{
		this.wall.loadTexture(gl, context);
		this.floor.loadTexture(gl, context);
		this.ceiling.loadTexture(gl, context);
	}

	public int getLeftWalls(int turnIndex)
	{
		return this.leftWalls[turnIndex];
	}

	public int getRightWalls(int turnIndex)
	{
		return this.rightWalls[turnIndex];
	}

	public int getFloors(int turnIndex)
	{
		return this.floors[turnIndex];
	}

	public int getCeilings(int turnIndex)
	{
		return this.ceilings[turnIndex];
	}
}
