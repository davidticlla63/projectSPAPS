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

import com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao;
import com.teds.spaps.interfaces.dao.IEstudioDao;
import com.teds.spaps.interfaces.dao.ITipoEstudioDao;
import com.teds.spaps.model.DetalleTipoEstudio;
import com.teds.spaps.model.Estudio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoEstudio;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "estudioController")
@ViewScoped
public class EstudioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6642247835952062624L;
	/******* DAO **********/
	private @Inject IEstudioDao estudioDao;
	private @Inject SessionMain sessionMain;
	private @Inject ITipoEstudioDao tipoEstudioDao;
	private @Inject IDetalleTipoEstudioDao detalleTipoEstudioDao;

	/******* OBJECT **********/
	private Estudio estudio;
	private Estudio estudioSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private TipoEstudio tipoEstudio;

	/******* LIST **********/
	private List<Estudio> listaEstudio;
	private List<TipoEstudio> listaTipoEstudio;
	private List<DetalleTipoEstudio> listaDetalleTipoEstudio;
	private List<DetalleTipoEstudio> listaDetalleTipoEstudioBackup;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public EstudioController() {
	}

	public Estudio getEstudio() {
		return estudio;
	}

	public Estudio getEstudioSelected() {
		return estudioSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Estudio> getListaEstudio() {
		return listaEstudio;
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

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public void setEstudioSelected(Estudio estudioSelected) {
		this.estudioSelected = estudioSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaEstudio(List<Estudio> listaEstudio) {
		this.listaEstudio = listaEstudio;
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

	public TipoEstudio getTipoEstudio() {
		return tipoEstudio;
	}

	public void setTipoEstudio(TipoEstudio tipoEstudio) {
		this.tipoEstudio = tipoEstudio;
	}

	public List<DetalleTipoEstudio> getListaDetalleTipoEstudio() {
		return listaDetalleTipoEstudio;
	}

	public void setListaDetalleTipoEstudio(
			List<DetalleTipoEstudio> listaDetalleTipoEstudiio) {
		this.listaDetalleTipoEstudio = listaDetalleTipoEstudiio;
	}

	public List<TipoEstudio> getListaTipoEstudio() {
		return listaTipoEstudio;
	}

	public void setListaTipoEstudio(List<TipoEstudio> listaTipoEstudio) {
		this.listaTipoEstudio = listaTipoEstudio;
	}

	public List<DetalleTipoEstudio> getListaDetalleTipoEstudioBackup() {
		return listaDetalleTipoEstudioBackup;
	}

	public void setListaDetalleTipoEstudioBackup(
			List<DetalleTipoEstudio> listaDetalleTipoEstudioBackup) {
		this.listaDetalleTipoEstudioBackup = listaDetalleTipoEstudioBackup;
	}

	@PostConstruct
	public void initNew() {
		estudio = new Estudio();
		estudioSelected = new Estudio();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaEstudio = estudioDao.obtenerEstudioOrdenAscPorId();
		listaDetalleTipoEstudio = new ArrayList<DetalleTipoEstudio>();
		listaTipoEstudio = new ArrayList<TipoEstudio>();
		tipoEstudio = new TipoEstudio();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public List<TipoEstudio> onCompleteTipoEstudio(String query) {
		String upperQuery = query.toUpperCase();
		listaTipoEstudio = tipoEstudioDao.onComplete(upperQuery);
		return listaTipoEstudio;
	}

	public void onRowSelectTipoEstudioClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		TipoEstudio t = tipoEstudioDao.obtenerTipoEstudio(nombre);
		DetalleTipoEstudio auxiliar = new DetalleTipoEstudio();
		auxiliar.setTipoEstudio(t);
		listaDetalleTipoEstudio.add(auxiliar);
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
		listaDetalleTipoEstudio = new ArrayList<DetalleTipoEstudio>();
		listaDetalleTipoEstudio = detalleTipoEstudioDao
				.obtenerPorEstudio(getEstudio());
		listaDetalleTipoEstudioBackup = detalleTipoEstudioDao
				.obtenerPorEstudio(getEstudio());
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void deleteDetalle(DetalleTipoEstudio detalleTipoEstudio) {
		if (listaDetalleTipoEstudio.size() > 0) {
			for (int i = 0; i < listaDetalleTipoEstudio.size(); i++) {
				DetalleTipoEstudio auxiliar = listaDetalleTipoEstudio.get(i);
				if (auxiliar.getTipoEstudio().equals(
						detalleTipoEstudio.getTipoEstudio())) {
					listaDetalleTipoEstudio.remove(i);
				}
			}
			FacesContext.getCurrentInstance().renderResponse();
			resetearFitrosTabla("form001:formDetalle:cars1");
		}
	}

	public void onRowEdit(DetalleTipoEstudio detalleTipoEstudio) {
		deleteDetalle(detalleTipoEstudio);
	}

	public void onRowDelete(DetalleTipoEstudio detalleTipoEstudio) {
		// deleteDetalle(detalleTipoEstudio);
		listaDetalleTipoEstudio.remove(detalleTipoEstudio);
	}

	public void registrar() {
		try {
			if (estudio.getNombre().trim().isEmpty() || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				estudio.setEstado("AC");
				estudio.setSucursal(getSucursal());
				estudio.setCompania(getSucursal().getCompania());
				estudio.setFechaRegistro(new Date());
				estudio.setFechaModificacion(estudio.getFechaRegistro());
				estudio.setUsuarioRegistro(getUsuario().getLogin());
				estudioDao.registrar(estudio, listaDetalleTipoEstudio);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de estudio: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (estudio.getNombre().trim().isEmpty()
					|| estudio.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				estudio.setFechaModificacion(new Date());
				estudio.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				estudioDao.modificar(estudio, listaDetalleTipoEstudioBackup,
						listaDetalleTipoEstudio);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de estudio: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			estudioDao.eliminar(getEstudio(), listaDetalleTipoEstudioBackup);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de estudio: "
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
