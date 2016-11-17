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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.teds.spaps.interfaces.dao.IMenuAccionDao;
import com.teds.spaps.interfaces.dao.IPermisoDao;
import com.teds.spaps.interfaces.dao.IRolDao;
import com.teds.spaps.model.Accion;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Menu;
import com.teds.spaps.model.MenuAccion;
import com.teds.spaps.model.Modulo;
import com.teds.spaps.model.Permiso;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.EDPermiso;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "permissionController")
@ViewScoped
public class PermissionController implements Serializable {

	private static final long serialVersionUID = 1L;

	// DAO
	private @Inject SessionMain sessionMain;
	private @Inject IRolDao rolDao;
	private @Inject IMenuAccionDao menuAccionDao;
	private @Inject IPermisoDao permisoDao;

	// OBJECT
	private Rol rol;

	// LIST
	private List<Rol> listRol;
	private List<Modulo> listModulo;
	private List<Menu> listMenu;
	private List<MenuAccion> listMenuAccion;
	private List<Permiso> listPermisos;

	// STATE
	private boolean seleccionado;

	// TREE
	private TreeNode root;
	private TreeNode[] selectedNodes;
	private String selectionModeTreeNode;

	// SESSION
	private Compania compania;
	private Usuario usuario;

	@PostConstruct
	public void init() {
		System.out.println("init - PermissionController");
		compania = sessionMain.getCompania();
		usuario = sessionMain.getUsuarioLogin();
		loadDefault();
	}

	public void loadDefault() {
		seleccionado = false;

		rol = new Rol();
		listRol = rolDao
				.obtenerTodosActivosEInactivosRpoCompaniaPorOrdenAsc(compania);// obtenerTodosActivosEInactivosPorOrdenAsc();

		listPermisos = new ArrayList<>();
		listMenuAccion = menuAccionDao
				.obtenerTodosActivosEInactivosPorOrdenAsc();

		selectionModeTreeNode = "none";
		selectedNodes = null;
		cargarModulo();
		cagarMenu();
		cargarPermisos();
	}

	// ACTION

	public void onRowSelect(SelectEvent event) {
		seleccionado = true;
		selectionModeTreeNode = "checkbox";
		listPermisos = permisoDao.obtenerPorRol(rol);
		cargarPermisosPorRol();
	}

	public void actionCancelar() {
		seleccionado = false;
		selectionModeTreeNode = "none";
		selectedNodes = null;
		rol = new Rol();
		cargarPermisos();
	}

	public void actionNuevoRol() {
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgGrupoUsuario");
	}

	public void actionMdificarRol(Integer idRol) {
		for (Rol rol : listRol) {
			if (rol.getId().equals(idRol)) {
				this.rol = rol;
			}
		}
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgGrupoUsuario");
	}

	// PROCESO

	public void registrarOModificarRol() {
		boolean sw = false;
		if (rol.getId().equals(0)) {
			rol.setCompania(compania);
			rol.setFechaRegistro(new Date());
			rol.setUsuarioRegistro("admin");
			Rol rolAux = rolDao.registrar(rol);
			sw = rolAux == null ? false : true;
		} else {
			for (Rol rol : listRol) {
				if (this.rol.equals(rol)) {
					rol = this.rol;
				}
			}
			sw = rolDao.modificar(rol);
		}
		if (sw) {
			FacesUtil.hideDialog("dlgGrupoUsuario");
			loadDefault();
		}
	}

	public void eliminarRol(Integer idRol) {
		for (Rol rol : listRol) {
			if (rol.getId().equals(idRol)) {
				this.rol = rol;
			}
		}
		boolean sw = rolDao.eliminar(rol);
		if (sw) {
			loadDefault();
		}
	}

	private void cargarModulo() {
		listModulo = new ArrayList<>();
		for (MenuAccion ma : listMenuAccion) {
			if (!listModulo.contains(ma.getMenu().getModulo())) {
				listModulo.add(ma.getMenu().getModulo());
			}
		}
	}

	private void cagarMenu() {
		listMenu = new ArrayList<>();
		for (MenuAccion ma : listMenuAccion) {
			if (!listMenu.contains(ma.getMenu())) {
				listMenu.add(ma.getMenu());
			}
		}
	}

	private List<Menu> obtenerMenusPorModulo(Modulo modulo) {
		List<Menu> listAux = new ArrayList<>();
		for (Menu menu : listMenu) {
			if (menu.getModulo().equals(modulo)) {
				if (!listAux.contains(menu)) {
					listAux.add(menu);
				}
			}
		}
		return listAux;
	}

	private List<Accion> obtenerAccionesPorMenu(Menu menu) {
		List<Accion> listAux = new ArrayList<>();
		for (MenuAccion menuAccion : listMenuAccion) {
			if (menuAccion.getMenu().equals(menu)) {
				listAux.add(menuAccion.getAccion());
			}
		}
		return listAux;
	}

	private MenuAccion obtenerMenuAccion(Accion accion, Menu menu) {
		for (MenuAccion menuAccion : listMenuAccion) {
			if (menuAccion.getAccion().equals(accion)
					&& menuAccion.getMenu().equals(menu)) {
				return menuAccion;
			}
		}
		return null;
	}

	private boolean tienePermisoPorModulo(Modulo modulo) {
		for (Permiso permiso : listPermisos) {
			if (permiso.getMenuAccion().getMenu().getModulo().equals(modulo)) {
				return true;
			}
		}
		return false;
	}

	private boolean tienePermisoPorMenu(Menu menu) {
		for (Permiso permiso : listPermisos) {
			if (permiso.getMenuAccion().getMenu().equals(menu)) {
				return true;
			}
		}
		return false;
	}

	private boolean tienePermisoPorMenuYAccion(Menu menu, Accion accion) {
		for (Permiso permiso : listPermisos) {
			if (permiso.getMenuAccion().getMenu().equals(menu)
					&& permiso.getMenuAccion().getAccion().equals(accion)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * cargar todos los permisos por defecto no seleccionados
	 */
	private void cargarPermisos() {
		root = new DefaultTreeNode(new EDPermiso(0, "", null, false), null);
		for (Modulo mod : listModulo) {
			TreeNode tn1 = new DefaultTreeNode(new EDPermiso(0,
					mod.getNombre(), null, false), root);
			tn1.setExpanded(true);
			tn1.setSelected(false);
			List<Menu> listMenuPorModulo = obtenerMenusPorModulo(mod);
			for (Menu menu : listMenuPorModulo) {
				TreeNode tn2 = new DefaultTreeNode("1", new EDPermiso(0,
						menu.getNombre(), null, false), tn1);
				tn2.setExpanded(true);
				tn2.setSelected(false);
				List<Accion> listAccion = obtenerAccionesPorMenu(menu);
				for (Accion accion : listAccion) {
					MenuAccion menuAccion = obtenerMenuAccion(accion, menu);
					TreeNode tn3 = new DefaultTreeNode("2", new EDPermiso(0,
							accion.getNombre(), menuAccion, false), tn2);
					tn3.setExpanded(true);
					tn3.setSelected(false);
				}
			}
		}
	}

	/**
	 * cargar todos los permisos, de acuerdo al rol seleccionado
	 */
	private void cargarPermisosPorRol() {
		root = new DefaultTreeNode(new EDPermiso(0, "", null, false), null);
		for (Modulo mod : listModulo) {
			TreeNode tn1 = new DefaultTreeNode(new EDPermiso(0,
					mod.getNombre(), null, false), root);
			tn1.setExpanded(true);
			tn1.setSelected(tienePermisoPorModulo(mod));
			List<Menu> listMenuPorModulo = obtenerMenusPorModulo(mod);
			for (Menu menu : listMenuPorModulo) {
				TreeNode tn2 = new DefaultTreeNode("1", new EDPermiso(0,
						menu.getNombre(), null, false), tn1);
				tn2.setExpanded(true);
				tn2.setSelected(tienePermisoPorMenu(menu));
				List<Accion> listAccion = obtenerAccionesPorMenu(menu);
				for (Accion accion : listAccion) {
					MenuAccion menuAccion = obtenerMenuAccion(accion, menu);
					TreeNode tn3 = new DefaultTreeNode("2", new EDPermiso(0,
							accion.getNombre(), menuAccion, false), tn2);
					tn3.setExpanded(true);
					tn3.setSelected(tienePermisoPorMenuYAccion(menu, accion));
				}
			}
		}
	}

	public void registrarPermiso() {
		if (selectedNodes == null) {
			FacesUtil.infoMessage("Seleccione Permisos", "");
			return;
		}
		boolean sw = permisoDao.registrar(listPermisos, selectedNodes, rol,
				usuario);
		if (sw) {
			// verificar si el rol modificado es el loggeado, para cargar los
			// nuevos datos
			if (usuario.getRol().equals(rol)) {
				FacesUtil
						.infoMessage("Observación",
								"Los cambios se aplicarán despues de reiniciar la sesión");
			}
			loadDefault();
		}
	}

	// ----------- Getters and Setters ------------

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public List<Rol> getListRol() {
		return listRol;
	}

	public void setListRol(List<Rol> listRol) {
		this.listRol = listRol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public String getSelectionModeTreeNode() {
		return selectionModeTreeNode;
	}

	public void setSelectionModeTreeNode(String selectionModeTreeNode) {
		this.selectionModeTreeNode = selectionModeTreeNode;
	}

	public List<Modulo> getListModulo() {
		return listModulo;
	}

	public void setListModulo(List<Modulo> listModulo) {
		this.listModulo = listModulo;
	}

	public List<MenuAccion> getListMenuAccion() {
		return listMenuAccion;
	}

	public void setListMenuAccion(List<MenuAccion> listMenuAccion) {
		this.listMenuAccion = listMenuAccion;
	}

	public List<Menu> getListMenu() {
		return listMenu;
	}

	public void setListMenu(List<Menu> listMenu) {
		this.listMenu = listMenu;
	}

	public List<Permiso> getListPermisos() {
		return listPermisos;
	}

	public void setListPermisos(List<Permiso> listPermisos) {
		this.listPermisos = listPermisos;
	}

}
