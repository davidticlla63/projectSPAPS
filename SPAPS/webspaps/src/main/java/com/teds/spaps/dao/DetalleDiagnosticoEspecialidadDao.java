/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleDiagnosticoEspecialidad;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleDiagnosticoEspecialidadDao extends
		DataAccessObjectJpa<DetalleDiagnosticoEspecialidad> implements
		IDetalleDiagnosticoEspecialidadDao {

	/**
	 * @param typeT
	 */
	public DetalleDiagnosticoEspecialidadDao() {
		super(DetalleDiagnosticoEspecialidad.class);
	}

	@Override
	public DetalleDiagnosticoEspecialidad create(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad) {
		return super.create(detalleDiagnosticoEspecialidad);
	}

	@Override
	public DetalleDiagnosticoEspecialidad update(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad) {
		return super.update(detalleDiagnosticoEspecialidad);
	}

	@Override
	public void delete(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad) {
		super.deleteReal(detalleDiagnosticoEspecialidad);
	}

	@Override
	public DetalleDiagnosticoEspecialidad registrar(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad) {
		try {
			beginTransaction();
			detalleDiagnosticoEspecialidad = create(detalleDiagnosticoEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"DetalleDiagnosticoEspecialidad "
							+ detalleDiagnosticoEspecialidad.toString());
			return detalleDiagnosticoEspecialidad;
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
	public DetalleDiagnosticoEspecialidad modificar(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad) {
		try {
			beginTransaction();
			detalleDiagnosticoEspecialidad = update(detalleDiagnosticoEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleDiagnosticoEspecialidad "
							+ detalleDiagnosticoEspecialidad.toString());
			return detalleDiagnosticoEspecialidad;
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
	public boolean eliminar(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad) {
		try {
			detalleDiagnosticoEspecialidad.setEstado("RM");
			beginTransaction();
			DetalleDiagnosticoEspecialidad bar = update(detalleDiagnosticoEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"DetalleDiagnosticoEspecialidad "
							+ detalleDiagnosticoEspecialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao#
	 * obtenerDetalleDiagnosticoEspecialidad(java.lang.Integer)
	 */
	@Override
	public DetalleDiagnosticoEspecialidad obtenerDetalleDiagnosticoEspecialidad(
			Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao#
	 * obtenerDetalleDiagnosticoEspecialidadOrdenAscPorId()
	 */
	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerDetalleDiagnosticoEspecialidadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao#
	 * obtenerDetalleDiagnosticoEspecialidadOrdenDescPorId()
	 */
	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerDetalleDiagnosticoEspecialidadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao#
	 * obtenerPorCompania (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerPorCompania(
			Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao#
	 * obtenerPorSucursal (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerPorSucursal(
			Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerPorHM(
			HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaMedica",
				historiaClinica.getId());
	}

	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerPorDiagnostico(
			Diagnostico diagnostico) {
		return findAllActivosByParameter("diagnostico", diagnostico.getId());
	}

	@Override
	public List<DetalleDiagnosticoEspecialidad> obtenerPorEspecialidad(
			Especialidad especialidad) {
		return findAllActivosByParameter("especialidad", especialidad.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao#onComplete
	 * (java. lang.String)
	 */
	@Override
	public List<DetalleDiagnosticoEspecialidad> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
