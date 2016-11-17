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

import com.teds.spaps.model.LabExamenImag;

@FacesConverter("pickListConverterImagenologia")
public class PickListConverterImagenologia implements Converter {

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
				string = String.valueOf(((LabExamenImag) object).getId());
			} catch (ClassCastException cce) {
				throw new ConverterException();
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private LabExamenImag getObjectFromUIPickListComponent(UIComponent component,
			String value) {
		final DualListModel<LabExamenImag> dualList;
		try {
			dualList = (DualListModel<LabExamenImag>) ((PickList) component)
					.getValue();
			LabExamenImag team = getObjectFromList(dualList.getSource(),
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

	private LabExamenImag getObjectFromList(final List<?> list,
			final Integer identifier) {
		for (final Object object : list) {
			final LabExamenImag team = (LabExamenImag) object;
			if (team.getId().equals(identifier)) {
				return team;
			}
		}
		return null;
	}
}