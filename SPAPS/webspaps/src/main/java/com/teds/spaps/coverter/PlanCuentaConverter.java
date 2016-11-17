package com.teds.spaps.coverter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@FacesConverter("planCuentaConverter")
public class PlanCuentaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		try {
			if (value == null || value.length() == 0 || FacesUtil.isDummySelectItem(component, value)) {
				return null;
			}
			//Integer data = Integer.valueOf(value);// get ID
			String data = value;// get Descripcion
			PlanCuenta p = new PlanCuenta();
			 //p.setId(data);
			p.setDescripcion(data);
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
			
			// return String.valueOf(((PlanCuenta) object).getId()); //return ID
			return String.valueOf(((PlanCuenta) object).getDescripcion()); //return DESCRIPCION
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
