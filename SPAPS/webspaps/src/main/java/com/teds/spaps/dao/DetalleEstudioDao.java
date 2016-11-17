/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleEstudioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleEstudio;
import com.teds.spaps.model.Estudio;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleEstudioDao extends DataAccessObjectJpa<DetalleEstudio>
		implements IDetalleEstudioDao {

	/**
	 * @param typeT
	 */
	public DetalleEstudioDao() {
		super(DetalleEstudio.class);
	}

	@Override
	public DetalleEstudio create(DetalleEstudio detalleEstudio) {
		return super.create(detalleEstudio);
	}

	@Override
	public DetalleEstudio update(DetalleEstudio detalleEstudio) {
		return super.update(detalleEstudio);
	}

	@Override
	public void delete(DetalleEstudio detalleEstudio) {
		super.deleteReal(detalleEstudio);
	}

	@Override
	public DetalleEstudio registrar(DetalleEstudio detalleEstudio) {
		try {
			beginTransaction();
			detalleEstudio = create(detalleEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleEstudio "
					+ detalleEstudio.toString());
			return detalleEstudio;
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
	public DetalleEstudio modificar(DetalleEstudio detalleEstudio) {
		try {
			beginTransaction();
			detalleEstudio = update(detalleEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DetalleEstudio "
					+ detalleEstudio.toString());
			return detalleEstudio;
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
	public boolean eliminar(DetalleEstudio detalleEstudio) {
		try {
			detalleEstudio.setEstado("RM");
			beginTransaction();
			DetalleEstudio bar = update(detalleEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleEstudio "
					+ detalleEstudio.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleEstudioDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleEstudio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleEstudioDao#
	 * obtenerDetalleEstudio(java.lang.Integer)
	 */
	@Override
	public DetalleEstudio obtenerDetalleEstudio(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleEstudioDao#
	 * obtenerDetalleEstudioOrdenAscPorId()
	 */
	@Override
	public List<DetalleEstudio> obtenerDetalleEstudioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleEstudioDao#
	 * obtenerDetalleEstudioOrdenDescPorId()
	 */
	@Override
	public List<DetalleEstudio> obtenerDetalleEstudioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleEstudioDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleEstudio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleEstudioDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleEstudio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleEstudio> obtenerPorHM(HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaMedica",
				historiaClinica.getId());
	}

	@Override
	public List<DetalleEstudio> obtenerPorEstudio(Estudio estudio) {
		return findAllActivosByParameter("estudio", estudio.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleEstudioDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleEstudio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
