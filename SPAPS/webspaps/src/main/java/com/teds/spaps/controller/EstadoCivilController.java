/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IEstadoCivilDao;
import com.teds.spaps.model.EstadoCivil;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "estadoCivilController")
@ViewScoped
/**
 * @author Cinthia Zabala
 *
 */
public class EstadoCivilController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4041494281757237757L;

	/******* DAO **********/
	private @Inject IEstadoCivilDao estadoCivilDao;
	private @Inject SessionMain sessionMain;
	@Inject
	private Logger log;

	/******* OBJECT **********/
	private EstadoCivil estadoCivil;
	private EstadoCivil estadoCivilSelected;
	private Sucursal sucursalLogin;
	private Usuario usuarioLogin;

	/******* LIST **********/
	private List<EstadoCivil> listaEstadoCivil;
	private String[] listaEstado = { "ACTIVO", "INACTIVO", "ELIMINADO" };

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";

	// columnas
	private String tipoColumnRegistro = "col-md-4"; // 4
	private String tipoColumnTable = "col-md-12"; // 8

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public EstadoCivilController() {
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public EstadoCivil getEstadoCivilSelected() {
		return estadoCivilSelected;
	}

	public void setEstadoCivilSelected(EstadoCivil estadoCivilSelected) {
		this.estadoCivilSelected = estadoCivilSelected;
	}

	public List<EstadoCivil> getListaEstadoCivil() {
		return listaEstadoCivil;
	}

	public void setListaEstadoCivil(List<EstadoCivil> listaEstadoCivil) {
		this.listaEstadoCivil = listaEstadoCivil;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public Sucursal getSucursalLogin() {
		return sucursalLogin;
	}

	public void setSucursalLogin(Sucursal sucursalLogin) {
		this.sucursalLogin = sucursalLogin;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String[] getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
	}

	public String getTipoColumnRegistro() {
		return tipoColumnRegistro;
	}

	public String getTipoColumnTable() {
		return tipoColumnTable;
	}

	public void setTipoColumnRegistro(String tipoColumnRegistro) {
		this.tipoColumnRegistro = tipoColumnRegistro;
	}

	public void setTipoColumnTable(String tipoColumnTable) {
		this.tipoColumnTable = tipoColumnTable;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@PostConstruct
	public void initNew() {
		initConversation();
		estadoCivil = new EstadoCivil();
		estadoCivilSelected = new EstadoCivil();
		usuarioLogin = sessionMain.getUsuarioLogin();
		sucursalLogin = sessionMain.PruebaSucursal();
		listaEstadoCivil = estadoCivilDao.obtenerPorSucursal(sucursalLogin);
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void actionNuevo() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void Init() {
		initConversation();
		estadoCivil = new EstadoCivil();
		estadoCivilSelected = new EstadoCivil();
		listaEstadoCivil = estadoCivilDao.obtenerEstadoCivilOrdenAscPorId();
		// sucursalLogin = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		if (estadoCivilDao.verificarRepetido(estadoCivil,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				log.info("entro a registrar");
				System.out.println("entro a registrar");
				if (estadoCivil.getDescripcion().trim().isEmpty()
						|| getSucursalLogin() == null
						|| getSucursalLogin().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					estadoCivil.setSucursal(getSucursalLogin());
					estadoCivil.setCompania(getSucursalLogin().getCompania());
					estadoCivil
							.setUsuarioRegistro(getUsuarioLogin().getLogin());
					estadoCivil.setFechaRegistro(new Date());
					estadoCivil.setFechaModificacion(estadoCivil
							.getFechaRegistro());
					estadoCivil.setEstado("AC");
					estadoCivilDao.registrar(estadoCivil);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en registro de estadoCivil: "
						+ e.getMessage());
			}
		}
	}

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void actualizar() {
		if (estadoCivilDao.verificarRepetidoUpdate(estadoCivil,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				if (estadoCivil.getDescripcion().trim().isEmpty()
						|| estadoCivil.getEstado().trim().isEmpty()) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					estadoCivil.setFechaModificacion(new Date());
					estadoCivil
							.setUsuarioRegistro(getUsuarioLogin().getLogin());
					if (getEstado().equals("ACTIVO")
							|| getEstado().equals("AC")) {
						estadoCivil.setEstado("AC");
					} else {
						if (getEstado().equals("INACTIVO")
								|| getEstado().equals("IN")) {
							estadoCivil.setEstado("IN");
						} else {
							if (getEstado().equals("ELIMINADO")
									|| getEstado().equals("RM")) {
								estadoCivil.setEstado("RM");
							}
						}
					}
					estadoCivilDao.modificar(estadoCivil);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en modificacion de estadoCivil: "
						+ e.getMessage());
			}
		}
	}

	public void eliminar() {
		try {
			estadoCivilDao.eliminar(estadoCivil);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de estadoCivil: "
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

	public void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		tipoColumnTable = "col-md-8";
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

}
