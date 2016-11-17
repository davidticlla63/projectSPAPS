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

import com.teds.spaps.interfaces.dao.IAntecedenteDao;
import com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao;
import com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao;
import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.interfaces.dao.ITipoAntecedenteDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.DetalleAntecedenteEspecialidad;
import com.teds.spaps.model.DetalleTipoAntecedente;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAntecedente;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "antecedenteController")
@ViewScoped
public class AntecedenteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405659926690000424L;
	/******* DAO **********/
	private @Inject IAntecedenteDao antecedenteDao;
	private @Inject SessionMain sessionMain;
	private @Inject IEspecialidadDao especialidadDao;
	private @Inject IDetalleAntecedenteEspecialidadDao detalleAntecedenteEspecialidadDao;
	private @Inject ITipoAntecedenteDao tipoAntecedenteDao;
	private @Inject IDetalleTipoAntecedenteDao detalleTipoAntecedenteDao;

	/******* OBJECT **********/
	private Antecedente antecedente;
	private Antecedente antecedenteSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private Especialidad especialidad;
	private TipoAntecedente tipoAntecedente;

	/******* LIST **********/
	private List<Antecedente> listaAntecedente;
	private List<Especialidad> listaEspecialidades;
	private List<Especialidad> listaEspecialidadesSelected;
	private List<DetalleAntecedenteEspecialidad> listaDetalleAntecedenteEspecialidad;
	private List<DetalleAntecedenteEspecialidad> listaBackupDetalle;
	private List<TipoAntecedente> listaTipoAntecedente;
	private List<DetalleTipoAntecedente> listaDetalleTipoAntecedente;
	private List<DetalleTipoAntecedente> listaDetalleTipoAntecedenteBackup;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public AntecedenteController() {
	}

	public Antecedente getAntecedente() {
		return antecedente;
	}

	public Antecedente getAntecedenteSelected() {
		return antecedenteSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Antecedente> getListaAntecedente() {
		return listaAntecedente;
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

	public void setAntecedente(Antecedente antecedente) {
		this.antecedente = antecedente;
	}

	public void setAntecedenteSelected(Antecedente antecedenteSelected) {
		this.antecedenteSelected = antecedenteSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaAntecedente(List<Antecedente> listaAntecedente) {
		this.listaAntecedente = listaAntecedente;
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

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public List<Especialidad> getListaEspecialidades() {
		return listaEspecialidades;
	}

	public void setListaEspecialidades(List<Especialidad> listaEspecialidades) {
		this.listaEspecialidades = listaEspecialidades;
	}

	public List<Especialidad> getListaEspecialidadesSelected() {
		return listaEspecialidadesSelected;
	}

	public void setListaEspecialidadesSelected(
			List<Especialidad> listaEspecialidadesSelected) {
		this.listaEspecialidadesSelected = listaEspecialidadesSelected;
	}

	public List<DetalleAntecedenteEspecialidad> getListaDetalleAntecedenteEspecialidad() {
		return listaDetalleAntecedenteEspecialidad;
	}

	public void setListaDetalleAntecedenteEspecialidad(
			List<DetalleAntecedenteEspecialidad> listaDetalleAntecedenteEspecialidad) {
		this.listaDetalleAntecedenteEspecialidad = listaDetalleAntecedenteEspecialidad;
	}

	public List<DetalleAntecedenteEspecialidad> getListaBackupDetalle() {
		return listaBackupDetalle;
	}

	public void setListaBackupDetalle(
			List<DetalleAntecedenteEspecialidad> listaBackupDetalle) {
		this.listaBackupDetalle = listaBackupDetalle;
	}

	public TipoAntecedente getTipoAntecedente() {
		return tipoAntecedente;
	}

	public void setTipoAntecedente(TipoAntecedente tipoAntecedente) {
		this.tipoAntecedente = tipoAntecedente;
	}

	public List<TipoAntecedente> getListaTipoAntecedente() {
		return listaTipoAntecedente;
	}

	public void setListaTipoAntecedente(
			List<TipoAntecedente> listaTipoAntecedente) {
		this.listaTipoAntecedente = listaTipoAntecedente;
	}

	public List<DetalleTipoAntecedente> getListaDetalleTipoAntecedente() {
		return listaDetalleTipoAntecedente;
	}

	public void setListaDetalleTipoAntecedente(
			List<DetalleTipoAntecedente> listaDetalleTipoAntecedente) {
		this.listaDetalleTipoAntecedente = listaDetalleTipoAntecedente;
	}

	public List<DetalleTipoAntecedente> getListaDetalleTipoAntecedenteBackup() {
		return listaDetalleTipoAntecedenteBackup;
	}

	public void setListaDetalleTipoAntecedenteBackup(
			List<DetalleTipoAntecedente> listaDetalleTipoAntecedenteBackup) {
		this.listaDetalleTipoAntecedenteBackup = listaDetalleTipoAntecedenteBackup;
	}

	@PostConstruct
	public void initNew() {
		antecedente = new Antecedente();
		antecedenteSelected = new Antecedente();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		especialidad = new Especialidad();
		tipoAntecedente = new TipoAntecedente();
		listaEspecialidades = new ArrayList<Especialidad>();
		listaEspecialidadesSelected = new ArrayList<Especialidad>();
		listaAntecedente = antecedenteDao.obtenerAntecedenteOrdenAscPorId();
		listaDetalleAntecedenteEspecialidad = new ArrayList<DetalleAntecedenteEspecialidad>();
		listaBackupDetalle = new ArrayList<DetalleAntecedenteEspecialidad>();
		listaTipoAntecedente = new ArrayList<TipoAntecedente>();
		listaDetalleTipoAntecedente = new ArrayList<DetalleTipoAntecedente>();
		listaDetalleTipoAntecedenteBackup = new ArrayList<DetalleTipoAntecedente>();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public List<Especialidad> completeEspecialidad(String query) {
		String upperQuery = query.toUpperCase();
		listaEspecialidades = especialidadDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listaEspecialidades;
	}

	public void onRowSelectEspecialidadClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		Especialidad t = especialidadDao.obtenerEspecialidad(nombre);
		DetalleAntecedenteEspecialidad auxiliar = new DetalleAntecedenteEspecialidad();
		auxiliar.setEspecialidad(t);
		listaDetalleAntecedenteEspecialidad.add(auxiliar);
	}

	public void onRowDeleteEspecialidad(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad) {
		// deleteDetalle(detalleTipoDiagnostico);
		listaDetalleAntecedenteEspecialidad
				.remove(detalleAntecedenteEspecialidad);
	}

	public List<TipoAntecedente> onCompleteTipoAntecedente(String query) {
		String upperQuery = query.toUpperCase();
		listaTipoAntecedente = tipoAntecedenteDao.onComplete(upperQuery);
		return listaTipoAntecedente;
	}

	public void onRowSelectTipoAntecedenteClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		TipoAntecedente t = tipoAntecedenteDao.obtenerTipoAntecedente(nombre);
		if (verificarTipoAntecedente(t)) {
			FacesUtil.errorMessage("Ya se encuentra el tipo de antecedente.");
		} else {
			DetalleTipoAntecedente auxiliar = new DetalleTipoAntecedente();
			auxiliar.setTipoAntecedente(t);
			listaDetalleTipoAntecedente.add(auxiliar);
		}
	}

	public boolean verificarTipoAntecedente(TipoAntecedente tipoAntecedente) {
		for (DetalleTipoAntecedente detalleTipoAntecedente : listaDetalleTipoAntecedente) {
			if (detalleTipoAntecedente.getTipoAntecedente().equals(
					tipoAntecedente))
				return true;
		}
		return false;
	}

	public void onRowDeleteTipoAntecedente(
			DetalleTipoAntecedente detalleTipoAntecedente) {
		listaDetalleTipoAntecedente.remove(detalleTipoAntecedente);
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
		listaDetalleTipoAntecedente = new ArrayList<DetalleTipoAntecedente>();
		listaDetalleTipoAntecedente = detalleTipoAntecedenteDao
				.obtenerPorAntecedente(antecedente);
		listaDetalleTipoAntecedenteBackup = detalleTipoAntecedenteDao
				.obtenerPorAntecedente(antecedente);
		listaDetalleAntecedenteEspecialidad = detalleAntecedenteEspecialidadDao
				.obtenerPorAntecedente(antecedente);
		listaBackupDetalle = detalleAntecedenteEspecialidadDao
				.obtenerPorAntecedente(antecedente);
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void registrar() {
		try {
			if (antecedenteDao.verificarRepetido(antecedente.getDescripcion(),
					getSucursal())) {
				FacesUtil
						.errorMessage("VALIDACION, Ya existe el presente antecedente");
			} else {
				if (antecedente.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
				} else {
					antecedente.setEstado("AC");
					antecedente.setSucursal(getSucursal());
					antecedente.setCompania(getSucursal().getCompania());
					antecedente.setFechaRegistro(new Date());
					antecedente.setFechaModificacion(antecedente
							.getFechaRegistro());
					antecedente.setUsuarioRegistro(getUsuario().getLogin());
					antecedenteDao.registrar(antecedente,
							listaDetalleAntecedenteEspecialidad,
							listaDetalleTipoAntecedente);
				}
			}
			initNew();
		} catch (Exception e) {
			System.out.println("Error en registro de antecedente: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (antecedente.getDescripcion().trim().isEmpty()
					|| antecedente.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				antecedente.setFechaModificacion(new Date());
				antecedente.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				antecedenteDao.modificar(antecedente, listaBackupDetalle,
						listaDetalleAntecedenteEspecialidad,
						listaDetalleTipoAntecedenteBackup,
						listaDetalleTipoAntecedente);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de antecedente: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			antecedenteDao.eliminar(getAntecedente(),
					listaDetalleTipoAntecedente,
					listaDetalleAntecedenteEspecialidad);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de antecedente: "
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

}
