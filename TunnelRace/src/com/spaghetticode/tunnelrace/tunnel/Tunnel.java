package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.spaghetticode.tunnelrace.R;

public class Tunnel
{
	private final Passage passage;

	private final Bend bend;

	public Tunnel(final int texture1, final int texture2)
	{
		this.passage = new Passage(10, R.drawable.arrows_and_bricks,
				R.drawable.arrows_and_bricks);
		this.bend = new Bend(40, -1, R.drawable.steel, R.drawable.steel);
	}

	public void draw(GL10 gl)
	{
		gl.glPushMatrix();
		this.passage.draw(gl);
		this.bend.draw(gl);
		gl.glPopMatrix();
	}

	public void loadTexture(GL10 gl, Context context)
	{
		this.passage.loadTexture(gl, context);
		this.bend.loadTexture(gl, context);
	}

	public void setZStart(final float zStart)
	{
		this.passage.setZStart(zStart);
		this.bend.setZStart(zStart + this.passage.getZEnd());
	}

	public SectionSequence getSectionSequence(final float z)
	{
		if (this.passage.isThisSection(z))
			return this.passage;
		else if (this.bend.isThisSection(z))
			return this.bend;
		return null;
	}

	public SectionKind getSectionKind(final float z)
	{
		final SectionSequence sectionSequence = getSectionSequence(z);
		if (sectionSequence != null)
			return sectionSequence.getKind();
		return SectionKind.PASSAGE; // FIXME para não dar null pointer
	}

	public int getSectionIndex(final float z)
	{
		final SectionSequence sectionSequence = getSectionSequence(z);
		if (sectionSequence != null)
			return sectionSequence.getSectionIndex(z);
		return 1; // FIXME para não dar problemas
	}
}