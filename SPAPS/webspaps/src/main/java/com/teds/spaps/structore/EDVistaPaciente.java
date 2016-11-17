package com.teds.spaps.structore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PlanesSeguro;

/**
 * Entity implementation class for Entity: Barrio
 *
 */
public class EDVistaPaciente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4857793163095485411L;
	private Paciente paciente;
	private StreamedContent content;
	private IdentificacionPaciente documentoIdentidad;
	private List<PlanesSeguro> seguros;

	public EDVistaPaciente() {
		super();
		this.paciente = new Paciente();
		this.seguros = new ArrayList<PlanesSeguro>();
		this.documentoIdentidad = new IdentificacionPaciente();
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public List<PlanesSeguro> getSeguros() {
		return seguros;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public void setSeguros(List<PlanesSeguro> seguros) {
		this.seguros = seguros;
	}

	/**
	 * @return the documentoIdentidad
	 */
	public IdentificacionPaciente getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	/**
	 * @param documentoIdentidad
	 *            the documentoIdentidad to set
	 */
	public void setDocumentoIdentidad(IdentificacionPaciente documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public StreamedContent getContent() {
		String mimeType = "image/jpg";
		try {
			InputStream input = new ByteArrayInputStream(
					this.paciente.getImagen());
			return new DefaultStreamedContent(input, mimeType);
		} catch (Exception e) {
			return null;
		}
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return paciente.getId().toString();
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (paciente.getId() != null ? paciente.getId().hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (!(obj instanceof EDVistaPaciente)) {
				return false;
			} else {
				if (((EDVistaPaciente) obj).getPaciente().getId() == this
						.getPaciente().getId()) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	private byte[] toByteArrayUsingJava(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		return baos.toByteArray();
	}

}
