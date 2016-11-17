package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IMenuAccionDao;
import com.teds.spaps.model.MenuAccion;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class MenuAccionDao extends DataAccessObjectJpa<MenuAccion> implements
		IMenuAccionDao {

	public MenuAccionDao() {
		super(MenuAccion.class);
	}

	@Override
	public MenuAccion create(MenuAccion menuAccion) {
		return super.create(menuAccion);
	}

	@Override
	public MenuAccion update(MenuAccion menuAccion) {
		return super.update(menuAccion);
	}

	@Override
	public MenuAccion registrar(MenuAccion menuAccion) {
		return null;
	}

	@Override
	public MenuAccion modificar(MenuAccion menu) {
		try {
			beginTransaction();
			menu = update(menu);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Menu " + menu.toString());
			return menu;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(MenuAccion menu) {
		try {
			beginTransaction();
			menu.setEstado("RM");
			MenuAccion bar = update(menu);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Menu " + menu.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<MenuAccion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	// @Override
	// public MenuAccion obtenerMenuAccion(Integer id) {
	// return findById(id);
	// }
	//
	// @Override
	// public List<MenuAccion> obtenerMenuAccionPorMenu(Integer id) {
	// return findAllActivosByParameter("menu", id);
	// }
	//
	// @Override
	// public List<MenuAccion> obtenerOrdenAscPorId() {
	// return findAscAllOrderedByParameter("id");
	// }
	//
	// @Override
	// public List<MenuAccion> obtenerOrdenDescPorId() {
	// return findDescAllOrderedByParameter("id");
	// }

}
