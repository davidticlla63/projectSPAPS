package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabEtiquetasDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabEtiquetas;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabEtiquetasDao extends DataAccessObjectJpa<LabEtiquetas>
		implements ILabEtiquetasDao {

	public LabEtiquetasDao() {
		super(LabEtiquetas.class);
	}

	@Override
	public LabEtiquetas create(LabEtiquetas especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabEtiquetas update(LabEtiquetas especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabEtiquetas registrar(LabEtiquetas especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabEtiquetas "
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
	public LabEtiquetas modificar(LabEtiquetas especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabEtiquetas "
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
	public boolean eliminar(LabEtiquetas especialidad) {
		try {
			beginTransaction();
			LabEtiquetas bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabEtiquetas "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabEtiquetas obtenerLabEtiquetas(Integer id) {
		return findById(id);
	}

	@Override
	public List<LabEtiquetas> obtenerLabEtiquetasOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabEtiquetas> obtenerLabEtiquetasOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabEtiquetas> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabEtiquetas> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabEtiquetasDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabEtiquetas> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}


}
