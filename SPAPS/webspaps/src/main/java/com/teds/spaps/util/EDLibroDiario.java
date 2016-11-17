package com.teds.spaps.util;

import com.teds.spaps.model.LineaContable;


/**
 * @author mauriciobejaranorivera
 *
 */
public class EDLibroDiario {

	private LineaContable lineaContable;
	
	private boolean yaSeMostro;

	
	public EDLibroDiario(LineaContable lineaContable, boolean yaSeMostro) {
		super();
		this.lineaContable = lineaContable;
		this.yaSeMostro = yaSeMostro;
	}

	public LineaContable getLineaContable() {
		return lineaContable;
	}

	public void setLineaContable(LineaContable lineaContable) {
		this.lineaContable = lineaContable;
	}

	public boolean isYaSeMostro() {
		return yaSeMostro;
	}

	public void setYaSeMostro(boolean yaSeMostro) {
		this.yaSeMostro = yaSeMostro;
	}
}
