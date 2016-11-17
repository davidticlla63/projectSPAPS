/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTransferencia;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Transferencia;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleTransferenciaDao extends
		DataAccessObjectJpa<DetalleTransferencia> implements
		IDetalleTransferenciaDao {

	/**
	 * @param typeT
	 */
	public DetalleTransferenciaDao() {
		super(DetalleTransferencia.class);
	}

	@Override
	public DetalleTransferencia create(DetalleTransferencia detalleTransferencia) {
		return super.create(detalleTransferencia);
	}

	@Override
	public DetalleTransferencia update(DetalleTransferencia detalleTransferencia) {
		return super.update(detalleTransferencia);
	}

	@Override
	public void delete(DetalleTransferencia detalleTransferencia) {
		super.deleteReal(detalleTransferencia);
	}

	@Override
	public DetalleTransferencia registrar(
			DetalleTransferencia detalleTransferencia) {
		try {
			beginTransaction();
			detalleTransferencia = create(detalleTransferencia);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleTransferencia "
					+ detalleTransferencia.toString());
			return detalleTransferencia;
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
	public DetalleTransferencia modificar(
			DetalleTransferencia detalleTransferencia) {
		try {
			beginTransaction();
			detalleTransferencia = update(detalleTransferencia);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleTransferencia " + detalleTransferencia.toString());
			return detalleTransferencia;
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
	public boolean eliminar(DetalleTransferencia detalleTransferencia) {
		try {
			detalleTransferencia.setEstado("RM");
			beginTransaction();
			DetalleTransferencia bar = update(detalleTransferencia);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"DetalleTransferencia " + detalleTransferencia.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleTransferencia> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao#
	 * obtenerDetalleTransferencia(java.lang.Integer)
	 */
	@Override
	public DetalleTransferencia obtenerDetalleTransferencia(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao#
	 * obtenerDetalleTransferenciaOrdenAscPorId()
	 */
	@Override
	public List<DetalleTransferencia> obtenerDetalleTransferenciaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao#
	 * obtenerDetalleTransferenciaOrdenDescPorId()
	 */
	@Override
	public List<DetalleTransferencia> obtenerDetalleTransferenciaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleTransferencia> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleTransferencia> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleTransferencia> obtenerPorHM(HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaMedica",
				historiaClinica.getId());
	}

	@Override
	public List<DetalleTransferencia> obtenerPorTransferencia(
			Transferencia transferencia) {
		return findAllActivosByParameter("transferencia", transferencia.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTransferenciaDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleTransferencia> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
