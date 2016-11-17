/**
 * @author david
 */
package com.teds.spaps.converter;

/**
 * @author david
 *
 */
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpServletRequest;

import com.teds.spaps.controller.PacienteController;
import com.teds.spaps.model.DocumentoIdentidad;

@FacesConverter("documentoIdentidadConverter")
public class DocumentoIdentidadConverter implements Converter {

	/*
	 * @ManagedProperty("#{DocumentoIdentidads}") private
	 * List<DocumentoIdentidad> listaDocumentoIdentidads;
	 */

	// private @Inject IDocumentoIdentidadDao DocumentoIdentidadDao;

	@SuppressWarnings("static-access")
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				/*
				 * ConsultaController service = (ConsultaController) fc
				 * .getExternalContext().getApplicationMap()
				 * .get("consultaController");
				 */
				String url = ((HttpServletRequest) fc.getCurrentInstance()
						.getExternalContext().getRequest()).getRequestURI();
				String[] protocolo = url.split("/");
				List<DocumentoIdentidad> DocumentoIdentidads = new ArrayList<DocumentoIdentidad>();
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("paciente.xhtml"))
					DocumentoIdentidads = PacienteController.listaDocumentoIdentidad;
				DocumentoIdentidad DocumentoIdentidad = getObjectFromList(
						DocumentoIdentidads, Integer.valueOf(value));
				if (DocumentoIdentidad != null) {
					return DocumentoIdentidad;
				} else {
					return null;
				}
			} catch (NumberFormatException e) {
				throw new ConverterException();
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((DocumentoIdentidad) object).getId());
		} else {
			return null;
		}
	}

	/*
	 * public Object getAsObject(FacesContext context, UIComponent component,
	 * String value) {
	 * 
	 * System.out.println("valor devuelto del converter = " + value);
	 * DocumentoIdentidad auxiliar = DocumentoIdentidadDao
	 * .obtenerDocumentoIdentidad(Integer.parseInt(value));
	 * System.out.println("DocumentoIdentidad converter = " + auxiliar.getId());
	 * 
	 * for (DocumentoIdentidad DocumentoIdentidad : listaDocumentoIdentidads) {
	 * if (DocumentoIdentidad.getId() == Integer.parseInt(value)) return
	 * DocumentoIdentidad; } return null; }
	 * 
	 * public String getAsString(FacesContext context, UIComponent component,
	 * Object object) { if (object instanceof DocumentoIdentidad) {
	 * DocumentoIdentidad DocumentoIdentidad = (DocumentoIdentidad) object;
	 * return DocumentoIdentidad.getId().toString(); } return ""; }
	 */

	/*
	 * private DocumentoIdentidad getObjectFromComponent(UIComponent component,
	 * String value) { final List<DocumentoIdentidad> dualList; try { dualList =
	 * (List<DocumentoIdentidad>) ((AutoComplete) component).getValue();
	 * DocumentoIdentidad team = getObjectFromList(dualList,
	 * Integer.valueOf(value)); if (team == null) { team =
	 * getObjectFromList(dualList, Integer.valueOf(value)); }
	 * 
	 * return team; } catch (ClassCastException cce) { throw new
	 * ConverterException(); } catch (NumberFormatException nfe) { throw new
	 * ConverterException(); } }
	 */

	private DocumentoIdentidad getObjectFromList(final List<?> list,
			final Integer identifier) {
		for (final Object object : list) {
			final DocumentoIdentidad team = (DocumentoIdentidad) object;
			if (team.getId().equals(identifier)) {
				return team;
			}
		}
		return null;
	}

	/*
	 * public List<DocumentoIdentidad> getListaDocumentoIdentidads() { return
	 * listaDocumentoIdentidads; }
	 * 
	 * public void setListaDocumentoIdentidads(List<DocumentoIdentidad>
	 * listaDocumentoIdentidads) { this.listaDocumentoIdentidads =
	 * listaDocumentoIdentidads; }
	 */

}