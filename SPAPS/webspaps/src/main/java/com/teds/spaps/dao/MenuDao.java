package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IMenuDao;
import com.teds.spaps.model.Menu;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class MenuDao extends DataAccessObjectJpa<Menu> implements IMenuDao {

	public MenuDao() {
		super(Menu.class);
	}

	@Override
	public Menu create(Menu menu) {
		return super.create(menu);
	}

	@Override
	public Menu update(Menu menu) {
		return super.update(menu);
	}

	@Override
	public Menu registrar(Menu menu) {
		return null;
	}

	public Menu modificar(Menu menu) {
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

	public boolean eliminar(Menu menu) {
		try {
			beginTransaction();
			menu.setEstado("RM");
			Menu bar = update(menu);
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
	public List<Menu> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	// public Menu obtenerMenu(Integer id) {
	// return findById(id);
	// }
	//
	// public Menu obtenerMenuPorNombre(String nombre) {
	// return findByParameter("nombre", nombre);
	// }
	//
	// public List<Menu> obtenerMenusPorNombre(String nombre) {
	// String query = "select em.* from Menu em where nombre like '" + nombre
	// + "'";
	// return executeQueryResulList(query);
	// }
	//
	// public List<Menu> obtenerMenusPorModulo(Integer id) {
	// return findAllActivosByParameter("modulo", id);
	// }
	//
	// public List<Menu> obtenerMenusPorModulo(String nombre) {
	// return findAllActivosByParameter("modulo", nombre);
	// }
	//
	// public List<Menu> obtenerMenuOrdenAscPorId() {
	// return findAscAllOrderedByParameter("id");
	// }
	//
	// public List<Menu> obtenerMenuOrdenDescPorId() {
	// return findDescAllOrderedByParameter("id");
	// }

}
