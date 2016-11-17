/**
 * @author david
 */
package com.teds.spaps.enums;

/**
 * @author david
 *
 */
public enum Sexo {
	NINGUNO("Ninguno"), MASCULINO("Masculino"), FEMENINO("Femenino"), AMBOS(
			"Ambos");
	private String label;

	Sexo(String nome) {
		this.label = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
};
