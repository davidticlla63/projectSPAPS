/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
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

import com.teds.spaps.interfaces.dao.IGrupoSanguineoDao;
import com.teds.spaps.model.GrupoSanguineo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "grupoSanguineoController")
@ViewScoped
/**
 * @author ANITA
 *
 */
public class GrupoSanguineoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2245907090770606622L;

	/******* DAO **********/
	private @Inject IGrupoSanguineoDao grupoSanguineoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private GrupoSanguineo grupoSanguineo;
	private GrupoSanguineo grupoSanguineoSelected;
	private Sucursal sucursalLogin;
	private Usuario usuarioLogin;

	/******* LIST **********/
	private List<GrupoSanguineo> listaGrupoSanguineo;
	private String[] listaEstado = { "ACTIVO", "INACTIVO", "ELIMINADO" };

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public GrupoSanguineoController() {
	}

	public GrupoSanguineo getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public GrupoSanguineo getGrupoSanguineoSelected() {
		return grupoSanguineoSelected;
	}

	public Sucursal getSucursal() {
		return sucursalLogin;
	}

	public List<GrupoSanguineo> getListaGrupoSanguineo() {
		return listaGrupoSanguineo;
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

	public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public void setGrupoSanguineoSelected(GrupoSanguineo grupoSanguineoSelected) {
		this.grupoSanguineoSelected = grupoSanguineoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursalLogin = sucursal;
	}

	public void setListaGrupoSanguineo(List<GrupoSanguineo> listaGrupoSanguineo) {
		this.listaGrupoSanguineo = listaGrupoSanguineo;
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

	public String[] getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void actionNuevo() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void actionCancelar() {
		crear = true;
		registrar = false;
		grupoSanguineo = new GrupoSanguineo();
		grupoSanguineoSelected = new GrupoSanguineo();
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		grupoSanguineo = grupoSanguineoSelected;
		estado = grupoSanguineo.getEstado();
		if (estado.equals("AC")) {
			setEstado("ACTIVO");
		} else {
			if (estado.equals("IN")) {
				setEstado("INACTIVO");
			} else {
				setEstado("ELIMINADO");
			}
		}
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("formTableGrupoSanguineo:dataTableGrupoSanguineo");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	@PostConstruct
	public void initNew() {
		grupoSanguineo = new GrupoSanguineo();
		grupoSanguineoSelected = new GrupoSanguineo();
		usuarioLogin = sessionMain.getUsuarioLogin();
		sucursalLogin = sessionMain.PruebaSucursal();
		listaGrupoSanguineo = grupoSanguineoDao
				.obtenerPorCompania(sucursalLogin.getCompania());
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (grupoSanguineo.getDescripcion().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				grupoSanguineo.setSucursal(getSucursalLogin());
				grupoSanguineo.setCompania(getSucursalLogin().getCompania());
				grupoSanguineo.setUsuarioRegistro(getUsuarioLogin().getLogin());
				grupoSanguineo.setEstado("AC");
				grupoSanguineo.setFechaRegistro(new Date());
				grupoSanguineo.setFechaModificacion(grupoSanguineo
						.getFechaRegistro());
				grupoSanguineoDao.registrar(grupoSanguineo);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de grupoSanguineo: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (grupoSanguineo.getDescripcion().trim().isEmpty()
					|| getEstado().trim().isEmpty() || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				grupoSanguineo.setUsuarioRegistro(getUsuarioLogin().getLogin());
				if (getEstado().equals("ACTIVO") || getEstado().equals("AC")) {
					grupoSanguineo.setEstado("AC");
				} else {
					if (getEstado().equals("INACTIVO")
							|| getEstado().equals("IN")) {
						grupoSanguineo.setEstado("IN");
					} else {
						if (getEstado().equals("ELIMINADO")
								|| getEstado().equals("RM")) {
						}
						grupoSanguineo.setEstado("RM");
					}
				}
			}
			grupoSanguineo.setFechaModificacion(new Date());
			grupoSanguineoDao.modificar(grupoSanguineo);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en modificacion de grupoSanguineo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			grupoSanguineoDao.eliminar(grupoSanguineo);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de grupoSanguineo: "
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

}
