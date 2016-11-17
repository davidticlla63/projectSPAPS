package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IParameterDao;
import com.teds.spaps.model.Parameter;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class ParameterDao extends DataAccessObjectJpa<Parameter> implements
		IParameterDao {

	public ParameterDao() {
		super(Parameter.class);
	}

	@Override
	public Parameter create(Parameter parameter) {
		return super.create(parameter);
	}

	@Override
	public Parameter update(Parameter parameter) {
		return super.update(parameter);
	}

	@Override
	public Parameter registrar(Parameter parameter) {
		try {
			beginTransaction();
			parameter = create(parameter);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"parameter " + parameter.toString());
			return parameter;
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
	public Parameter modificar(Parameter parameter) {
		try {
			beginTransaction();
			parameter = update(parameter);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "parameter "
					+ parameter.toString());
			return parameter;
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
	public boolean eliminar(Parameter parameter) {
		try {
			beginTransaction();
			parameter.setEstado("RM");
			parameter.setKey(new Date() + "|" + parameter.getKey());
			Parameter bar = update(parameter);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "parameter "
					+ parameter.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<Parameter> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
