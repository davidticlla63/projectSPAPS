/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoEstudioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoEstudio;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class TipoEstudioDao extends DataAccessObjectJpa<TipoEstudio> implements
		ITipoEstudioDao {

	/**
	 * @param typeT
	 */
	public TipoEstudioDao() {
		super(TipoEstudio.class);
	}

	@Override
	public TipoEstudio create(TipoEstudio tipoEstudio) {
		return super.create(tipoEstudio);
	}

	@Override
	public TipoEstudio update(TipoEstudio tipoEstudio) {
		return super.update(tipoEstudio);
	}

	@Override
	public TipoEstudio registrar(TipoEstudio tipoEstudio) {
		try {
			beginTransaction();
			tipoEstudio = create(tipoEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "TipoEstudio "
					+ tipoEstudio.toString());
			return tipoEstudio;
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
	public TipoEstudio modificar(TipoEstudio tipoEstudio) {
		try {
			beginTransaction();
			tipoEstudio = update(tipoEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "TipoEstudio "
					+ tipoEstudio.toString());
			return tipoEstudio;
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
	public boolean eliminar(TipoEstudio tipoEstudio) {
		try {
			tipoEstudio.setEstado("RM");
			beginTransaction();
			TipoEstudio bar = update(tipoEstudio);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "TipoEstudio "
					+ tipoEstudio.toString());
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
	 * @see com.teds.spaps.interfaces.dao.ITipoEstudioDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<TipoEstudio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoEstudioDao#obtenerTipoEstudio(java
	 * .lang.Integer)
	 */
	@Override
	public TipoEstudio obtenerTipoEstudio(Integer id) {
		return findById(id);
	}

	@Override
	public TipoEstudio obtenerTipoEstudio(String nombre) {
		return findByParameter("nombre", nombre);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoEstudioDao#obtenerTipoEstudioOrdenAscPorId
	 * ()
	 */
	@Override
	public List<TipoEstudio> obtenerTipoEstudioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoEstudioDao#
	 * obtenerTipoEstudioOrdenDescPorId()
	 */
	@Override
	public List<TipoEstudio> obtenerTipoEstudioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoEstudioDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<TipoEstudio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoEstudioDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<TipoEstudio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoEstudioDao#onComplete(java.lang.String
	 * )
	 */
	@Override
	public List<TipoEstudio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
