/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IExamenFisicoSignosVitalesDao;
import com.teds.spaps.interfaces.dao.IHistoriaClinicaDao;
import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.ExamenFisicoSignosVitales;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "examenFisicoController")
@ViewScoped
public class ExamenFisicoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -716221131828218086L;
	/******* DAO **********/
	private @Inject IExamenFisicoSignosVitalesDao examenFisicoSignosVitalesDao;
	private @Inject SessionMain sessionMain;
	private @Inject IHistoriaClinicaDao historiaClinicaDao;
	private @Inject IPersonalDao personalDao;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;

	/******* OBJECT **********/
	private ExamenFisicoSignosVitales examenFisicoSignosVitales;
	private ExamenFisicoSignosVitales examenFisicoSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private Personal selectedMedico;
	private Consulta consulta;
	private Paciente paciente;

	/******* LIST **********/
	private List<ExamenFisicoSignosVitales> listaExamenFisico;
	private List<HistoriaClinica> listaHistoriaClinica;
	public static List<Paciente> listaPacientes;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String tipoBusqueda;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public ExamenFisicoController() {
	}

	public ExamenFisicoSignosVitales getExamenFisico() {
		return examenFisicoSignosVitales;
	}

	public ExamenFisicoSignosVitales getExamenFisicoSelected() {
		return examenFisicoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<ExamenFisicoSignosVitales> getListaExamenFisico() {
		return listaExamenFisico;
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

	public void setExamenFisico(
			ExamenFisicoSignosVitales examenFisicoSignosVitales) {
		this.examenFisicoSignosVitales = examenFisicoSignosVitales;
	}

	public void setExamenFisicoSelected(
			ExamenFisicoSignosVitales examenFisicoSelected) {
		this.examenFisicoSelected = examenFisicoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaExamenFisico(
			List<ExamenFisicoSignosVitales> listaExamenFisico) {
		this.listaExamenFisico = listaExamenFisico;
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

	public List<HistoriaClinica> getListaHistoriaClinica() {
		return listaHistoriaClinica;
	}

	public void setListaHistoriaClinica(
			List<HistoriaClinica> listaHistoriaClinica) {
		this.listaHistoriaClinica = listaHistoriaClinica;
	}

	public Personal getSelectedMedico() {
		return selectedMedico;
	}

	public void setSelectedMedico(Personal selectedMedico) {
		this.selectedMedico = selectedMedico;
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

	@PostConstruct
	public void initNew() {
		examenFisicoSignosVitales = new ExamenFisicoSignosVitales();
		examenFisicoSelected = new ExamenFisicoSignosVitales();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		consulta = sessionMain.getConsulta();
		listaExamenFisico = examenFisicoSignosVitalesDao
				.obtenerListaCompania(sucursal.getCompania());
		listaHistoriaClinica = new ArrayList<HistoriaClinica>();
		List<Personal> listPersonals = new ArrayList<Personal>();
		listPersonals = personalDao.obtenerPorUsuario(sessionMain
				.getUsuarioLogin());
		if (listPersonals.size() == 1) {
			selectedMedico = listPersonals.get(0);
		} else
			selectedMedico = new Personal();
		tipoBusqueda = "NP";
		paciente = new Paciente();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public List<Paciente> onCompleteFind(String query) {
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
		return listaPacientes;
	}

	public void onSelectPacienteClick(SelectEvent event) {
		paciente = (Paciente) event.getObject();
		if (historiaClinicaDao.verificarHC(paciente)) {
			HistoriaClinica historiaClinica = historiaClinicaDao
					.obtenerPorPaciente(paciente);
			examenFisicoSignosVitales.setHistoriaClinica(historiaClinica);
		} else {
			FacesUtil
					.errorMessage("El paciente no tiene una historia clinica registrada.");
			paciente = null;
		}
	}

	private List<Paciente> getPacientesDI(
			List<IdentificacionPaciente> identificacionPacientes) {
		List<Paciente> pacientes = new ArrayList<>();
		for (IdentificacionPaciente identificacionPaciente : identificacionPacientes) {
			pacientes.add(identificacionPaciente.getIndividuo());
		}
		return pacientes;
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
		/*
		 * if (estado.equals("AC")) { setEstado("ACTIVO"); } else { if
		 * (estado.equals("IN")) { setEstado("INACTIVO"); } else {
		 * setEstado("ELIMINADO"); } }
		 */
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void calcularPA() {
		if (examenFisicoSignosVitales.getPresionSistolica() == 0) {
			FacesUtil
					.errorMessage("Debe ingresar la presión sistólica para la clasificación respectiva.");
			return;
		} else {
			if (examenFisicoSignosVitales.getPresionDiastolica() == 0) {
				FacesUtil
						.errorMessage("Debe ingresar la presión diastólica para la clasificación respectiva.");
				return;
			} else {
				if (examenFisicoSignosVitales.getPresionSistolica() < 120
						&& examenFisicoSignosVitales.getPresionDiastolica() < 80)
					examenFisicoSignosVitales
							.setClasificacionPresionArterial("Óptima.");
				if (examenFisicoSignosVitales.getPresionSistolica() >= 120
						&& examenFisicoSignosVitales.getPresionSistolica() <= 139
						|| examenFisicoSignosVitales.getPresionDiastolica() >= 80
						&& examenFisicoSignosVitales.getPresionDiastolica() <= 89)
					examenFisicoSignosVitales
							.setClasificacionPresionArterial("Prehipertensión.");
				if (examenFisicoSignosVitales.getPresionSistolica() < 130
						&& examenFisicoSignosVitales.getPresionDiastolica() < 85)
					examenFisicoSignosVitales
							.setClasificacionPresionArterial("Normal.");
				if (examenFisicoSignosVitales.getPresionSistolica() >= 130
						&& examenFisicoSignosVitales.getPresionSistolica() <= 139
						|| examenFisicoSignosVitales.getPresionDiastolica() >= 85
						&& examenFisicoSignosVitales.getPresionDiastolica() <= 89)
					examenFisicoSignosVitales
							.setClasificacionPresionArterial("Normal Alta.");
				if (examenFisicoSignosVitales.getPresionSistolica() >= 140
						&& examenFisicoSignosVitales.getPresionSistolica() <= 159
						|| examenFisicoSignosVitales.getPresionDiastolica() >= 90
						&& examenFisicoSignosVitales.getPresionDiastolica() <= 99)
					examenFisicoSignosVitales
							.setClasificacionPresionArterial("Hipertensión grado 1.");
				if (examenFisicoSignosVitales.getPresionSistolica() >= 160
						&& examenFisicoSignosVitales.getPresionSistolica() <= 179
						|| examenFisicoSignosVitales.getPresionDiastolica() >= 100
						&& examenFisicoSignosVitales.getPresionDiastolica() <= 109)
					examenFisicoSignosVitales
							.setClasificacionPresionArterial("Hipertensión grado 2.");
				if (examenFisicoSignosVitales.getPresionSistolica() >= 180
						|| examenFisicoSignosVitales.getPresionDiastolica() >= 110)
					examenFisicoSignosVitales
							.setClasificacionPresionArterial("Hipertensión grado 3.");
			}
		}
	}

	public void calcularIMC() {
		if (examenFisicoSignosVitales.getPeso() == 0) {
			FacesUtil
					.errorMessage("Debe ingresar el peso para la clasificación respectiva del IMC.");
			System.out.println("peso == 0");
			return;
		} else {
			if (examenFisicoSignosVitales.getAltura() == 0) {
				FacesUtil
						.errorMessage("Debe ingresar la altura para la clasificación respectiva del IMC.");
				System.out.println("altura == 0");
				return;
			} else {
				System.out.println("valor = "
						+ examenFisicoSignosVitales.getAltura());
				float resultado = (float) (examenFisicoSignosVitales.getPeso() / Math
						.pow(examenFisicoSignosVitales.getAltura(), 2));
				System.out.println("peso = "
						+ Float.toString(examenFisicoSignosVitales.getPeso()));
				System.out
						.println("altura = "
								+ Float.toString(examenFisicoSignosVitales
										.getAltura()));
				System.out.println("imc = " + Float.toString(resultado));
				examenFisicoSignosVitales.setIndiceMasaCorporal(resultado);
				if (resultado < 16)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Delgadez severa.");
				if (resultado >= 16 && resultado <= 16.99)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Delgadez moderada.");
				if (resultado >= 17 && resultado <= 18.49)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Delgadez no muy pronunciada.");
				if (resultado >= 18.5 && resultado <= 24.99)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Normal");
				if (resultado >= 25 && resultado <= 27.49)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Sobrepeso");
				if (resultado >= 27.5 && resultado <= 29.99)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Preobesidad.");
				if (resultado >= 30 && resultado <= 34.99)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Obesidad grado I.");
				if (resultado >= 35 && resultado <= 39.99)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Obesidad grado II.");
				if (resultado >= 40)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Obesidad grado III.");
				System.out.println("clase imc = "
						+ examenFisicoSignosVitales
								.getClasificacionIndiceMasaCorporal());
			}
		}
	}

	public List<HistoriaClinica> onCompleteHistoriaClinica(String query) {
		String upperQuery = query.toUpperCase();
		listaHistoriaClinica = historiaClinicaDao.obtenerAutoComplete(
				upperQuery, sucursal.getCompania());
		return listaHistoriaClinica;
	}

	public void onRowSelectHistoriaClinicaClick(SelectEvent event) {
		String codigo = event.getObject().toString();
		HistoriaClinica t = historiaClinicaDao.obtenerPorCodigo(codigo,
				sucursal.getCompania());
		examenFisicoSignosVitales.setHistoriaClinica(t);
	}

	public void registrar() {
		try {
			if (getSucursal() == null || getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				examenFisicoSignosVitales.setConsulta(consulta);
				examenFisicoSignosVitales.setEnfermera(selectedMedico);
				examenFisicoSignosVitales.setEstado("II");
				examenFisicoSignosVitales.setSucursal(getSucursal());
				examenFisicoSignosVitales.setCompania(getSucursal()
						.getCompania());
				examenFisicoSignosVitales.setFechaRegistro(new Date());
				examenFisicoSignosVitales
						.setFechaModificacion(examenFisicoSignosVitales
								.getFechaRegistro());
				examenFisicoSignosVitales.setUsuarioRegistro(getUsuario()
						.getLogin());
				examenFisicoSignosVitalesDao
						.registrar(examenFisicoSignosVitales);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de examenFisico: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (examenFisicoSignosVitales.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				examenFisicoSignosVitales.setFechaModificacion(new Date());
				examenFisicoSignosVitales.setUsuarioRegistro(sessionMain
						.getUsuarioLogin().getLogin());
				examenFisicoSignosVitalesDao
						.modificar(examenFisicoSignosVitales);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de examenFisico: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			examenFisicoSignosVitalesDao.eliminar(getExamenFisico());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de examenFisico: "
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
	 * @return the tipoBusqueda
	 */
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	/**
	 * @param tipoBusqueda
	 *            the tipoBusqueda to set
	 */
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * @return the listaPacientes
	 */
	public static List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	/**
	 * @param listaPacientes
	 *            the listaPacientes to set
	 */
	public static void setListaPacientes(List<Paciente> listaPacientes) {
		ExamenFisicoController.listaPacientes = listaPacientes;
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente
	 *            the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
