/**
 * @author ANITA
 */
package com.teds.spaps.controller;

/**
 * @author ANITA
 *
 */
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.teds.spaps.model.PlanSeguro;

@FacesConverter("planSeguroConverter")
public class PlanSeguroConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				PacienteController service = (PacienteController) fc
						.getExternalContext().getApplicationMap()
						.get("themeService");
				return service.getListaPlanSeguro()
						.get(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Conversion Error",
						"Not a valid theme."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((PlanSeguro) object).getId());
		} else {
			return null;
		}
	}
}
