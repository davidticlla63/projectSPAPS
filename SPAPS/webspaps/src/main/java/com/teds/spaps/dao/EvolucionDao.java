/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IEvolucionDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Evolucion;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class EvolucionDao extends DataAccessObjectJpa<Evolucion> implements
		IEvolucionDao {

	/**
	 * @param typeT
	 */
	public EvolucionDao() {
		super(Evolucion.class);
	}

	@Override
	public Evolucion create(Evolucion evolucion) {
		return super.create(evolucion);
	}

	@Override
	public Evolucion update(Evolucion evolucion) {
		return super.update(evolucion);
	}

	@Override
	public Evolucion registrar(Evolucion evolucion) {
		try {
			beginTransaction();
			evolucion = create(evolucion);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Evolucion " + evolucion.toString());
			return evolucion;
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
	public Evolucion modificar(Evolucion evolucion) {
		try {
			beginTransaction();
			evolucion = update(evolucion);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Evolucion "
					+ evolucion.toString());
			return evolucion;
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
	public boolean eliminar(Evolucion evolucion) {
		try {
			evolucion.setEstado("RM");
			beginTransaction();
			Evolucion bar = update(evolucion);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Evolucion "
					+ evolucion.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IEvolucionDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Evolucion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEvolucionDao#obtenerEvolucion(java.lang
	 * .Integer)
	 */
	@Override
	public Evolucion obtenerEvolucion(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEvolucionDao#obtenerEvolucionOrdenAscPorId
	 * ()
	 */
	@Override
	public List<Evolucion> obtenerEvolucionOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEvolucionDao#obtenerEvolucionOrdenDescPorId
	 * ()
	 */
	@Override
	public List<Evolucion> obtenerEvolucionOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEvolucionDao#obtenerPorCompania(com.teds.
	 * spaps.model.Compania)
	 */
	@Override
	public List<Evolucion> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEvolucionDao#obtenerPorSucursal(com.teds.
	 * spaps.model.Sucursal)
	 */
	@Override
	public List<Evolucion> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public boolean verificarPorHM(HistoriaClinica historiaClinica,
			Compania compania) {
		return (Long) (findCountEvolucionHM("id", "historiaClinica",
				"compania", historiaClinica.getId(), compania.getId())) > 0;
	}

	@Override
	public List<Evolucion> obtenerPorHM(HistoriaClinica historiaClinica,
			Compania compania) {
		return findAllActiveByParameter("compania", compania.getId(),
				"historiaClinica", historiaClinica.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEvolucionDao#onComplete(java.lang.String)
	 */
	@Override
	public List<Evolucion> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
