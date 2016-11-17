/**
 * @author ANITA
 */
package com.teds.spaps.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.teds.spaps.model.Diagnostico;

/**
 * @author ANITA
 *
 */

@FacesConverter(forClass = Diagnostico.class)
public class DiagnosticoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext,
			UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Diagnostico) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object value) {
		if (value instanceof Diagnostico) {
			Diagnostico entity = (Diagnostico) value;
			if (entity != null && entity instanceof Diagnostico
					&& entity.getId() != null) {
				uiComponent.getAttributes().put(entity.getId().toString(),
						entity);
				return entity.getDescripcion().toString();
			}
		}
		return "";
	}

}
