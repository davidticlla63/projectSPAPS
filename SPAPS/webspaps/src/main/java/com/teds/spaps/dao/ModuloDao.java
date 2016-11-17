package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IModuloDao;
import com.teds.spaps.model.Modulo;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class ModuloDao extends DataAccessObjectJpa<Modulo> implements
		IModuloDao {

	public ModuloDao() {
		super(Modulo.class);
	}

	@Override
	public Modulo create(Modulo modulo) {
		return super.create(modulo);
	}

	@Override
	public Modulo update(Modulo modulo) {
		return super.update(modulo);
	}

	@Override
	public Modulo registrar(Modulo modulo) {
		try {
			beginTransaction();
			modulo = create(modulo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Modulo " + modulo.toString());
			return modulo;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public Modulo modificar(Modulo modulo) {
		try {
			beginTransaction();
			modulo = update(modulo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Modulo " + modulo.toString());
			return modulo;
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
	public boolean eliminar(Modulo modulo) {
		try {
			beginTransaction();
			modulo.setEstado("RM");
			Modulo bar = update(modulo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Modulo " + modulo.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<Modulo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}
	//
	// @Override
	// public Modulo obtenerModulo(Integer id) {
	// return findById(id);
	// }
	//
	// @Override
	// public Modulo obtenerModuloPorNombre(String nombre) {
	// return findByParameter("nombre", nombre);
	// }
	//
	// @Override
	// public List<Modulo> obtenerModuloOrdenAscPorId() {
	// return findAscAllOrderedByParameter("id");
	// }
	//
	// @Override
	// public List<Modulo> obtenerModuloOrdenDescPorId() {
	// return findDescAllOrderedByParameter("id");
	// }

}
