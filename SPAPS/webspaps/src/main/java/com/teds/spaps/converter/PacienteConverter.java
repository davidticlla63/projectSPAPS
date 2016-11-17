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

import com.teds.spaps.controller.AgendaController;
import com.teds.spaps.controller.Citas2Controller;
import com.teds.spaps.controller.ConsultaController;
import com.teds.spaps.controller.DesgOrdenServicioController;
import com.teds.spaps.controller.ExamenFisicoController;
import com.teds.spaps.controller.LabOrdenController;
import com.teds.spaps.controller.LabOrdenImagenologiaController;
import com.teds.spaps.controller.PacienteController;
import com.teds.spaps.controller.TareaController;
import com.teds.spaps.model.Paciente;

@FacesConverter("pacienteConverter")
public class PacienteConverter implements Converter {

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
				 */
				String url = ((HttpServletRequest) fc.getCurrentInstance()
						.getExternalContext().getRequest()).getRequestURI();
				String[] protocolo = url.split("/");
				List<Paciente> pacientes = new ArrayList<Paciente>();
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("consulta.xhtml"))
					pacientes = ConsultaController.listaPacientes;
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("paciente.xhtml"))
					pacientes = PacienteController.listaPacientes;
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("tarea.xhtml"))
					pacientes = TareaController.listaPacientes;
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("examen_fisico_enfermera.xhtml"))
					pacientes = ExamenFisicoController.listaPacientes;
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("index.xhtml")) {
					if (protocolo[protocolo.length - 2]
							.equalsIgnoreCase("gestion_citas")) {
						pacientes = Citas2Controller.listaPacientes;
					} else {
						pacientes = AgendaController.listaPacientes;
					}
				}
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("index.xhtml")) {
					if (protocolo[protocolo.length - 2]
							.equals("ordenes_imagenologia")) {
						pacientes = LabOrdenImagenologiaController.listaPacientes;
					} else if (protocolo[protocolo.length - 2]
							.equals("ordenes")) {
						pacientes = LabOrdenController.listaPacientes;
					}
				}
				
				if (protocolo[protocolo.length - 1]
						.equalsIgnoreCase("desg_orden_servicio.xhtml"))
					pacientes = DesgOrdenServicioController.listaPacientes;
				Paciente paciente = getObjectFromList(pacientes,
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
			return String.valueOf(((Paciente) object).getId());
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

	private Paciente getObjectFromList(final List<?> list,
			final Integer identifier) {
		for (final Object object : list) {
			final Paciente team = (Paciente) object;
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