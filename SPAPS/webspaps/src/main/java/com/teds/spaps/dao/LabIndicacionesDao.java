package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabIndicacionesDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabIndicaciones;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabIndicacionesDao extends DataAccessObjectJpa<LabIndicaciones>
		implements ILabIndicacionesDao {

	public LabIndicacionesDao() {
		super(LabIndicaciones.class);
	}

	@Override
	public LabIndicaciones create(LabIndicaciones especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabIndicaciones update(LabIndicaciones especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabIndicaciones registrar(LabIndicaciones especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabIndicaciones "
					+ especialidad.toString());
			return especialidad;
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
	public LabIndicaciones modificar(LabIndicaciones especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabIndicaciones "
					+ especialidad.toString());
			return especialidad;
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
	public boolean eliminar(LabIndicaciones especialidad) {
		try {
			beginTransaction();
			LabIndicaciones bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabIndicaciones "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabIndicaciones obtenerLabIndicaciones(Integer id) {
		return findById(id);
	}

	@Override
	public List<LabIndicaciones> obtenerLabIndicacionesOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabIndicaciones> obtenerLabIndicacionesOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabIndicaciones> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabIndicaciones> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabIndicacionesDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabIndicaciones> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

}
