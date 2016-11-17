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

import com.teds.spaps.controller.DesgOrdenServicioController;
import com.teds.spaps.model.Empresa;

@FacesConverter("empresaConverter")
public class EmpresaConverter implements Converter {

	/*
	 * @ManagedProperty("#{pacientes}") private List<Paciente> listaPacientes;
	 */

	// private @Inject IPacienteDao pacienteDao;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				/*
				 * ConsultaController service = (ConsultaController) fc
				 * .getExternalContext().getApplicationMap()
				 * .get("consultaController");
				 * 
				 * String url = ((HttpServletRequest) fc.getCurrentInstance()
				 * .getExternalContext().getRequest()).getRequestURI();
				 * System.out.println("url = " + url); String[] protocolo =
				 * url.split("/");
				 */
				List<Empresa> pacientes = new ArrayList<Empresa>();
				pacientes = DesgOrdenServicioController.empresas;
				Empresa paciente = getObjectFromList(pacientes,
						Integer.valueOf(value));
				if (paciente != null) {
					return paciente;
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
			return String.valueOf(((Empresa) object).getId());
		} else {
			return null;
		}
	}

	/*
	 * public Object getAsObject(FacesContext context, UIComponent component,
	 * String value) {
	 * 
	 * System.out.println("valor devuelto del converter = " + value); Paciente
	 * auxiliar = pacienteDao .obtenerPaciente(Integer.parseInt(value));
	 * System.out.println("paciente converter = " + auxiliar.getId());
	 * 
	 * for (Paciente paciente : listaPacientes) { if (paciente.getId() ==
	 * Integer.parseInt(value)) return paciente; } return null; }
	 * 
	 * public String getAsString(FacesContext context, UIComponent component,
	 * Object object) { if (object instanceof Paciente) { Paciente paciente =
	 * (Paciente) object; return paciente.getId().toString(); } return ""; }
	 */

	/*
	 * private Paciente getObjectFromComponent(UIComponent component, String
	 * value) { final List<Paciente> dualList; try { dualList = (List<Paciente>)
	 * ((AutoComplete) component).getValue(); Paciente team =
	 * getObjectFromList(dualList, Integer.valueOf(value)); if (team == null) {
	 * team = getObjectFromList(dualList, Integer.valueOf(value)); }
	 * 
	 * return team; } catch (ClassCastException cce) { throw new
	 * ConverterException(); } catch (NumberFormatException nfe) { throw new
	 * ConverterException(); } }
	 */

	private Empresa getObjectFromList(final List<?> list,
			final Integer identifier) {
		for (final Object object : list) {
			final Empresa team = (Empresa) object;
			if (team.getId().equals(identifier)) {
				return team;
			}
		}
		return null;
	}

	/*
	 * public List<Paciente> getListaPacientes() { return listaPacientes; }
	 * 
	 * public void setListaPacientes(List<Paciente> listaPacientes) {
	 * this.listaPacientes = listaPacientes; }
	 */

}