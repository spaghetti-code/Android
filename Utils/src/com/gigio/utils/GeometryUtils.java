package com.gigio.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * Classe utilitária para OpenGL.
 * 
 * @author FRANCESCO
 */
public class GeometryUtils
{
	/**
	 * Inicializa o buffer dos vertices.
	 * 
	 * @param vertices coordenadas dos vertices
	 * @return vertexBuffer buffer dos vertices
	 */
	public static FloatBuffer initVertexBuffer(final float[] vertices)
	{
		final FloatBuffer vertexBuffer;
		final ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		return vertexBuffer;
	}

	/**
	 * Converte coordenadas da tela para coordenadas do mundo 3D.
	 * 
	 * @param gl Obs: para que funcione, a view precisa setar um wrapper na sua inicialização,
	 * como no exemplo a seguir:
	 * 
	 * view.setGLWrapper(new GLSurfaceView.GLWrapper()
	 *	{
	 *		@Override
	 *		public GL wrap(GL gl)
	 *		{
	 *			return new MatrixTrackingGL(gl);
	 *		}
	 *	});
	 * 
	 * @param screenX Coordenada X da tela (0 fica a esquerda)
	 * @param screenY Coordenada Y da tela (0 fica em baixo)
	 * @param viewWidth Largura em pixel da tela
	 * @param viewHeight Altura em pixel da tela
	 * @return float[4] com as coordenadas do ponto no mundo 3D
	 */
	public static float[] convertScreenCoordsToWorldCoords(GL10 gl,
			float screenX, float screenY, int viewWidth, int viewHeight)
	{
		final MatrixGrabber grabber = new MatrixGrabber();
		final float[] coords = new float[4];
		final int[] view = new int[] { 0, 0, viewWidth, viewHeight };

		grabber.getCurrentProjection(gl);
		grabber.getCurrentModelView(gl);

		GLU.gluUnProject(screenX, screenY, 0.0f, grabber.mModelView, 0,
				grabber.mProjection, 0, view, 0, coords, 0);
		return coords;
	}

	/**
	 * Obs.: a chamada deve estar entre gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
	 * e gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	 * 
	 * @param gl
	 * @param segments
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @param totalDegrees
	 * @param filled
	 * @param closed
	 */
	public static void drawEllipse(final GL10 gl, final int segments,
			final float width, final float height, final float x,
			final float y, final float totalDegrees, final boolean filled,
			final boolean closed)
	{
		gl.glTranslatef(x, y, 0.0f);
		final float vertices[] = new float[segments * 2];
		int count = 0;
		for (float i = 0; i < totalDegrees; i += (totalDegrees / segments))
		{
			vertices[count++] = (float) (Math.cos(Math.toRadians(i)) * width);
			vertices[count++] = (float) (Math.sin(Math.toRadians(i)) * height);
		}
		final FloatBuffer vertexBuffer = initVertexBuffer(vertices);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		int mode;
		if (filled)
			mode = GL10.GL_TRIANGLE_FAN;
		else if (closed)
			mode = GL10.GL_LINE_LOOP;
		else
			mode = GL10.GL_LINE_STRIP;
		gl.glDrawArrays(mode, 0, segments);
	}

	/**
	 * Obs.: a chamada deve estar entre gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
	 * e gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	 * 
	 * @param gl
	 * @param circleSegments
	 * @param circleSize
	 * @param x
	 * @param y
	 * @param filled
	 */
	public static void drawCircle(final GL10 gl, final int circleSegments,
			final float circleSize, final float x, final float y,
			final boolean filled)
	{
		drawEllipse(gl, circleSegments, circleSize, circleSize, x, y, 360.0f,
				filled, true);
	}

	/**
	 * 
	 * @param gl
	 */
	public static void drawCylinder(final GL10 gl)
	{
		for (float z = 0.0f; z <= 5.0f; z += 0.1f)
		{
			gl.glTranslatef(0.0f, 0.0f, 0.1f);
			drawCircle(gl, 100, 1.0f, 0.0f, 0.0f, false);
		}
	}

	/**
	 * 
	 * @param gl
	 */
	public static void drawCylinderBend(final GL10 gl)
	{
		for (float r = 0.0f; r <= 90.0f; r++)
		{
			gl.glTranslatef(0.0f, 0.0f, -0.1f);
			gl.glRotatef(1.0f, 0.0f, 1.0f, 0.0f);
			drawCircle(gl, 100, 1.0f, 0.0f, 0.0f, false);
		}
	}

	/**
	 * 
	 * @param gl
	 */
	public static void drawSphere(final GL10 gl)
	{
		for (float r = 0.0f; r <= 360; r++)
		{
			gl.glRotatef(r, 0.0f, 1.0f, 0.0f);
			drawCircle(gl, 100, 1.0f, 0.0f, 0.0f, false);
		}
	}

	/**
	 * Obs.: a chamada deve estar entre gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
	 * e gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	 * 
	 * @param gl
	 * @param arcSegments
	 * @param arcWidth
	 * @param arcHeight
	 * @param x
	 * @param y
	 * @param totalDegrees
	 */
	public static void drawArc(final GL10 gl, final int arcSegments,
			final float arcWidth, final float arcHeight, final float x,
			final float y, final float totalDegrees)
	{
		drawEllipse(gl, arcSegments, arcWidth, arcHeight, x, y, totalDegrees,
				false, false);
	}

	/**
	 * Obs.: a chamada deve estar entre gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
	 * e gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	 * 
	 * @param gl
	 * @param x
	 * @param y
	 */
	public static void drawPoint(final GL10 gl, final float x, final float y)
	{
		final float vertices[] = new float[2];
		vertices[0] = x;
		vertices[1] = y;
		final FloatBuffer vertexBuffer = initVertexBuffer(vertices);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
	}
}
