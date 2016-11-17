/**
 * @author david
 */
package com.teds.spaps.enums;

/**
 * @author david
 *
 */
public enum TipoSeleccion {
	NINGUNO("Ninguno"), SEXO("Sexo"), EDAD("Edad"), SEXO_EDAD("Edad y Sexo"), CIUDAD(
			"Ciudad"), CIUDAD_SEXO("Ciudad y Sexo"), CIUDAD_EDAD(
			"Ciudad y Edad"), CIUDAD_SEXO_EDAD("Ciudad, Sexo y Edad");
	private String label;

	TipoSeleccion(String nome) {
		this.label = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
};
