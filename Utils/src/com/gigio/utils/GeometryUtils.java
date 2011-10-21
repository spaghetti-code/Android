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
}
