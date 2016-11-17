package com.teds.spaps.coverter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.teds.spaps.model.TipoComprobante;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@FacesConverter("tipoComprobanteConverter")
public class TipoComprobanteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
    	try {
			if (value == null || value.length() == 0 || FacesUtil.isDummySelectItem(component, value)) {
				return null;
			}
			Integer data = Integer.valueOf(value);
			TipoComprobante p = new TipoComprobante();
			 p.setId(data);
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
			return String.valueOf(((TipoComprobante) object).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}
