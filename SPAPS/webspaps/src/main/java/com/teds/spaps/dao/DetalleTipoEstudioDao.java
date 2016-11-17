/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoEstudio;
import com.teds.spaps.model.Estudio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoEstudio;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleTipoEstudioDao extends
		DataAccessObjectJpa<DetalleTipoEstudio> implements
		IDetalleTipoEstudioDao {

	/**
	 * @param typeT
	 */
	public DetalleTipoEstudioDao() {
		super(DetalleTipoEstudio.class);
	}

	@Override
	public DetalleTipoEstudio create(DetalleTipoEstudio detalleTipoEstudio) {
		return super.create(detalleTipoEstudio);
	}

	@Override
	public DetalleTipoEstudio update(DetalleTipoEstudio detalleTipoEstudio) {
		return super.update(detalleTipoEstudio);
	}

	@Override
	public void delete(DetalleTipoEstudio detalleTipoEstudio) {
		super.deleteReal(detalleTipoEstudio);
	}

	@Override
	public DetalleTipoEstudio registrar(DetalleTipoEstudio detalleTipoEstudio) {
		try {
			beginTransaction();
			detalleTipoEstudio = create(detalleTipoEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleTipoEstudio "
					+ detalleTipoEstudio.toString());
			return detalleTipoEstudio;
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
	public DetalleTipoEstudio modificar(DetalleTipoEstudio detalleTipoEstudio) {
		try {
			beginTransaction();
			detalleTipoEstudio = update(detalleTipoEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleTipoEstudio " + detalleTipoEstudio.toString());
			return detalleTipoEstudio;
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
	public boolean eliminar(DetalleTipoEstudio detalleTipoEstudio) {
		try {
			detalleTipoEstudio.setEstado("RM");
			beginTransaction();
			DetalleTipoEstudio bar = update(detalleTipoEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleTipoEstudio "
					+ detalleTipoEstudio.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleTipoEstudio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao#
	 * obtenerDetalleTipoEstudio(java.lang.Integer)
	 */
	@Override
	public DetalleTipoEstudio obtenerDetalleTipoEstudio(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao#
	 * obtenerDetalleTipoEstudioOrdenAscPorId()
	 */
	@Override
	public List<DetalleTipoEstudio> obtenerDetalleTipoEstudioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao#
	 * obtenerDetalleTipoEstudioOrdenDescPorId()
	 */
	@Override
	public List<DetalleTipoEstudio> obtenerDetalleTipoEstudioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleTipoEstudio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleTipoEstudio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleTipoEstudio> obtenerPorEstudio(Estudio estudio) {
		return findAllActivosByParameter("estudio", estudio.getId());
	}

	@Override
	public List<DetalleTipoEstudio> obtenerPorTipoEstudio(
			TipoEstudio tipoEstudio) {
		return findAllActivosByParameter("tipoEstudio", tipoEstudio.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleTipoEstudio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
