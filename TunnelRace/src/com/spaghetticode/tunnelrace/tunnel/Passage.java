package com.spaghetticode.tunnelrace.tunnel;

import javax.microedition.khronos.opengles.GL10;

/**
 * Passagem reta do tunel.
 * 
 * @author Francesco Bertolino
 */
public class Passage extends SectionSequence
{
	/**
	 * @param sections
	 * @param texture1
	 * @param texture2
	 */
	public Passage(final int sections, final int texture1, final int texture2)
	{
		super(SectionKind.PASSAGE, sections, texture1, texture2, 2.0f);
	}

	@Override
	public void draw(GL10 gl)
	{
		for (int i = 0; i < this.nSections; i++)
		{
			gl.glTranslatef(0.0f, 0.0f, -this.translationStep);
			this.section.draw(gl);
		}
	}

}
