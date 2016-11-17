package com.teds.spaps.coverter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.model.Seguro;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */

@Named(value = "seguroConverter")
public class SeguroConverter implements Converter {

	private @Inject ISeguroDao seguroDao;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		try {
			if (value == null || value.length() == 0) {
				return null;
			}
			Integer data = Integer.valueOf(value);
			// System.out.println("value: "+value);
			// String data = value;
			return seguroDao.DevolverSeguroPorId(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object object) {
		try {
			if (object == null
					|| (object instanceof String && ((String) object).length() == 0)) {
				return null;
			}
			// System.out.println("object: "+object);
			return String.valueOf(((Seguro) object).getId());
			// return String.valueOf(((MonedaEmpresa) object).getTipo());
			// //return TIPO
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
