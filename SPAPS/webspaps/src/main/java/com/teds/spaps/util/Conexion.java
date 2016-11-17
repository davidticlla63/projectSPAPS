package com.teds.spaps.util;

import java.io.Serializable;

public class Conexion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579990640927344648L;
	public static String datasourse = "java:jboss/datasources/webspapsDS";

	// facturacionDS

	public String getDatasourse() {
		return datasourse;
	}

	@SuppressWarnings("static-access")
	public void setDatasourse(String datasourse) {
		this.datasourse = datasourse;
	}

}
