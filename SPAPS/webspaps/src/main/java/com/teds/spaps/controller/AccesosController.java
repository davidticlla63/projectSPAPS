package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.teds.spaps.model.Modulo;
import com.teds.spaps.model.Permiso;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "accesosController")
@ViewScoped
public class AccesosController implements Serializable {

	private static final long serialVersionUID = 1L;

	// LIST
	private List<Permiso> listPermisos;
	private List<Modulo> listModulo;
	private List<com.teds.spaps.model.Menu> listMenu;

	// COMPONENT
	private MenuModel menu = new DefaultMenuModel();

	// SESSION
	private @Inject SessionMain sessionMain;

	@PostConstruct
	public void init() {
		//System.out.println("init - accessController");
		loadDefault();
	}

	public void loadDefault() {
		listPermisos = sessionMain.getListPermisos();
		cargarModulo();
		cagarMenu();
		List<DefaultSubMenu> listDefaultSubMenu = new ArrayList<>();
		int cont = 1;
		for (Modulo modulo : listModulo) {
			// Create submenu
			DefaultSubMenu mod = new DefaultSubMenu(modulo.getNombre().toUpperCase());
			// mod.setExpanded(false);
			mod.setId("om_"+cont);
			mod.setExpanded(false);			
			cont = cont + 1;
			List<com.teds.spaps.model.Menu> listAux = obtenerMenusPorModulo(modulo);
			for (com.teds.spaps.model.Menu menu : listAux) {
				DefaultMenuItem men = new DefaultMenuItem(menu.getNombre().toUpperCase(),menu.getMenuIcono());//,menu.getRuta());
				men.setId("om_"+menu.getId());
				cont = cont + 1;
				men.setOutcome(menu.getRuta());
				mod.addElement(men);
			}
			listDefaultSubMenu.add(mod);
		}
		for(DefaultSubMenu dsm: listDefaultSubMenu){
			menu.addElement(dsm);
		}
		// // Create submenu
		// DefaultSubMenu help = new DefaultSubMenu("SEGURIDAD");
		// // Create menuitem
		// DefaultMenuItem open = new DefaultMenuItem("Usuarios");
		// open.setIcon("fa fa-users");
		// open.setOutcome("/pages/security/user");
		// // Determine menuitem action
		// open.setCommand("#{menuManagedBean.openAction}");
		//
		// // Associate menuitem with submenu
		// file.addElement(open);
		// file.addElement(new DefaultSeparator());
		//
		// // Associate submenu with menu
		// menu.addElement(help);
	}

	// PROCESO

	private void cargarModulo() {
		listModulo = new ArrayList<>();
		for (Permiso ma : listPermisos) {
			if (!listModulo.contains(ma.getMenuAccion().getMenu().getModulo())) {
				listModulo.add(ma.getMenuAccion().getMenu().getModulo());
			}
		}
	}

	private void cagarMenu() {
		listMenu = new ArrayList<com.teds.spaps.model.Menu>();
		for (Permiso ma : listPermisos) {
			if (!listMenu.contains(ma.getMenuAccion().getMenu())) {
				listMenu.add(ma.getMenuAccion().getMenu());
			}
		}
	}

	private List<com.teds.spaps.model.Menu> obtenerMenusPorModulo(Modulo modulo) {
		List<com.teds.spaps.model.Menu> listAux = new ArrayList<>();
		for (Permiso ma : listPermisos) {
			if (ma.getMenuAccion().getMenu().getModulo().equals(modulo)) {
				if (!listAux.contains(ma.getMenuAccion().getMenu())) {
					listAux.add(ma.getMenuAccion().getMenu());
				}
			}
		}
		return listAux;
	}

	// GET AND SET
	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

}
