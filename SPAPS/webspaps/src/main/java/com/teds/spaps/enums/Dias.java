/**
 * @author david
 */
package com.teds.spaps.enums;

/**
 * @author david
 *
 */
public enum Dias {
	DOMINGO("Domingo", 1), LUNES("Lunes", 2), MARTES("Martes", 3), MIERCOLES(
			"Miercoles", 4), JUEVES("Jueves", 5), VIERNES("Viernes", 6), SABADO(
			"Sabado", 7);
	private String label;
	private int number;

	Dias(String nome, int numb) {
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
