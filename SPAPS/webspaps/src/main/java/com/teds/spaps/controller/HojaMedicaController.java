/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "hojaMedicaController")
@ViewScoped
public class HojaMedicaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1682168731519850189L;
	/******* DAO **********/
	private @Inject IPacienteDao pacienteDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Paciente paciente;
	private Paciente pacienteSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Paciente> listaPaciente;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	/***** VARIABLES DE BUSQUEDA *****/

	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;

	@Inject
	Conversation conversation;

	@Inject
	private FacesContext facesContext;
	private HttpServletRequest request;

	// url page

	private String currentPage;

	@PostConstruct
	public void initNew() {
		paciente = new Paciente();
		pacienteSelected = new Paciente();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaPaciente = pacienteDao.obtenerPorCompania(sessionMain
				.getCompania());
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		setCurrentPage("/pages/historias_clinicas/registro_historias_clinicas/list_search.xhtml");
	}

	@SuppressWarnings("unused")
	private void obtenerPacientesPorNombre() {
		try {
			System.out.println("Ingreso a obtenerPacientesPorNombre");
			listaPaciente = pacienteDao.obtenerIndividuosPorNombreCompania(
					nombre, apellidoPaterno, apellidoMaterno,
					sessionMain.getCompania());
		} catch (Exception e) {
			System.err.println("Error en obtenerPacientesPorNombre : "
					+ e.getStackTrace());
		}
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;

		FacesContext.getCurrentInstance().renderResponse();
		// resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void redireccionar(Paciente newPaciente) {
		try {
			String navigateString;
			if (newPaciente.getCodigoHc() == null
					|| newPaciente.getCodigoHc().trim().length() == 0) {
				// CREACION DE HOJA MEDICA
				navigateString = "/pages/historias_clinicas/registro_historias_clinicas/index.xhtml?idPaciente="
						+ newPaciente.getId();
			} else {
				// CREACION DE HISTORIA CLINICA
				navigateString = "/pages/historias_clinicas/registro_detalle_historias_clinicas/historia_medica.xhtml?idPaciente="
						+ newPaciente.getId();
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put("historiaClinicaDetalleController", null);
			request = (HttpServletRequest) facesContext.getExternalContext()
					.getRequest();
			System.out.println(request.getContextPath() + navigateString);
			facesContext.getExternalContext().redirect(
					request.getContextPath() + navigateString);

		} catch (Exception e) {
			System.out.println("Error en registro de paciente: "
					+ e.getMessage());
		}

	}

	public void initConversation() {
		if (!FacesContext.getCurrentInstance().isPostback()
				&& conversation.isTransient()) {
			conversation.begin();
			System.out.println(">>>>>>>>>> CONVERSACION INICIADA...");
		}
	}

	public String endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
		return "kardex_producto.xhtml?faces-redirect=true";
	}

	/**
	 * 
	 */
	public HojaMedicaController() {
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Paciente getPacienteSelected() {
		return pacienteSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Paciente> getListaPaciente() {
		return listaPaciente;
	}

	public boolean isModificar() {
		return modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public void setPacienteSelected(Paciente pacienteSelected) {
		this.pacienteSelected = pacienteSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaPaciente(List<Paciente> listaPaciente) {
		this.listaPaciente = listaPaciente;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno
	 *            the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno
	 *            the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

}
