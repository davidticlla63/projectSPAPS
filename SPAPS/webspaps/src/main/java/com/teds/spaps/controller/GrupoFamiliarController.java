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

import com.teds.spaps.interfaces.dao.IGrupoFamiliarDao;
import com.teds.spaps.model.GrupoFamiliar;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "grupoFamiliarController")
@ViewScoped
/**
 * @author ANITA
 *
 */
public class GrupoFamiliarController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5933148600485216988L;
	/******* DAO **********/
	private @Inject IGrupoFamiliarDao grupoFamiliarDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private GrupoFamiliar grupoFamiliar;
	private GrupoFamiliar grupoFamiliarSelected;
	private Sucursal sucursalLogin;
	private Usuario usuarioLogin;

	/******* LIST **********/
	private List<GrupoFamiliar> listaGrupoFamiliar;
	private String[] listaEstado = { "ACTIVO", "INACTIVO", "ELIMINADO" };
	private String[] listaSexo = { "Femenino", "Masculino" };

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";
	private String sexo = "";

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public GrupoFamiliarController() {
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}

	public GrupoFamiliar getGrupoFamiliarSelected() {
		return grupoFamiliarSelected;
	}

	public List<GrupoFamiliar> getListaGrupoFamiliar() {
		return listaGrupoFamiliar;
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

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

	public void setGrupoFamiliarSelected(GrupoFamiliar grupoFamiliarSelected) {
		this.grupoFamiliarSelected = grupoFamiliarSelected;
	}

	public void setListaGrupoFamiliar(List<GrupoFamiliar> listaGrupoFamiliar) {
		this.listaGrupoFamiliar = listaGrupoFamiliar;
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

	public Sucursal getSucursalLogin() {
		return sucursalLogin;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public String[] getListaEstado() {
		return listaEstado;
	}

	public String[] getListaSexo() {
		return listaSexo;
	}

	public String getEstado() {
		return estado;
	}

	public void setSucursalLogin(Sucursal sucursalLogin) {
		this.sucursalLogin = sucursalLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
	}

	public void setListaSexo(String[] listaSexo) {
		this.listaSexo = listaSexo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@PostConstruct
	public void initNew() {
		grupoFamiliar = new GrupoFamiliar();
		grupoFamiliarSelected = new GrupoFamiliar();
		sucursalLogin = sessionMain.PruebaSucursal();
		usuarioLogin = sessionMain.getUsuarioLogin();
		listaGrupoFamiliar = grupoFamiliarDao.obtenerPorSucursal(sucursalLogin);
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
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
		estado = grupoFamiliar.getEstado();
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void actionNuevo() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void actionCancelar() {
		crear = true;
		registrar = false;
		grupoFamiliar = new GrupoFamiliar();
	}

	public void registrar() {
		if (grupoFamiliarDao.verificarRepetido(grupoFamiliar,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el grupo familiar.");
		} else {
			try {
				if (grupoFamiliar.getCodigo().trim().isEmpty()
						|| grupoFamiliar.getNombre().trim().isEmpty()
						|| grupoFamiliar.getSexo() == null
						|| getSucursalLogin() == null
						|| getSucursalLogin().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					System.out.println("sexo = " + grupoFamiliar.getSexo());
					System.out.println("");
					grupoFamiliar.setEstado("AC");
					grupoFamiliar.setSucursal(getSucursalLogin());
					grupoFamiliar.setCompania(getSucursalLogin().getCompania());
					grupoFamiliar.setFechaRegistro(new Date());
					grupoFamiliar.setFechaModificacion(grupoFamiliar
							.getFechaRegistro());
					grupoFamiliar.setUsuarioRegistro(getUsuarioLogin()
							.getLogin());
					grupoFamiliarDao.registrar(grupoFamiliar);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en registro de grupoFamiliar: "
						+ e.getMessage());
			}
		}
	}

	public void actualizar() {
		if (grupoFamiliarDao.verificarRepetidoUpdate(grupoFamiliar,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el grupo familiar.");
		} else {
			try {
				if (grupoFamiliar.getCodigo().trim().isEmpty()
						|| grupoFamiliar.getNombre().trim().isEmpty()
						|| grupoFamiliar.getSexo() == null
						|| getEstado().trim().isEmpty()
						|| getSucursalLogin() == null
						|| getSucursalLogin().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					grupoFamiliar.setSucursal(getSucursalLogin());
					grupoFamiliar.setCompania(getSucursalLogin().getCompania());
					grupoFamiliar.setFechaModificacion(new Date());
					grupoFamiliar.setUsuarioRegistro(getUsuarioLogin()
							.getLogin());
					grupoFamiliarDao.modificar(grupoFamiliar);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en modificacion de grupoFamiliar: "
						+ e.getMessage());
			}
		}
	}

	public void eliminar() {
		try {
			grupoFamiliarDao.eliminar(grupoFamiliar);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de grupoFamiliar: "
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
