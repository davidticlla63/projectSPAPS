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
import javax.servlet.http.HttpServletRequest;

import com.teds.spaps.controller.DesgOrdenServicioAprobacionController;
import com.teds.spaps.controller.TareaController;
import com.teds.spaps.model.NombreTarea;

@FacesConverter("nombreTareaConverter")
public class NombreTareaConverter implements Converter {

	/*
	 * @ManagedProperty("#{pacientes}") private List<Paciente> listaPacientes;
	 */

	// private @Inject IPacienteDao pacienteDao;

	@SuppressWarnings("static-access")
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
				String url = ((HttpServletRequest) fc.getCurrentInstance()
						.getExternalContext().getRequest()).getRequestURI();
				String[] protocolo = url.split("/");
				List<NombreTarea> pacientes = new ArrayList<NombreTarea>();
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("desg_orden_servicio_listado.xhtml"))
					pacientes = DesgOrdenServicioAprobacionController.nombreTareas;
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("tarea.xhtml"))
					pacientes = TareaController.nombreTareas;
				NombreTarea paciente = getObjectFromList(pacientes,
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
			return String.valueOf(((NombreTarea) object).getId());
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

	private NombreTarea getObjectFromList(final List<?> list,
			final Integer identifier) {
		for (final Object object : list) {
			final NombreTarea team = (NombreTarea) object;
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