/**
 * @author david
 */
package com.teds.spaps.converter;

/**
 * @author david
 *
 */
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.teds.spaps.model.Servicio;

@FacesConverter("pickListConverterServicio")
public class PickListConverterServicio implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		return getObjectFromUIPickListComponent(component, value);
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object object) {
		String string;
		if (object == null) {
			string = "";
		} else {
			try {
				string = String.valueOf(((Servicio) object).getId());
			} catch (ClassCastException cce) {
				throw new ConverterException();
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private Servicio getObjectFromUIPickListComponent(UIComponent component,
			String value) {
		final DualListModel<Servicio> dualList;
		try {
			dualList = (DualListModel<Servicio>) ((PickList) component)
					.getValue();
			Servicio team = getObjectFromList(dualList.getSource(),
					Integer.valueOf(value));
			if (team == null) {
				team = getObjectFromList(dualList.getTarget(),
						Integer.valueOf(value));
			}

			return team;
		} catch (ClassCastException cce) {
			throw new ConverterException();
		} catch (NumberFormatException nfe) {
			throw new ConverterException();
		}
	}

	private Servicio getObjectFromList(final List<?> list,
			final Integer identifier) {
		for (final Object object : list) {
			final Servicio team = (Servicio) object;
			if (team.getId().equals(identifier)) {
				return team;
			}
		}
		return null;
	}
}