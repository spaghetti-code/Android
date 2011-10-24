package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

/**
 * Curva do tunel.
 * 
 * @author Francesco Bertolino
 */
public class Bend extends SectionSequence
{
	/**
	 * Direção da curva: 1 = direita, -1 = esquerda
	 */
	private final int direction;

	/**
	 * Graus de rotação de cada seção respeito à anterior
	 */
	private final float ROTATION_DEGREES;

	/**
	 * @param nSections
	 * @param direction
	 * @param texture1
	 * @param texture2
	 */
	public Bend(final int nSections, final int direction, final int texture1,
			final int texture2)
	{
		super(SectionKind.BEND, nSections, texture1, texture2, 1.7f);
		this.direction = direction;
		this.ROTATION_DEGREES = 2 * this.direction;
	}

	@Override
	public void draw(GL10 gl)
	{
		for (int i = 0; i < this.nSections; i++)
		{
			gl.glTranslatef(0.0f, 0.0f, -this.translationStep);
			gl.glRotatef(this.ROTATION_DEGREES, 0.0f, 1.0f, 0.0f);
			this.section.draw(gl);
		}
	}

	// TODO!
	//	public float getRotationAngle(final float z)
	//	{
	//
	//	}
}
