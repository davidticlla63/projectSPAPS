package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IAccionDao;
import com.teds.spaps.model.Accion;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class AccionDao extends DataAccessObjectJpa<Accion> implements
		IAccionDao {

	public AccionDao() {
		super(Accion.class);
	}

	@Override
	public Accion create(Accion accion) {
		return super.create(accion);
	}

	@Override
	public Accion update(Accion accion) {
		return super.update(accion);
	}

	@Override
	public Accion registrar(Accion accion) {
		try {
			beginTransaction();
			accion = create(accion);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Accion " + accion.toString());
			return accion;
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
	public Accion modificar(Accion accion) {
		try {
			beginTransaction();
			accion = update(accion);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Accion " + accion.toString());
			return accion;
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
	public boolean eliminar(Accion accion) {
		try {
			beginTransaction();
			Accion bar = modificar(accion);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Accion " + accion.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<Accion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	// public Accion obtenerAccion(Integer id) {
	// return findById(id);
	// }
	//
	// public Accion obtenerAccionPorNombre(String nombre) {
	// return findByParameter("nombre", nombre);
	// }
	//
	// public List<Accion> obtenerAccionOrdenAscPorId() {
	// return findAscAllOrderedByParameter("id");
	// }
	//
	// public List<Accion> obtenerAccionOrdenDescPorId() {
	// return findDescAllOrderedByParameter("id");
	// }

}
