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

import com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao;
import com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao;
import com.teds.spaps.interfaces.dao.IDiagnosticoDao;
import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao;
import com.teds.spaps.model.DetalleDiagnosticoEspecialidad;
import com.teds.spaps.model.DetalleTipoDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoDiagnostico;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "diagnosticoController")
@ViewScoped
public class DiagnosticoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3603065705613537017L;
	/******* DAO **********/
	private @Inject IDiagnosticoDao diagnosticoDao;
	private @Inject SessionMain sessionMain;
	private @Inject ITipoDiagnosticoDao tipoDiagnosticoDao;
	private @Inject IDetalleTipoDiagnosticoDao detalleTipoDiagnosticoDao;
	private @Inject IEspecialidadDao especialidadDao;
	private @Inject IDetalleDiagnosticoEspecialidadDao detalleDiagnosticoEspecialidadDao;

	/******* OBJECT **********/
	private Diagnostico diagnostico;
	private Diagnostico diagnosticoSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private TipoDiagnostico tipoDiagnostico;
	private Especialidad especialidad;

	/******* LIST **********/
	private List<Diagnostico> listaDiagnostico;
	private List<TipoDiagnostico> listaTipoDiagnostico;
	private List<DetalleTipoDiagnostico> listaDetalleTipoDiagnostico;
	private List<DetalleTipoDiagnostico> listaDetalleTipoDiagnosticoBackup;
	private List<DetalleDiagnosticoEspecialidad> listaDetalleDiagnosticoEspecialidad;
	private List<DetalleDiagnosticoEspecialidad> listaBackupDetalleDiagnostico;
	private List<Especialidad> listaEspecialidades;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public DiagnosticoController() {
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public Diagnostico getDiagnosticoSelected() {
		return diagnosticoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Diagnostico> getListaDiagnostico() {
		return listaDiagnostico;
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

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
		this.diagnosticoSelected = diagnosticoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaDiagnostico(List<Diagnostico> listaDiagnostico) {
		this.listaDiagnostico = listaDiagnostico;
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

	public TipoDiagnostico getTipoDiagnostico() {
		return tipoDiagnostico;
	}

	public void setTipoDiagnostico(TipoDiagnostico tipoDiagnostico) {
		this.tipoDiagnostico = tipoDiagnostico;
	}

	public List<DetalleTipoDiagnostico> getListaDetalleTipoDiagnostico() {
		return listaDetalleTipoDiagnostico;
	}

	public void setListaDetalleTipoDiagnostico(
			List<DetalleTipoDiagnostico> listaDetalleTipoEstudiio) {
		this.listaDetalleTipoDiagnostico = listaDetalleTipoEstudiio;
	}

	public List<TipoDiagnostico> getListaTipoDiagnostico() {
		return listaTipoDiagnostico;
	}

	public void setListaTipoDiagnostico(
			List<TipoDiagnostico> listaTipoDiagnostico) {
		this.listaTipoDiagnostico = listaTipoDiagnostico;
	}

	public List<DetalleTipoDiagnostico> getListaDetalleTipoDiagnosticoBackup() {
		return listaDetalleTipoDiagnosticoBackup;
	}

	public void setListaDetalleTipoDiagnosticoBackup(
			List<DetalleTipoDiagnostico> listaDetalleTipoDiagnosticoBackup) {
		this.listaDetalleTipoDiagnosticoBackup = listaDetalleTipoDiagnosticoBackup;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public List<DetalleDiagnosticoEspecialidad> getListaDetalleDiagnosticoEspecialidad() {
		return listaDetalleDiagnosticoEspecialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public void setListaDetalleDiagnosticoEspecialidad(
			List<DetalleDiagnosticoEspecialidad> listaDetalleDiagnosticoEspecialidad) {
		this.listaDetalleDiagnosticoEspecialidad = listaDetalleDiagnosticoEspecialidad;
	}

	public List<Especialidad> getListaEspecialidades() {
		return listaEspecialidades;
	}

	public void setListaEspecialidades(List<Especialidad> listaEspecialidades) {
		this.listaEspecialidades = listaEspecialidades;
	}

	public List<DetalleDiagnosticoEspecialidad> getListaBackupDetalleDiagnostico() {
		return listaBackupDetalleDiagnostico;
	}

	public void setListaBackupDetalleDiagnostico(
			List<DetalleDiagnosticoEspecialidad> listaBackupDetalleDiagnostico) {
		this.listaBackupDetalleDiagnostico = listaBackupDetalleDiagnostico;
	}

	@PostConstruct
	public void initNew() {
		diagnostico = new Diagnostico();
		diagnosticoSelected = new Diagnostico();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaDiagnostico = diagnosticoDao.obtenerDiagnosticoOrdenAscPorId();
		listaDetalleTipoDiagnostico = new ArrayList<DetalleTipoDiagnostico>();
		listaTipoDiagnostico = new ArrayList<TipoDiagnostico>();
		tipoDiagnostico = new TipoDiagnostico();
		especialidad = new Especialidad();
		listaDetalleDiagnosticoEspecialidad = new ArrayList<DetalleDiagnosticoEspecialidad>();
		listaBackupDetalleDiagnostico = new ArrayList<DetalleDiagnosticoEspecialidad>();
		listaEspecialidades = new ArrayList<Especialidad>();
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
		DetalleDiagnosticoEspecialidad auxiliar = new DetalleDiagnosticoEspecialidad();
		auxiliar.setEspecialidad(t);
		listaDetalleDiagnosticoEspecialidad.add(auxiliar);
	}

	public void onRowDeleteEspecialidad(
			DetalleDiagnosticoEspecialidad detalleAntecedenteEspecialidad) {
		// deleteDetalle(detalleTipoDiagnostico);
		listaDetalleDiagnosticoEspecialidad
				.remove(detalleAntecedenteEspecialidad);
	}

	public List<TipoDiagnostico> onCompleteTipoDiagnostico(String query) {
		String upperQuery = query.toUpperCase();
		listaTipoDiagnostico = tipoDiagnosticoDao.onComplete(upperQuery);
		return listaTipoDiagnostico;
	}

	public boolean verificarTipoDiagnostico(TipoDiagnostico tipoDiagnostico) {
		for (DetalleTipoDiagnostico detalleTipoDiagnostico : listaDetalleTipoDiagnostico) {
			if (detalleTipoDiagnostico.getTipoDiagnostico().equals(
					tipoDiagnostico))
				return true;
		}
		return false;
	}

	public void onRowSelectTipoDiagnosticoClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		TipoDiagnostico t = tipoDiagnosticoDao.obtenerTipoDiagnostico(nombre);
		if (verificarTipoDiagnostico(t)) {
			FacesUtil.errorMessage("Ya se encuentra el tipo de diagnostico.");
		} else {
			DetalleTipoDiagnostico auxiliar = new DetalleTipoDiagnostico();
			auxiliar.setTipoDiagnostico(t);
			listaDetalleTipoDiagnostico.add(auxiliar);
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
		listaDetalleTipoDiagnostico = new ArrayList<DetalleTipoDiagnostico>();
		listaDetalleTipoDiagnostico = detalleTipoDiagnosticoDao
				.obtenerPorDiagnostico(getDiagnostico());
		listaDetalleTipoDiagnosticoBackup = detalleTipoDiagnosticoDao
				.obtenerPorDiagnostico(getDiagnostico());
		listaDetalleDiagnosticoEspecialidad = detalleDiagnosticoEspecialidadDao
				.obtenerPorDiagnostico(diagnostico);
		listaBackupDetalleDiagnostico = detalleDiagnosticoEspecialidadDao
				.obtenerPorDiagnostico(diagnostico);
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void deleteDetalle(DetalleTipoDiagnostico detalleTipoDiagnostico) {
		if (listaDetalleTipoDiagnostico.size() > 0) {
			for (int i = 0; i < listaDetalleTipoDiagnostico.size(); i++) {
				DetalleTipoDiagnostico auxiliar = listaDetalleTipoDiagnostico
						.get(i);
				if (auxiliar.getTipoDiagnostico().equals(
						detalleTipoDiagnostico.getTipoDiagnostico())) {
					listaDetalleTipoDiagnostico.remove(i);
				}
			}
			FacesContext.getCurrentInstance().renderResponse();
			resetearFitrosTabla("form001:formDetalle:cars1");
		}
	}

	public void onRowEdit(DetalleTipoDiagnostico detalleTipoDiagnostico) {
		deleteDetalle(detalleTipoDiagnostico);
	}

	public void onRowDelete(DetalleTipoDiagnostico detalleTipoDiagnostico) {
		// deleteDetalle(detalleTipoDiagnostico);
		listaDetalleTipoDiagnostico.remove(detalleTipoDiagnostico);
	}

	public void registrar() {
		if (diagnosticoDao.verificarRepetido(diagnostico,
				sucursal.getCompania())) {
			FacesUtil.errorMessage("Ya existe el diagnostico");
		} else {
			try {
				if (diagnostico.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					diagnostico.setEstado("AC");
					diagnostico.setSucursal(getSucursal());
					diagnostico.setCompania(getSucursal().getCompania());
					diagnostico.setFechaRegistro(new Date());
					diagnostico.setFechaModificacion(diagnostico
							.getFechaRegistro());
					diagnostico.setUsuarioRegistro(getUsuario().getLogin());
					diagnosticoDao.registrar(diagnostico,
							listaDetalleTipoDiagnostico,
							listaDetalleDiagnosticoEspecialidad);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en registro de diagnostico: "
						+ e.getMessage());
			}
		}
	}

	public void actualizar() {
		if (diagnosticoDao.verificarRepetidoUpdate(diagnostico,
				sucursal.getCompania())) {
			FacesUtil.errorMessage("Ya existe el diagnostico");
		} else {
			try {
				if (diagnostico.getDescripcion().trim().isEmpty()
						|| diagnostico.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					diagnostico.setFechaModificacion(new Date());
					diagnostico.setUsuarioRegistro(getUsuario().getLogin());
					diagnosticoDao.modificar(diagnostico,
							listaDetalleTipoDiagnosticoBackup,
							listaDetalleTipoDiagnostico,
							listaBackupDetalleDiagnostico,
							listaDetalleDiagnosticoEspecialidad);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en modificacion de diagnostico: "
						+ e.getMessage());
			}
		}
	}

	public void eliminar() {
		try {
			diagnosticoDao.eliminar(getDiagnostico(),
					listaDetalleTipoDiagnosticoBackup,
					listaDetalleDiagnosticoEspecialidad);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de diagnostico: "
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
