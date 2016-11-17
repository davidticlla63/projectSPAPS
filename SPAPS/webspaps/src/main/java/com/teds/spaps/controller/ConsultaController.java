/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.ICompaniaDao;
import com.teds.spaps.interfaces.dao.IConsultaDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao;
import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPacienteEmpresaDao;
import com.teds.spaps.interfaces.dao.IPlanSeguroDao;
import com.teds.spaps.interfaces.dao.IPlanesSeguroDao;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PacienteEmpresa;
import com.teds.spaps.model.PlanesSeguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "consultaController")
@ViewScoped
public class ConsultaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7676770863628348111L;
	/******* DAO **********/
	private @Inject IConsultaDao consultaDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;
	private @Inject IPlanesSeguroDao planesSeguroDao;
	private @Inject IPlanSeguroDao planSeguroDao;
	private @Inject IPacienteEmpresaDao pacienteEmpresaDao;
	private @Inject IDesgOrdenServicioDao desgOrdenServicioDao;
	private @Inject ICompaniaDao companiaDao;

	/******* OBJECT **********/
	private Consulta consulta;
	private Sucursal sucursal;
	private Usuario usuario;
	private Paciente paciente;

	/******* LIST **********/
	// @ManagedProperty("#{pacientes}")
	public static List<Paciente> listaPacientes;
	private List<PlanesSeguro> planesSeguros;
	private List<PacienteEmpresa> empresas;
	private List<DesgOrdenServicio> ordenesServicios;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String tipoBusqueda = "NP";
	private String tipoConsulta = "";
	private String tipoAtencion = "P";

	@Inject
	Conversation conversation;
	@Inject
	private FacesContext facesContext;

	/**
	 * 
	 */
	public ConsultaController() {
	}

	@PostConstruct
	public void initNew() {
		consulta = new Consulta();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		paciente = new Paciente();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		tipoBusqueda = "NP";
		tipoConsulta = "";
		tipoAtencion = "";
		listaPacientes = new ArrayList<Paciente>();
		planesSeguros = new ArrayList<PlanesSeguro>();
		empresas = new ArrayList<PacienteEmpresa>();
		ordenesServicios = new ArrayList<DesgOrdenServicio>();
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void cargarSeguros(ValueChangeEvent changeEvent) {
		String tipoAtencion1 = changeEvent.getNewValue().toString();
		System.out.println("entro a cargar seguros con " + tipoAtencion);
		if (tipoAtencion1.equals("S")) {
			consulta.setTipoAtencion(tipoAtencion);
			planesSeguros = planesSeguroDao.obtenerPorIndividuo(paciente);
		}
		if (tipoAtencion1.equals("E")) {
			consulta.setTipoAtencion(tipoAtencion);
			empresas = pacienteEmpresaDao.obtenerPorPaciente(paciente);
			ordenesServicios = desgOrdenServicioDao.obtenerPorClienteAprobadas(
					paciente, sucursal);
		}
		if (tipoAtencion1.equals("P")) {
			consulta.setTipoAtencion(tipoAtencion);
		}
		tipoAtencion = tipoAtencion1;
	}

	public void selectSeguro(ValueChangeEvent event) {
		int id = Integer.parseInt(event.getNewValue().toString());
		consulta.setPlanSeguro(planSeguroDao.obtenerPlanSeguro(id));
	}

	public void selectEmpresa(ValueChangeEvent event) {
		int id = Integer.parseInt(event.getNewValue().toString());
		consulta.setEmpresa(companiaDao.obtenerCompania(id));
	}

	public void selectOrdenServicio(ValueChangeEvent event) {
		int id = Integer.parseInt(event.getNewValue().toString());
		consulta.setOrdenServicio(desgOrdenServicioDao
				.obtenerDesgOrdenServicio(id));
	}

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public List<Paciente> onComplete(String query) {
		String upperQuery = query.toUpperCase().trim();
		if (tipoBusqueda.equals("NP")) {
			listaPacientes = pacienteDao.obtenerIndividuosPorNombreSucursal(
					upperQuery, upperQuery, upperQuery, sucursal);
		}
		if (tipoBusqueda.equals("CP"))
			listaPacientes = pacienteDao.obtenerIndividuosPorCodigo(upperQuery,
					sucursal.getCompania());
		if (tipoBusqueda.equals("CH"))
			listaPacientes = pacienteDao.obtenerPorCodigoHC(upperQuery,
					sucursal.getCompania());
		if (tipoBusqueda.equals("DI")) {
			List<IdentificacionPaciente> auxiliar = identificacionIndividuoDao
					.obtenerPorCompaniaAutoComplete(upperQuery,
							sucursal.getCompania());
			System.out.println("DIs = " + auxiliar.size());
			listaPacientes = getPacientesDI(auxiliar);
		}
		System.out.println("lista de pacientes = " + listaPacientes.size());
		return listaPacientes;
	}

	private List<Paciente> getPacientesDI(
			List<IdentificacionPaciente> identificacionPacientes) {
		List<Paciente> pacientes = new ArrayList<>();
		for (IdentificacionPaciente identificacionPaciente : identificacionPacientes) {
			pacientes.add(identificacionPaciente.getIndividuo());
		}
		return pacientes;
	}

	public void onSelectPacienteClick(SelectEvent event) {
		paciente = (Paciente) event.getObject();
		tipoAtencion = paciente.getTipoAfiliacion();
	}

	public void registrar() {
		if (paciente.getId() == 0) {
			FacesUtil.errorMessage("Debe seleccionar al paciente por favor.");
		} else {
			if (consultaDao.tieneConsultaPaciente(paciente,
					sucursal.getCompania())) {
				FacesUtil
						.errorMessage("El paciente tiene una consulta activa.");
			} else {
				if (tipoConsulta.equals("CO")) {
					consulta.setReconsulta(0);
					consulta.setTipoConsulta(tipoConsulta);
				}
				if (tipoConsulta.equals("RC")) {
					Consulta reconsulta = consultaDao.obtenerReConsulta(
							paciente, sucursal.getCompania());
					consulta.setReconsulta(reconsulta.getId());
					consulta.setTipoConsulta(tipoConsulta);
				}
				consulta.setCompania(sucursal.getCompania());
				consulta.setEstado("AT");
				consulta.setFechaRegistro(new Date());
				consulta.setFechaModificacion(consulta.getFechaRegistro());
				consulta.setPaciente(paciente);
				consulta.setSucursal(sucursal);
				consulta.setUsuarioRegistro(usuario.getLogin());
				consulta = consultaDao.registrar(consulta);
				if (consulta != null) {
					String navigateString;
					if (consulta.getPaciente().getCodigoHc() == null
							|| consulta.getPaciente().getCodigoHc().trim()
									.isEmpty()) {
						navigateString = "/pages/historias_clinicas/registro_historias_clinicas/index.xhtml?idPaciente="
								+ consulta.getPaciente().getId();
					} else {
						navigateString = "/pages/historias_clinicas/registro_detalle_historias_clinicas/historia_medica.xhtml?idPaciente="
								+ consulta.getPaciente().getId();
					}
					sessionMain.setConsulta(consulta);
					FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap()
							.put("historiaClinicaDetalleController", null);
					HttpServletRequest request = (HttpServletRequest) facesContext
							.getExternalContext().getRequest();
					try {
						facesContext.getExternalContext().redirect(
								request.getContextPath() + navigateString);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {

				}
			}
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

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public Sucursal getSucursal() {
		return sucursal;
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

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
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
	 * @return the consulta
	 */
	public Consulta getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta
	 *            the consulta to set
	 */
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	public void setListaPacientes(List<Paciente> listaPacientes) {
		ConsultaController.listaPacientes = listaPacientes;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the tipoConsulta
	 */
	public String getTipoConsulta() {
		return tipoConsulta;
	}

	/**
	 * @param tipoConsulta
	 *            the tipoConsulta to set
	 */
	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public List<PlanesSeguro> getPlanesSeguros() {
		return planesSeguros;
	}

	public void setPlanesSeguros(List<PlanesSeguro> planesSeguros) {
		this.planesSeguros = planesSeguros;
	}

	public String getTipoAtencion() {
		return tipoAtencion;
	}

	public void setTipoAtencion(String tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}

	/**
	 * @return the listaEmpresas
	 */
	public List<PacienteEmpresa> getEmpresas() {
		return empresas;
	}

	/**
	 * @param listaEmpresas
	 *            the listaEmpresas to set
	 */
	public void setEmpresas(List<PacienteEmpresa> listaEmpresas) {
		this.empresas = listaEmpresas;
	}

	/**
	 * @return the ordenesServicios
	 */
	public List<DesgOrdenServicio> getOrdenesServicios() {
		return ordenesServicios;
	}

	/**
	 * @param ordenesServicios
	 *            the ordenesServicios to set
	 */
	public void setOrdenesServicios(List<DesgOrdenServicio> ordenesServicios) {
		this.ordenesServicios = ordenesServicios;
	}

}
