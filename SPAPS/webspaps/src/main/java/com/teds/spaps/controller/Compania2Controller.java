package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.ICompaniaDao;
import com.teds.spaps.interfaces.dao.IPaisDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.interfaces.dao.IUsuarioSucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.MenuAccion;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Permiso;
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
@ManagedBean(name = "compania2Controller")
@ViewScoped
public class Compania2Controller implements Serializable {

	private static final long serialVersionUID = 1L;

	// DAO
	private @Inject ICompaniaDao companiaDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IUsuarioSucursalDao usuarioSucursalDao;
	private @Inject IPaisDao paisDao;

	// OBJECT
	private Compania compania;
	private Sucursal sucursal;
	private Usuario usuario;
	private Pais pais;

	// VAR
	private String currentPage = "/pages/config/compania/list.xhtml";
	private String repeatPassword;

	// LIST
	private List<Compania> listCompania = new ArrayList<Compania>();
	private List<ParamSistemaIndices> listSistemaIndices = new ArrayList<ParamSistemaIndices>();
	private List<Pais> listPais = new ArrayList<Pais>();

	// STATE
	private boolean nuevo;
	private boolean registrar;
	private boolean seleccionado;
	private boolean skip;
	private boolean swModificoUsuario;

	// SESSION
	private @Inject SessionMain sessionMain;

	// INDICES
	private @Inject IParamSistemaIndicesDao parametrosSistemaIndicesDao;

	@PostConstruct
	public void init() {
		System.out.println("init - UserController");
		loadDefault();
	}

	public void loadDefault() {
		swModificoUsuario = true;
		repeatPassword = "";
		nuevo = true;
		seleccionado = false;
		registrar = false;
		setPais(new Pais());
		sucursal = new Sucursal();
		usuario = new Usuario();
		compania = new Compania();

		if (sessionMain.getUsuarioLogin().getTipo().equals("SUPER")) {
			listCompania = companiaDao
					.obtenerTodosActivosEInactivosPorOrdenAsc();
		} else {
			redirectPage();// ingreso caso que no es SUPER ADMINISTRADOR
		}
	}

	public void redirectPage() {
		List<UsuarioSucursal> usuarioSucursals = usuarioSucursalDao
				.obtenerTodosPorUsuario(sessionMain.getUsuarioLogin());
		Set<Compania> set = new HashSet<Compania>();
		List<Sucursal> sucursals = new ArrayList<Sucursal>();
		for (UsuarioSucursal usuarioSucursal : usuarioSucursals) {
			set.add(usuarioSucursal.getSucursal().getCompania());
			sucursals.add(usuarioSucursal.getSucursal());
		}
		listCompania.clear();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Compania compania = (Compania) iterator.next();
			listCompania.add(compania);
		}
		for (Compania compania : listCompania) {
			compania.setListSucursal(new ArrayList<Sucursal>());
			for (Sucursal sucursal : sucursals) {
				if (sucursal.getCompania().getId().intValue() == compania
						.getId().intValue()) {
					compania.getListSucursal().add(sucursal);
				}
			}
		}
		if (listCompania.size() == 1
				&& listCompania.get(0).getListSucursal().size() == 1) {
			try {
				sucursal = listCompania.get(0).getListSucursal().get(0);
				sessionMain.setCompania(sucursal.getCompania());
				sessionMain.setSucursalLogin(sucursal);
				inicializarParamSistema(sucursal.getCompania());
				FacesUtil.redirect("/pages/dashboard.xhtml");
			} catch (Exception e) {
			}
		}
	}

	// ACTION

	public void onRowSelect(SelectEvent event) {
		nuevo = false;
		seleccionado = true;
		registrar = false;
		inicializarParamSistema(compania);
		// currentPage = "/pages/config/compania/edit.xhtml";
		try {
			sessionMain.setCompania(compania);
			FacesUtil.redirect("/pages/dashboard.xhtml");
		} catch (Exception e) {
		}
	}

	public void onRowSelectSucursal(SelectEvent event) {
		nuevo = false;
		seleccionado = true;
		registrar = false;

		// currentPage = "/pages/config/compania/edit.xhtml";
		try {
			sessionMain.setCompania(sucursal.getCompania());
			sessionMain.setSucursalLogin(sucursal);
			inicializarParamSistema(sucursal.getCompania());
			FacesUtil.redirect("/pages/dashboard.xhtml");
		} catch (Exception e) {
		}
	}

	private void inicializarParamSistema(Compania compania) {
		listSistemaIndices = parametrosSistemaIndicesDao
				.obtenerPorCompania(compania);
		if (listSistemaIndices.size() == 0) {
			parametrosSistemaIndicesDao.registrar(sessionMain.getUsuarioLogin()
					.getLogin(), compania);
		}
	}

	public void actionNuevo() {
		nuevo = false;
		seleccionado = false;
		registrar = true;

		compania = new Compania();
		usuario.setPassword(FacesUtil.geerarCadenaAlfanumAleatoria(6));
		usuario.setTipo("ADMIN");
		repeatPassword = usuario.getPassword();

		sucursal.setNombre("CASA MATRIZ");
		sucursal.setDescripcion("SUCURSAL PRINCIPAL");
		sucursal.setTipo("P");
		listPais = paisDao.obtenerPaisOrdenDescPorId();
		currentPage = "/pages/config/compania/edit.xhtml";
	}

	public void actionCancelar() {
		nuevo = true;
		seleccionado = false;
		registrar = false;
		swModificoUsuario = true;

		compania = new Compania();
		currentPage = "/pages/config/compania/list.xhtml";
	}

	public void actionModificar(Integer id) {
		for (Compania c : listCompania) {
			if (c.getId().equals(id)) {
				compania = c;
				sucursal = getSucursalPrincialPorCompania(compania);
				usuario = getUsuarioAdminPorCompania(compania);
				repeatPassword = usuario.getPassword();
				break;
			}
		}
		listPais = paisDao.obtenerPaisOrdenDescPorId();
		nuevo = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/config/compania/edit.xhtml";
	}

	public void selectedPais() {
		pais = paisDao.obtenerPais(pais.getId());
	}

	/**
	 * PRIMEFACES
	 * 
	 * @param event
	 * @return
	 */
	public String onFlowProcess(FlowEvent event) {
		// poner campos por defecto
		if (swModificoUsuario) {
			usuario.setNombre(compania.getNombreRepresentante());
			usuario.setLogin(compania.getCiRepresentante());
			sucursal.setDireccion(compania.getDireccion());
			swModificoUsuario = false;
		}
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	// PROCESO

	private Sucursal getSucursalPrincialPorCompania(Compania compania) {
		List<Sucursal> list = sucursalDao.obtenerPorCompania(compania);
		for (Sucursal s : list) {
			if (s.getTipo().equals("P")) {
				return s;
			}
		}
		return new Sucursal();
	}

	private Usuario getUsuarioAdminPorCompania(Compania compania) {
		List<UsuarioSucursal> list = usuarioSucursalDao
				.obtenerTodosPorCompania(compania);
		for (UsuarioSucursal us : list) {
			if (us.getUsuario().getTipo().equals("ADMIN")) {
				return us.getUsuario();
			}
		}
		return new Usuario();
	}

	public void registrarCompania() {
		if (!usuario.getPassword().equals(repeatPassword)) {
			FacesUtil.errorMessage("Las Contraseñas no coinciden.");
			return;
		}
		// fecha Registro
		Date fechaActual = new Date();
		compania.setFechaRegistro(fechaActual);
		// crearle un nuevo rol
		Rol rol = new Rol();
		rol.setNombre("ADMINISTRADOR");
		rol.setDescripcion("GRUPO ADMINISTRADOR");
		rol.setEstado("AC");
		// rol.setFechaRegistro(new Date());
		Rol superRol = new Rol();
		superRol.setId(1);// super ROL padre
		rol.setRol(superRol);
		// crearle todos sus permisos para ese rol
		MenuAccion ma1 = new MenuAccion();
		ma1.setId(1);//
		MenuAccion ma2 = new MenuAccion();
		ma2.setId(2);//
		MenuAccion ma3 = new MenuAccion();
		ma3.setId(3);//
		MenuAccion ma4 = new MenuAccion();
		ma4.setId(4);//
		MenuAccion ma5 = new MenuAccion();
		ma5.setId(5);//
		MenuAccion ma6 = new MenuAccion();
		ma6.setId(6);
		// PERMISOS
		List<Permiso> listPermiso = new ArrayList<>();
		Permiso p1 = new Permiso();
		p1.setUsuarioRegistro("admin");
		p1.setMenuAccion(ma1);
		listPermiso.add(p1);
		Permiso p2 = new Permiso();
		p2.setUsuarioRegistro("admin");
		p2.setMenuAccion(ma2);
		listPermiso.add(p2);
		Permiso p3 = new Permiso();
		p3.setUsuarioRegistro("admin");
		p3.setMenuAccion(ma3);
		listPermiso.add(p3);
		Permiso p4 = new Permiso();
		p4.setUsuarioRegistro("admin");
		p4.setMenuAccion(ma4);
		listPermiso.add(p4);
		Permiso p5 = new Permiso();
		p5.setUsuarioRegistro("admin");
		p5.setMenuAccion(ma5);
		listPermiso.add(p5);
		Permiso p6 = new Permiso();
		p6.setUsuarioRegistro("admin");
		p6.setMenuAccion(ma6);
		listPermiso.add(p6);
		boolean sw = companiaDao.registrar(compania, sucursal, usuario, rol,
				listPermiso);
		if (sw) {
			loadDefault();
			currentPage = "/pages/config/compania/list.xhtml";
		}
	}

	public void modificarCompania() {
		if (!usuario.getPassword().equals(repeatPassword)) {
			FacesUtil.errorMessage("Las Contraseñas no coinciden.");
			return;
		}
		boolean sw = companiaDao.modificar(compania, sucursal, usuario);
		if (sw) {
			loadDefault();
		}
	}

	public void eliminarCompania() {
		boolean sw = companiaDao.eliminar(compania, sucursal, usuario);
		if (sw) {
			loadDefault();
		}
	}

	// ----------- Getters and Setters ------------

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

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public List<Compania> getListCompania() {
		return listCompania;
	}

	public void setListCompania(List<Compania> listCompania) {
		this.listCompania = listCompania;
	}

	public String getPage() {
		return currentPage;
	}

	public void setPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	/**
	 * @return the listSistemaIndices
	 */
	public List<ParamSistemaIndices> getListSistemaIndices() {
		return listSistemaIndices;
	}

	/**
	 * @param listSistemaIndices
	 *            the listSistemaIndices to set
	 */
	public void setListSistemaIndices(
			List<ParamSistemaIndices> listSistemaIndices) {
		this.listSistemaIndices = listSistemaIndices;
	}

	/**
	 * @return the listPais
	 */
	public List<Pais> getListPais() {
		return listPais;
	}

	/**
	 * @param listPais
	 *            the listPais to set
	 */
	public void setListPais(List<Pais> listPais) {
		this.listPais = listPais;
	}

	/**
	 * @return the pais
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}
}
