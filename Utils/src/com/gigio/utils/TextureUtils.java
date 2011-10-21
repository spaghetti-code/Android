package com.gigio.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * Classe utilit�ria para Textures OpenGL.
 * 
 * @author FRANCESCO
 */
public class TextureUtils
{
	/**
	 * Carrega uma texture.
	 * Obs: filtro mipmapping.
	 * Obs: no Kyros, para que tudo funcione, os bitmaps utilizados devem ser quadrados, de lado uma pot�ncia
	 * de 2 (ex: 256x256, 512x512)
	 * 
	 * @param gl
	 * @param context
	 * @param resource array de recursos, tipo R.drawable.image
	 * @return textureIDs array onde ficam armazenadas as textures
	 */
	public static int[] loadTextures(GL10 gl, Context context, int[] resources)
	{
		final int[] textureIDs = new int[resources.length];
		gl.glGenTextures(resources.length, textureIDs, 0);

		for (int i = 0; i < resources.length; i++)
		{
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[i]);

			//			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
			//					GL10.GL_LINEAR);
			//			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
			//					GL10.GL_LINEAR_MIPMAP_NEAREST);
			//			if (gl instanceof GL11)
			//				gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP,
			//						GL11.GL_TRUE);

			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
					GL10.GL_LINEAR);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_LINEAR);

			final Bitmap bitmap = loadBitmap(context, resources[i]);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			bitmap.recycle();
		}
		return textureIDs;
	}

	/**
	 * Carrega uma imagem bitmap.
	 * 
	 * @param context
	 * @param resource
	 * @return Bitmap
	 */
	public static Bitmap loadBitmap(Context context, int resource)
	{
		Bitmap bitmap;
		InputStream is = context.getResources().openRawResource(resource);
		try
		{
			bitmap = BitmapFactory.decodeStream(is);

		} finally
		{
			// Always clear and close
			try
			{
				is.close();
				is = null;
			} catch (final IOException e)
			{
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	/**
	 * Inicializa o buffer das textures
	 * 
	 * @param textureCoords coordenadas dos cantos da texture, para mapeamento com os poligonos
	 * @return textureBuffer buffer das textures
	 */
	public static FloatBuffer initTextureBuffer(final float[] textureCoords)
	{
		final FloatBuffer textureBuffer;
		final ByteBuffer tbb = ByteBuffer
				.allocateDirect(textureCoords.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		textureBuffer = tbb.asFloatBuffer();
		textureBuffer.put(textureCoords);
		textureBuffer.position(0);
		return textureBuffer;
	}
}
