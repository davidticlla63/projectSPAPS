/**
 * @author david
 */
package com.teds.spaps.enums;

/**
 * @author david
 *
 */
public enum Meses {
	ENERO("Enero", 1), FEBRERO("Febrero", 2), MARZO("Marzo", 3), ABRIL("Abril",
			4), MAYO("Mayo", 5), JUNIO("Junio", 6), JULIO("Julio", 7), AGOSTO(
			"Agosto", 8), SEPTIEMBRE("Septiembre", 9), OCTUBRE("Octubre", 10), NOVIEMBRE(
			"Noviembre", 11), DICIEMBRE("Diciembre", 12);
	private String label;
	private int number;

	Meses(String nome, int numb) {
		this.label = nome;
		this.number = numb;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int numb) {
		this.number = numb;
	}
};
