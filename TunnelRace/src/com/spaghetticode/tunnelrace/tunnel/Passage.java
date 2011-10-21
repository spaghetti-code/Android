package com.spaghetticode.tunnelrace.tunnel;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * Passagem do tunel, constituida de uma sequência de
 * seções.
 * 
 * TODO testar desempenho redesenhando várias vezes uma única seção.
 * 
 * @author Francesco Bertolino
 */
public class Passage
{
	private final List<Section> sections;

	public Passage(final int sections, final int texture1, final int texture2)
	{
		this.sections = new ArrayList<Section>(sections);
		for (int i = 0; i < sections; i++)
			this.sections.add(new Section(texture1, texture2));
	}

	public void draw(GL10 gl)
	{
		Section section;
		for (int i = 0; i < this.sections.size(); i++)
		{
			section = this.sections.get(i);
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, 0.0f, -2.0f * i);
			section.draw(gl);
			gl.glPopMatrix();
		}
	}

	public void loadTexture(GL10 gl, Context context)
	{
		for (final Section section : this.sections)
			section.loadTexture(gl, context);
	}
}
