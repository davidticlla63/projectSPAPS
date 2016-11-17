/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleMotivoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMotivo;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Motivo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleMotivoDao extends DataAccessObjectJpa<DetalleMotivo>
		implements IDetalleMotivoDao {

	/**
	 * @param typeT
	 */
	public DetalleMotivoDao() {
		super(DetalleMotivo.class);
	}

	@Override
	public DetalleMotivo create(DetalleMotivo detalleMotivo) {
		return super.create(detalleMotivo);
	}

	@Override
	public DetalleMotivo update(DetalleMotivo detalleMotivo) {
		return super.update(detalleMotivo);
	}

	@Override
	public void delete(DetalleMotivo detalleMotivo) {
		super.deleteReal(detalleMotivo);
	}

	@Override
	public DetalleMotivo registrar(DetalleMotivo detalleMotivo) {
		try {
			beginTransaction();
			detalleMotivo = create(detalleMotivo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleMotivo "
					+ detalleMotivo.toString());
			return detalleMotivo;
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
	public DetalleMotivo modificar(DetalleMotivo detalleMotivo) {
		try {
			beginTransaction();
			detalleMotivo = update(detalleMotivo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DetalleMotivo "
					+ detalleMotivo.toString());
			return detalleMotivo;
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
	public boolean eliminar(DetalleMotivo detalleMotivo) {
		try {
			detalleMotivo.setEstado("RM");
			beginTransaction();
			DetalleMotivo bar = update(detalleMotivo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleMotivo "
					+ detalleMotivo.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleMotivoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleMotivo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMotivoDao#
	 * obtenerDetalleMotivo(java.lang.Integer)
	 */
	@Override
	public DetalleMotivo obtenerDetalleMotivo(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMotivoDao#
	 * obtenerDetalleMotivoOrdenAscPorId()
	 */
	@Override
	public List<DetalleMotivo> obtenerDetalleMotivoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMotivoDao#
	 * obtenerDetalleMotivoOrdenDescPorId()
	 */
	@Override
	public List<DetalleMotivo> obtenerDetalleMotivoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMotivoDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleMotivo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMotivoDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleMotivo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleMotivo> obtenerPorHM(HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("estudio", historiaClinica.getId());
	}

	@Override
	public List<DetalleMotivo> obtenerPorMotivo(Motivo motivo) {
		return findAllActivosByParameter("motivo", motivo.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMotivoDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleMotivo> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
