package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IRolDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.interfaces.dao.IUsuarioDao;
import com.teds.spaps.interfaces.dao.IUsuarioSucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "userController")
@ViewScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;

	// DAO
	private @Inject IUsuarioDao usuarioDao;
	private @Inject IUsuarioSucursalDao usuarioSucursalDao;
	private @Inject IRolDao rolDao;
	private @Inject SessionMain sessionMain;
	private @Inject ISucursalDao sucursalDao;

	// OBJECT
	private Usuario usuario;
	private Sucursal sucursal;
	private UsuarioSucursal usuarioSucursal;

	// VAR
	private String repeatPassword;

	// LIST
	private List<Usuario> listUsuario;
	private List<Rol> listRol;
	private List<Sucursal> listSucursal;
	private List<UsuarioSucursal> listUsuarioSucursal;
	private List<UsuarioSucursal> listUsuarioSucursalEliminadas;

	// STATE
	private boolean nuevo;
	private boolean registrar;
	private boolean seleccionado;

	// SESSION
	private Compania compania;

	@PostConstruct
	public void init() {
		System.out.println("init - UserController");
		compania = sessionMain.getCompania();
		loadDefault();
	}

	public void loadDefault() {
		repeatPassword = "";

		nuevo = true;
		seleccionado = false;
		registrar = false;

		usuarioSucursal = new UsuarioSucursal();
		sucursal = new Sucursal();
		listSucursal = sucursalDao.obtenerPorCompania(compania);
		listUsuarioSucursalEliminadas = new ArrayList<>();
		listUsuarioSucursal = new ArrayList<>();
		usuario = new Usuario();
		listUsuario = getListUsuarioPorCompania(compania);// usuarioDao.obtenerTodosActivosEInactivosPorOrdenAsc();
		listRol = rolDao
				.obtenerTodosActivosEInactivosRpoCompaniaPorOrdenAsc(compania);// obtenerTodosActivosEInactivosPorOrdenAsc();
	}

	// ACTION

	public void onRowSelect(SelectEvent event) {
		nuevo = false;
		seleccionado = true;
		registrar = false;
		repeatPassword = usuario.getPassword();
		listUsuarioSucursal = obtenerSucursalesPorUsuario(usuario);
	}

	public void actionNuevo() {
		nuevo = false;
		seleccionado = false;
		registrar = true;

		repeatPassword = "";
		usuario = new Usuario();
		// agregar por defecto una sucursal(SUCURSAL PRINCIPAL| CASA MATRIZ)
		usuarioSucursal.setEstado("AC");
		usuarioSucursal.setFechaRegistro(new Date());
		usuarioSucursal.setSucursal(obtenerSucursalPrincipalPorCompania());
		usuarioSucursal.setUsuario(usuario);
		usuarioSucursal.setUsuarioRegistro("admin");
		listUsuarioSucursal.add(usuarioSucursal);
	}

	public void actionCancelar() {
		nuevo = true;
		seleccionado = false;
		registrar = false;

		repeatPassword = "";
		usuario = new Usuario();
		usuarioSucursal = new UsuarioSucursal();
		listUsuarioSucursal = new ArrayList<>();
		listUsuarioSucursalEliminadas = new ArrayList<>();
	}

	public void actionMdificarUsuarioSucursal(Integer idUsuarioSucursal) {
		for (UsuarioSucursal us : listUsuarioSucursal) {
			if (us.getId().equals(idUsuarioSucursal)) {
				this.usuarioSucursal = us;
			}
		}
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgSucursal");
	}

	public void actionEliminarUsuarioSucursal(Integer idUsuarioSucursal) {
		// no debe permitir eliminar todas las sucursales, debe tener al menos
		// una sucursal
		if (listUsuarioSucursal.size() == 1) {
			FacesUtil.infoMessage("Verificación",
					"El usuario debe tener al menos una sucursal asocida.");
			return;
		}
		for (UsuarioSucursal us : listUsuarioSucursal) {
			if (us.getId().equals(idUsuarioSucursal)) {
				usuarioSucursal = us;
			}
		}
		if (usuarioSucursal.getId().intValue() > 0) {
			listUsuarioSucursalEliminadas.add(usuarioSucursal);
		}
		listUsuarioSucursal.remove(usuarioSucursal);
	}

	public void actionNuevoUsuarioSucursal() {
		usuarioSucursal = new UsuarioSucursal();
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgSucursal");
	}

	public void registrarOModificarSucursal() {
		if (usuarioSucursal.getId().equals(0)) {// nuevo
			// ID's negativos para los nuevos registros, para que el row.key
			// datatable los valide
			usuarioSucursal
					.setSucursal(obtenerObjectSucursalPorId(usuarioSucursal
							.getSucursal().getId()));// porque no recupera todo
														// el objeto completo,
														// solo el ID
			usuarioSucursal.setId(listUsuarioSucursal.size() * (-1));
			usuarioSucursal.setUsuario(usuario);
			usuarioSucursal.setUsuarioRegistro("admin");
			usuarioSucursal.setFechaRegistro(new Date());
			listUsuarioSucursal.add(usuarioSucursal);
		} else {
			for (UsuarioSucursal us : listUsuarioSucursal) {
				if (us.equals(usuarioSucursal)) {
					us = usuarioSucursal;
				}
			}
		}
		FacesUtil.hideDialog("dlgSucursal");
	}

	private Sucursal obtenerSucursalPrincipalPorCompania() {
		for (Sucursal sucursal : listSucursal) {
			if (sucursal.getTipo().equals("P")) {
				return sucursal;
			}
		}
		return null;
	}

	// PROCESO

	private Sucursal obtenerObjectSucursalPorId(Integer idSucursal) {
		for (Sucursal suc : listSucursal) {
			if (suc.getId().equals(idSucursal)) {
				return suc;
			}
		}
		return null;
	}

	private List<UsuarioSucursal> obtenerSucursalesPorUsuario(Usuario usurario) {
		return usuarioSucursalDao.obtenerTodosPorUsuario(usurario);
	}

	private List<Usuario> getListUsuarioPorCompania(Compania compania) {
		List<UsuarioSucursal> listUsuarioSucursal = usuarioSucursalDao
				.obtenerTodosPorCompania(compania);
		List<Usuario> listUsuario = new ArrayList<>();
		for (UsuarioSucursal us : listUsuarioSucursal) {
			if (!listUsuario.contains(us.getUsuario())) {
				listUsuario.add(us.getUsuario());
			}
		}
		return listUsuario;
	}

	public void registrarUsuario() {
		if (!usuario.getPassword().equals(repeatPassword)) {
			FacesUtil.errorMessage("Las Contraseñas no coinciden.");
			return;
		}
		usuario.setFechaRegistro(new Date());
		usuario.setUsuarioRegistro("admin");
		boolean sw = usuarioDao.registrar(usuario, listUsuarioSucursal);
		if (sw) {
			loadDefault();
		}
	}

	public void modificarUsuario() {
		if (!usuario.getPassword().equals(repeatPassword)) {
			FacesUtil.errorMessage("Las Contraseñas no coinciden.");
			return;
		}
		usuario.setFechaModificacion(new Date());
		boolean sw = usuarioDao.modificar(usuario, listUsuarioSucursal,
				listUsuarioSucursalEliminadas);
		if (sw) {
			loadDefault();
		}
	}

	/**
	 * Tomar en cuenta que deberian eliminarse tambien todo lo asociado a este
	 * usuario como ser: Sucursales
	 */
	public void eliminarUsuario() {
		boolean sw = usuarioDao.eliminar(usuario);
		if (sw) {
			loadDefault();
		}
	}

	// ----------- Getters and Setters ------------

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public List<Rol> getListRol() {
		return listRol;
	}

	public void setListRol(List<Rol> listRol) {
		this.listRol = listRol;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}

	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<UsuarioSucursal> getListUsuarioSucursal() {
		return listUsuarioSucursal;
	}

	public void setListUsuarioSucursal(List<UsuarioSucursal> listUsuarioSucursal) {
		this.listUsuarioSucursal = listUsuarioSucursal;
	}

	public UsuarioSucursal getUsuarioSucursal() {
		return usuarioSucursal;
	}

	public void setUsuarioSucursal(UsuarioSucursal usuarioSucursal) {
		this.usuarioSucursal = usuarioSucursal;
	}

	public List<UsuarioSucursal> getListUsuarioSucursalEliminadas() {
		return listUsuarioSucursalEliminadas;
	}

	public void setListUsuarioSucursalEliminadas(
			List<UsuarioSucursal> listUsuarioSucursalEliminadas) {
		this.listUsuarioSucursalEliminadas = listUsuarioSucursalEliminadas;
	}
}
