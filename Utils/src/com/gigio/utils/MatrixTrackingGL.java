/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigio.utils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL10Ext;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.util.Log;

/**
 * Allows retrieving the current matrix even if the current OpenGL ES
 * driver does not support retrieving the current matrix.
 *
 * Note: the actual matrix may differ from the retrieved matrix, due
 * to differences in the way the math is implemented by GLMatrixWrapper
 * as compared to the way the math is implemented by the OpenGL ES
 * driver.
 */
public class MatrixTrackingGL implements GL, GL10, GL10Ext, GL11, GL11Ext
{
	private final GL10 mgl;
	private GL10Ext mgl10Ext;
	private GL11 mgl11;
	private GL11Ext mgl11Ext;
	private int mMatrixMode;
	private MatrixStack mCurrent;
	private final MatrixStack mModelView;
	private final MatrixStack mTexture;
	private final MatrixStack mProjection;

	private final static boolean _check = false;
	ByteBuffer mByteBuffer;
	FloatBuffer mFloatBuffer;
	float[] mCheckA;
	float[] mCheckB;

	public MatrixTrackingGL(GL gl)
	{
		this.mgl = (GL10) gl;
		if (gl instanceof GL10Ext)
		{
			this.mgl10Ext = (GL10Ext) gl;
		}
		if (gl instanceof GL11)
		{
			this.mgl11 = (GL11) gl;
		}
		if (gl instanceof GL11Ext)
		{
			this.mgl11Ext = (GL11Ext) gl;
		}
		this.mModelView = new MatrixStack();
		this.mProjection = new MatrixStack();
		this.mTexture = new MatrixStack();
		this.mCurrent = this.mModelView;
		this.mMatrixMode = GL10.GL_MODELVIEW;
	}

	// ---------------------------------------------------------------------
	// GL10 methods:

	@Override
	public void glActiveTexture(int texture)
	{
		this.mgl.glActiveTexture(texture);
	}

	@Override
	public void glAlphaFunc(int func, float ref)
	{
		this.mgl.glAlphaFunc(func, ref);
	}

	@Override
	public void glAlphaFuncx(int func, int ref)
	{
		this.mgl.glAlphaFuncx(func, ref);
	}

	@Override
	public void glBindTexture(int target, int texture)
	{
		this.mgl.glBindTexture(target, texture);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor)
	{
		this.mgl.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glClear(int mask)
	{
		this.mgl.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha)
	{
		this.mgl.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearColorx(int red, int green, int blue, int alpha)
	{
		this.mgl.glClearColorx(red, green, blue, alpha);
	}

	@Override
	public void glClearDepthf(float depth)
	{
		this.mgl.glClearDepthf(depth);
	}

	@Override
	public void glClearDepthx(int depth)
	{
		this.mgl.glClearDepthx(depth);
	}

	@Override
	public void glClearStencil(int s)
	{
		this.mgl.glClearStencil(s);
	}

	@Override
	public void glClientActiveTexture(int texture)
	{
		this.mgl.glClientActiveTexture(texture);
	}

	@Override
	public void glColor4f(float red, float green, float blue, float alpha)
	{
		this.mgl.glColor4f(red, green, blue, alpha);
	}

	@Override
	public void glColor4x(int red, int green, int blue, int alpha)
	{
		this.mgl.glColor4x(red, green, blue, alpha);
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue,
			boolean alpha)
	{
		this.mgl.glColorMask(red, green, blue, alpha);
	}

	@Override
	public void glColorPointer(int size, int type, int stride, Buffer pointer)
	{
		this.mgl.glColorPointer(size, type, stride, pointer);
	}

	@Override
	public void glCompressedTexImage2D(int target, int level,
			int internalformat, int width, int height, int border,
			int imageSize, Buffer data)
	{
		this.mgl.glCompressedTexImage2D(target, level, internalformat, width,
				height, border, imageSize, data);
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int width, int height, int format, int imageSize,
			Buffer data)
	{
		this.mgl.glCompressedTexSubImage2D(target, level, xoffset, yoffset,
				width, height, format, imageSize, data);
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalformat,
			int x, int y, int width, int height, int border)
	{
		this.mgl.glCopyTexImage2D(target, level, internalformat, x, y, width,
				height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int x, int y, int width, int height)
	{
		this.mgl.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y,
				width, height);
	}

	@Override
	public void glCullFace(int mode)
	{
		this.mgl.glCullFace(mode);
	}

	@Override
	public void glDeleteTextures(int n, int[] textures, int offset)
	{
		this.mgl.glDeleteTextures(n, textures, offset);
	}

	@Override
	public void glDeleteTextures(int n, IntBuffer textures)
	{
		this.mgl.glDeleteTextures(n, textures);
	}

	@Override
	public void glDepthFunc(int func)
	{
		this.mgl.glDepthFunc(func);
	}

	@Override
	public void glDepthMask(boolean flag)
	{
		this.mgl.glDepthMask(flag);
	}

	@Override
	public void glDepthRangef(float near, float far)
	{
		this.mgl.glDepthRangef(near, far);
	}

	@Override
	public void glDepthRangex(int near, int far)
	{
		this.mgl.glDepthRangex(near, far);
	}

	@Override
	public void glDisable(int cap)
	{
		this.mgl.glDisable(cap);
	}

	@Override
	public void glDisableClientState(int array)
	{
		this.mgl.glDisableClientState(array);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count)
	{
		this.mgl.glDrawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, Buffer indices)
	{
		this.mgl.glDrawElements(mode, count, type, indices);
	}

	@Override
	public void glEnable(int cap)
	{
		this.mgl.glEnable(cap);
	}

	@Override
	public void glEnableClientState(int array)
	{
		this.mgl.glEnableClientState(array);
	}

	@Override
	public void glFinish()
	{
		this.mgl.glFinish();
	}

	@Override
	public void glFlush()
	{
		this.mgl.glFlush();
	}

	@Override
	public void glFogf(int pname, float param)
	{
		this.mgl.glFogf(pname, param);
	}

	@Override
	public void glFogfv(int pname, float[] params, int offset)
	{
		this.mgl.glFogfv(pname, params, offset);
	}

	@Override
	public void glFogfv(int pname, FloatBuffer params)
	{
		this.mgl.glFogfv(pname, params);
	}

	@Override
	public void glFogx(int pname, int param)
	{
		this.mgl.glFogx(pname, param);
	}

	@Override
	public void glFogxv(int pname, int[] params, int offset)
	{
		this.mgl.glFogxv(pname, params, offset);
	}

	@Override
	public void glFogxv(int pname, IntBuffer params)
	{
		this.mgl.glFogxv(pname, params);
	}

	@Override
	public void glFrontFace(int mode)
	{
		this.mgl.glFrontFace(mode);
	}

	@Override
	public void glFrustumf(float left, float right, float bottom, float top,
			float near, float far)
	{
		this.mCurrent.glFrustumf(left, right, bottom, top, near, far);
		this.mgl.glFrustumf(left, right, bottom, top, near, far);
		if (_check)
			check();
	}

	@Override
	public void glFrustumx(int left, int right, int bottom, int top, int near,
			int far)
	{
		this.mCurrent.glFrustumx(left, right, bottom, top, near, far);
		this.mgl.glFrustumx(left, right, bottom, top, near, far);
		if (_check)
			check();
	}

	@Override
	public void glGenTextures(int n, int[] textures, int offset)
	{
		this.mgl.glGenTextures(n, textures, offset);
	}

	@Override
	public void glGenTextures(int n, IntBuffer textures)
	{
		this.mgl.glGenTextures(n, textures);
	}

	@Override
	public int glGetError()
	{
		final int result = this.mgl.glGetError();
		return result;
	}

	@Override
	public void glGetIntegerv(int pname, int[] params, int offset)
	{
		this.mgl.glGetIntegerv(pname, params, offset);
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params)
	{
		this.mgl.glGetIntegerv(pname, params);
	}

	@Override
	public String glGetString(int name)
	{
		final String result = this.mgl.glGetString(name);
		return result;
	}

	@Override
	public void glHint(int target, int mode)
	{
		this.mgl.glHint(target, mode);
	}

	@Override
	public void glLightModelf(int pname, float param)
	{
		this.mgl.glLightModelf(pname, param);
	}

	@Override
	public void glLightModelfv(int pname, float[] params, int offset)
	{
		this.mgl.glLightModelfv(pname, params, offset);
	}

	@Override
	public void glLightModelfv(int pname, FloatBuffer params)
	{
		this.mgl.glLightModelfv(pname, params);
	}

	@Override
	public void glLightModelx(int pname, int param)
	{
		this.mgl.glLightModelx(pname, param);
	}

	@Override
	public void glLightModelxv(int pname, int[] params, int offset)
	{
		this.mgl.glLightModelxv(pname, params, offset);
	}

	@Override
	public void glLightModelxv(int pname, IntBuffer params)
	{
		this.mgl.glLightModelxv(pname, params);
	}

	@Override
	public void glLightf(int light, int pname, float param)
	{
		this.mgl.glLightf(light, pname, param);
	}

	@Override
	public void glLightfv(int light, int pname, float[] params, int offset)
	{
		this.mgl.glLightfv(light, pname, params, offset);
	}

	@Override
	public void glLightfv(int light, int pname, FloatBuffer params)
	{
		this.mgl.glLightfv(light, pname, params);
	}

	@Override
	public void glLightx(int light, int pname, int param)
	{
		this.mgl.glLightx(light, pname, param);
	}

	@Override
	public void glLightxv(int light, int pname, int[] params, int offset)
	{
		this.mgl.glLightxv(light, pname, params, offset);
	}

	@Override
	public void glLightxv(int light, int pname, IntBuffer params)
	{
		this.mgl.glLightxv(light, pname, params);
	}

	@Override
	public void glLineWidth(float width)
	{
		this.mgl.glLineWidth(width);
	}

	@Override
	public void glLineWidthx(int width)
	{
		this.mgl.glLineWidthx(width);
	}

	@Override
	public void glLoadIdentity()
	{
		this.mCurrent.glLoadIdentity();
		this.mgl.glLoadIdentity();
		if (_check)
			check();
	}

	@Override
	public void glLoadMatrixf(float[] m, int offset)
	{
		this.mCurrent.glLoadMatrixf(m, offset);
		this.mgl.glLoadMatrixf(m, offset);
		if (_check)
			check();
	}

	@Override
	public void glLoadMatrixf(FloatBuffer m)
	{
		final int position = m.position();
		this.mCurrent.glLoadMatrixf(m);
		m.position(position);
		this.mgl.glLoadMatrixf(m);
		if (_check)
			check();
	}

	@Override
	public void glLoadMatrixx(int[] m, int offset)
	{
		this.mCurrent.glLoadMatrixx(m, offset);
		this.mgl.glLoadMatrixx(m, offset);
		if (_check)
			check();
	}

	@Override
	public void glLoadMatrixx(IntBuffer m)
	{
		final int position = m.position();
		this.mCurrent.glLoadMatrixx(m);
		m.position(position);
		this.mgl.glLoadMatrixx(m);
		if (_check)
			check();
	}

	@Override
	public void glLogicOp(int opcode)
	{
		this.mgl.glLogicOp(opcode);
	}

	@Override
	public void glMaterialf(int face, int pname, float param)
	{
		this.mgl.glMaterialf(face, pname, param);
	}

	@Override
	public void glMaterialfv(int face, int pname, float[] params, int offset)
	{
		this.mgl.glMaterialfv(face, pname, params, offset);
	}

	@Override
	public void glMaterialfv(int face, int pname, FloatBuffer params)
	{
		this.mgl.glMaterialfv(face, pname, params);
	}

	@Override
	public void glMaterialx(int face, int pname, int param)
	{
		this.mgl.glMaterialx(face, pname, param);
	}

	@Override
	public void glMaterialxv(int face, int pname, int[] params, int offset)
	{
		this.mgl.glMaterialxv(face, pname, params, offset);
	}

	@Override
	public void glMaterialxv(int face, int pname, IntBuffer params)
	{
		this.mgl.glMaterialxv(face, pname, params);
	}

	@Override
	public void glMatrixMode(int mode)
	{
		switch (mode)
		{
			case GL10.GL_MODELVIEW:
				this.mCurrent = this.mModelView;
				break;
			case GL10.GL_TEXTURE:
				this.mCurrent = this.mTexture;
				break;
			case GL10.GL_PROJECTION:
				this.mCurrent = this.mProjection;
				break;
			default:
				throw new IllegalArgumentException("Unknown matrix mode: "
						+ mode);
		}
		this.mgl.glMatrixMode(mode);
		this.mMatrixMode = mode;
		if (_check)
			check();
	}

	@Override
	public void glMultMatrixf(float[] m, int offset)
	{
		this.mCurrent.glMultMatrixf(m, offset);
		this.mgl.glMultMatrixf(m, offset);
		if (_check)
			check();
	}

	@Override
	public void glMultMatrixf(FloatBuffer m)
	{
		final int position = m.position();
		this.mCurrent.glMultMatrixf(m);
		m.position(position);
		this.mgl.glMultMatrixf(m);
		if (_check)
			check();
	}

	@Override
	public void glMultMatrixx(int[] m, int offset)
	{
		this.mCurrent.glMultMatrixx(m, offset);
		this.mgl.glMultMatrixx(m, offset);
		if (_check)
			check();
	}

	@Override
	public void glMultMatrixx(IntBuffer m)
	{
		final int position = m.position();
		this.mCurrent.glMultMatrixx(m);
		m.position(position);
		this.mgl.glMultMatrixx(m);
		if (_check)
			check();
	}

	@Override
	public void glMultiTexCoord4f(int target, float s, float t, float r, float q)
	{
		this.mgl.glMultiTexCoord4f(target, s, t, r, q);
	}

	@Override
	public void glMultiTexCoord4x(int target, int s, int t, int r, int q)
	{
		this.mgl.glMultiTexCoord4x(target, s, t, r, q);
	}

	@Override
	public void glNormal3f(float nx, float ny, float nz)
	{
		this.mgl.glNormal3f(nx, ny, nz);
	}

	@Override
	public void glNormal3x(int nx, int ny, int nz)
	{
		this.mgl.glNormal3x(nx, ny, nz);
	}

	@Override
	public void glNormalPointer(int type, int stride, Buffer pointer)
	{
		this.mgl.glNormalPointer(type, stride, pointer);
	}

	@Override
	public void glOrthof(float left, float right, float bottom, float top,
			float near, float far)
	{
		this.mCurrent.glOrthof(left, right, bottom, top, near, far);
		this.mgl.glOrthof(left, right, bottom, top, near, far);
		if (_check)
			check();
	}

	@Override
	public void glOrthox(int left, int right, int bottom, int top, int near,
			int far)
	{
		this.mCurrent.glOrthox(left, right, bottom, top, near, far);
		this.mgl.glOrthox(left, right, bottom, top, near, far);
		if (_check)
			check();
	}

	@Override
	public void glPixelStorei(int pname, int param)
	{
		this.mgl.glPixelStorei(pname, param);
	}

	@Override
	public void glPointSize(float size)
	{
		this.mgl.glPointSize(size);
	}

	@Override
	public void glPointSizex(int size)
	{
		this.mgl.glPointSizex(size);
	}

	@Override
	public void glPolygonOffset(float factor, float units)
	{
		this.mgl.glPolygonOffset(factor, units);
	}

	@Override
	public void glPolygonOffsetx(int factor, int units)
	{
		this.mgl.glPolygonOffsetx(factor, units);
	}

	@Override
	public void glPopMatrix()
	{
		this.mCurrent.glPopMatrix();
		this.mgl.glPopMatrix();
		if (_check)
			check();
	}

	@Override
	public void glPushMatrix()
	{
		this.mCurrent.glPushMatrix();
		this.mgl.glPushMatrix();
		if (_check)
			check();
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format,
			int type, Buffer pixels)
	{
		this.mgl.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glRotatef(float angle, float x, float y, float z)
	{
		this.mCurrent.glRotatef(angle, x, y, z);
		this.mgl.glRotatef(angle, x, y, z);
		if (_check)
			check();
	}

	@Override
	public void glRotatex(int angle, int x, int y, int z)
	{
		this.mCurrent.glRotatex(angle, x, y, z);
		this.mgl.glRotatex(angle, x, y, z);
		if (_check)
			check();
	}

	@Override
	public void glSampleCoverage(float value, boolean invert)
	{
		this.mgl.glSampleCoverage(value, invert);
	}

	@Override
	public void glSampleCoveragex(int value, boolean invert)
	{
		this.mgl.glSampleCoveragex(value, invert);
	}

	@Override
	public void glScalef(float x, float y, float z)
	{
		this.mCurrent.glScalef(x, y, z);
		this.mgl.glScalef(x, y, z);
		if (_check)
			check();
	}

	@Override
	public void glScalex(int x, int y, int z)
	{
		this.mCurrent.glScalex(x, y, z);
		this.mgl.glScalex(x, y, z);
		if (_check)
			check();
	}

	@Override
	public void glScissor(int x, int y, int width, int height)
	{
		this.mgl.glScissor(x, y, width, height);
	}

	@Override
	public void glShadeModel(int mode)
	{
		this.mgl.glShadeModel(mode);
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask)
	{
		this.mgl.glStencilFunc(func, ref, mask);
	}

	@Override
	public void glStencilMask(int mask)
	{
		this.mgl.glStencilMask(mask);
	}

	@Override
	public void glStencilOp(int fail, int zfail, int zpass)
	{
		this.mgl.glStencilOp(fail, zfail, zpass);
	}

	@Override
	public void glTexCoordPointer(int size, int type, int stride, Buffer pointer)
	{
		this.mgl.glTexCoordPointer(size, type, stride, pointer);
	}

	@Override
	public void glTexEnvf(int target, int pname, float param)
	{
		this.mgl.glTexEnvf(target, pname, param);
	}

	@Override
	public void glTexEnvfv(int target, int pname, float[] params, int offset)
	{
		this.mgl.glTexEnvfv(target, pname, params, offset);
	}

	@Override
	public void glTexEnvfv(int target, int pname, FloatBuffer params)
	{
		this.mgl.glTexEnvfv(target, pname, params);
	}

	@Override
	public void glTexEnvx(int target, int pname, int param)
	{
		this.mgl.glTexEnvx(target, pname, param);
	}

	@Override
	public void glTexEnvxv(int target, int pname, int[] params, int offset)
	{
		this.mgl.glTexEnvxv(target, pname, params, offset);
	}

	@Override
	public void glTexEnvxv(int target, int pname, IntBuffer params)
	{
		this.mgl.glTexEnvxv(target, pname, params);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat,
			int width, int height, int border, int format, int type,
			Buffer pixels)
	{
		this.mgl.glTexImage2D(target, level, internalformat, width, height,
				border, format, type, pixels);
	}

	@Override
	public void glTexParameterf(int target, int pname, float param)
	{
		this.mgl.glTexParameterf(target, pname, param);
	}

	@Override
	public void glTexParameterx(int target, int pname, int param)
	{
		this.mgl.glTexParameterx(target, pname, param);
	}

	@Override
	public void glTexParameteriv(int target, int pname, int[] params, int offset)
	{
		this.mgl11.glTexParameteriv(target, pname, params, offset);
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params)
	{
		this.mgl11.glTexParameteriv(target, pname, params);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int width, int height, int format, int type,
			Buffer pixels)
	{
		this.mgl.glTexSubImage2D(target, level, xoffset, yoffset, width,
				height, format, type, pixels);
	}

	@Override
	public void glTranslatef(float x, float y, float z)
	{
		this.mCurrent.glTranslatef(x, y, z);
		this.mgl.glTranslatef(x, y, z);
		if (_check)
			check();
	}

	@Override
	public void glTranslatex(int x, int y, int z)
	{
		this.mCurrent.glTranslatex(x, y, z);
		this.mgl.glTranslatex(x, y, z);
		if (_check)
			check();
	}

	@Override
	public void glVertexPointer(int size, int type, int stride, Buffer pointer)
	{
		this.mgl.glVertexPointer(size, type, stride, pointer);
	}

	@Override
	public void glViewport(int x, int y, int width, int height)
	{
		this.mgl.glViewport(x, y, width, height);
	}

	@Override
	public void glClipPlanef(int plane, float[] equation, int offset)
	{
		this.mgl11.glClipPlanef(plane, equation, offset);
	}

	@Override
	public void glClipPlanef(int plane, FloatBuffer equation)
	{
		this.mgl11.glClipPlanef(plane, equation);
	}

	@Override
	public void glClipPlanex(int plane, int[] equation, int offset)
	{
		this.mgl11.glClipPlanex(plane, equation, offset);
	}

	@Override
	public void glClipPlanex(int plane, IntBuffer equation)
	{
		this.mgl11.glClipPlanex(plane, equation);
	}

	// Draw Texture Extension

	@Override
	public void glDrawTexfOES(float x, float y, float z, float width,
			float height)
	{
		this.mgl11Ext.glDrawTexfOES(x, y, z, width, height);
	}

	@Override
	public void glDrawTexfvOES(float[] coords, int offset)
	{
		this.mgl11Ext.glDrawTexfvOES(coords, offset);
	}

	@Override
	public void glDrawTexfvOES(FloatBuffer coords)
	{
		this.mgl11Ext.glDrawTexfvOES(coords);
	}

	@Override
	public void glDrawTexiOES(int x, int y, int z, int width, int height)
	{
		this.mgl11Ext.glDrawTexiOES(x, y, z, width, height);
	}

	@Override
	public void glDrawTexivOES(int[] coords, int offset)
	{
		this.mgl11Ext.glDrawTexivOES(coords, offset);
	}

	@Override
	public void glDrawTexivOES(IntBuffer coords)
	{
		this.mgl11Ext.glDrawTexivOES(coords);
	}

	@Override
	public void glDrawTexsOES(short x, short y, short z, short width,
			short height)
	{
		this.mgl11Ext.glDrawTexsOES(x, y, z, width, height);
	}

	@Override
	public void glDrawTexsvOES(short[] coords, int offset)
	{
		this.mgl11Ext.glDrawTexsvOES(coords, offset);
	}

	@Override
	public void glDrawTexsvOES(ShortBuffer coords)
	{
		this.mgl11Ext.glDrawTexsvOES(coords);
	}

	@Override
	public void glDrawTexxOES(int x, int y, int z, int width, int height)
	{
		this.mgl11Ext.glDrawTexxOES(x, y, z, width, height);
	}

	@Override
	public void glDrawTexxvOES(int[] coords, int offset)
	{
		this.mgl11Ext.glDrawTexxvOES(coords, offset);
	}

	@Override
	public void glDrawTexxvOES(IntBuffer coords)
	{
		this.mgl11Ext.glDrawTexxvOES(coords);
	}

	@Override
	public int glQueryMatrixxOES(int[] mantissa, int mantissaOffset,
			int[] exponent, int exponentOffset)
	{
		return this.mgl10Ext.glQueryMatrixxOES(mantissa, mantissaOffset,
				exponent, exponentOffset);
	}

	@Override
	public int glQueryMatrixxOES(IntBuffer mantissa, IntBuffer exponent)
	{
		return this.mgl10Ext.glQueryMatrixxOES(mantissa, exponent);
	}

	// Unsupported GL11 methods

	@Override
	public void glBindBuffer(int target, int buffer)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glBufferData(int target, int size, Buffer data, int usage)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glBufferSubData(int target, int offset, int size, Buffer data)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glColor4ub(byte red, byte green, byte blue, byte alpha)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glDeleteBuffers(int n, int[] buffers, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glDeleteBuffers(int n, IntBuffer buffers)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGenBuffers(int n, int[] buffers, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGenBuffers(int n, IntBuffer buffers)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetBooleanv(int pname, boolean[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetBooleanv(int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, int[] params,
			int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetClipPlanef(int pname, float[] eqn, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetClipPlanef(int pname, FloatBuffer eqn)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetClipPlanex(int pname, int[] eqn, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetClipPlanex(int pname, IntBuffer eqn)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetFixedv(int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetFixedv(int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetFloatv(int pname, float[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetLightfv(int light, int pname, float[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetLightfv(int light, int pname, FloatBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetLightxv(int light, int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetLightxv(int light, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetMaterialfv(int face, int pname, float[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetMaterialfv(int face, int pname, FloatBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetMaterialxv(int face, int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetMaterialxv(int face, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexEnviv(int env, int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexEnviv(int env, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexEnvxv(int env, int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexEnvxv(int env, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, float[] params,
			int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, int[] params,
			int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexParameterxv(int target, int pname, int[] params,
			int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetTexParameterxv(int target, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean glIsBuffer(int buffer)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean glIsEnabled(int cap)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean glIsTexture(int texture)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glPointParameterf(int pname, float param)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glPointParameterfv(int pname, float[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glPointParameterfv(int pname, FloatBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glPointParameterx(int pname, int param)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glPointParameterxv(int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glPointParameterxv(int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glPointSizePointerOES(int type, int stride, Buffer pointer)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexEnvi(int target, int pname, int param)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexEnviv(int target, int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexEnviv(int target, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexParameterfv(int target, int pname, float[] params,
			int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexParameteri(int target, int pname, int param)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexParameterxv(int target, int pname, int[] params, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexParameterxv(int target, int pname, IntBuffer params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glColorPointer(int size, int type, int stride, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glDrawElements(int mode, int count, int type, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetPointerv(int pname, Buffer[] params)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glNormalPointer(int type, int stride, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glTexCoordPointer(int size, int type, int stride, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glVertexPointer(int size, int type, int stride, int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glCurrentPaletteMatrixOES(int matrixpaletteindex)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glLoadPaletteFromModelViewMatrixOES()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glMatrixIndexPointerOES(int size, int type, int stride,
			Buffer pointer)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glMatrixIndexPointerOES(int size, int type, int stride,
			int offset)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glWeightPointerOES(int size, int type, int stride,
			Buffer pointer)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void glWeightPointerOES(int size, int type, int stride, int offset)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the current matrix
	 */

	public void getMatrix(float[] m, int offset)
	{
		this.mCurrent.getMatrix(m, offset);
	}

	/**
	 * Get the current matrix mode
	 */

	public int getMatrixMode()
	{
		return this.mMatrixMode;
	}

	private void check()
	{
		int oesMode;
		switch (this.mMatrixMode)
		{
			case GL_MODELVIEW:
				oesMode = GL11.GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES;
				break;
			case GL_PROJECTION:
				oesMode = GL11.GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES;
				break;
			case GL_TEXTURE:
				oesMode = GL11.GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES;
				break;
			default:
				throw new IllegalArgumentException("Unknown matrix mode");
		}

		if (this.mByteBuffer == null)
		{
			this.mCheckA = new float[16];
			this.mCheckB = new float[16];
			this.mByteBuffer = ByteBuffer.allocateDirect(64);
			this.mByteBuffer.order(ByteOrder.nativeOrder());
			this.mFloatBuffer = this.mByteBuffer.asFloatBuffer();
		}
		this.mgl.glGetIntegerv(oesMode, this.mByteBuffer.asIntBuffer());
		for (int i = 0; i < 16; i++)
		{
			this.mCheckB[i] = this.mFloatBuffer.get(i);
		}
		this.mCurrent.getMatrix(this.mCheckA, 0);

		boolean fail = false;
		for (int i = 0; i < 16; i++)
		{
			if (this.mCheckA[i] != this.mCheckB[i])
			{
				Log.d("GLMatWrap", "i:" + i + " a:" + this.mCheckA[i] + " a:"
						+ this.mCheckB[i]);
				fail = true;
			}
		}
		if (fail)
		{
			throw new IllegalArgumentException("Matrix math difference.");
		}
	}

}
