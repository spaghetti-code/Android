package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * Sequência de seções do tunel.
 * 
 * @author Francesco Bertolino
 */
public abstract class SectionSequence
{
	private final SectionKind kind;

	protected final Section section;

	protected final int nSections;

	protected float zStart;

	protected float zEnd;

	/**
	 * Traslação entre uma seção e a sucessiva
	 */
	protected final float translationStep;

	public SectionSequence(final SectionKind kind, final int nSections,
			final int texture1, final int texture2, final float translationStep)
	{
		this.kind = kind;
		this.section = new Section(texture1, texture2);
		this.nSections = nSections;
		this.translationStep = translationStep;
	}

	public void loadTexture(GL10 gl, Context context)
	{
		this.section.loadTexture(gl, context);
	}

	public abstract void draw(GL10 gl);

	public float getZStart()
	{
		return this.zStart;
	}

	public float getZEnd()
	{
		return this.zStart - ((this.nSections - 1) * this.translationStep);
	}

	public void setZStart(float zStart)
	{
		this.zStart = zStart;
	}

	/**
	 * @param z
	 * @return Índice (1 based) da seção correspondente ao z informado.
	 */
	public int getSectionIndex(final float z)
	{
		final float deltaZ = Math.abs(z - getZStart());
		return (int) (deltaZ / this.translationStep) + 1;
	}

	public SectionKind getKind()
	{
		return this.kind;
	}

	public boolean isThisSection(final float z)
	{
		return (z <= getZStart() && z >= getZEnd());
	}
}
