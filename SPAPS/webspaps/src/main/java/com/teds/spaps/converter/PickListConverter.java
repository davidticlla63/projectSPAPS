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

import com.teds.spaps.model.LabExamen;

@FacesConverter("pickListConverter")
public class PickListConverter implements Converter {

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
				string = String.valueOf(((LabExamen) object).getId());
			} catch (ClassCastException cce) {
				throw new ConverterException();
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private LabExamen getObjectFromUIPickListComponent(UIComponent component,
			String value) {
		final DualListModel<LabExamen> dualList;
		try {
			dualList = (DualListModel<LabExamen>) ((PickList) component)
					.getValue();
			LabExamen team = getObjectFromList(dualList.getSource(),
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

	private LabExamen getObjectFromList(final List<?> list,
			final Integer identifier) {
		for (final Object object : list) {
			final LabExamen team = (LabExamen) object;
			if (team.getId().equals(identifier)) {
				return team;
			}
		}
		return null;
	}
}