package com.teds.spaps.coverter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.teds.spaps.model.MonedaEmpresa;
import com.teds.spaps.util.FacesUtil;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@FacesConverter("monedaEmpresaConverter")
public class MonedaEmpresaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		try {
			if (value == null || value.length() == 0 || FacesUtil.isDummySelectItem(component, value)) {
				return null;
			}
			Integer data = Integer.valueOf(value);
			//System.out.println("value: "+value);
			//String data = value;
			MonedaEmpresa p = new MonedaEmpresa();
			p.setId(data);
			//p.setTipo(data);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		try {
			if (object == null  || (object instanceof String && ((String) object).length() == 0)) {
				return null;
			}
			//System.out.println("object: "+object);
			return String.valueOf(((MonedaEmpresa) object).getId()); // ID
			//return String.valueOf(((MonedaEmpresa) object).getTipo()); //return TIPO
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
