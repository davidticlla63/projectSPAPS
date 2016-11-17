/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleAntecedenteEspecialidad;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleAntecedenteEspecialidadDao extends
		DataAccessObjectJpa<DetalleAntecedenteEspecialidad> implements
		IDetalleAntecedenteEspecialidadDao {

	/**
	 * @param typeT
	 */
	public DetalleAntecedenteEspecialidadDao() {
		super(DetalleAntecedenteEspecialidad.class);
	}

	@Override
	public DetalleAntecedenteEspecialidad create(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad) {
		return super.create(detalleAntecedenteEspecialidad);
	}

	@Override
	public DetalleAntecedenteEspecialidad update(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad) {
		return super.update(detalleAntecedenteEspecialidad);
	}

	@Override
	public void delete(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad) {
		super.deleteReal(detalleAntecedenteEspecialidad);
	}

	@Override
	public DetalleAntecedenteEspecialidad registrar(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad) {
		try {
			beginTransaction();
			detalleAntecedenteEspecialidad = create(detalleAntecedenteEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"DetalleAntecedenteEspecialidad "
							+ detalleAntecedenteEspecialidad.toString());
			return detalleAntecedenteEspecialidad;
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
	public DetalleAntecedenteEspecialidad modificar(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad) {
		try {
			beginTransaction();
			detalleAntecedenteEspecialidad = update(detalleAntecedenteEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleAntecedenteEspecialidad "
							+ detalleAntecedenteEspecialidad.toString());
			return detalleAntecedenteEspecialidad;
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
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad) {
		try {
			detalleAntecedenteEspecialidad.setEstado("RM");
			beginTransaction();
			DetalleAntecedenteEspecialidad bar = update(detalleAntecedenteEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"DetalleAntecedenteEspecialidad "
							+ detalleAntecedenteEspecialidad.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao#
	 * obtenerDetalleAntecedenteEspecialidad(java.lang.Integer)
	 */
	@Override
	public DetalleAntecedenteEspecialidad obtenerDetalleAntecedenteEspecialidad(
			Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao#
	 * obtenerDetalleAntecedenteEspecialidadOrdenAscPorId()
	 */
	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerDetalleAntecedenteEspecialidadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao#
	 * obtenerDetalleAntecedenteEspecialidadOrdenDescPorId()
	 */
	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerDetalleAntecedenteEspecialidadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao#
	 * obtenerPorCompania (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerPorCompania(
			Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao#
	 * obtenerPorSucursal (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerPorSucursal(
			Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerPorHM(
			HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaMedica",
				historiaClinica.getId());
	}

	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerPorAntecedente(
			Antecedente antecedente) {
		return findAllActivosByParameter("antecedente", antecedente.getId());
	}

	@Override
	public List<DetalleAntecedenteEspecialidad> obtenerPorEspecialidad(
			Especialidad especialidad) {
		return findAllActivosByParameter("especialidad", especialidad.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao#onComplete
	 * (java. lang.String)
	 */
	@Override
	public List<DetalleAntecedenteEspecialidad> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
