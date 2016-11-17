package com.teds.spaps.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class ImageUtil {

	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArrayUsingJava(InputStream is)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		return baos.toByteArray();
	}

	/**
	 * 
	 * @param nameResourceImage
	 *            Ej: logo.png
	 * @param mime
	 *            Ej: image/png
	 * @return
	 */
	public static StreamedContent getImageResources(String nameResourceImage,
			String mime) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(nameResourceImage);
		return new DefaultStreamedContent(stream, mime);
	}

}
