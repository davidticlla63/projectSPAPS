/**
 * @author david
 */
package com.teds.spaps.enums;

/**
 * @author david
 *
 */
public enum TipoValor {
	RANGOS("Rangos"), NUMERICO("Numerico"), MULTIPLE("Multiple"), TEXTO("Texto");
	private String label;

	TipoValor(String nome) {
		this.label = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
};
